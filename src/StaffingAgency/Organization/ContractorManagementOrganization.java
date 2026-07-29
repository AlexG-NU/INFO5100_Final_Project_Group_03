package StaffingAgency.Organization;

import Core.Organization;
import Core.Role;
import java.util.ArrayList;
import java.util.List;

public class ContractorManagementOrganization extends Organization {

    public ContractorManagementOrganization() {
        super("Contractor Management Organization");
    }

    @Override
    public List<Role> getSupportedRoles() {
        return new ArrayList<>();
    }
}
