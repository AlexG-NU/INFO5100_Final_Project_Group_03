package PayrollBilling.Role;

import Core.Enterprise;
import Core.Organization;
import Core.Role;
import Core.UserAccount;
import UserInterface.WorkArea.WorkAreaTemplatePanel;
import javax.swing.JPanel;

public class PayrollSpecialistRole extends Role {

    @Override
    public JPanel createWorkArea(JPanel userProcessContainer,
                                 UserAccount account,
                                 Organization organization,
                                 Enterprise enterprise) {

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