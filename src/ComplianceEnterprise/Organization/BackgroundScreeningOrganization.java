package ComplianceEnterprise.Organization;

import ComplianceEnterprise.Model.ComplianceData;
import ComplianceEnterprise.Role.ComplianceAnalystRole;
import Core.Organization;
import Core.Role;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles contractor background and identity verification.
 *
 * @author janet
 */
public class BackgroundScreeningOrganization extends Organization {

    private final ComplianceData complianceData;

    public BackgroundScreeningOrganization(ComplianceData complianceData) {
        super("Background Screening Organization");
        this.complianceData = complianceData;
    }

    @Override
    public List<Role> getSupportedRoles() {
        List<Role> roles = new ArrayList<>();
        roles.add(new ComplianceAnalystRole(complianceData));
        return roles;
    }
}
