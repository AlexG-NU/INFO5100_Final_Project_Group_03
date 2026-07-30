/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StaffingAgency.Role;

/**
 *
 * @author abhit
 */


import Business.Network;
import Core.Role;
import Core.UserAccount;
import StaffingAgency.People.Contractor;
import StaffingAgency.Request.ContractExtensionRequest;
import StaffingAgency.Request.ContractorAssignment;
import UserInterface1.ContractorCoordinatorWorkAreaJPanel;
import java.util.List;
import javax.swing.JPanel;

public class ContractorCoordinatorRole extends Role {

    private final List<Contractor> contractorList;
    private final List<ContractorAssignment> assignmentList;
    private final List<ContractExtensionRequest> extensionRequestList;

    public ContractorCoordinatorRole(
            List<Contractor> contractorList,
            List<ContractorAssignment> assignmentList,
            List<ContractExtensionRequest> extensionRequestList
    ) {
        this.contractorList = contractorList;
        this.assignmentList = assignmentList;
        this.extensionRequestList = extensionRequestList;
    }

    @Override
    public JPanel createWorkArea(
            JPanel userProcessContainer,
            UserAccount account,
            Network network
    ) {
        return new ContractorCoordinatorWorkAreaJPanel(
                userProcessContainer,
                contractorList,
                assignmentList,
                extensionRequestList,
                network
        );
    }
}
