package StaffingAgency;

import Core.Enterprise;
import Core.Role;
import StaffingAgency.Organization.ContractorManagementOrganization;
import StaffingAgency.Organization.RecruitingOrganization;
import StaffingAgency.People.Candidate;
import StaffingAgency.Request.CandidateSubmission;
import StaffingAgency.Role.RecruiterRole;
import WorkOrders.StaffingRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Enterprise responsible for recruiting and contractor placement.
 */
public class StaffingAgencyEnterprise extends Enterprise {

    private final List<StaffingRequest> staffingRequestList;
    private final List<Candidate> candidateList;
    private final List<CandidateSubmission> submissionList;

    public StaffingAgencyEnterprise(String name,
            List<StaffingRequest> staffingRequestList,
            List<Candidate> candidateList,
            List<CandidateSubmission> submissionList) {
        super(name);
        this.staffingRequestList = staffingRequestList;
        this.candidateList = candidateList;
        this.submissionList = submissionList;

        getOrganizationDirectory().addOrganization(
                new RecruitingOrganization(
                        staffingRequestList, candidateList, submissionList));
        getOrganizationDirectory().addOrganization(
                new ContractorManagementOrganization());
    }

    @Override
    public List<Role> getSupportedRoles() {
        List<Role> roles = new ArrayList<>();
        roles.add(new RecruiterRole(
                staffingRequestList, candidateList, submissionList));
        return roles;
    }
}
