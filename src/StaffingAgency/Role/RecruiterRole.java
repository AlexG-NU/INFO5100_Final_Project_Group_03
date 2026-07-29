package StaffingAgency.Role;

import Business.Network;
import Core.Role;
import Core.UserAccount;
import StaffingAgency.People.Candidate;
import StaffingAgency.Request.CandidateSubmission;
import UserInterface1.StaffingAgency.RecruiterWorkAreaJPanel;
import WorkOrders.StaffingRequest;
import java.util.List;
import javax.swing.JPanel;

/**
 * Opens the Recruiter work area with the shared application records.
 */
public class RecruiterRole extends Role {

    private final List<StaffingRequest> staffingRequestList;
    private final List<Candidate> candidateList;
    private final List<CandidateSubmission> submissionList;

    public RecruiterRole(List<StaffingRequest> staffingRequestList,
            List<Candidate> candidateList,
            List<CandidateSubmission> submissionList) {
        this.staffingRequestList = staffingRequestList;
        this.candidateList = candidateList;
        this.submissionList = submissionList;
    }

    @Override
    public JPanel createWorkArea(JPanel userProcessContainer,
            UserAccount account, Network network) {
        return new RecruiterWorkAreaJPanel(
                userProcessContainer,
                staffingRequestList,
                candidateList,
                submissionList,
                network); // @janet - shared handoff to Compliance
    }
}
