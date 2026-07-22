package ComplianceEnterprise.Model;

import ComplianceEnterprise.Enums.CredentialStatus;
import StaffingAgency.People.Contractor;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author janet
 */
public class CredentialRecord {

    private static final AtomicInteger ID_SEQUENCE = new AtomicInteger(10000);

    private final int credentialId;
    private final Contractor contractor;
    private String credentialType;
    private String documentNumber;
    private LocalDate expirationDate;
    private CredentialStatus status;

    public CredentialRecord(Contractor contractor, String credentialType,
            String documentNumber, LocalDate expirationDate) {
        if (contractor == null) {
            throw new IllegalArgumentException("Contractor is required.");
        }
        this.credentialId = ID_SEQUENCE.incrementAndGet();
        this.contractor = contractor;
        setCredentialType(credentialType);
        setDocumentNumber(documentNumber);
        setExpirationDate(expirationDate);
        this.status = CredentialStatus.SUBMITTED;
    }

    public int getCredentialId() {
        return credentialId;
    }

    public Contractor getContractor() {
        return contractor;
    }

    public String getCredentialType() {
        return credentialType;
    }

    public void setCredentialType(String credentialType) {
        if (credentialType == null || credentialType.trim().isEmpty()) {
            throw new IllegalArgumentException("Credential type is required.");
        }
        if (!credentialType.trim().matches("[A-Za-z0-9 &'()/-]{3,60}")) {
            throw new IllegalArgumentException("Credential type contains invalid characters.");
        }
        this.credentialType = credentialType.trim();
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        if (documentNumber == null || documentNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Document number is required.");
        }
        String cleanedNumber = documentNumber.trim().toUpperCase();
        if (!cleanedNumber.matches("[A-Z0-9-]{4,30}")) {
            throw new IllegalArgumentException("Document number must be 4-30 letters, numbers, or hyphens.");
        }
        this.documentNumber = cleanedNumber;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        if (expirationDate == null) {
            throw new IllegalArgumentException("Expiration date is required.");
        }
        this.expirationDate = expirationDate;
    }

    public CredentialStatus getStatus() {
        if (expirationDate.isBefore(LocalDate.now()) && status == CredentialStatus.VERIFIED) {
            status = CredentialStatus.EXPIRED;
        }
        return status;
    }

    public void setStatus(CredentialStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Credential status is required.");
        }
        this.status = status;
    }
}
