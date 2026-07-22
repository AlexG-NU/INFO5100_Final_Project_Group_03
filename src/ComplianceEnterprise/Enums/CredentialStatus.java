package ComplianceEnterprise.Enums;

/**
 *
 * @author janet
 */
public enum CredentialStatus {
    NOT_STARTED("Not Started"),
    SUBMITTED("Submitted"),
    VERIFIED("Verified"),
    EXPIRED("Expired"),
    REJECTED("Rejected");

    private final String displayName;

    private CredentialStatus(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
