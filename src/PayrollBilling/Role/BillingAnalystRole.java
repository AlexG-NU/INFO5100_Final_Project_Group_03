package PayrollBilling.Role;

import Core.Enterprise;
import Core.Organization;
import Core.Role;
import Core.UserAccount;
import UserInterface.WorkArea.WorkAreaTemplatePanel;
import javax.swing.JPanel;

public class BillingAnalystRole extends Role {

    @Override
    public JPanel createWorkArea(JPanel userProcessContainer,
                                 UserAccount account,
                                 Organization organization,
                                 Enterprise enterprise) {

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