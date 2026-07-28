package PayrollBilling;

import Business.Network;
import Core.Person;
import Core.WorkOrders.TimecardWorkOrder;
import PayrollBilling.Record.BillingRecord;
import PayrollBilling.Record.Invoice;
import PayrollBilling.Record.PaymentRecord;
import PayrollBilling.Record.PayrollRecord;
import PayrollBilling.Report.PayrollReport;
import PayrollBilling.Request.BillingRequest;
import PayrollBilling.Request.ContractorPaymentRequest;
import PayrollBilling.Request.PayrollRequest;
import PayrollBilling.Role.BillingAnalystRole;
import PayrollBilling.Role.PayrollSpecialistRole;
import StaffingAgency.People.Contractor;
import StaffingAgency.Request.Contract;
import StaffingAgency.Request.ContractorAssignment;
import java.math.BigDecimal;
import java.time.LocalDate;

public class ConfigurePayrollBilling {

    public static PayrollBillingModule configurePayrollBillingModule() {

        PayrollBillingModule module = new PayrollBillingModule();

        Contractor contractor = new Contractor(
                "Jordan",
                "Rivera",
                "jordan.rivera@example.com",
                "555-123-4567",
                "Java, QA, DevOps",
                new BigDecimal("45.00")
        );

        ContractorAssignment assignment = new ContractorAssignment(
                contractor,
                LocalDate.now().minusDays(14)
        );

        new Contract(
                assignment,
                LocalDate.now().minusDays(14),
                LocalDate.now().plusMonths(3),
                new BigDecimal("45.00"),
                new BigDecimal("75.00")
        );

        TimecardWorkOrder timecard = new TimecardWorkOrder(
                LocalDate.now(),
                45.00
        );

        timecard.setDailyHours(new double[]{8.0, 8.0, 8.0, 8.0, 6.0, 0.0, 0.0});
        timecard.setWorkSummary("Completed assigned project work for the week.");

        PayrollRequest payrollRequest = new PayrollRequest(timecard, assignment);
        PayrollRecord payrollRecord = payrollRequest.processPayroll();

        PaymentRecord paymentRecord = new PaymentRecord(payrollRecord);

        ContractorPaymentRequest contractorPaymentRequest =
                new ContractorPaymentRequest(paymentRecord);

        BillingRequest billingRequest = new BillingRequest(timecard, assignment);
        Invoice invoice = billingRequest.processBilling();
        BillingRecord billingRecord = billingRequest.getBillingRecord();

        PayrollReport report = new PayrollReport();

        module.getPayrollRequests().add(payrollRequest);
        module.getPayrollRecords().add(payrollRecord);
        module.getPaymentRecords().add(paymentRecord);
        module.getContractorPaymentRequests().add(contractorPaymentRequest);

        module.getBillingRequests().add(billingRequest);
        module.getBillingRecords().add(billingRecord);
        module.getInvoices().add(invoice);

        report.generateSummary(
                module.getPayrollRecords(),
                module.getBillingRecords(),
                module.getInvoices(),
                module.getPayrollRequests()
        );

        module.getPayrollReports().add(report);

        return module;
    }

    public static PayrollBillingModule populatePayrollBillingData(Network network) {

        PayrollBillingModule module = configurePayrollBillingModule();

        PayrollBillingEnterprise payrollBillingEnterprise =
                new PayrollBillingEnterprise("PayrollBilling");

        network.getEnterpriseList().add(payrollBillingEnterprise);

        Person payrollPerson = new Person("Payroll Specialist");

        network.getUserAccountDirectory().createUserAccount(
                "p.specialist",
                "password",
                payrollPerson,
                new PayrollSpecialistRole()
        );

        Person billingPerson = new Person("Billing Analyst");

        network.getUserAccountDirectory().createUserAccount(
                "b.analyst",
                "password",
                billingPerson,
                new BillingAnalystRole()
        );

        return module;
    }
}