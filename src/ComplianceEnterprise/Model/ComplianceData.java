package ComplianceEnterprise.Model;

import ComplianceEnterprise.Role.ComplianceAnalyst;
import ComplianceEnterprise.Role.ComplianceUser;
import ComplianceEnterprise.Role.ComplianceManager;
import ComplianceEnterprise.Role.CredentialSpecialist;
import ComplianceEnterprise.Role.ComplianceAnalystRole;
import Business.Network;
import Core.UserAccount;
import StaffingAgency.People.Contractor;
import StaffingAgency.Request.CredentialVerificationRequest;
import java.util.List;

/**
 *
 * @author janet
 */
public class ComplianceData {

    private final ComplianceDirectory complianceDirectory;
    private final List<Contractor> contractorList;
    private final ComplianceManager manager;
    private final ComplianceAnalyst analyst;
    private final CredentialSpecialist specialist;

    public ComplianceData(ComplianceDirectory complianceDirectory,
            List<Contractor> contractorList, ComplianceManager manager,
            ComplianceAnalyst analyst, CredentialSpecialist specialist) {
        this.complianceDirectory = complianceDirectory;
        this.contractorList = contractorList;
        this.manager = manager;
        this.analyst = analyst;
        this.specialist = specialist;
    }

    public ComplianceDirectory getComplianceDirectory() {
        return complianceDirectory;
    }

    public List<Contractor> getContractorList() {
        return contractorList;
    }

    public ComplianceManager getManager() {
        return manager;
    }

    public ComplianceAnalyst getAnalyst() {
        return analyst;
    }

    public ComplianceAnalyst getAnalystForAccount(UserAccount account) {
        if (account == null) {
            return analyst;
        }
        for (ComplianceUser user : complianceDirectory.getUserList()) {
            if (user instanceof ComplianceAnalyst
                    && user.getUsername().equalsIgnoreCase(
                            account.getUsername())) {
                return (ComplianceAnalyst) user;
            }
        }
        String name = account.getPerson() == null
                ? account.getUsername() : account.getPerson().toString();
        ComplianceAnalyst newAnalyst = new ComplianceAnalyst(
                name,
                account.getUsername() + "@compliance.local",
                account.getUsername());
        complianceDirectory.addUser(newAnalyst);
        return newAnalyst;
    }

    public void syncAnalystsFromNetwork(Network network) {
        if (network == null || network.getUserAccountDirectory() == null) {
            return;
        }
        for (UserAccount account : network.getUserAccountDirectory()
                .getUserAccountList()) {
            if (account.getRole() instanceof ComplianceAnalystRole) {
                getAnalystForAccount(account);
            }
        }
    }

    public CredentialSpecialist getSpecialist() {
        return specialist;
    }

    public VerificationReview receiveVerificationRequest(
            CredentialVerificationRequest request) {
        Contractor contractor = request.getAssignment().getContractor();
        if (!contractorList.contains(contractor)) {
            contractorList.add(contractor);
        }
        return complianceDirectory.addVerificationRequest(request);
    }
}
