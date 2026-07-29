package PayrollBilling;

import Business.ConfigureABusiness;
import Business.Network;
import Core.NetworkUtils;
import Core.Organization;
import Core.UserAccount;

public class PayrollBillingNetworkSmokeTest {

    public static void main(String[] args) {

        Network network = ConfigureABusiness.configure();

        UserAccount payrollAccount =
                network.getUserAccountDirectory().authenticateUser(
                        "p.specialist",
                        "password"
                );

        UserAccount billingAccount =
                network.getUserAccountDirectory().authenticateUser(
                        "b.analyst",
                        "password"
                );

        Organization payrollOrg =
                NetworkUtils.findOrganizationByName(
                        network,
                        "PayrollBilling",
                        "PayrollProcessing"
                );

        Organization billingOrg =
                NetworkUtils.findOrganizationByName(
                        network,
                        "PayrollBilling",
                        "ClientBilling"
                );

        System.out.println("Payroll account found: " + (payrollAccount != null));
        System.out.println("Billing account found: " + (billingAccount != null));
        System.out.println("Payroll org found: " + (payrollOrg != null));
        System.out.println("Billing org found: " + (billingOrg != null));
    }
}
