package ComplianceEnterprise;

import Business.Network;
import ComplianceEnterprise.Model.ComplianceData;
import ComplianceEnterprise.Model.VerificationReview;
import StaffingAgency.Request.ContractorAssignment;
import StaffingAgency.Request.CredentialVerificationRequest;

/**
 * Shared handoff used when Staffing sends an assignment to Compliance.
 *
 * @author janet
 */
public final class ComplianceIntegrationService {

    private ComplianceIntegrationService() {
    }

    public static VerificationReview submitForVerification(
            Network network, ContractorAssignment assignment,
            String verificationType, String notes) {

        if (network == null) {
            throw new IllegalArgumentException("Network is required.");
        }

        ComplianceData complianceData = network.getComplianceData();
        if (complianceData == null) {
            throw new IllegalStateException(
                    "The Compliance Enterprise has not been configured.");
        }

        CredentialVerificationRequest request =
                new CredentialVerificationRequest(
                        assignment, verificationType, notes);

        return complianceData.receiveVerificationRequest(request);
    }
}
