package PayrollBilling.Organization;

import Core.Organization;
import Core.Role;
import PayrollBilling.Role.BillingAnalystRole;
import java.util.ArrayList;
import java.util.List;

public class ClientBillingOrganization extends Organization {

    public ClientBillingOrganization() {
        super("ClientBilling");
    }

    public void generateClientInvoice() {
        System.out.println("Generating client invoice...");
    }

    @Override
    public List<Role> getSupportedRoles() {
        List<Role> roles = new ArrayList<>();
        roles.add(new BillingAnalystRole());
        return roles;
    }
}