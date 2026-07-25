/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PayrollBilling.Record;

import PayrollBilling.Enums.PaymentStatus;
import StaffingAgency.Request.Contract;
import StaffingAgency.Request.ContractorAssignment;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

public class PayrollRecord {

    private static final AtomicInteger ID_SEQUENCE = new AtomicInteger(7000);

    private int payrollId;
    private ContractorAssignment assignment;
    private int hoursWorked;
    private BigDecimal payRate;
    private BigDecimal totalAmount;
    private LocalDate processedDate;
    private PaymentStatus paymentStatus;

    public PayrollRecord(ContractorAssignment assignment, int hoursWorked) {
        this.payrollId = ID_SEQUENCE.incrementAndGet();
        this.assignment = assignment;
        this.hoursWorked = hoursWorked;
        this.paymentStatus = PaymentStatus.PENDING;
        this.processedDate = LocalDate.now();

        if (assignment != null && assignment.getContract() != null) {
            Contract contract = assignment.getContract();
            this.payRate = contract.getPayRate();
            this.totalAmount = calculatePay();
        } else {
            this.payRate = BigDecimal.ZERO;
            this.totalAmount = BigDecimal.ZERO;
        }
    }

    public BigDecimal calculatePay() {
        return payRate.multiply(BigDecimal.valueOf(hoursWorked));
    }

    public void markAsPaid() {
        this.paymentStatus = PaymentStatus.PAID;
    }

    public int getPayrollId() {
        return payrollId;
    }

    public ContractorAssignment getAssignment() {
        return assignment;
    }

    public int getHoursWorked() {
        return hoursWorked;
    }

    public BigDecimal getPayRate() {
        return payRate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public LocalDate getProcessedDate() {
        return processedDate;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }
}
