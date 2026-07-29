package Core;

import Business.Network;
import UserInterface.Admin.EnterpriseAdminWorkAreaJPanel;
import javax.swing.JPanel;

/**
 * Opens the work area for one assigned enterprise.
 *
 * @author janet
 */
public class EnterpriseAdminRole extends Role {

    private final Enterprise enterprise;

    public EnterpriseAdminRole(Enterprise enterprise) {
        if (enterprise == null) {
            throw new IllegalArgumentException("Enterprise is required.");
        }
        this.enterprise = enterprise;
    }

    public Enterprise getEnterprise() {
        return enterprise;
    }

    @Override
    public JPanel createWorkArea(JPanel userProcessContainer,
            UserAccount account, Network network) {
        return new EnterpriseAdminWorkAreaJPanel(
                userProcessContainer, account, network, enterprise);
    }
}
