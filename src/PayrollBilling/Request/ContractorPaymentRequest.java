package PayrollBilling.Request;

import Core.WorkOrder;
import Core.WorkOrderStatus;
import PayrollBilling.Enums.PaymentStatus;
import PayrollBilling.Record.PaymentRecord;

public class ContractorPaymentRequest extends WorkOrder {

    private PaymentRecord paymentRecord;
    private PaymentStatus paymentStatus;

    public ContractorPaymentRequest(PaymentRecord paymentRecord) {
        super();
        this.paymentRecord = paymentRecord;
        this.paymentStatus = PaymentStatus.PENDING;
        setStatus(WorkOrderStatus.PENDING);
    }

    public void confirmPayment() {
        if (paymentRecord == null) {
            throw new IllegalArgumentException("Payment record is required to confirm payment.");
        }

        paymentRecord.processPayment();
        this.paymentStatus = paymentRecord.getPaymentStatus();
        setStatus(WorkOrderStatus.COMPLETED);
    }

    public PaymentRecord getPaymentRecord() {
        return paymentRecord;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }
}