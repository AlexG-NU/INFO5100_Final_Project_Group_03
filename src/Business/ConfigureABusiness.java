/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business;

import PayrollBilling.ConfigurePayrollBilling;
import Client.ClientEnterprise;
import ComplianceEnterprise.Model.ComplianceData;
import ComplianceEnterprise.Model.ComplianceDataGenerator;
import ComplianceEnterprise.ComplianceServicesEnterprise;
import ComplianceEnterprise.Role.ComplianceAnalystRole;
import ComplianceEnterprise.Role.ComplianceManagerRole;
import ComplianceEnterprise.Role.CredentialSpecialistRole;
import Client.Roles.ContractorRole;
import Client.Roles.HiringManagerRole;
import Core.Person;
import Core.Enterprise;
import Core.EnterpriseAdminRole;
import Core.NetworkAdminRole;
import Core.UserAccount;
import Core.UserAccountDirectory;
import Core.WorkOrderStatus;
import Core.WorkOrders.CrossEnterpriseWorkOrder;
import StaffingAgency.People.Candidate;
import StaffingAgency.People.Contractor;
import StaffingAgency.Request.CandidateSubmission;
import StaffingAgency.Request.ContractorAssignment;
import StaffingAgency.Role.RecruiterRole;
import StaffingAgency.StaffingAgencyEnterprise;
import StaffingAgency.Enums.CandidateStatus;
import WorkOrders.StaffingRequest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alex
 */
public class ConfigureABusiness {
    
    public static Network configure() {
        return configure(
                populateStaffingRequests(),
                new ArrayList<>(),
                new ArrayList<>());
    }

    public static Network configure(
            List<StaffingRequest> staffingRequestList,
            List<Candidate> candidateList,
            List<CandidateSubmission> submissionList) {
        Network network = new Network();
        
        StaffingAgencyEnterprise staffing =
                new StaffingAgencyEnterprise(
                        "Staffing Agency Enterprise",
                        staffingRequestList,
                        candidateList,
                        submissionList);
        network.addEnterprise(staffing);

        Person recruiterPerson = new Person("Staffing Recruiter");
        UserAccount recruiterAccount =
                network.getUserAccountDirectory().createUserAccount(
                "recruiter",
                "password",
                recruiterPerson,
                new RecruiterRole(
                        staffingRequestList,
                        candidateList,
                        submissionList)
        );
        staffing.getUserAccountDirectory()
                .getUserAccountList().add(recruiterAccount);

        // @janet - One approved shared record makes the live
        // Staffing-to-Compliance handoff testable from the recruiter screen.
        addApprovedSubmissionForHandoff(
                staffingRequestList, candidateList, submissionList);
        
        /*
         * Add the Compliance users and demonstration records to the same
         * network-wide UserAccountDirectory.
         */
        ComplianceData complianceData =
                populateComplianceData(network.getUserAccountDirectory());
        network.setComplianceData(complianceData);
        ComplianceServicesEnterprise compliance =
                new ComplianceServicesEnterprise(
                        "Compliance Services Enterprise",
                        complianceData);
        network.addEnterprise(compliance);
        ConfigureAClient.populateClientData(network);
        ConfigurePayrollBilling.populatePayrollBillingData(network);

        // @janet - Add the two administrator levels to the shared login.
        addAdministratorAccounts(network);
        connectExistingUsersToEnterprises(network);
        addCrossEnterpriseDemoRequests(network);
        return network;
    }

    // @janet - Network Admin manages enterprises. Each Enterprise Admin
    // is linked to one enterprise and manages its organizations and users.
    private static void addAdministratorAccounts(Network network) {
        network.getUserAccountDirectory().createUserAccount(
                "network.admin",
                "password",
                new Person("Network Administrator"),
                new NetworkAdminRole());

        for (Enterprise enterprise : network.getEnterpriseList()) {
            String username = buildAdminUsername(enterprise.getName());
            UserAccount admin = network.getUserAccountDirectory()
                    .createUserAccount(
                            username,
                            "password",
                            new Person(enterprise.getName() + " Administrator"),
                            new EnterpriseAdminRole(enterprise));
            if (admin != null) {
                enterprise.getUserAccountDirectory()
                        .getUserAccountList().add(admin);
            }
        }
    }

    private static String buildAdminUsername(String enterpriseName) {
        String lowerName = enterpriseName.toLowerCase();
        if (lowerName.contains("staffing")) {
            return "staffing.admin";
        }
        if (lowerName.contains("compliance")) {
            return "compliance.admin";
        }
        if (lowerName.contains("client")) {
            return "client.admin";
        }
        if (lowerName.contains("payroll")) {
            return "payroll.admin";
        }
        return lowerName.replaceAll("[^a-z0-9]+", ".")
                .replaceAll("^\\.|\\.$", "") + ".admin";
    }

    // @janet - Keep the enterprise directory and the network login directory
    // pointed at the same UserAccount objects.
    private static void connectExistingUsersToEnterprises(Network network) {
        for (UserAccount user : network.getUserAccountDirectory()
                .getUserAccountList()) {
            if (user.getRole() instanceof NetworkAdminRole
                    || user.getRole() instanceof EnterpriseAdminRole) {
                continue;
            }
            Enterprise enterprise = findEnterpriseForRole(network, user);
            if (enterprise != null
                    && !enterprise.getUserAccountDirectory()
                            .getUserAccountList().contains(user)) {
                enterprise.getUserAccountDirectory()
                        .getUserAccountList().add(user);
            }
        }
    }

    private static Enterprise findEnterpriseForRole(
            Network network, UserAccount user) {
        String rolePackage = user.getRole().getClass().getPackageName();
        for (Enterprise enterprise : network.getEnterpriseList()) {
            String enterprisePackage =
                    enterprise.getClass().getPackageName();
            if (rolePackage.startsWith(enterprisePackage)
                    || (enterprisePackage.equals("StaffingAgency")
                    && rolePackage.startsWith("StaffingAgency"))
                    || (enterprisePackage.equals("ComplianceEnterprise")
                    && rolePackage.startsWith("ComplianceEnterprise"))) {
                return enterprise;
            }
        }
        return null;
    }

    // @janet - Eight visible requests document the network handoffs required
    // by the rubric. Each request crosses two enterprises and organizations.
    private static void addCrossEnterpriseDemoRequests(Network network) {
        addCrossEnterpriseRequest(network, "Staffing Request",
                "Client Enterprise", "Human Resources Organization",
                "Staffing Agency Enterprise", "Recruiting Organization",
                "HR", "recruiter",
                "Request candidates for a technical project.",
                WorkOrderStatus.IN_PROGRESS);
        addCrossEnterpriseRequest(network, "Candidate Submission",
                "Staffing Agency Enterprise", "Recruiting Organization",
                "Client Enterprise", "Human Resources Organization",
                "recruiter", "HR",
                "Submit a qualified candidate for client review.",
                WorkOrderStatus.UNDER_REVIEW);
        addCrossEnterpriseRequest(network, "Background Check",
                "Staffing Agency Enterprise",
                "Contractor Management Organization",
                "Compliance Services Enterprise",
                "Background Screening Organization",
                "recruiter", "C.analyst",
                "Complete the candidate background screening.",
                WorkOrderStatus.IN_PROGRESS);
        addCrossEnterpriseRequest(network, "Credential Verification",
                "Staffing Agency Enterprise",
                "Contractor Management Organization",
                "Compliance Services Enterprise",
                "Credentialing and Compliance Organization",
                "recruiter", "C.specialist",
                "Verify licenses and assignment credentials.",
                WorkOrderStatus.UNDER_REVIEW);
        addCrossEnterpriseRequest(network, "Compliance Clearance",
                "Compliance Services Enterprise",
                "Credentialing and Compliance Organization",
                "Staffing Agency Enterprise",
                "Contractor Management Organization",
                "C.manager", "recruiter",
                "Return the contractor clearance decision.",
                WorkOrderStatus.APPROVED);
        addCrossEnterpriseRequest(network, "Approved Timecard",
                "Client Enterprise", "Operations Organization",
                "Payroll and Billing Enterprise",
                "Payroll Processing Organization",
                "Sup", "p.specialist",
                "Send approved contractor hours for payroll.",
                WorkOrderStatus.APPROVED);
        addCrossEnterpriseRequest(network, "Client Invoice",
                "Payroll and Billing Enterprise",
                "Client Billing Organization",
                "Client Enterprise", "Human Resources Organization",
                "b.analyst", "HR",
                "Send the contractor invoice for client review.",
                WorkOrderStatus.PENDING);
        addCrossEnterpriseRequest(network, "Contract Extension",
                "Client Enterprise", "Human Resources Organization",
                "Staffing Agency Enterprise",
                "Contractor Management Organization",
                "HR", "recruiter",
                "Request an extension for an active assignment.",
                WorkOrderStatus.PENDING);
    }

    private static void addCrossEnterpriseRequest(Network network,
            String type, String sourceEnterprise, String sourceOrganization,
            String destinationEnterprise, String destinationOrganization,
            String senderUsername, String receiverUsername, String message,
            WorkOrderStatus status) {
        UserAccount sender = findUser(network, senderUsername);
        UserAccount receiver = findUser(network, receiverUsername);
        if (sender == null || receiver == null) {
            throw new IllegalStateException(
                    "Demo request users must be configured before requests.");
        }
        CrossEnterpriseWorkOrder request =
                new CrossEnterpriseWorkOrder(
                        type, sourceEnterprise, sourceOrganization,
                        destinationEnterprise, destinationOrganization,
                        message);
        request.setSender(sender);
        request.setReceiver(receiver);
        request.setStatus(status);
        network.getWorkOrderQueue().addWorkOrder(request);
        sender.getWorkQueue().addWorkOrder(request);
        receiver.getWorkQueue().addWorkOrder(request);
    }

    private static UserAccount findUser(Network network, String username) {
        for (UserAccount user : network.getUserAccountDirectory()
                .getUserAccountList()) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return user;
            }
        }
        return null;
    }

    private static void addApprovedSubmissionForHandoff(
            List<StaffingRequest> staffingRequestList,
            List<Candidate> candidateList,
            List<CandidateSubmission> submissionList) {
        if (staffingRequestList.isEmpty() || !submissionList.isEmpty()) {
            return;
        }

        Candidate candidate = new Candidate(
                "Jordan",
                "Kim",
                "jordan.kim@example.com",
                "949-555-0118",
                "Java, QA, Git",
                5);
        candidate.setCandidateStatus(CandidateStatus.PLACED);
        candidateList.add(candidate);

        StaffingRequest request = staffingRequestList.get(0);
        CandidateSubmission submission = new CandidateSubmission(
                candidate,
                request,
                "Qualified candidate approved for integration testing.");
        request.addSubmission(submission);
        submission.submitToClient();

        Contractor contractor = new Contractor(
                candidate.getFirstName(),
                candidate.getLastName(),
                candidate.getEmail(),
                candidate.getPhone(),
                candidate.getSkills(),
                new BigDecimal("55.00"));
        ContractorAssignment assignment = new ContractorAssignment(
                contractor, request.getStartDate());
        submission.linkAssignment(assignment);
        submissionList.add(submission);
    }
    
    public static ComplianceData populateComplianceData(
            UserAccountDirectory userAccountDirectory) {

        ComplianceData complianceData =
                ComplianceDataGenerator.generate();

        /*
         * All Compliance accounts are added to the one shared
         * network-wide UserAccountDirectory.
         */
        userAccountDirectory.createUserAccount(
                "C.manager",
                "password",
                new Person("Morgan Lee"),
                new ComplianceManagerRole(complianceData)
        );

        userAccountDirectory.createUserAccount(
                "C.analyst",
                "password",
                new Person("Jamie Cruz"),
                new ComplianceAnalystRole(complianceData)
        );

        userAccountDirectory.createUserAccount(
                "C.specialist",
                "password",
                new Person("Taylor Reed"),
                new CredentialSpecialistRole(complianceData)
        );

        return complianceData;
    }
    
    public static List<StaffingRequest> populateStaffingRequests() {
        List<StaffingRequest> requests = new ArrayList<>();

        // Entry 1
        requests.add(new StaffingRequest(
            "Senior Java Developer",
            "Lead backend developer",
            "Java 17, Swing, PostgreSQL, Git",
            3,
            LocalDate.now().plusDays(14)
        ));

        // Entry 2
        requests.add(new StaffingRequest(
            "QA Automation Engineer",
            "Build automated integration",
            "JUnit, Selenium, CI/CD, Java",
            2,
            LocalDate.now().plusDays(21)
        ));

        // Entry 3
        requests.add(new StaffingRequest(
            "DevOps Infrastructure Lead",
            "Manage local deployment pipelines",
            "Docker, Kubernetes, Linux, Bash",
            1,
            LocalDate.now().plusDays(30)
        ));

        // Entry 4
        requests.add(new StaffingRequest(
            "UI/UX Designer",
            "Create user workflow components",
            "Figma, Wireframing, Swing Layouts",
            1,
            LocalDate.now().plusDays(7)
        ));

        // Entry 5
        requests.add(new StaffingRequest(
            "Technical Project Manager",
            "Oversee sprint cycles and deliverables across cross-functional teams",
            "Agile, Jira, Risk Management",
            2,
            LocalDate.now().plusDays(45)
        ));

        return requests;
    }
    
}
