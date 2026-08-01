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
import Core.WorkOrders.StaffingReqWorkOrder;
import Core.WorkOrders.TimecardWorkOrder;
import WorkOrders.StaffingRequest;
import com.github.javafaker.Faker;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Alex
 */
public class ConfigureAClient {
    public static void populateClientData(Network network) {
                ClientEnterprise client = new ClientEnterprise("Client Enterprise");
        //PayrollEnterprise payroll = new PayrollEnterprise("Payroll Enterprise");
        //StaffingEnterprise staffing = new StaffingEnterprise("Staffing Enterprise");
        //ComplianceEnterprise compliance = new ComplianceEnterprise("Compliance Enterprise");
    
        network.addEnterprise(client);
        //network.getEnterpriseList().add(payroll);
        //network.getEnterpriseList().add(staffing);
        //network.getEnterpriseList().add(compliance);
        Faker faker = new Faker(new Random(5102));
        Person hrPerson = new Person(faker.name().fullName());
        UserAccount hrAccount = network.getUserAccountDirectory().createUserAccount(
                "HR", 
                "password", 
                hrPerson,
                new HiringManagerRole()
        );
        Person contractorPerson = new Person(faker.name().fullName());
        UserAccount contractorAccount = network.getUserAccountDirectory().createUserAccount(
                "Contractor", 
                "password", 
                contractorPerson,
                new ContractorRole()
        );
        
        Person supervisorPerson = new Person(faker.name().fullName());
        UserAccount supervisorAccount = network.getUserAccountDirectory().createUserAccount(
                "Sup", 
                "password", 
                supervisorPerson,
                new ProjectSupervisorRole()
        );
        contractorAccount.setSupervisor(supervisorAccount);
        
        populateStaffingRequests();
        populateContractorTasks(contractorAccount, supervisorAccount);
        populateClientRequests(hrAccount, network, faker);
        populateTimecards(contractorAccount, supervisorAccount, faker);
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
        Faker faker = new Faker(new Random(5103));
        for (int index = 0; index < 6; index++) {
            TaskWorkOrder task = new TaskWorkOrder();
            task.setTaskName(faker.job().field() + " Project Task");
            task.setMessage("Complete assigned work for "
                    + faker.company().name() + ".");
            task.setSender(supervisor);
            task.setReceiver(contractor);
            task.setStatus(index < 2
                    ? WorkOrderStatus.IN_PROGRESS : WorkOrderStatus.PENDING);
            contractor.getWorkQueue().getWorkOrderList().add(task);
            supervisor.getWorkQueue().getWorkOrderList().add(task);
        }
    }

    private static void populateClientRequests(UserAccount hrAccount,
            Network network, Faker faker) {
        UserAccount recruiter = network.getUserAccountDirectory()
                .authenticateUser("recruiter", "password");
        String[] titles = {
            "Java Developer", "Quality Analyst", "Data Analyst",
            "Project Coordinator"
        };

        for (int index = 0; index < titles.length; index++) {
            StaffingReqWorkOrder request = new StaffingReqWorkOrder();
            request.setJobTitle(titles[index]);
            request.setDescription("Contract support for "
                    + faker.company().name() + ".");
            request.setRequiredSkills(faker.job().keySkills());
            request.setNumberOfPositions(1 + (index % 2));
            request.setStartDate(LocalDate.now().plusDays(14 + index * 7));
            request.setSender(hrAccount);
            request.setReceiver(recruiter);
            request.setStatus(index < 2
                    ? WorkOrderStatus.IN_PROGRESS : WorkOrderStatus.PENDING);
            hrAccount.getWorkQueue().addWorkOrder(request);
            if (recruiter != null) {
                recruiter.getWorkQueue().addWorkOrder(request);
            }
        }
    }

    private static void populateTimecards(UserAccount contractor,
            UserAccount supervisor, Faker faker) {
        for (int index = 0; index < 4; index++) {
            TimecardWorkOrder timecard = new TimecardWorkOrder(
                    LocalDate.now().minusWeeks(index), 0.0);
            timecard.setDailyHours(new double[]{
                0.0, 8.0, 8.0, 8.0, 8.0,
                4.0 + (index % 2), 0.0
            });
            timecard.setWorkSummary("Completed "
                    + faker.job().field().toLowerCase() + " project work.");
            timecard.setSender(contractor);
            timecard.setReceiver(supervisor);
            timecard.setStatus(index == 0
                    ? WorkOrderStatus.PENDING : WorkOrderStatus.APPROVED);
            contractor.getWorkQueue().addWorkOrder(timecard);
            supervisor.getWorkQueue().addWorkOrder(timecard);
        }
    }
    
}
