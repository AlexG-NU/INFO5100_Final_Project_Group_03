/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Core;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alex
 */
public abstract class Enterprise {
    
    private String name;
    private UserAccountDirectory userAccountDirectory;
    private PersonDirectory personDirectory;
    private OrganizationDirectory organizationDirectory;
    
    public Enterprise(String name) {
        this.name = name;
        this.userAccountDirectory = new UserAccountDirectory();
        this.personDirectory = new PersonDirectory();
        this.organizationDirectory = new OrganizationDirectory();
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public OrganizationDirectory getOrganizationDirectory() {
         return organizationDirectory;
    }
    
    public PersonDirectory getPersonDirectory() {
        return personDirectory;
    }

    public UserAccountDirectory getUserAccountDirectory() {
        return userAccountDirectory;
    }
    
    public abstract List<Role> getSupportedRoles();
    
    @Override
    public String toString() {
        return name;
    }
}
