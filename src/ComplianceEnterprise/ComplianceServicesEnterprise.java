package ComplianceEnterprise;

import ComplianceEnterprise.Model.ComplianceData;
import ComplianceEnterprise.Organization.BackgroundScreeningOrganization;
import ComplianceEnterprise.Organization.CredentialingComplianceOrganization;
import ComplianceEnterprise.Role.ComplianceAnalystRole;
import ComplianceEnterprise.Role.ComplianceManagerRole;
import ComplianceEnterprise.Role.CredentialSpecialistRole;
import Core.Enterprise;
import Core.Role;
import java.util.ArrayList;
import java.util.List;

/**
 * Enterprise responsible for background screening and credential clearance.
 *
 * @author janet
 */
public class ComplianceServicesEnterprise extends Enterprise {

    private final ComplianceData complianceData;

    public ComplianceServicesEnterprise(
            String name, ComplianceData complianceData) {
        super(name);
        this.complianceData = complianceData;

        getOrganizationDirectory().addOrganization(
                new BackgroundScreeningOrganization(complianceData));
        getOrganizationDirectory().addOrganization(
                new CredentialingComplianceOrganization(complianceData));
    }

    @Override
    public List<Role> getSupportedRoles() {
        List<Role> roles = new ArrayList<>();
        roles.add(new ComplianceManagerRole(complianceData));
        roles.add(new ComplianceAnalystRole(complianceData));
        roles.add(new CredentialSpecialistRole(complianceData));
        return roles;
    }
}
