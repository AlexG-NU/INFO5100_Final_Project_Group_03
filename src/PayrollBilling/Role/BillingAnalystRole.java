package PayrollBilling.Role;

import Business.Network;
import Core.Role;
import Core.UserAccount;
import UserInterface.WorkArea.WorkAreaTemplatePanel;
import javax.swing.JPanel;

public class BillingAnalystRole extends Role {

    @Override
    public JPanel createWorkArea(JPanel userProcessContainer,
                                 UserAccount account,
                                 Network network) {

        WorkAreaTemplatePanel panel = new WorkAreaTemplatePanel();

        panel.setWorkAreaHeader(
                "Billing Analyst Work Area",
                "Payroll & Billing Enterprise - Client Billing Organization"
        );

        panel.setButtonLabels(
                "View Billing Requests",
                "Generate Invoice",
                "Manage Billing Records",
                "Billing Report"
        );

        return panel;
    }
}