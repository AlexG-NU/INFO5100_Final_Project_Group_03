/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business;

import Client.ClientEnterprise;
import Client.Roles.ContractorRole;
import Client.Roles.HiringManagerRole;
import Core.Person;
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
        Network network = new Network();
        
        ClientEnterprise client = new ClientEnterprise("Client Enterprise");
        //PayrollEnterprise payroll = new PayrollEnterprise("Payroll Enterprise");
        //StaffingEnterprise staffing = new StaffingEnterprise("Staffing Enterprise");
        //ComplianceEnterprise compliance = new ComplianceEnterprise("Compliance Enterprise");
    
        network.getEnterpriseList().add(client);
        //network.getEnterpriseList().add(payroll);
        //network.getEnterpriseList().add(staffing);
        //network.getEnterpriseList().add(compliance);
        Person hrPerson = new Person("Ted HR");
        network.getUserAccountDirectory().createUserAccount(
                "HR", 
                "password", 
                hrPerson,
                new HiringManagerRole()
        );
        Person contractorPerson = new Person("Alex Contractor");
        network.getUserAccountDirectory().createUserAccount(
                "Contractor", 
                "password", 
                contractorPerson,
                new ContractorRole()
        );
        
        populateStaffingRequests();
        return network;
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
