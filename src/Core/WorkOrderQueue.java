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
public class WorkOrderQueue {
    
    private final List<WorkOrder> workOrderList;

    public WorkOrderQueue() {
        this.workOrderList = new ArrayList<>();
    }

    public List<WorkOrder> getWorkOrderList() {
        return workOrderList;
    }

    public void addWorkOrder(WorkOrder workOrder) {
        workOrderList.add(workOrder);
    }
    
}
