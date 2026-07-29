package Core.WorkOrders;

import Core.WorkOrder;

/**
 * A network request that records the enterprise and organization handoff.
 *
 * @author janet
 */
public class CrossEnterpriseWorkOrder extends WorkOrder {

    private final String requestType;
    private final String sourceEnterprise;
    private final String sourceOrganization;
    private final String destinationEnterprise;
    private final String destinationOrganization;

    public CrossEnterpriseWorkOrder(String requestType,
            String sourceEnterprise, String sourceOrganization,
            String destinationEnterprise, String destinationOrganization,
            String message) {
        if (isBlank(requestType) || isBlank(sourceEnterprise)
                || isBlank(sourceOrganization)
                || isBlank(destinationEnterprise)
                || isBlank(destinationOrganization)) {
            throw new IllegalArgumentException(
                    "Request type and both routing locations are required.");
        }
        if (sourceEnterprise.equalsIgnoreCase(destinationEnterprise)) {
            throw new IllegalArgumentException(
                    "A cross-enterprise request must route to another enterprise.");
        }
        this.requestType = requestType.trim();
        this.sourceEnterprise = sourceEnterprise.trim();
        this.sourceOrganization = sourceOrganization.trim();
        this.destinationEnterprise = destinationEnterprise.trim();
        this.destinationOrganization = destinationOrganization.trim();
        setMessage(message == null ? "" : message.trim());
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    public String getRequestType() {
        return requestType;
    }

    public String getSourceEnterprise() {
        return sourceEnterprise;
    }

    public String getSourceOrganization() {
        return sourceOrganization;
    }

    public String getDestinationEnterprise() {
        return destinationEnterprise;
    }

    public String getDestinationOrganization() {
        return destinationOrganization;
    }
}
