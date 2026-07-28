package ComplianceEnterprise.Model;

import ComplianceEnterprise.Role.ComplianceAnalyst;
import ComplianceEnterprise.Role.ComplianceManager;
import ComplianceEnterprise.Role.CredentialSpecialist;
import StaffingAgency.People.Contractor;
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

    public CredentialSpecialist getSpecialist() {
        return specialist;
    }
}
