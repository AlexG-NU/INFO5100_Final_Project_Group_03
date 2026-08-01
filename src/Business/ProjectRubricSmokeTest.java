package Business;

import ComplianceEnterprise.ComplianceIntegrationService;
import ComplianceEnterprise.Enums.ComplianceDecision;
import ComplianceEnterprise.Model.VerificationReview;
import ComplianceEnterprise.Model.CredentialRecord;
import ComplianceEnterprise.Model.CredentialVerificationTask;
import ComplianceEnterprise.Enums.CredentialStatus;
import ComplianceEnterprise.Role.ComplianceAnalyst;
import ComplianceEnterprise.Role.ComplianceAnalystRole;
import Core.Enterprise;
import Core.EnterpriseAdminRole;
import Core.NetworkAdminRole;
import Core.Person;
import Core.Role;
import Core.UserAccount;
import Core.WorkOrder;
import Core.WorkOrders.CrossEnterpriseWorkOrder;
import StaffingAgency.People.Candidate;
import StaffingAgency.Enums.AssignmentStatus;
import StaffingAgency.Request.CandidateSubmission;
import PayrollBilling.ConfigurePayrollBilling;
import PayrollBilling.PayrollBillingModule;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.time.LocalDate;
import java.awt.Component;
import java.awt.Container;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Configuration checks used before the final project demonstration.
 *
 * @author janet
 */
public class ProjectRubricSmokeTest {

    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "true");

        List<Candidate> candidates =
                ConfigureABusiness.populateCandidates();
        List<CandidateSubmission> submissions = new ArrayList<>();
        Network network = ConfigureABusiness.configure(
                ConfigureABusiness.populateStaffingRequests(),
                candidates, submissions);

        require(network.getEnterpriseList().size() == 4,
                "Expected 4 enterprises.");

        int organizationCount = 0;
        for (Enterprise enterprise : network.getEnterpriseList()) {
            organizationCount += enterprise.getOrganizationDirectory()
                    .getOrganizationList().size();
        }
        require(organizationCount == 8, "Expected 8 organizations.");

        Set<Class<?>> roleTypes = new HashSet<>();
        int workAreaCount = 0;
        JPanel container = new JPanel();
        for (UserAccount account : network.getUserAccountDirectory()
                .getUserAccountList()) {
            Role role = account.getRole();
            if (!(role instanceof NetworkAdminRole)
                    && !(role instanceof EnterpriseAdminRole)) {
                roleTypes.add(role.getClass());
                JPanel workArea = role.createWorkArea(
                        container, account, network);
                require(workArea != null,
                        "Every role must open a work area.");
                verifyEnabledButtons(workArea);
                workAreaCount++;
            }
        }
        require(roleTypes.size() == 10,
                "Expected 10 configured non-admin roles.");
        require(workAreaCount >= 10,
                "Expected a configured account for every operational role.");

        List<WorkOrder> requests =
                network.getWorkOrderQueue().getWorkOrderList();
        require(requests.size() >= 8,
                "Expected at least 8 network work requests.");
        for (WorkOrder order : requests) {
            require(order instanceof CrossEnterpriseWorkOrder,
                    "Network requests must record cross-enterprise routing.");
            CrossEnterpriseWorkOrder request =
                    (CrossEnterpriseWorkOrder) order;
            require(!request.getSourceEnterprise().equalsIgnoreCase(
                    request.getDestinationEnterprise()),
                    "Every network request must cross enterprises.");
            require(order.getSender() != null && order.getReceiver() != null,
                    "Every request must have sender and receiver accounts.");
            require(order.getStatus() != null,
                    "Every request must have a status.");
        }

        require(network.getComplianceData() != null
                && network.getComplianceData().getContractorList().size() >= 12,
                "Expected Java Faker compliance demo records.");
        require(candidates.size() >= 16,
                "Expected Java Faker Staffing candidates.");

        UserAccount hrAccount = network.getUserAccountDirectory()
                .authenticateUser("HR", "password");
        UserAccount contractorAccount = network.getUserAccountDirectory()
                .authenticateUser("Contractor", "password");
        require(hrAccount != null
                && hrAccount.getWorkQueue().getWorkOrderList().size() >= 4,
                "Expected Java Faker Client staffing requests.");
        require(contractorAccount != null
                && contractorAccount.getWorkQueue().getWorkOrderList().size() >= 10,
                "Expected Java Faker Client tasks and timecards.");

        PayrollBillingModule payrollModule =
                ConfigurePayrollBilling.getSharedPayrollBillingModule();
        require(payrollModule.getPayrollRecords().size() >= 8,
                "Expected Java Faker Payroll records.");
        require(payrollModule.getInvoices().size() >= 8,
                "Expected Java Faker Billing invoices.");

        require(!submissions.isEmpty()
                && submissions.get(0).getResultingAssignment() != null,
                "Expected a shared Staffing assignment.");
        VerificationReview review =
                ComplianceIntegrationService.submitForVerification(
                        network,
                        submissions.get(0).getResultingAssignment(),
                        "Final Integration Test",
                        "Confirm the shared assignment can be cleared.");
        review.assignAnalyst(network.getComplianceData().getAnalyst());
        CredentialRecord testCredential = new CredentialRecord(
                submissions.get(0).getResultingAssignment().getContractor(),
                "Final Integration Test", "FINAL-5100",
                LocalDate.now().plusYears(1));
        network.getComplianceData().getComplianceDirectory()
                .addCredential(testCredential);
        CredentialVerificationTask credentialTask =
                network.getComplianceData().getComplianceDirectory()
                        .requestCredentialVerification(review);
        credentialTask.completeTask(
                network.getComplianceData().getSpecialist(),
                CredentialStatus.VERIFIED,
                "Document number and expiration date were confirmed.");
        review.completeReview(
                ComplianceDecision.APPROVED,
                "All background and credential requirements were verified.");
        require(submissions.get(0).getResultingAssignment()
                .getStatus() == AssignmentStatus.CLEARED,
                "Compliance must clear the same Staffing assignment.");

        require(network.getUserAccountDirectory()
                .authenticateUser("network.admin", "password") != null,
                "Network Admin authentication failed.");
        require(network.getUserAccountDirectory()
                .authenticateUser("C.analyst", "password") != null,
                "Role-based authentication failed.");
        verifyAllDemoAccounts(network);
        verifyMultipleComplianceAnalysts(network);

        boolean shortPasswordRejected = false;
        try {
            network.getUserAccountDirectory().createUserAccount(
                    "short.password.user", "short",
                    new Person("Short Password User"),
                    roleTypes.isEmpty() ? null
                            : network.getUserAccountDirectory()
                                    .getUserAccountList().get(0).getRole());
        } catch (IllegalArgumentException ex) {
            shortPasswordRejected = true;
        }
        require(shortPasswordRejected,
                "Passwords shorter than eight characters must be rejected.");

        System.out.println("PROJECT RUBRIC SMOKE TEST PASSED");
        System.out.println("Enterprises: 4");
        System.out.println("Organizations: 8");
        System.out.println("Non-admin roles: " + roleTypes.size());
        System.out.println("Role work areas opened: " + workAreaCount);
        System.out.println("Cross-enterprise requests: " + requests.size());
        System.out.println("Faker compliance contractors: "
                + network.getComplianceData().getContractorList().size());
        System.out.println("Faker staffing candidates: "
                + candidates.size());
        System.out.println("Faker payroll records: "
                + payrollModule.getPayrollRecords().size());
        System.out.println("Faker billing invoices: "
                + payrollModule.getInvoices().size());
        System.out.println("Demo accounts authenticated: 15");
    }

    private static void verifyMultipleComplianceAnalysts(Network network) {
        UserAccount secondAccount = new UserAccount(
                "C.analyst2", "password",
                new ComplianceAnalystRole(network.getComplianceData()));
        secondAccount.setPerson(new Person("Jordan Kim"));

        ComplianceAnalyst firstAnalyst =
                network.getComplianceData().getAnalyst();
        ComplianceAnalyst secondAnalyst =
                network.getComplianceData()
                        .getAnalystForAccount(secondAccount);
        require(firstAnalyst != secondAnalyst,
                "Each Compliance Analyst account must use "
                + "a separate analyst profile.");

        VerificationReview pendingReview = null;
        for (VerificationReview review : network.getComplianceData()
                .getComplianceDirectory().getReviewList()) {
            if (review.getDecision() == ComplianceDecision.PENDING) {
                pendingReview = review;
                break;
            }
        }
        require(pendingReview != null,
                "A pending Compliance request is required.");
        pendingReview.assignAnalyst(firstAnalyst);
        pendingReview.assignAnalyst(secondAnalyst);
        require(pendingReview.getAssignedAnalyst() == secondAnalyst,
                "Compliance Manager reassignment failed.");
    }

    private static void require(boolean condition, String message) {
        if (!condition) {
            throw new IllegalStateException(message);
        }
    }

    private static void verifyEnabledButtons(Container container) {
        for (Component component : container.getComponents()) {
            if (component.getClass() == JButton.class) {
                JButton button = (JButton) component;
                require(!button.isVisible() || !button.isEnabled()
                        || button.getActionListeners().length > 0,
                        "Enabled button has no action: " + button.getText());
            }
            if (component instanceof Container) {
                verifyEnabledButtons((Container) component);
            }
        }
    }

    private static void verifyAllDemoAccounts(Network network) {
        String[] usernames = {
            "network.admin",
            "staffing.admin",
            "compliance.admin",
            "client.admin",
            "payroll.admin",
            "recruiter",
            "coordinator",
            "C.manager",
            "C.analyst",
            "C.specialist",
            "HR",
            "Contractor",
            "Sup",
            "p.specialist",
            "b.analyst"
        };

        for (String username : usernames) {
            UserAccount account = network.getUserAccountDirectory()
                    .authenticateUser(username, "password");
            require(account != null,
                    "Authentication failed for demo account: " + username);
            require(account.getRole() != null,
                    "Demo account has no assigned role: " + username);
        }

        require(network.getUserAccountDirectory()
                .authenticateUser("account.manager", "password") == null,
                "Removed Staffing Account Manager must not be configured.");
    }
}
