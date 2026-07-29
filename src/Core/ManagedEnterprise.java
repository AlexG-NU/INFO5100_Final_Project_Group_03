package Core;

import java.util.ArrayList;
import java.util.List;

/**
 * Basic enterprise record that can be created by the Network Administrator.
 *
 * @author janet
 */
public class ManagedEnterprise extends Enterprise {

    public ManagedEnterprise(String name) {
        super(name);
    }

    @Override
    public List<Role> getSupportedRoles() {
        return new ArrayList<>();
    }
}
