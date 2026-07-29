package StaffingAgency.Organization;

import Core.Organization;
import Core.Role;
import StaffingAgency.People.Candidate;
import StaffingAgency.Request.CandidateSubmission;
import StaffingAgency.Role.RecruiterRole;
import WorkOrders.StaffingRequest;
import java.util.ArrayList;
import java.util.List;

public class RecruitingOrganization extends Organization {

    private final List<StaffingRequest> staffingRequestList;
    private final List<Candidate> candidateList;
    private final List<CandidateSubmission> submissionList;

    public RecruitingOrganization(List<StaffingRequest> staffingRequestList,
            List<Candidate> candidateList,
            List<CandidateSubmission> submissionList) {
        super("Recruiting Organization");
        this.staffingRequestList = staffingRequestList;
        this.candidateList = candidateList;
        this.submissionList = submissionList;
    }

    @Override
    public List<Role> getSupportedRoles() {
        List<Role> roles = new ArrayList<>();
        roles.add(new RecruiterRole(
                staffingRequestList, candidateList, submissionList));
        return roles;
    }
}
