/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Core;

import javax.swing.JPanel;

/**
 *
 * @author Alex
 */
public abstract class Role {
    
    public abstract JPanel createWorkArea(JPanel userProcessContainer, 
                                          UserAccount account,  
                                          Organization organization, 
                                          Enterprise enterprise 
                                          /* Network network */);
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
