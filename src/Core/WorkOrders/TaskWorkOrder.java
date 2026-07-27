/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Core.WorkOrders;

import Core.WorkOrder;

/**
 *
 * @author Alex
 */
public class TaskWorkOrder extends WorkOrder{
    
    public enum TaskPriority {
        LOW("Low"),
        MEDIUM("Medium"),
        HIGH("High"),
        URGENT("Urgent");

        private final String displayName;

        TaskPriority(String displayName) {
            this.displayName = displayName;
        }

        @Override
        public String toString() {
            return displayName;
        }
    }
    
    private String taskName;
    private TaskPriority priority;
    
    public TaskWorkOrder() {
        super();
        this.priority = TaskPriority.MEDIUM;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.getWorkOrderId());
    }
    
}
