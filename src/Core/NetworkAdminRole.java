package Core;

import Business.Network;
import UserInterface.Admin.NetworkAdminWorkAreaJPanel;
import javax.swing.JPanel;

/**
 * Opens the work area used to manage the complete network.
 *
 * @author janet
 */
public class NetworkAdminRole extends Role {

    @Override
    public JPanel createWorkArea(JPanel userProcessContainer,
            UserAccount account, Network network) {
        return new NetworkAdminWorkAreaJPanel(
                userProcessContainer, account, network);
    }
}
