/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Client;

import Client.Roles.ContractorRole;
import Client.Roles.HiringManagerRole;
import Client.Roles.ProjectSupervisorRole;
import Core.Enterprise;
import Core.Role;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alex
 */
public class ClientEnterprise extends Enterprise {
    
    public ClientEnterprise(String name) {
        super(name);
    }
    
    @Override
    public List<Role> getSupportedRoles() {
        List<Role> roles = new ArrayList<>();
        roles.add(new HiringManagerRole());
        roles.add(new ProjectSupervisorRole());
        roles.add(new ContractorRole());
        return roles;
    }
    
    
}
