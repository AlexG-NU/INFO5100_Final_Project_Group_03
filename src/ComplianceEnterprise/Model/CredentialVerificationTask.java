package ComplianceEnterprise.Model;

import ComplianceEnterprise.Enums.CredentialStatus;
import ComplianceEnterprise.Role.CredentialSpecialist;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author janet
 */
public class CredentialVerificationTask {

    private static final AtomicInteger ID_SEQUENCE = new AtomicInteger(12000);

    private final int taskId;
    private final VerificationReview review;
    private CredentialRecord credential;
    private CredentialSpecialist completedBy;
    private CredentialStatus result;
    private String verificationNotes;
    private LocalDate completedDate;

    public CredentialVerificationTask(VerificationReview review,
            CredentialRecord credential) {
        if (review == null) {
            throw new IllegalArgumentException("Compliance review is required.");
        }
        this.taskId = ID_SEQUENCE.incrementAndGet();
        this.review = review;
        this.credential = credential;
        this.result = CredentialStatus.NOT_STARTED;
        this.verificationNotes = "";
    }

    public int getTaskId() {
        return taskId;
    }

    public VerificationReview getReview() {
        return review;
    }

    public CredentialRecord getCredential() {
        return credential;
    }

    public void attachCredential(CredentialRecord credential) {
        if (credential == null) {
            throw new IllegalArgumentException("Credential record is required.");
        }
        if (isComplete()) {
            throw new IllegalStateException(
                    "A completed task cannot be linked to another credential.");
        }
        this.credential = credential;
    }

    public CredentialSpecialist getCompletedBy() {
        return completedBy;
    }

    public CredentialStatus getResult() {
        return result;
    }

    public String getVerificationNotes() {
        return verificationNotes;
    }

    public LocalDate getCompletedDate() {
        return completedDate;
    }

    public boolean isComplete() {
        return result != CredentialStatus.NOT_STARTED
                && result != CredentialStatus.SUBMITTED;
    }

    public void completeTask(CredentialSpecialist specialist,
            CredentialStatus result, String notes) {
        if (specialist == null) {
            throw new IllegalArgumentException("Credential specialist is required.");
        }
        if (isComplete()) {
            throw new IllegalStateException("This credential verification task is already complete.");
        }
        if (result == null || result == CredentialStatus.NOT_STARTED
                || result == CredentialStatus.SUBMITTED) {
            throw new IllegalArgumentException("Select Verified, Missing, Expired, or Rejected.");
        }
        if (notes == null || notes.trim().length() < 10
                || notes.trim().length() > 500) {
            throw new IllegalArgumentException(
                    "Evidence notes must contain 10-500 characters and describe what was checked.");
        }
        if (result == CredentialStatus.VERIFIED && credential == null) {
            throw new IllegalStateException("A missing credential cannot be marked Verified.");
        }
        if (result == CredentialStatus.VERIFIED
                && credential.getExpirationDate().isBefore(LocalDate.now())) {
            throw new IllegalStateException("An expired credential cannot be marked Verified.");
        }

        this.completedBy = specialist;
        this.result = result;
        this.verificationNotes = notes.trim();
        this.completedDate = LocalDate.now();
        if (credential != null) {
            credential.setStatus(result);
        }
    }
}
