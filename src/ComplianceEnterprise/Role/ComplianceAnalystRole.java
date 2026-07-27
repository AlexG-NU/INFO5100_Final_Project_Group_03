package ComplianceEnterprise.Role;

import ComplianceEnterprise.Model.ComplianceData;
import Core.Enterprise;
import Core.Organization;
import Core.Role;
import Core.UserAccount;
import UserInterface.Compliance.ComplianceAnalystWorkAreaJPanel;
import javax.swing.JPanel;

/**
 *
 * @author janet
 */
public class ComplianceAnalystRole extends Role {

    private final ComplianceData complianceData;

    public ComplianceAnalystRole(ComplianceData complianceData) {
        this.complianceData = complianceData;
    }

    @Override
    public JPanel createWorkArea(JPanel userProcessContainer,
            UserAccount account, Organization organization,
            Enterprise enterprise) {
        return new ComplianceAnalystWorkAreaJPanel(
                userProcessContainer,
                complianceData.getComplianceDirectory(),
                complianceData.getAnalyst(),
                null);
    }
}
