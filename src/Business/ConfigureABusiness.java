/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business;

import Client.ClientEnterprise;
import Client.Roles.ContractorRole;
import Client.Roles.HiringManagerRole;
import Client.Roles.ProjectSupervisorRole;
import Core.Person;
import Core.UserAccount;
import Core.WorkOrderStatus;
import Core.WorkOrders.TaskWorkOrder;
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
        UserAccount hrAccount = network.getUserAccountDirectory().createUserAccount(
                "HR", 
                "password", 
                hrPerson,
                new HiringManagerRole()
        );
        Person contractorPerson = new Person("Alex Contractor");
        UserAccount contractorAccount = network.getUserAccountDirectory().createUserAccount(
                "Contractor", 
                "password", 
                contractorPerson,
                new ContractorRole()
        );
        Person supervisorPerson = new Person("Lisa Supervisor");
        UserAccount supervisorAccount = network.getUserAccountDirectory().createUserAccount(
                "Sup", 
                "password", 
                supervisorPerson,
                new ProjectSupervisorRole()
        );
        
        populateStaffingRequests();
        populateContractorTasks(contractorAccount, supervisorAccount);
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
    
    public static void populateContractorTasks(UserAccount contractor, UserAccount supervisor) {
        // Task 1
        TaskWorkOrder task1 = new TaskWorkOrder();
        task1.setTaskName("Inspect HVAC System");
        task1.setMessage("Perform quarterly inspection and replace air filters in Building B.");
        task1.setSender(supervisor);
        task1.setReceiver(contractor);
        task1.setStatus(WorkOrderStatus.PENDING);

        // Task 2
        TaskWorkOrder task2 = new TaskWorkOrder();
        task2.setTaskName("Repair Lobby Drywall");
        task2.setMessage("Patch drywall damage near the front entrance and prep for painting.");
        task2.setSender(supervisor);
        task2.setReceiver(contractor);
        task2.setStatus(WorkOrderStatus.IN_PROGRESS);

        // Add to Contractor's private queue
        contractor.getWorkQueue().getWorkOrderList().add(task1);
        contractor.getWorkQueue().getWorkOrderList().add(task2);

        // Add to Supervisor's private queue for tracking
        supervisor.getWorkQueue().getWorkOrderList().add(task1);
        supervisor.getWorkQueue().getWorkOrderList().add(task2);
    }
    
}
