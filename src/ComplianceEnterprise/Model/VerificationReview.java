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
        if (this.assignedAnalyst != null && this.assignedAnalyst != assignedAnalyst) {
            throw new IllegalStateException("This request is already assigned to another analyst.");
        }
        this.assignedAnalyst = assignedAnalyst;
    }

    public ComplianceDecision getDecision() {
        return decision;
    }

    public String getFindings() {
        return findings;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
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
        this.decision = decision;
        this.findings = findings.trim();
        this.reviewDate = LocalDate.now();

        if (decision == ComplianceDecision.APPROVED) {
            request.approveRequest();
            request.applyVerification();
        } else if (decision == ComplianceDecision.REJECTED) {
            request.rejectRequest();
        }
    }
}
