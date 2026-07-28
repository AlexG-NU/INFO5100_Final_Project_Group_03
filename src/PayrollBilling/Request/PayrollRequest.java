package PayrollBilling.Request;

import Core.WorkOrder;
import Core.WorkOrderStatus;
import Core.WorkOrders.TimecardWorkOrder;
import PayrollBilling.Record.PayrollRecord;
import StaffingAgency.Request.ContractorAssignment;
import java.time.LocalDate;

public class PayrollRequest extends WorkOrder {

    private TimecardWorkOrder timecard;
    private ContractorAssignment assignment;
    private PayrollRecord payrollRecord;

    public PayrollRequest(TimecardWorkOrder timecard, ContractorAssignment assignment) {
        super();
        this.timecard = timecard;
        this.assignment = assignment;
        setStatus(WorkOrderStatus.PENDING);
    }

    // Backward-compatible constructor in case older code still passes raw hours.
    public PayrollRequest(ContractorAssignment assignment, double hoursWorked) {
        super();
        this.assignment = assignment;
        setStatus(WorkOrderStatus.PENDING);

        TimecardWorkOrder tempTimecard = new TimecardWorkOrder(LocalDate.now(), 0.0);
        tempTimecard.setDailyHours(new double[]{hoursWorked, 0, 0, 0, 0, 0, 0});
        this.timecard = tempTimecard;
    }

    public PayrollRecord processPayroll() {
        validateReadyToProcess();

        this.payrollRecord = new PayrollRecord(assignment, timecard.getTotalHours());
        setStatus(WorkOrderStatus.COMPLETED);
        return payrollRecord;
    }

    private void validateReadyToProcess() {
        if (timecard == null) {
            throw new IllegalArgumentException("Timecard is required to process payroll.");
        }

        if (assignment == null) {
            throw new IllegalArgumentException("Contractor assignment is required to process payroll.");
        }

        if (timecard.getTotalHours() <= 0) {
            throw new IllegalArgumentException("Timecard must have more than zero hours.");
        }
    }

    public TimecardWorkOrder getTimecard() {
        return timecard;
    }

    public ContractorAssignment getAssignment() {
        return assignment;
    }

    public double getHoursWorked() {
        return timecard == null ? 0.0 : timecard.getTotalHours();
    }

    public PayrollRecord getPayrollRecord() {
        return payrollRecord;
    }
}