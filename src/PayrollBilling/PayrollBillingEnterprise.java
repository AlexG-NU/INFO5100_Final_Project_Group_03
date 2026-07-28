package PayrollBilling;

import Core.Enterprise;
import Core.Role;
import PayrollBilling.Organization.ClientBillingOrganization;
import PayrollBilling.Organization.PayrollProcessingOrganization;
import PayrollBilling.Role.BillingAnalystRole;
import PayrollBilling.Role.PayrollSpecialistRole;
import java.util.ArrayList;
import java.util.List;

public class PayrollBillingEnterprise extends Enterprise {

    public PayrollBillingEnterprise(String name) {
        super(name);

        getOrganizationDirectory().addOrganization(
                new PayrollProcessingOrganization()
        );

        getOrganizationDirectory().addOrganization(
                new ClientBillingOrganization()
        );
    }

    @Override
    public List<Role> getSupportedRoles() {
        List<Role> roles = new ArrayList<>();
        roles.add(new PayrollSpecialistRole());
        roles.add(new BillingAnalystRole());
        return roles;
    }
}