package PayrollBilling.Role;

import Business.Network;
import Core.Role;
import Core.UserAccount;
import UserInterface.PayrollBilling.PayrollSpecialistWorkAreaJPanel;
import javax.swing.JPanel;

public class PayrollSpecialistRole extends Role {

    @Override
    public JPanel createWorkArea(JPanel userProcessContainer,
                                 UserAccount account,
                                 Network network) {
        return new PayrollSpecialistWorkAreaJPanel(
                userProcessContainer,
                account,
                network
        );
    }
}