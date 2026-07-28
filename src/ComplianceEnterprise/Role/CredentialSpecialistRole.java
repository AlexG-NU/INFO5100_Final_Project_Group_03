package ComplianceEnterprise.Role;

import ComplianceEnterprise.Model.ComplianceData;
import Business.Network;
import Core.Role;
import Core.UserAccount;
import UserInterface.Compliance.CredentialSpecialistWorkAreaJPanel;
import javax.swing.JPanel;

/**
 *
 * @author janet
 */
public class CredentialSpecialistRole extends Role {

    private final ComplianceData complianceData;

    public CredentialSpecialistRole(ComplianceData complianceData) {
        this.complianceData = complianceData;
    }

    @Override
    public JPanel createWorkArea(JPanel userProcessContainer,
            UserAccount account, Network network) {
        return new CredentialSpecialistWorkAreaJPanel(
                userProcessContainer,
                complianceData.getComplianceDirectory(),
                complianceData.getSpecialist(),
                complianceData.getContractorList(),
                null);
    }
}
