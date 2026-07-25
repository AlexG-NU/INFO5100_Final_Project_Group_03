/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alex
 */
public class OrganizationDirectory {
    
    private final List<Organization> organizationList;

    public OrganizationDirectory() {
        this.organizationList = new ArrayList<>();
    }
    
    public List<Organization> getOrganizationList() {
        return organizationList;
    }
    
    public void addOrganization(Organization organization) {
        if (organization != null) {
            organizationList.add(organization);
        }
    }
    
    public boolean removeOrganization(Organization org) {
        return organizationList.remove(org);
    }
}
