/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Client.Roles;

import Business.Network;
import Core.Enterprise;
import Core.Organization;
import Core.Role;
import Core.UserAccount;
import javax.swing.JPanel;

/**
 *
 * @author Alex
 */
public class ProjectSupervisorRole extends Role{
    
    @Override
    public JPanel createWorkArea(JPanel userProcessContainer, UserAccount account, Network network) {
        return new JPanel(); //new HiringManagerWorkAreaJPanel(userProcessContainer, organization, enterprise);
    }

    
}
