package ComplianceEnterprise.Enums;

/**
 *
 * @author janet
 */
public enum ComplianceDecision {
    PENDING("Pending"),
    APPROVED("Approved"),
    REJECTED("Rejected"),
    MORE_INFORMATION_REQUIRED("More Information Required");

    private final String displayName;

    private ComplianceDecision(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
