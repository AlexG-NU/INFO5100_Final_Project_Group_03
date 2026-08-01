package PayrollBilling;

import Business.Network;
import Core.NetworkUtils;
import Core.Organization;
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
import com.github.javafaker.Faker;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Random;

public class ConfigurePayrollBilling {
    
    private static PayrollBillingModule sharedPayrollBillingModule;

    public static PayrollBillingModule getSharedPayrollBillingModule() {
        if (sharedPayrollBillingModule == null) {
            sharedPayrollBillingModule = configurePayrollBillingModule();
        }
        return sharedPayrollBillingModule;
    }
    
    public static PayrollBillingModule configurePayrollBillingModule() {

        PayrollBillingModule module = new PayrollBillingModule();
        Faker faker = new Faker(new Random(5104));

        for (int index = 0; index < 8; index++) {
            BigDecimal payRate =
                    BigDecimal.valueOf(35 + (index * 3));
            BigDecimal billRate =
                    payRate.add(BigDecimal.valueOf(25));

            Contractor contractor = new Contractor(
                    faker.name().firstName(),
                    faker.name().lastName(),
                    faker.internet().emailAddress(),
                    faker.phoneNumber().cellPhone(),
                    faker.job().keySkills(),
                    payRate
            );

            ContractorAssignment assignment = new ContractorAssignment(
                    contractor,
                    LocalDate.now().minusDays(14 + index)
            );

            new Contract(
                    assignment,
                    LocalDate.now().minusDays(14 + index),
                    LocalDate.now().plusMonths(3 + (index % 3)),
                    payRate,
                    billRate
            );

            TimecardWorkOrder timecard = new TimecardWorkOrder(
                    LocalDate.now().minusWeeks(index), payRate.doubleValue()
            );
            timecard.setDailyHours(new double[]{
                8.0, 8.0, 8.0, 8.0,
                4.0 + (index % 5), 0.0, 0.0
            });
            timecard.setWorkSummary("Completed "
                    + faker.job().field().toLowerCase()
                    + " work for " + faker.company().name() + ".");

            PayrollRequest payrollRequest =
                    new PayrollRequest(timecard, assignment);
            PayrollRecord payrollRecord = payrollRequest.processPayroll();
            PaymentRecord paymentRecord =
                    new PaymentRecord(payrollRecord);
            ContractorPaymentRequest contractorPaymentRequest =
                    new ContractorPaymentRequest(paymentRecord);

            BillingRequest billingRequest =
                    new BillingRequest(timecard, assignment);
            Invoice invoice = billingRequest.processBilling();
            BillingRecord billingRecord =
                    billingRequest.getBillingRecord();

            module.getPayrollRequests().add(payrollRequest);
            module.getPayrollRecords().add(payrollRecord);
            module.getPaymentRecords().add(paymentRecord);
            module.getContractorPaymentRequests()
                    .add(contractorPaymentRequest);
            module.getBillingRequests().add(billingRequest);
            module.getBillingRecords().add(billingRecord);
            module.getInvoices().add(invoice);
        }

        PayrollReport report = new PayrollReport();
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

        PayrollBillingModule module = getSharedPayrollBillingModule();

        PayrollBillingEnterprise payrollBillingEnterprise =
                new PayrollBillingEnterprise("Payroll and Billing Enterprise");

        network.addEnterprise(payrollBillingEnterprise);

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

        
        Organization hrOrg = NetworkUtils.findOrganizationByName(
                network,
                "Client Enterprise",
                "Human Resources Organization"
        );

        if (hrOrg != null) {
            for (BillingRequest billingRequest : module.getBillingRequests()) {
                if (billingRequest.getInvoice() != null
                        && !hrOrg.getWorkQueue().getWorkOrderList().contains(billingRequest)) {
                    hrOrg.getWorkQueue().addWorkOrder(billingRequest);
                }
            }
        }

        return module;
    }
}
