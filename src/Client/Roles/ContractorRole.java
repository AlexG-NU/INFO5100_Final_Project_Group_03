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
import UserInterface.Client.ContractorJPanel;
import javax.swing.JPanel;

/**
 *
 * @author Alex
 */
public class ContractorRole extends Role{
    
    @Override
    public JPanel createWorkArea(JPanel userProcessContainer, UserAccount account, Network network) {
        return new ContractorJPanel(userProcessContainer, account, network);
    }

    
}
