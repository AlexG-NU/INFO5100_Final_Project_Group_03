/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Core;

/**
 *
 * @author Alex
 */
public class UserAccount {
    private String username;
    private String password;
    private Role role;
    //private Organization organization;
    private Person person;
    private WorkOrderQueue workQueue;
    
    public UserAccount(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.person = person;
        this.workQueue = new WorkOrderQueue();
    }

    public WorkOrderQueue getWorkQueue() {
        //if (this.workQueue == null) {
        //    this.workQueue = new WorkOrderQueue();
        //}
        return workQueue;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    
    
    
    @Override
    public String toString() {
        return username;
    }
}
