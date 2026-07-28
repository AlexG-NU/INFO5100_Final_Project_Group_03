package PayrollBilling.Role;

import Business.Network;
import Core.Role;
import Core.UserAccount;
import UserInterface.PayrollBilling.BillingAnalystWorkAreaJPanel;
import javax.swing.JPanel;

public class BillingAnalystRole extends Role {

    @Override
    public JPanel createWorkArea(JPanel userProcessContainer,
                                 UserAccount account,
                                 Network network) {
        return new BillingAnalystWorkAreaJPanel(
                userProcessContainer,
                account,
                network
        );
    }
}