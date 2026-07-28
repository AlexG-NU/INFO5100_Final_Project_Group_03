package PayrollBilling.Request;

import Core.WorkOrder;
import Core.WorkOrderStatus;
import Core.WorkOrders.TimecardWorkOrder;
import PayrollBilling.Record.BillingRecord;
import PayrollBilling.Record.Invoice;
import StaffingAgency.Request.ContractorAssignment;

public class BillingRequest extends WorkOrder {

    private TimecardWorkOrder timecard;
    private ContractorAssignment assignment;
    private BillingRecord billingRecord;
    private Invoice invoice;

    public BillingRequest(TimecardWorkOrder timecard, ContractorAssignment assignment) {
        super();
        this.timecard = timecard;
        this.assignment = assignment;
        setStatus(WorkOrderStatus.PENDING);
    }

    public Invoice processBilling() {
        validateReadyToProcess();

        this.billingRecord = new BillingRecord(assignment, timecard.getTotalHours());
        this.invoice = new Invoice(billingRecord, timecard.getWorkOrderId());
        this.invoice.generateInvoice();

        setStatus(WorkOrderStatus.COMPLETED);
        return invoice;
    }

    private void validateReadyToProcess() {
        if (timecard == null) {
            throw new IllegalArgumentException("Timecard is required to process billing.");
        }

        if (assignment == null) {
            throw new IllegalArgumentException("Contractor assignment is required to process billing.");
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

    public double getHoursBilled() {
        return timecard == null ? 0.0 : timecard.getTotalHours();
    }

    public BillingRecord getBillingRecord() {
        return billingRecord;
    }

    public Invoice getInvoice() {
        return invoice;
    }
}