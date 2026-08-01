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
import Core.NetworkUtils;
import Core.Organization;
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
import StaffingAgency.Role.ContractorCoordinatorRole;
import Core.WorkOrders.StaffingReqWorkOrder;
import com.github.javafaker.Faker;
import java.util.Random;
/**
 *
 * @author Alex
 */
public class ConfigureABusiness {
    
    public static Network configure() {
        return configure(
                populateStaffingRequests(),
                populateCandidates(),
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
        Person coordinatorPerson =
        new Person("Contractor Coordinator");

UserAccount coordinatorAccount =
        network.getUserAccountDirectory()
                .createUserAccount(
                        "coordinator",
                        "password",
                        coordinatorPerson,
                        new ContractorCoordinatorRole(
                                submissionList
                        )
                );

staffing.getUserAccountDirectory()
        .getUserAccountList()
        .add(coordinatorAccount);
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
        addStaffingRequestsToSharedQueue(
                network,
                staffingRequestList
        );

        // @janet - Add the two administrator levels to the shared login.
        addAdministratorAccounts(network);
        connectExistingUsersToEnterprises(network);
        addCrossEnterpriseDemoRequests(network);
        return network;
    }

    private static void addStaffingRequestsToSharedQueue(
            Network network,
            List<StaffingRequest> staffingRequestList
    ) {
        Organization recruitingOrganization =
                NetworkUtils.findOrganizationByName(
                        network,
                        "Staffing Agency Enterprise",
                        "Recruiting Organization"
                );

        UserAccount hiringManager =
                findUser(network, "HR");

        if (recruitingOrganization == null
                || hiringManager == null) {
            throw new IllegalStateException(
                    "Staffing request demo data could not "
                    + "be connected to HR and Recruiting."
            );
        }

        for (StaffingRequest staffingRequest
                : staffingRequestList) {

            StaffingReqWorkOrder workOrder =
                    new StaffingReqWorkOrder();

            workOrder.setSender(hiringManager);
            workOrder.setStatus(
                    WorkOrderStatus.PENDING
            );
            workOrder.setJobTitle(
                    staffingRequest.getJobTitle()
            );
            workOrder.setDescription(
                    staffingRequest.getDescription()
            );
            workOrder.setRequiredSkills(
                    staffingRequest.getRequiredSkills()
            );
            workOrder.setNumberOfPositions(
                    staffingRequest.getNumberOfPositions()
            );
            workOrder.setStartDate(
                    staffingRequest.getStartDate()
            );

            recruitingOrganization
                    .getWorkQueue()
                    .addWorkOrder(workOrder);

            hiringManager
                    .getWorkQueue()
                    .addWorkOrder(workOrder);
        }
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

        Candidate candidate;
        if (candidateList.isEmpty()) {
            candidate = populateCandidates().get(0);
            candidateList.add(candidate);
        } else {
            candidate = candidateList.get(0);
        }
        candidate.setCandidateStatus(CandidateStatus.PLACED);

       StaffingRequest legacyRequest =
        staffingRequestList.get(0);

StaffingReqWorkOrder request =
        new StaffingReqWorkOrder();

request.setJobTitle(
        legacyRequest.getJobTitle()
);

request.setDescription(
        legacyRequest.getDescription()
);

request.setRequiredSkills(
        legacyRequest.getRequiredSkills()
);

request.setNumberOfPositions(
        legacyRequest.getNumberOfPositions()
);

request.setStartDate(
        legacyRequest.getStartDate()
);

CandidateSubmission submission =
        new CandidateSubmission(
                candidate,
                request,
                "Qualified candidate approved for integration testing."
        );

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
        Faker faker = new Faker(new Random(5100));
        String[] jobTitles = {
            "Senior Java Developer", "QA Automation Engineer",
            "DevOps Infrastructure Lead", "UI/UX Designer",
            "Technical Project Manager", "Data Analyst",
            "Business Systems Analyst", "Cloud Support Engineer"
        };
        String[] skills = {
            "Java, Swing, PostgreSQL, Git",
            "JUnit, Selenium, CI/CD, Java",
            "Docker, Kubernetes, Linux, Bash",
            "Figma, Wireframing, Swing Layouts",
            "Agile, Jira, Risk Management",
            "Power BI, SQL, Excel",
            "Requirements, UAT, Process Mapping",
            "AWS, Networking, Troubleshooting"
        };

        for (int index = 0; index < jobTitles.length; index++) {
            requests.add(new StaffingRequest(
                    jobTitles[index],
                    "Support " + faker.company().name()
                    + " with " + faker.company().industry().toLowerCase()
                    + " project work.",
                    skills[index],
                    1 + (index % 3),
                    LocalDate.now().plusDays(7 + (index * 5))
            ));
        }

        return requests;
    }

    public static List<Candidate> populateCandidates() {
        List<Candidate> candidates = new ArrayList<>();
        Faker faker = new Faker(new Random(5101));
        String[] skills = {
            "Java, Swing, Git", "Quality Assurance, JUnit",
            "Power BI, SQL, Excel", "Project Management, Jira",
            "Cloud Infrastructure, Linux", "UX Design, Figma",
            "Business Analysis, UAT", "Payroll Operations"
        };

        for (int index = 0; index < 16; index++) {
            Candidate candidate = new Candidate(
                    faker.name().firstName(),
                    faker.name().lastName(),
                    faker.internet().emailAddress(),
                    faker.phoneNumber().cellPhone(),
                    skills[index % skills.length],
                    1 + (index % 10)
            );
            if (index < 4) {
                candidate.setCandidateStatus(CandidateStatus.SCREENING);
            } else if (index < 8) {
                candidate.setCandidateStatus(CandidateStatus.SUBMITTED);
            }
            candidates.add(candidate);
        }
        return candidates;
    }
    
}
