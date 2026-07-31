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
        if (credentialTask == null) return "Analyst Review";
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
        if (decision == ComplianceDecision.APPROVED) {
            if (credentialTask == null) {
                throw new IllegalStateException(
                        "Request credential verification before approving this review.");
            }
            if (credentialTask.getResult()
                    != ComplianceEnterprise.Enums.CredentialStatus.VERIFIED) {
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
