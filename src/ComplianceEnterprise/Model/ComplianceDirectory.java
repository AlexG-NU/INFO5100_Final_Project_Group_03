package ComplianceEnterprise.Model;

import ComplianceEnterprise.Role.ComplianceAnalyst;
import ComplianceEnterprise.Role.ComplianceUser;
import StaffingAgency.Request.CredentialVerificationRequest;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author janet
 */
public class ComplianceDirectory {

    private final ArrayList<ComplianceUser> userList;
    private final ArrayList<CredentialRecord> credentialList;
    private final ArrayList<VerificationReview> reviewList;
    private final ArrayList<CredentialVerificationTask> credentialTaskList;

    public ComplianceDirectory() {
        userList = new ArrayList<>();
        credentialList = new ArrayList<>();
        reviewList = new ArrayList<>();
        credentialTaskList = new ArrayList<>();
    }

    public List<ComplianceUser> getUserList() {
        return userList;
    }

    public List<CredentialRecord> getCredentialList() {
        return credentialList;
    }

    public List<VerificationReview> getReviewList() {
        return reviewList;
    }

    public List<CredentialVerificationTask> getCredentialTaskList() {
        return credentialTaskList;
    }

    public void addUser(ComplianceUser user) {
        if (user == null) {
            throw new IllegalArgumentException("Compliance user is required.");
        }
        for (ComplianceUser existingUser : userList) {
            if (existingUser.getUsername().equalsIgnoreCase(user.getUsername())) {
                throw new IllegalArgumentException("Username already exists.");
            }
        }
        userList.add(user);
    }

    public void addCredential(CredentialRecord credential) {
        if (credential == null) {
            throw new IllegalArgumentException("Credential record is required.");
        }
        for (CredentialRecord existing : credentialList) {
            if (existing.getDocumentNumber().equalsIgnoreCase(credential.getDocumentNumber())) {
                throw new IllegalArgumentException("This document number already exists.");
            }
            if (existing.getContractor() == credential.getContractor()
                    && existing.getCredentialType().equalsIgnoreCase(credential.getCredentialType())) {
                throw new IllegalArgumentException("This contractor already has a record for that credential type.");
            }
        }
        credentialList.add(credential);
        for (CredentialVerificationTask task : credentialTaskList) {
            if (task.getCredential() == null && !task.isComplete()
                    && task.getReview().getRequest().getAssignment().getContractor()
                    == credential.getContractor()
                    && task.getReview().getRequest().getVerificationType()
                            .equalsIgnoreCase(credential.getCredentialType())) {
                task.attachCredential(credential);
            }
        }
    }

    public VerificationReview addVerificationRequest(CredentialVerificationRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Verification request is required.");
        }
        for (VerificationReview existing : reviewList) {
            if (existing.getRequest() == request
                    || existing.getRequest().getVerificationRequestId()
                    == request.getVerificationRequestId()) {
                throw new IllegalArgumentException("This verification request is already in the compliance queue.");
            }
        }
        VerificationReview review = new VerificationReview(request);
        reviewList.add(review);
        return review;
    }

    public boolean documentNumberExists(String documentNumber, CredentialRecord recordBeingEdited) {
        if (documentNumber == null) {
            return false;
        }
        for (CredentialRecord existing : credentialList) {
            if (existing != recordBeingEdited
                    && existing.getDocumentNumber().equalsIgnoreCase(documentNumber.trim())) {
                return true;
            }
        }
        return false;
    }

    public void removeCredential(CredentialRecord credential) {
        if (credential == null || !credentialList.remove(credential)) {
            throw new IllegalArgumentException("Credential record was not found.");
        }
    }

    public List<VerificationReview> getReviewsForAnalyst(ComplianceAnalyst analyst) {
        ArrayList<VerificationReview> assignedReviews = new ArrayList<>();
        for (VerificationReview review : reviewList) {
            if (review.getAssignedAnalyst() == analyst) {
                assignedReviews.add(review);
            }
        }
        return assignedReviews;
    }

    public CredentialVerificationTask requestCredentialVerification(
            VerificationReview review) {
        if (review == null) {
            throw new IllegalArgumentException("Select a compliance review.");
        }
        if (review.getAssignedAnalyst() == null) {
            throw new IllegalStateException(
                    "The review must be assigned to an analyst first.");
        }
        if (review.getCredentialTask() != null) {
            throw new IllegalStateException(
                    "Credential verification was already requested.");
        }

        CredentialRecord matchingCredential = null;
        for (CredentialRecord credential : credentialList) {
            if (credential.getContractor()
                    == review.getRequest().getAssignment().getContractor()
                    && credential.getCredentialType().equalsIgnoreCase(
                            review.getRequest().getVerificationType())) {
                matchingCredential = credential;
                break;
            }
        }

        CredentialVerificationTask task =
                new CredentialVerificationTask(review, matchingCredential);
        credentialTaskList.add(task);
        review.setCredentialTask(task);
        return task;
    }

    public CredentialVerificationTask requestRenewalVerification(
            CredentialRecord credential) {
        if (credential == null) {
            throw new IllegalArgumentException("Select a credential to renew.");
        }
        VerificationReview matchingReview = null;
        for (VerificationReview review : reviewList) {
            if (review.getRequest().getAssignment().getContractor()
                    == credential.getContractor()
                    && review.getRequest().getVerificationType()
                            .equalsIgnoreCase(credential.getCredentialType())
                    && review.getCredentialTask() != null
                    && review.getCredentialTask().isComplete()) {
                matchingReview = review;
            }
        }
        if (matchingReview == null) {
            return null;
        }
        CredentialVerificationTask task =
                new CredentialVerificationTask(matchingReview, credential);
        credentialTaskList.add(task);
        matchingReview.setCredentialTask(task);
        return task;
    }
}
