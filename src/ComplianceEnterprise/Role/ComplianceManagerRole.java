package ComplianceEnterprise.Role;

import ComplianceEnterprise.Model.ComplianceData;
import Business.Network;
import Core.Role;
import Core.UserAccount;
import UserInterface.Compliance.ComplianceManagerWorkAreaJPanel;
import javax.swing.JPanel;

/**
 *
 * @author janet
 */
public class ComplianceManagerRole extends Role {

    private final ComplianceData complianceData;

    public ComplianceManagerRole(ComplianceData complianceData) {
        this.complianceData = complianceData;
    }

    @Override
    public JPanel createWorkArea(JPanel userProcessContainer,
            UserAccount account, Network network) {
        return new ComplianceManagerWorkAreaJPanel(
                userProcessContainer,
                complianceData, network,
                null);
    }
}
