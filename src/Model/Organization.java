/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.List;

/**
 *
 * @author Alex
 */
public abstract class Organization {
    
    private String name;
    
    //private UserAccountDirectory userAccountDirectory;
    private WorkOrderQueue workOrderQueue;
    
    public Organization(String name) {
        this.name = name;
        //this.userAccountDirectory = new UserAccountDirectory();
        this.workOrderQueue = new WorkOrderQueue();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    //public UserAccountDirectory getUserAccountDirectory() {
    //    return userAccountDirectory;
    //}

    public WorkOrderQueue getWorkQueue() {
        return workOrderQueue;
    }

    public abstract List<Role> getSupportedRoles();
    
    @Override
    public String toString() {
        return name;
    }
}
