/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Client;

import Client.Roles.ContractorRole;
import Client.Roles.ProjectSupervisorRole;
import Core.Organization;
import Core.Role;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alex
 */
public class OperationsOrganization extends Organization{
    
    public OperationsOrganization(String name) {
        super(name);
    }
    
    @Override
    public List<Role> getSupportedRoles() {
        List<Role> roles = new ArrayList<>();
        roles.add(new ContractorRole());
        roles.add(new ProjectSupervisorRole());
        return roles;
    }
    
}
