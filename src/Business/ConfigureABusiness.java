/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business;

import WorkOrders.StaffingRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alex
 */
public class ConfigureABusiness {
    
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
