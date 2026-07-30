/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StaffingAgency.Role;

/**
 *
 * @author abhit
 */




import Business.Network;
import Core.Role;
import Core.UserAccount;
import StaffingAgency.Request.CandidateSubmission;
import UserInterface1.StaffingAgency.ContractorCoordinatorWorkAreaJPanel;
import java.util.List;
import javax.swing.JPanel;

public class ContractorCoordinatorRole extends Role {

    private final List<CandidateSubmission> submissionList;

    public ContractorCoordinatorRole(
            List<CandidateSubmission> submissionList
    ) {
        if (submissionList == null) {
            throw new IllegalArgumentException(
                    "Submission list cannot be null."
            );
        }

        this.submissionList = submissionList;
    }

    @Override
    public JPanel createWorkArea(
            JPanel userProcessContainer,
            UserAccount account,
            Network network
    ) {
        return new ContractorCoordinatorWorkAreaJPanel(
                userProcessContainer,
                submissionList,
                account,
                network
        );
    }
}
