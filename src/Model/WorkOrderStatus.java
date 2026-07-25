/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Alex
 */
public enum WorkOrderStatus {
    
    PENDING("Pending", false),
    IN_PROGRESS("In Progress", false),
    UNDER_REVIEW("Under Review", false),
    APPROVED("Approved", false),
    REJECTED("Rejected", true),
    COMPLETED("Completed", true),
    CANCELLED("Cancelled", true);

    private final String displayName;
    private final boolean done;
    
    WorkOrderStatus(String displayName, boolean done) {
        this.displayName = displayName;
        this.done = done;
    }

    public String getDisplayName() {
        return displayName;
    }
    
    public boolean isDone() {
        return done;
    }
    
    @Override
    public String toString() {
        return displayName;
    }
    
}
