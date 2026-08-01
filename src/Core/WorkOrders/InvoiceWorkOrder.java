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
public class InvoiceWorkOrder extends WorkOrder {
    private LocalDate dueDate = LocalDate.now().plusDays(30);
    private LocalDate billingStartDate = LocalDate.now().minusDays(14);
    private LocalDate billingEndDate = LocalDate.now();
    private double totalHours = 80.5;
    private double totalAmount = 6440.00;
    private int timeCardID = 5055;

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getBillingStartDate() {
        return billingStartDate;
    }

    public void setBillingStartDate(LocalDate billingStartDate) {
        this.billingStartDate = billingStartDate;
    }

    public LocalDate getBillingEndDate() {
        return billingEndDate;
    }

    public void setBillingEndDate(LocalDate billingEndDate) {
        this.billingEndDate = billingEndDate;
    }

    public double getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(double totalHours) {
        this.totalHours = totalHours;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getTimeCardID() {
        return timeCardID;
    }

    public void setTimeCardID(int timeCardID) {
        this.timeCardID = timeCardID;
    }



}
