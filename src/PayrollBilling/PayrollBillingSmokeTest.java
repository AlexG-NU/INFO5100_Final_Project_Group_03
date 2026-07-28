package PayrollBilling;

import PayrollBilling.Report.PayrollReport;

public class PayrollBillingSmokeTest {

    public static void main(String[] args) {
        PayrollBillingModule module =
                ConfigurePayrollBilling.configurePayrollBillingModule();

        System.out.println("Payroll requests: " + module.getPayrollRequests().size());
        System.out.println("Billing requests: " + module.getBillingRequests().size());
        System.out.println("Contractor payment requests: " + module.getContractorPaymentRequests().size());
        System.out.println("Payroll records: " + module.getPayrollRecords().size());
        System.out.println("Payment records: " + module.getPaymentRecords().size());
        System.out.println("Billing records: " + module.getBillingRecords().size());
        System.out.println("Invoices: " + module.getInvoices().size());
        System.out.println("Reports: " + module.getPayrollReports().size());

        if (!module.getPayrollReports().isEmpty()) {
            PayrollReport report = module.getPayrollReports().get(0);

            System.out.println("Total contractor hours: " + report.getTotalContractorHours());
            System.out.println("Total payroll amount: " + report.getTotalPayrollAmount());
            System.out.println("Total billing amount: " + report.getTotalBillingAmount());
            System.out.println("Invoices generated: " + report.getInvoicesGenerated());
        }
    }
}