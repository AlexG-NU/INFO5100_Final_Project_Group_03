package ComplianceEnterprise.Model;

import ComplianceEnterprise.Enums.CredentialStatus;
import java.time.LocalDate;

/** A read-only record returned by the classroom simulated credential registry. */
public class RegistryCredentialRecord {

    private final String contractorName;
    private final String credentialType;
    private final String credentialNumber;
    private final String issuingOrganization;
    private final LocalDate expirationDate;
    private final CredentialStatus status;

    public RegistryCredentialRecord(String contractorName, String credentialType,
            String credentialNumber, String issuingOrganization,
            LocalDate expirationDate, CredentialStatus status) {
        this.contractorName = contractorName;
        this.credentialType = credentialType;
        this.credentialNumber = credentialNumber;
        this.issuingOrganization = issuingOrganization;
        this.expirationDate = expirationDate;
        this.status = status;
    }

    public String getContractorName() { return contractorName; }
    public String getCredentialType() { return credentialType; }
    public String getCredentialNumber() { return credentialNumber; }
    public String getIssuingOrganization() { return issuingOrganization; }
    public LocalDate getExpirationDate() { return expirationDate; }
    public CredentialStatus getStatus() { return status; }
}
