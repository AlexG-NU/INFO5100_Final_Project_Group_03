package ComplianceEnterprise.Organization;

import ComplianceEnterprise.Model.ComplianceData;
import ComplianceEnterprise.Role.ComplianceManagerRole;
import ComplianceEnterprise.Role.CredentialSpecialistRole;
import Core.Organization;
import Core.Role;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles credential records, compliance decisions, and reporting.
 *
 * @author janet
 */
public class CredentialingComplianceOrganization extends Organization {

    private final ComplianceData complianceData;

    public CredentialingComplianceOrganization(
            ComplianceData complianceData) {
        super("Credentialing and Compliance Organization");
        this.complianceData = complianceData;
    }

    @Override
    public List<Role> getSupportedRoles() {
        List<Role> roles = new ArrayList<>();
        roles.add(new ComplianceManagerRole(complianceData));
        roles.add(new CredentialSpecialistRole(complianceData));
        return roles;
    }
}
