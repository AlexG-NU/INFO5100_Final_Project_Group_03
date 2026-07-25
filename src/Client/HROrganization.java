/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Client;

import Client.Roles.HiringManagerRole;
import Core.Organization;
import Core.Role;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alex
 */
public class HROrganization extends Organization{
    
    public HROrganization(String name) {
        super(name);
    }
    
    @Override
    public List<Role> getSupportedRoles() {
        List<Role> roles = new ArrayList<>();
        roles.add(new HiringManagerRole());
        return roles;
    }
    
}
