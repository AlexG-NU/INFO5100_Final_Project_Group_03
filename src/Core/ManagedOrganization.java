package Core;

import java.util.ArrayList;
import java.util.List;

/**
 * Basic organization record that can be created by an Enterprise Administrator.
 *
 * @author janet
 */
public class ManagedOrganization extends Organization {

    public ManagedOrganization(String name) {
        super(name);
    }

    @Override
    public List<Role> getSupportedRoles() {
        return new ArrayList<>();
    }
}
