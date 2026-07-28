package PayrollBilling.Role;

import Business.Network;
import Core.Role;
import Core.UserAccount;
import UserInterface.WorkArea.WorkAreaTemplatePanel;
import javax.swing.JPanel;

public class PayrollSpecialistRole extends Role {

    @Override
    public JPanel createWorkArea(JPanel userProcessContainer,
                                 UserAccount account,
                                 Network network) {

        WorkAreaTemplatePanel panel = new WorkAreaTemplatePanel();

        panel.setWorkAreaHeader(
                "Payroll Specialist Work Area",
                "Payroll & Billing Enterprise - Payroll Processing Organization"
        );

        panel.setButtonLabels(
                "View Payroll Requests",
                "Process Payroll",
                "Manage Payments",
                "Payroll Report"
        );

        return panel;
    }
}