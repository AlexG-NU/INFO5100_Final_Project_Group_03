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
public class TimecardWorkOrder extends WorkOrder{
    
    private LocalDate weekEndingDate;
    private double[] dailyHours;
    private String workSummary;
    
    public TimecardWorkOrder(LocalDate weekEndingDate, double hourlyPayRate) {
        super(); 
        this.weekEndingDate = weekEndingDate;
        this.dailyHours = new double[7]; 
    }
    
    public double getTotalHours() {
        double total = 0.0;
        for (double hours : dailyHours) {
            total += hours;
        }
        return total;
    }

    public LocalDate getWeekEndingDate() {
        return weekEndingDate;
    }

    public void setWeekEndingDate(LocalDate weekEndingDate) {
        this.weekEndingDate = weekEndingDate;
    }

    public double[] getDailyHours() {
        return dailyHours;
    }

    public void setDailyHours(double[] dailyHours) {
        if (dailyHours != null && dailyHours.length == 7) {
            this.dailyHours = dailyHours;
        } else {
            throw new IllegalArgumentException("Timecard must have exactly 7 days.");
        }
    }

    public String getWorkSummary() {
        return workSummary;
    }

    public void setWorkSummary(String workSummary) {
        this.workSummary = workSummary;
    }
    
    
    
}
