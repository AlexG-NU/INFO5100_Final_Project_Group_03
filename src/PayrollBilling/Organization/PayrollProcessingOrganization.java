package PayrollBilling.Organization;

import Core.Organization;
import Core.Role;
import PayrollBilling.Role.PayrollSpecialistRole;
import java.util.ArrayList;
import java.util.List;

public class PayrollProcessingOrganization extends Organization {

    public PayrollProcessingOrganization() {
        super("PayrollProcessing");
    }

    public void processApprovedTimecards() {
        System.out.println("Processing approved timecards...");
    }

    @Override
    public List<Role> getSupportedRoles() {
        List<Role> roles = new ArrayList<>();
        roles.add(new PayrollSpecialistRole());
        return roles;
    }
}