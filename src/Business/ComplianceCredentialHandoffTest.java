package Business;

import ComplianceEnterprise.Enums.ComplianceDecision;
import ComplianceEnterprise.Enums.CredentialStatus;
import ComplianceEnterprise.Model.ComplianceData;
import ComplianceEnterprise.Model.ComplianceDataGenerator;
import ComplianceEnterprise.Model.CredentialVerificationTask;
import ComplianceEnterprise.Model.VerificationReview;

/**
 *
 * @author janet
 */
public class ComplianceCredentialHandoffTest {

    public static void main(String[] args) {
        ComplianceData data = ComplianceDataGenerator.generate();
        VerificationReview pendingReview = null;

        for (VerificationReview review
                : data.getComplianceDirectory().getReviewList()) {
            if (review.getDecision() == ComplianceDecision.PENDING
                    && review.getCredentialTask() != null) {
                pendingReview = review;
                break;
            }
        }
        require(pendingReview != null,
                "Expected an assigned review with a credential task.");

        boolean approvalBlocked = false;
        try {
            pendingReview.completeReview(ComplianceDecision.APPROVED,
                    "Background review is complete and acceptable.");
        } catch (IllegalStateException ex) {
            approvalBlocked = ex.getMessage().contains("must be Verified");
        }
        require(approvalBlocked,
                "Approval must be blocked while the credential check is pending.");

        CredentialVerificationTask task = pendingReview.getCredentialTask();
        task.completeTask(data.getSpecialist(), CredentialStatus.VERIFIED,
                "Document number and expiration date were confirmed.");
        pendingReview.completeReview(ComplianceDecision.APPROVED,
                "Background and credential requirements were confirmed.");

        require(pendingReview.getDecision() == ComplianceDecision.APPROVED,
                "Verified credential should allow final approval.");
        require(task.getCompletedBy() == data.getSpecialist(),
                "Credential task must record the specialist.");
        require(!task.getVerificationNotes().isEmpty(),
                "Credential task must retain evidence notes.");
        require("Background and credential requirements were confirmed."
                .equals(pendingReview.getFindings()),
                "Completing a review must save the analyst assessment.");

        boolean completedAssessmentLocked = false;
        try {
            pendingReview.recordAnalystAssessment(
                    "This edit should not be allowed after completion.");
        } catch (IllegalStateException ex) {
            completedAssessmentLocked =
                    ex.getMessage().contains("cannot be changed");
        }
        require(completedAssessmentLocked,
                "Completed analyst assessments must remain read-only.");

        System.out.println("COMPLIANCE CREDENTIAL HANDOFF TEST PASSED");
        System.out.println("Analyst request: " + pendingReview.getReviewId());
        System.out.println("Credential task: " + task.getTaskId());
        System.out.println("Credential result: " + task.getResult());
        System.out.println("Final review: " + pendingReview.getDecision());
    }

    private static void require(boolean condition, String message) {
        if (!condition) {
            throw new IllegalStateException(message);
        }
    }
}
