/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StaffingAgency.Request;

/**
 *
 * @author abhit
 */
    


import Core.WorkOrder;
import Core.WorkOrderStatus;
import java.time.LocalDate;

/**
 * Work order created by the Client and reviewed by the
 * Staffing Agency Contractor Management Organization.
 */
public class ContractExtensionRequest extends WorkOrder {

    private final LocalDate currentEndDate;
    private final LocalDate requestedEndDate;
    private final String reason;
    private final Contract contract;

    public ContractExtensionRequest(
            Contract contract,
            LocalDate requestedEndDate,
            String reason
    ) {
        super();

        if (contract == null) {
            throw new IllegalArgumentException(
                    "Contract is required."
            );
        }

        if (requestedEndDate == null) {
            throw new IllegalArgumentException(
                    "Requested end date is required."
            );
        }

        if (!requestedEndDate.isAfter(
                contract.getEndDate()
        )) {
            throw new IllegalArgumentException(
                    "Requested end date must be after "
                    + "the current end date."
            );
        }

        if (reason == null
                || reason.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Extension reason is required."
            );
        }

        this.contract = contract;
        this.currentEndDate =
                contract.getEndDate();
        this.requestedEndDate =
                requestedEndDate;
        this.reason = reason.trim();

        setMessage(
                "Contract extension requested for Contract #"
                + contract.getContractId()
        );

        contract.addExtensionRequest(this);
    }

    public int getExtensionRequestId() {
        return getWorkOrderId();
    }

    public LocalDate getCurrentEndDate() {
        return currentEndDate;
    }

    public LocalDate getRequestedEndDate() {
        return requestedEndDate;
    }

    public String getReason() {
        return reason;
    }

    public Contract getContract() {
        return contract;
    }

    public void approveRequest() {

        if (getStatus()
                != WorkOrderStatus.IN_PROGRESS
                && getStatus()
                != WorkOrderStatus.UNDER_REVIEW) {

            throw new IllegalStateException(
                    "Claim the request before approving it."
            );
        }

        setStatus(
                WorkOrderStatus.APPROVED
        );
    }

    public void rejectRequest() {

        if (getStatus().isDone()) {
            throw new IllegalStateException(
                    "This request is already closed."
            );
        }

        setStatus(
                WorkOrderStatus.REJECTED
        );
    }

    /**
     * Applies the approved date to both the contract
     * and its contractor assignment.
     */
    public void applyExtension() {

        if (getStatus()
                != WorkOrderStatus.APPROVED) {

            throw new IllegalStateException(
                    "The extension must be approved "
                    + "before it can be applied."
            );
        }

        contract.extendContract(
                requestedEndDate
        );

        if (contract.getAssignment() != null) {

            contract.getAssignment()
                    .extendAssignment(
                            requestedEndDate
                    );
        }

        setStatus(
                WorkOrderStatus.COMPLETED
        );
    }

    @Override
    public String toString() {
        return getWorkOrderId()
                + " - Contract #"
                + contract.getContractId();
    }
}