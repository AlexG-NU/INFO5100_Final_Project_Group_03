/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UserInterface.Client;

import Business.Network;
import Core.Enterprise;
import Core.Organization;
import Core.UserAccount;
import Core.WorkOrder;
import Core.WorkOrders.TimecardWorkOrder;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Alex
 */


public class HiringManagerReportsJPanel extends javax.swing.JPanel {

    /**
     * Creates new form HiringManagerReportsJPanel
     */
    
    private JPanel container;
    private UserAccount userAccount;
    private Network network;
    
    private class ContractorStats {
        int id;
        String name;
        int weeksOvertime = 0;
        int weeksUnderutilized = 0;
        double totalHoursLogged = 0;
        int totalTimecards = 0;
        double totalTaskDurationHours = 0;
        int totalTasksCompleted = 0;
    }
    
    public HiringManagerReportsJPanel(JPanel container, UserAccount userAccount, Network network) {
        initComponents();
        this.container = container;
        this.userAccount = userAccount;
        this.network = network;
        
        generateDashboardMetrics();
    }
    
private void generateDashboardMetrics() {
        DefaultTableModel modelOvertime = (DefaultTableModel) tblOvertime.getModel();
        DefaultTableModel modelUnder = (DefaultTableModel) tblUnderutilized.getModel();
        DefaultTableModel modelDuration = (DefaultTableModel) tblTaskDuration.getModel();
        DefaultTableModel modelSpan = (DefaultTableModel) tblContractorCount.getModel();
 

        modelOvertime.setRowCount(0);
        modelUnder.setRowCount(0);
        modelDuration.setRowCount(0);
        modelSpan.setRowCount(0);
 
        Map<String, ContractorStats> contractorStatsMap = new HashMap<>();
        Map<String, Set<String>> supervisorSpanMap = new HashMap<>();
 
        for (UserAccount ua : network.getUserAccountDirectory().getUserAccountList()) {
 
            if (!(ua.getRole() instanceof Client.Roles.ContractorRole)) {
                continue;
            }
 

            if (ua.getSupervisor() != null) {
                String supervisorName = ua.getSupervisor().getUsername();
                String subordinateName = ua.getUsername();
                
                supervisorSpanMap.putIfAbsent(supervisorName, new HashSet<>());
                supervisorSpanMap.get(supervisorName).add(subordinateName);
            }
 
            String contractorName = ua.getUsername();
            contractorStatsMap.putIfAbsent(contractorName, new ContractorStats());
            ContractorStats stats = contractorStatsMap.get(contractorName);
            stats.name = contractorName;
 
            for (WorkOrder wo : ua.getWorkQueue().getWorkOrderList()) {
 
                if (wo instanceof Core.WorkOrders.TaskWorkOrder) {
                    Core.WorkOrders.TaskWorkOrder taskOrder = (Core.WorkOrders.TaskWorkOrder) wo;
 
                    stats.totalTasksCompleted++;
                    
                    if (taskOrder.getResolveDate() != null && taskOrder.getRequestDate() != null) {
                        long hoursTaken = java.time.temporal.ChronoUnit.HOURS.between(taskOrder.getRequestDate(), taskOrder.getResolveDate());
                        stats.totalTaskDurationHours += hoursTaken;
                    }
                } else if (wo instanceof TimecardWorkOrder) {
                    TimecardWorkOrder timecard = (TimecardWorkOrder) wo;
 
                    double weekHours = timecard.getTotalHours();
                    stats.totalHoursLogged += weekHours;
                    stats.totalTimecards++;
 
                    if (weekHours > 40) {
                        stats.weeksOvertime++;
                    } else if (weekHours < 40) {
                        stats.weeksUnderutilized++;
                    }
                }
            }
        }
 
        // Push Overtime / Underutilized to Tabs 1 and 2
        for (ContractorStats stats : contractorStatsMap.values()) {
            if (stats.totalTimecards > 0) {
                double avgHoursPerWeek = stats.totalHoursLogged / stats.totalTimecards;
 
                if (stats.weeksOvertime > 0) {
                    modelOvertime.addRow(new Object[]{stats.name, stats.name, stats.weeksOvertime, String.format("%.2f", avgHoursPerWeek)});
                }
 
                if (stats.weeksUnderutilized > 0) {
                    modelUnder.addRow(new Object[]{stats.name, stats.name, stats.weeksUnderutilized, String.format("%.2f", avgHoursPerWeek)});
                }
            }
        }
 
        // Push Task Durations to Tab 3
        for (ContractorStats stats : contractorStatsMap.values()) {
            if (stats.totalTasksCompleted > 0) {
                double avgTaskHrs = stats.totalTaskDurationHours / stats.totalTasksCompleted;
                modelDuration.addRow(new Object[]{stats.name, stats.name, String.format("%.2f", avgTaskHrs)});
            }
        }
        
        // Push Supervisor Span of Control to Tab 4
        for (Map.Entry<String, Set<String>> entry : supervisorSpanMap.entrySet()) {
            String supervisorName = entry.getKey();
            int spanCount = entry.getValue().size();
            modelSpan.addRow(new Object[]{supervisorName, supervisorName, spanCount});
        }
    }
    
    /*private void populateTables(Map<Integer, ContractorStats> contractorStatsMap, Map<Integer, Set<Integer>> supervisorSpanMap) {
        DefaultTableModel modelOvertime = (DefaultTableModel) tblOvertime.getModel();
        DefaultTableModel modelUnder = (DefaultTableModel) tblUnderutilized.getModel();
        DefaultTableModel modelDuration = (DefaultTableModel) tblTaskDuration.getModel();
        DefaultTableModel modelSpan = (DefaultTableModel) tblContractorCount.getModel();

        modelOvertime.setRowCount(0);
        modelUnder.setRowCount(0);
        modelDuration.setRowCount(0);
        modelSpan.setRowCount(0);

        for (ContractorStats stats : contractorStatsMap.values()) {
            double avgHours = stats.totalTimecards > 0 ? (stats.totalHoursLogged / stats.totalTimecards) : 0;
            double avgTaskHrs = stats.totalTasksCompleted > 0 ? (stats.totalTaskDurationHours / stats.totalTasksCompleted) : 0;

            if (stats.weeksOvertime > 0) {
                modelOvertime.addRow(new Object[]{stats.id, stats.name, stats.weeksOvertime, String.format("%.2f", avgHours)});
            }

            if (stats.weeksUnderutilized > 0) {
                modelUnder.addRow(new Object[]{stats.id, stats.name, stats.weeksUnderutilized, String.format("%.2f", avgHours)});
            }

            if (stats.totalTasksCompleted > 0) {
                modelDuration.addRow(new Object[]{stats.id, stats.name, String.format("%.2f", avgTaskHrs)});
            }
        }

        for (Map.Entry<Integer, Set<Integer>> entry : supervisorSpanMap.entrySet()) {
            int supervisorId = entry.getKey();
            String supervisorName = lookupUserName(supervisorId);
            int assignedCount = entry.getValue().size();
            
            modelSpan.addRow(new Object[]{supervisorId, supervisorName, assignedCount});
        }
    }*/
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblOvertime = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblUnderutilized = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblTaskDuration = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblContractorCount = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        btnBack = new javax.swing.JButton();

        tblOvertime.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Contractor ID", "Name", "Weeks >40hrs", "Avg Hours per Week"
            }
        ));
        jScrollPane1.setViewportView(tblOvertime);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Contractors with Overtime");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(47, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 503, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(135, 135, 135))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Overtime", jPanel1);

        tblUnderutilized.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Contractor ID", "Name", "Weeks <40 hrs", "Avg Hours per Week"
            }
        ));
        jScrollPane2.setViewportView(tblUnderutilized);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setText("Underutilized Contractors");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(47, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 503, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(142, 142, 142))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Underutilized", jPanel2);

        tblTaskDuration.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Contractor ID", "Name", "Avg Task Hours"
            }
        ));
        jScrollPane3.setViewportView(tblTaskDuration);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setText("Task Duration");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(47, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 503, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(208, 208, 208)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Task Duration", jPanel3);

        tblContractorCount.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Supervisor ID", "Name", "Assigned Contractors"
            }
        ));
        jScrollPane4.setViewportView(tblContractorCount);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setText("Contractors Per Supervisor");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(47, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 503, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(128, 128, 128))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Contractor Count", jPanel4);

        btnBack.setText("<< Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(btnBack)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 549, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addComponent(btnBack)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        container.remove(this);
        java.awt.CardLayout layout = (java.awt.CardLayout) container.getLayout();
        layout.previous(container);
    }//GEN-LAST:event_btnBackActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tblContractorCount;
    private javax.swing.JTable tblOvertime;
    private javax.swing.JTable tblTaskDuration;
    private javax.swing.JTable tblUnderutilized;
    // End of variables declaration//GEN-END:variables
}
