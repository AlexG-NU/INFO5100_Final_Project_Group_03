/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Core.WorkOrders;

import Core.WorkOrder;

/**
 *
 * @author Alex
 */
public class CandidateApprovalWorkOrder extends WorkOrder{
    
    private int requestId = 101;
    private String candidateName = "Jane Doe";
    private String resume = "Senior Java Developer with 5 years of experience...";
    private String agencyComments = "Highly recommended, available immediately.";
    private String approvalStatus = "Pending";

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getAgencyComments() {
        return agencyComments;
    }

    public void setAgencyComments(String agencyComments) {
        this.agencyComments = agencyComments;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }
    
    
    
}
