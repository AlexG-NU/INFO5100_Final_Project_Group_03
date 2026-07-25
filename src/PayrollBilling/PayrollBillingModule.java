/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
*/
package PayrollBilling;

import PayrollBilling.Record.BillingRecord;
import PayrollBilling.Record.Invoice;
import PayrollBilling.Record.PaymentRecord;
import PayrollBilling.Record.PayrollRecord;
import PayrollBilling.Request.BillingRequest;
import PayrollBilling.Request.ContractorPaymentRequest;
import PayrollBilling.Request.PayrollRequest;
import java.util.ArrayList;

public class PayrollBillingModule {

    private String moduleName;

    private ArrayList<PayrollRequest> payrollRequests;
    private ArrayList<BillingRequest> billingRequests;
    private ArrayList<ContractorPaymentRequest> contractorPaymentRequests;

    private ArrayList<PayrollRecord> payrollRecords;
    private ArrayList<PaymentRecord> paymentRecords;
    private ArrayList<BillingRecord> billingRecords;
    private ArrayList<Invoice> invoices;

    public PayrollBillingModule() {
        this.moduleName = "Payroll and Billing Module";

        payrollRequests = new ArrayList<>();
        billingRequests = new ArrayList<>();
        contractorPaymentRequests = new ArrayList<>();

        payrollRecords = new ArrayList<>();
        paymentRecords = new ArrayList<>();
        billingRecords = new ArrayList<>();
        invoices = new ArrayList<>();
    }

    public String getModuleName() {
        return moduleName;
    }

    public ArrayList<PayrollRequest> getPayrollRequests() {
        return payrollRequests;
    }

    public ArrayList<BillingRequest> getBillingRequests() {
        return billingRequests;
    }

    public ArrayList<ContractorPaymentRequest> getContractorPaymentRequests() {
        return contractorPaymentRequests;
    }

    public ArrayList<PayrollRecord> getPayrollRecords() {
        return payrollRecords;
    }

    public ArrayList<PaymentRecord> getPaymentRecords() {
        return paymentRecords;
    }

    public ArrayList<BillingRecord> getBillingRecords() {
        return billingRecords;
    }

    public ArrayList<Invoice> getInvoices() {
        return invoices;
    }
}
