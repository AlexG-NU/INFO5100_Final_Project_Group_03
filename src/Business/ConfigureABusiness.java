/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business;

import Client.ClientEnterprise;
import ComplianceEnterprise.Model.ComplianceData;
import ComplianceEnterprise.Model.ComplianceDataGenerator;
import ComplianceEnterprise.Role.ComplianceAnalystRole;
import ComplianceEnterprise.Role.ComplianceManagerRole;
import ComplianceEnterprise.Role.CredentialSpecialistRole;
import Client.Roles.ContractorRole;
import Client.Roles.HiringManagerRole;
import Core.Person;
import Core.UserAccountDirectory;
import StaffingAgency.People.Candidate;
import StaffingAgency.Request.CandidateSubmission;
import StaffingAgency.Role.RecruiterRole;
import WorkOrders.StaffingRequest;
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
        
        
        //PayrollEnterprise payroll = new PayrollEnterprise("Payroll Enterprise");
        //StaffingEnterprise staffing = new StaffingEnterprise("Staffing Enterprise");
        //ComplianceEnterprise compliance = new ComplianceEnterprise("Compliance Enterprise");
    

        //network.getEnterpriseList().add(payroll);
        //network.getEnterpriseList().add(staffing);
        //network.getEnterpriseList().add(compliance);


        Person recruiterPerson = new Person("Staffing Recruiter");
        network.getUserAccountDirectory().createUserAccount(
                "recruiter",
                "password",
                recruiterPerson,
                new RecruiterRole(
                        staffingRequestList,
                        candidateList,
                        submissionList)
        );
        
        /*
         * Add the Compliance users and demonstration records to the same
         * network-wide UserAccountDirectory.
         */
        populateComplianceData(network.getUserAccountDirectory());
        ConfigureAClient.populateClientData(network);
        return network;
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
