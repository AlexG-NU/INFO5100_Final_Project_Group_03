/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Core.WorkOrders;

import Core.WorkOrder;
import java.time.LocalDate;

/**
 *
 * @author Alex
 */
public class StaffingReqWorkOrder extends WorkOrder{
    private String jobTitle;
    private int positionsNeeded;
    private double targetHourlyRate;
    private String requiredSkills;
    private LocalDate targetStartDate;
    
    public StaffingReqWorkOrder(String jobTitle, int positionsNeeded, double targetHourlyRate) {
        super();
        this.jobTitle = jobTitle;
        this.positionsNeeded = positionsNeeded;
        this.targetHourlyRate = targetHourlyRate;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public int getPositionsNeeded() {
        return positionsNeeded;
    }

    public void setPositionsNeeded(int positionsNeeded) {
        this.positionsNeeded = positionsNeeded;
    }

    public double getTargetHourlyRate() {
        return targetHourlyRate;
    }

    public void setTargetHourlyRate(double targetHourlyRate) {
        this.targetHourlyRate = targetHourlyRate;
    }

    public String getRequiredSkills() {
        return requiredSkills;
    }

    public void setRequiredSkills(String requiredSkills) {
        this.requiredSkills = requiredSkills;
    }

    public LocalDate getTargetStartDate() {
        return targetStartDate;
    }

    public void setTargetStartDate(LocalDate targetStartDate) {
        this.targetStartDate = targetStartDate;
    }
    
    
}
