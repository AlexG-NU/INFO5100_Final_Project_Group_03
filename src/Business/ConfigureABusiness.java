/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business;


import Core.Person;
import Core.UserAccount;
import Core.WorkOrderStatus;
import Core.WorkOrders.TaskWorkOrder;
import WorkOrders.StaffingRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alex
 */
public class ConfigureABusiness {
    
    public static Network configure() {
        Network network = new Network();
        

        ConfigureAClient.populateClientData(network);
        return network;
    }
    
    
    
}
