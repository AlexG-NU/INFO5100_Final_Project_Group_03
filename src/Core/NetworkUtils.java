/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Core;

import Business.Network;

/**
 *
 * @author Alex
 */
public class NetworkUtils {
    
    public static Organization findOrganizationByName(Network network, String enterpriseName, String orgName) {
        if (network == null || network.getEnterpriseList() == null) {
            return null;
        }

        for (Enterprise enterprise : network.getEnterpriseList()) {
            if (enterprise.getName().equalsIgnoreCase(enterpriseName)) {
                for (Organization org : enterprise.getOrganizationDirectory().getOrganizationList()) {
                    if (org.getName().equalsIgnoreCase(orgName) || org.getName().contains(orgName)) {
                        return org;
                    }
                }
            }
        }
        return null;
    }
    
    public static Enterprise findEnterpriseByName(Network network, String enterpriseName) {
        if (network == null || network.getEnterpriseList() == null) {
            return null;
        }

        for (Enterprise enterprise : network.getEnterpriseList()) {
            if (enterprise.getName().equalsIgnoreCase(enterpriseName)) {
                return enterprise;
            }
        }
        return null;
    }
    
}
