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
public class UserAccountDirectory {
    
    private final List<UserAccount> userAccountList;

    public UserAccountDirectory() {
        this.userAccountList = new ArrayList<>();
    }

    public List<UserAccount> getUserAccountList() {
        return userAccountList;
    }
    
    public UserAccount authenticateUser(String username, String password) {
        for (UserAccount ua : userAccountList) {
            if (ua.getUsername().equalsIgnoreCase(username)
                    && ua.getPassword().equals(password)) {
                return ua;
            }
        }
        return null;
    }
    
    public UserAccount createUserAccount(String username, String password, Person person, Role role) {
        // Enforce uniqueness before creation
        if (!uniqueUsername(username)) {
            return null;
        }
        
        UserAccount userAccount = new UserAccount(username, password, role);
        userAccount.setPerson(person);
        userAccountList.add(userAccount);
        return userAccount;
    }
    
    public boolean uniqueUsername(String username) {
        for (UserAccount ua : userAccountList) {
            if (ua.getUsername().equalsIgnoreCase(username)) {
                return false;
            }
        }
        return true;
    }
    
}
