package ComplianceEnterprise.Model;

import ComplianceEnterprise.Enums.ComplianceDecision;
import ComplianceEnterprise.Role.ComplianceAnalyst;
import StaffingAgency.Request.CredentialVerificationRequest;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author janet
 */
public class VerificationReview {

    private static final AtomicInteger ID_SEQUENCE = new AtomicInteger(11000);

    private final int reviewId;
    private final CredentialVerificationRequest request;
    private ComplianceAnalyst assignedAnalyst;
    private ComplianceDecision decision;
    private String findings;
    private String requiredCredentialType;
    private LocalDate reviewDate;
    private CredentialVerificationTask credentialTask;

    public VerificationReview(CredentialVerificationRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Verification request is required.");
        }
        this.reviewId = ID_SEQUENCE.incrementAndGet();
        this.request = request;
        this.decision = ComplianceDecision.PENDING;
        this.findings = "";
        this.requiredCredentialType = "";
    }

    public int getReviewId() {
        return reviewId;
    }

    public CredentialVerificationRequest getRequest() {
        return request;
    }

    public ComplianceAnalyst getAssignedAnalyst() {
        return assignedAnalyst;
    }

    public void assignAnalyst(ComplianceAnalyst assignedAnalyst) {
        if (assignedAnalyst == null) {
            throw new IllegalArgumentException("Compliance analyst is required.");
        }
        if (decision != ComplianceDecision.PENDING) {
            throw new IllegalStateException("A completed review cannot be reassigned.");
        }
        this.assignedAnalyst = assignedAnalyst;
    }

    public ComplianceDecision getDecision() {
        return decision;
    }

    public String getFindings() {
        return findings;
    }

    public String getRequiredCredentialType() {
        return requiredCredentialType;
    }

    public void selectRequiredCredential(String credentialType) {
        if (decision != ComplianceDecision.PENDING) {
            throw new IllegalStateException("A completed case cannot be changed.");
        }
        if (assignedAnalyst == null) {
            throw new IllegalStateException("Assign an analyst before selecting a credential.");
        }
        if (credentialTask != null) {
            throw new IllegalStateException("The required credential cannot be changed after the task is sent.");
        }
        if (credentialType == null || credentialType.trim().isEmpty()) {
            throw new IllegalArgumentException("Select the credential required for this assignment.");
        }
        this.requiredCredentialType = credentialType.trim();
    }

    public void recordAnalystAssessment(String assessment) {
        if (decision != ComplianceDecision.PENDING) {
            throw new IllegalStateException("A completed assessment cannot be changed.");
        }
        if (assignedAnalyst == null) {
            throw new IllegalStateException("Assign an analyst before recording an assessment.");
        }
        if (assessment == null || assessment.trim().length() < 10
                || assessment.trim().length() > 500) {
            throw new IllegalArgumentException(
                    "Analyst assessment must contain 10-500 characters.");
        }
        this.findings = assessment.trim();
    }

    public void recordSpecialistInstructions(String instructions) {
        if (decision != ComplianceDecision.PENDING) {
            throw new IllegalStateException("A completed case cannot be changed.");
        }
        if (assignedAnalyst == null) {
            throw new IllegalStateException("Assign an analyst before sending instructions.");
        }
        if (instructions == null || instructions.trim().isEmpty()) {
            this.findings = "";
            return;
        }
        if (instructions.trim().length() > 500) {
            throw new IllegalArgumentException(
                    "Instructions cannot exceed 500 characters.");
        }
        this.findings = instructions.trim();
    }

    public void closeForCredentialIssue() {
        if (decision != ComplianceDecision.PENDING || credentialTask == null
                || !credentialTask.isComplete()) {
            return;
        }
        if (credentialTask.getResult()
                == ComplianceEnterprise.Enums.CredentialStatus.VERIFIED) {
            return;
        }
        if (credentialTask.getResult()
                == ComplianceEnterprise.Enums.CredentialStatus.RECORD_NOT_FOUND) {
            return;
        }
        this.decision = ComplianceDecision.REJECTED;
        this.reviewDate = LocalDate.now();
        this.findings = credentialTask.getResult() + ": "
                + credentialTask.getVerificationNotes();
        request.rejectRequest();
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    public CredentialVerificationTask getCredentialTask() {
        return credentialTask;
    }

    public void setCredentialTask(CredentialVerificationTask credentialTask) {
        if (credentialTask == null) {
            throw new IllegalArgumentException("Credential verification task is required.");
        }
        if (this.credentialTask != null && !this.credentialTask.isComplete()) {
            throw new IllegalStateException("Credential verification was already requested.");
        }
        this.credentialTask = credentialTask;
    }

    public String getCredentialStatusText() {
        return credentialTask == null
                ? "Not Requested" : credentialTask.getResult().toString();
    }

    public String getWorkflowStatus() {
        if (credentialTask != null && !credentialTask.isComplete()) {
            return "Credential Verification Requested";
        }
        if (decision == ComplianceDecision.APPROVED) return "Compliance Approved";
        if (decision == ComplianceDecision.REJECTED) return "Compliance Rejected";
        if (assignedAnalyst == null) return "Awaiting Analyst Assignment";
        if (requiredCredentialType.isEmpty()) return "Credential Selection Required";
        if (credentialTask == null) return "Analyst Review";
        if (credentialTask.getResult()
                == ComplianceEnterprise.Enums.CredentialStatus.RECORD_NOT_FOUND) {
            return "Record Not Found - Manual Review Required";
        }
        return credentialTask.getResult()
                == ComplianceEnterprise.Enums.CredentialStatus.VERIFIED
                ? "Credential Verified - Final Decision Required"
                : "Credential Issue - Analyst Follow-up Required";
    }

    public String getWaitingOn() {
        if (credentialTask != null && !credentialTask.isComplete()) {
            return "Credential Specialist";
        }
        if (decision == ComplianceDecision.APPROVED) return "Contractor Coordinator";
        if (decision == ComplianceDecision.REJECTED) return "No Further Action";
        if (assignedAnalyst == null) return "Compliance Manager";
        return "Compliance Analyst";
    }

    public String getRequestedByText() {
        return credentialTask == null || assignedAnalyst == null
                ? "" : assignedAnalyst.getName();
    }

    public String getCompletedByText() {
        return credentialTask == null || credentialTask.getCompletedBy() == null
                ? "" : credentialTask.getCompletedBy().getName();
    }

    public void completeReview(ComplianceDecision decision, String findings) {
        if (this.decision != ComplianceDecision.PENDING) {
            throw new IllegalStateException("This verification review is already complete.");
        }
        if (assignedAnalyst == null) {
            throw new IllegalStateException("Assign an analyst before completing the review.");
        }
        if (decision == null || decision == ComplianceDecision.PENDING) {
            throw new IllegalArgumentException("Select a review decision.");
        }
        if (findings == null || findings.trim().isEmpty()) {
            throw new IllegalArgumentException("Enter the verification findings.");
        }
        if (findings.trim().length() < 10 || findings.trim().length() > 500) {
            throw new IllegalArgumentException("Findings must contain 10-500 characters.");
        }
        if (credentialTask == null) {
            throw new IllegalStateException(
                    "Send the credential task to the Credential Specialist before making a final decision.");
        }
        if (!credentialTask.isComplete()) {
            throw new IllegalStateException(
                    "Wait for the Credential Specialist to return a result before making a final decision.");
        }
        if (decision == ComplianceDecision.APPROVED) {
            ComplianceEnterprise.Enums.CredentialStatus credentialResult =
                    credentialTask.getResult();
            if (credentialResult
                    != ComplianceEnterprise.Enums.CredentialStatus.VERIFIED
                    && credentialResult
                    != ComplianceEnterprise.Enums.CredentialStatus.RECORD_NOT_FOUND) {
                throw new IllegalStateException(
                        "The required credential must be Verified before final approval.");
            }
        }
        recordAnalystAssessment(findings);
        this.decision = decision;
        this.reviewDate = LocalDate.now();

        if (decision == ComplianceDecision.APPROVED) {
            request.approveRequest();
            request.applyVerification();
        } else if (decision == ComplianceDecision.REJECTED) {
            request.rejectRequest();
        }
    }
}
