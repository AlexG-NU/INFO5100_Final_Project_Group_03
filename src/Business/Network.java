/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business;

import Core.Enterprise;
import Core.UserAccountDirectory;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alex
 */
public class Network {
    
    private List<Enterprise> enterpriseList;
    private UserAccountDirectory userAccountDirectory;
    
    public Network() {
        this.enterpriseList = new ArrayList<>();
        this.userAccountDirectory = new UserAccountDirectory();
        
        
    }
    
    public List<Enterprise> getEnterpriseList() {
        return enterpriseList;
    }

    public UserAccountDirectory getUserAccountDirectory() {
        return userAccountDirectory;
    }
    
}
