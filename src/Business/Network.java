/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Business;

import ComplianceEnterprise.Model.ComplianceData;
import Core.Enterprise;
import Core.UserAccountDirectory;
import Core.WorkOrderQueue;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alex
 */
public class Network {
    
    private List<Enterprise> enterpriseList;
    private UserAccountDirectory userAccountDirectory;
    private final WorkOrderQueue workOrderQueue;
    private ComplianceData complianceData;
    
    public Network() {
        this.enterpriseList = new ArrayList<>();
        this.userAccountDirectory = new UserAccountDirectory();
        this.workOrderQueue = new WorkOrderQueue();
    }
    
    public List<Enterprise> getEnterpriseList() {
        return enterpriseList;
    }

    public UserAccountDirectory getUserAccountDirectory() {
        return userAccountDirectory;
    }

    public WorkOrderQueue getWorkOrderQueue() {
        return workOrderQueue;
    }

    public ComplianceData getComplianceData() {
        return complianceData;
    }

    public void setComplianceData(ComplianceData complianceData) {
        this.complianceData = complianceData;
    }

    public void addEnterprise(Enterprise enterprise) {
        if (enterprise == null) {
            throw new IllegalArgumentException("Enterprise is required.");
        }
        enterpriseList.add(enterprise);
    }
    
}
