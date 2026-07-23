/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PayrollBilling.Request;

import PayrollBilling.Record.PayrollRecord;
import StaffingAgency.Enums.RequestStatus;
import StaffingAgency.Request.ContractorAssignment;
import java.util.concurrent.atomic.AtomicInteger;

public class PayrollRequest {

    private static final AtomicInteger ID_SEQUENCE = new AtomicInteger(8000);

    private int requestId;
    private RequestStatus status;
    private ContractorAssignment assignment;
    private int hoursWorked;
    private PayrollRecord payrollRecord;

    public PayrollRequest(ContractorAssignment assignment, int hoursWorked) {
        this.requestId = ID_SEQUENCE.incrementAndGet();
        this.assignment = assignment;
        this.hoursWorked = hoursWorked;
        this.status = RequestStatus.SUBMITTED;
    }

    public PayrollRecord processPayroll() {
        if (assignment == null) {
            throw new IllegalArgumentException("Assignment is required to process payroll.");
        }

        if (hoursWorked <= 0) {
            throw new IllegalArgumentException("Hours worked must be greater than zero.");
        }

        this.payrollRecord = new PayrollRecord(assignment, hoursWorked);
        this.status = RequestStatus.COMPLETED;
        return payrollRecord;
    }

    public int getRequestId() {
        return requestId;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public ContractorAssignment getAssignment() {
        return assignment;
    }

    public int getHoursWorked() {
        return hoursWorked;
    }

    public PayrollRecord getPayrollRecord() {
        return payrollRecord;
    }
}
