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
        if (username == null
                || !username.trim().matches("[A-Za-z0-9._-]{2,30}")) {
            throw new IllegalArgumentException(
                    "Username must be 2-30 letters, numbers, periods, "
                    + "underscores, or hyphens.");
        }
        PasswordPolicy.validate(password);
        if (person == null || role == null) {
            throw new IllegalArgumentException(
                    "A person and role are required for every account.");
        }
        // Enforce uniqueness before creation
        String cleanedUsername = username.trim();
        if (!uniqueUsername(cleanedUsername)) {
            return null;
        }
        
        UserAccount userAccount =
                new UserAccount(cleanedUsername, password, role);
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
