
package StaffingAgency.Organization;

import Core.Organization;
import Core.Role;
import StaffingAgency.Request.CandidateSubmission;
import StaffingAgency.Role.ContractorCoordinatorRole;
import java.util.ArrayList;
import java.util.List;

public class ContractorManagementOrganization
        extends Organization {

    private final List<CandidateSubmission> submissionList;

    public ContractorManagementOrganization(
            List<CandidateSubmission> submissionList
    ) {
        super("Contractor Management Organization");

        if (submissionList == null) {
            throw new IllegalArgumentException(
                    "Submission list cannot be null."
            );
        }

        this.submissionList = submissionList;
    }

    @Override
    public List<Role> getSupportedRoles() {

        List<Role> roles = new ArrayList<>();

        roles.add(
                new ContractorCoordinatorRole(
                        submissionList
                )
        );

        return roles;
    }
}