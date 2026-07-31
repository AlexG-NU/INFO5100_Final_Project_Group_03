package UserInterface.Compliance;

import Business.Network;
import ComplianceEnterprise.Enums.ComplianceDecision;
import ComplianceEnterprise.Model.ComplianceData;
import ComplianceEnterprise.Model.ComplianceDirectory;
import ComplianceEnterprise.Model.VerificationReview;
import ComplianceEnterprise.Role.ComplianceAnalyst;
import ComplianceEnterprise.Role.ComplianceUser;
import ComplianceEnterprise.Role.ComplianceManager;
import StaffingAgency.People.Contractor;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author janet
 */
public class ComplianceManagerWorkAreaJPanel extends javax.swing.JPanel {

    private final ComplianceDirectory complianceDirectory;
    private final ComplianceManager manager;
    private final java.util.List<Contractor> contractorList;
    private final javax.swing.JPanel container;
    private final javax.swing.JPanel previousPanel;
    private final ComplianceData complianceData;
    private final Network network;

    public ComplianceManagerWorkAreaJPanel(ComplianceDirectory complianceDirectory,
            ComplianceManager manager, java.util.List<Contractor> contractorList) {
        this(null, complianceDirectory, manager, contractorList, null);
    }

    public ComplianceManagerWorkAreaJPanel(javax.swing.JPanel container,
            ComplianceDirectory complianceDirectory, ComplianceManager manager,
            java.util.List<Contractor> contractorList, javax.swing.JPanel previousPanel) {
        this.container = container;
        this.previousPanel = previousPanel;
        this.complianceData = null;
        this.network = null;
        this.complianceDirectory = complianceDirectory; this.manager = manager; this.contractorList = contractorList;
        initComponents();
        btnBack.setVisible(previousPanel != null);
        lblOrganization.setText("Compliance Enterprise - Compliance Oversight");
        configureTables();
        applyResponsiveLayout();
        populateTable();
    }

    public ComplianceManagerWorkAreaJPanel(javax.swing.JPanel container,
            ComplianceData complianceData, Network network,
            javax.swing.JPanel previousPanel) {
        this.container = container;
        this.previousPanel = previousPanel;
        this.complianceData = complianceData;
        this.network = network;
        this.complianceDirectory = complianceData.getComplianceDirectory();
        this.manager = complianceData.getManager();
        this.contractorList = complianceData.getContractorList();
        initComponents();
        btnBack.setVisible(previousPanel != null);
        lblOrganization.setText("Compliance Enterprise - Compliance Oversight");
        configureTables();
        applyResponsiveLayout();
        refreshManagerData();
    }

    private void configureTables() {
        ComplianceTableUI.configure(tblRequests,
                90, 100, 100, 170, 210, 150, 190, 150);
        ComplianceTableUI.configure(tblWorkload,
                100, 160, 130, 85, 90, 90);
        lblNextStep.setVisible(false);
        lblCaseStatus.setText("Selected case: none");
        tblRequests.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                updateSelectedCaseStatus();
            }
        });
    }

    private void applyResponsiveLayout() {
        java.awt.Color background = new java.awt.Color(255, 255, 204);
        javax.swing.JPanel header = verticalPanel(background);
        header.add(lblTitle);
        header.add(javax.swing.Box.createVerticalStrut(4));
        header.add(lblOrganization);
        header.add(javax.swing.Box.createVerticalStrut(8));
        header.add(lblCaseStatus);
        header.add(javax.swing.Box.createVerticalStrut(10));
        header.add(managerStyleGrid(btnView, btnQueue, btnManage, btnReports));

        ComplianceTableUI.styleScrollPane(jScrollPane1, 230);
        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(
                "Compliance Request Queue"));

        javax.swing.JPanel assignment = new javax.swing.JPanel(
                new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 4));
        assignment.setBackground(background);
        assignment.add(lblAssign);
        assignment.add(cmbAnalyst);
        assignment.add(btnAssign);

        ComplianceTableUI.styleScrollPane(jScrollPane2, 145);
        jScrollPane2.setBorder(javax.swing.BorderFactory.createTitledBorder(
                "Team Workload"));

        javax.swing.JPanel tables = new javax.swing.JPanel(new java.awt.GridBagLayout());
        tables.setBackground(background);
        java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
        gbc.gridx = 0; gbc.weightx = 1.0; gbc.fill = java.awt.GridBagConstraints.BOTH;
        gbc.gridy = 0; gbc.weighty = 0.62;
        tables.add(jScrollPane1, gbc);
        gbc.gridy = 1; gbc.weighty = 0; gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;
        tables.add(assignment, gbc);
        gbc.gridy = 2; gbc.weighty = 0.38; gbc.fill = java.awt.GridBagConstraints.BOTH;
        tables.add(jScrollPane2, gbc);

        javax.swing.JPanel bottom = managerStyleGrid(btnBack, btnRefresh);
        btnBack.setVisible(previousPanel != null);

        removeAll();
        setLayout(new java.awt.BorderLayout());
        setBackground(background);
        setBorder(javax.swing.BorderFactory.createEmptyBorder(16, 24, 16, 24));
        add(header, java.awt.BorderLayout.NORTH);
        add(tables, java.awt.BorderLayout.CENTER);
        add(bottom, java.awt.BorderLayout.SOUTH);
    }

    private javax.swing.JPanel verticalPanel(java.awt.Color background) {
        javax.swing.JPanel panel = new javax.swing.JPanel();
        panel.setBackground(background);
        panel.setLayout(new javax.swing.BoxLayout(panel, javax.swing.BoxLayout.Y_AXIS));
        return panel;
    }

    private javax.swing.JPanel managerStyleGrid(
            javax.swing.JButton... buttons) {
        javax.swing.JPanel panel = new javax.swing.JPanel(
                new java.awt.GridLayout(0, 2, 14, 10));
        panel.setBackground(new java.awt.Color(255, 255, 204));
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setMaximumSize(new java.awt.Dimension(
                Integer.MAX_VALUE, buttons.length <= 2 ? 38 : 82));
        for (javax.swing.JButton button : buttons) {
            button.setPreferredSize(new java.awt.Dimension(210, 32));
            panel.add(button);
        }
        return panel;
    }

    private void updateSelectedCaseStatus() {
        int row = tblRequests.getSelectedRow();
        if (row < 0) {
            lblCaseStatus.setText("Selected case: none");
            return;
        }
        lblCaseStatus.setText("Current step: " + tblRequests.getValueAt(row, 4)
                + "  |  Waiting on: " + tblRequests.getValueAt(row, 5));
    }

    private void populateTable() {
        DefaultTableModel model = (DefaultTableModel) tblRequests.getModel(); model.setRowCount(0);
        for (VerificationReview review : complianceDirectory.getReviewList()) {
            model.addRow(new Object[]{review.getRequest().getVerificationRequestId(),
                review.getRequest().getAssignment().getAssignmentId(),
                review.getRequest().getAssignment().getContractor().getContractorId(),
                review.getRequest().getAssignment().getContractor().getFullName(),
                review.getWorkflowStatus(), review.getWaitingOn(),
                review.getRequest().getVerificationType(),
                review.getAssignedAnalyst() == null
                        ? "Unassigned" : review.getAssignedAnalyst().getName()});
        }
    }

    private void refreshManagerData() {
        if (complianceData != null) {
            complianceData.syncAnalystsFromNetwork(network);
        }
        populateTable();
        populateAnalystList();
        populateWorkloadTable();
    }

    private void populateAnalystList() {
        Object selected = cmbAnalyst.getSelectedItem();
        cmbAnalyst.removeAllItems();
        for (ComplianceUser user : complianceDirectory.getUserList()) {
            if (user instanceof ComplianceAnalyst && user.isActive()) {
                cmbAnalyst.addItem((ComplianceAnalyst) user);
            }
        }
        if (selected != null) {
            cmbAnalyst.setSelectedItem(selected);
        }
    }

    private void populateWorkloadTable() {
        DefaultTableModel model =
                (DefaultTableModel) tblWorkload.getModel();
        model.setRowCount(0);
        for (ComplianceUser user : complianceDirectory.getUserList()) {
            if (!(user instanceof ComplianceAnalyst)) {
                continue;
            }
            ComplianceAnalyst analyst = (ComplianceAnalyst) user;
            int assigned = 0;
            int inProgress = 0;
            int completed = 0;
            for (VerificationReview review
                    : complianceDirectory.getReviewList()) {
                if (review.getAssignedAnalyst() == analyst) {
                    assigned++;
                    if (review.getDecision()
                            == ComplianceDecision.PENDING) {
                        inProgress++;
                    } else {
                        completed++;
                    }
                }
            }
            model.addRow(new Object[]{
                analyst.getEmployeeId(), analyst.getName(),
                analyst.getUsername(), assigned, inProgress, completed
            });
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        lblTitle = new javax.swing.JLabel(); lblOrganization = new javax.swing.JLabel(); btnView = new javax.swing.JButton(); btnManage = new javax.swing.JButton();
        btnQueue = new javax.swing.JButton(); btnReports = new javax.swing.JButton(); jScrollPane1 = new javax.swing.JScrollPane(); tblRequests = new javax.swing.JTable();
        btnBack = new javax.swing.JButton(); btnRefresh = new javax.swing.JButton();
        lblAssign = new javax.swing.JLabel(); cmbAnalyst = new javax.swing.JComboBox<>();
        btnAssign = new javax.swing.JButton(); jScrollPane2 = new javax.swing.JScrollPane();
        tblWorkload = new javax.swing.JTable();
        lblNextStep = new javax.swing.JLabel(); lblCaseStatus = new javax.swing.JLabel();
        setBackground(new java.awt.Color(255, 255, 204));
        lblTitle.setFont(new java.awt.Font("Myanmar Sangam MN", 3, 18)); lblTitle.setText("Compliance Manager Work Area");
        lblOrganization.setFont(new java.awt.Font("Myanmar MN", 0, 13));
        lblOrganization.setForeground(new java.awt.Color(102, 102, 102));
        lblOrganization.setText("Compliance Enterprise - Compliance Oversight"); btnView.setText("View Request Details"); btnQueue.setText("Show All Requests"); btnManage.setText("Show Needs Attention"); btnReports.setText("Compliance Report");
        lblNextStep.setFont(new java.awt.Font("Segoe UI", 1, 13));
        lblCaseStatus.setForeground(new java.awt.Color(0, 102, 153));
        btnBack.setText("Back");
        btnRefresh.setText("Refresh");
        tblRequests.setModel(new DefaultTableModel(new Object[][]{}, new String[]{"Request ID", "Assignment ID", "Contractor ID", "Contractor", "Workflow Status", "Waiting On", "Verification Type", "Assigned Analyst"}) { public boolean isCellEditable(int r, int c) { return false; }});
        tblRequests.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION); jScrollPane1.setViewportView(tblRequests);
        lblAssign.setText("Assign selected request to:");
        btnAssign.setText("Assign / Reassign");
        tblWorkload.setModel(new DefaultTableModel(new Object[][]{}, new String[]{"Employee ID", "Analyst", "Username", "Assigned", "In Progress", "Completed"}) { public boolean isCellEditable(int r, int c) { return false; }});
        tblWorkload.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setBorder(javax.swing.BorderFactory.createTitledBorder("Team Workload"));
        jScrollPane2.setViewportView(tblWorkload);
        btnView.addActionListener(evt -> btnViewActionPerformed(evt));
        btnQueue.addActionListener(evt -> btnQueueActionPerformed(evt));
        btnManage.addActionListener(evt -> btnManageActionPerformed(evt));
        btnReports.addActionListener(evt -> btnReportsActionPerformed(evt));
        btnRefresh.addActionListener(evt -> btnRefreshActionPerformed(evt));
        btnBack.addActionListener(evt -> btnBackActionPerformed(evt));
        btnAssign.addActionListener(evt -> btnAssignActionPerformed(evt));
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this); setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(32).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTitle).addComponent(lblOrganization).addComponent(lblNextStep).addComponent(lblCaseStatus).addGroup(layout.createSequentialGroup().addComponent(btnView, 180, 180, 180).addGap(18).addComponent(btnQueue, 180, 180, 180))
            .addGroup(layout.createSequentialGroup().addComponent(btnManage, 180, 180, 180).addGap(18).addComponent(btnReports, 180, 180, 180))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 820, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup().addComponent(lblAssign).addGap(12).addComponent(cmbAnalyst, 220, 220, 220).addGap(12).addComponent(btnAssign))
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 820, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup().addComponent(btnBack, 390, 390, Short.MAX_VALUE).addGap(18).addComponent(btnRefresh, 390, 390, Short.MAX_VALUE))).addGap(32)));
        layout.setVerticalGroup(layout.createSequentialGroup().addGap(22).addComponent(lblTitle).addGap(4).addComponent(lblOrganization).addGap(8).addComponent(lblNextStep).addGap(4).addComponent(lblCaseStatus).addGap(12)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(btnView).addComponent(btnQueue)).addGap(12)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(btnManage).addComponent(btnReports)).addGap(18)
            .addComponent(jScrollPane1, 190, 190, Short.MAX_VALUE).addGap(10)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(lblAssign).addComponent(cmbAnalyst).addComponent(btnAssign)).addGap(10)
            .addComponent(jScrollPane2, 150, 150, Short.MAX_VALUE).addGap(12)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(btnBack).addComponent(btnRefresh)).addGap(18));
    }// </editor-fold>//GEN-END:initComponents

    private void btnViewActionPerformed(java.awt.event.ActionEvent evt) { viewRequest(); }

    private void btnQueueActionPerformed(java.awt.event.ActionEvent evt) {
        populateTable();
        tblRequests.clearSelection();
    }

    private void btnManageActionPerformed(java.awt.event.ActionEvent evt) {
        DefaultTableModel model = (DefaultTableModel) tblRequests.getModel();
        model.setRowCount(0);
        for (VerificationReview review : complianceDirectory.getReviewList()) {
            if (review.getAssignedAnalyst() == null
                    || review.getDecision() == ComplianceDecision.REJECTED) {
                model.addRow(new Object[]{
                    review.getRequest().getVerificationRequestId(),
                    review.getRequest().getAssignment().getAssignmentId(),
                    review.getRequest().getAssignment().getContractor().getContractorId(),
                    review.getRequest().getAssignment().getContractor().getFullName(),
                    review.getWorkflowStatus(),
                    review.getWaitingOn(),
                    review.getRequest().getVerificationType(),
                    review.getAssignedAnalyst() == null
                            ? "Unassigned" : review.getAssignedAnalyst().getName()
                });
            }
        }
        tblRequests.clearSelection();
        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this,
                    "There are no unassigned or rejected requests.",
                    "No Requests Need Attention",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void btnReportsActionPerformed(java.awt.event.ActionEvent evt) {
        javax.swing.JPanel nextPanel = new ComplianceReportJPanel(container, complianceDirectory, this);
        if (container == null) { JOptionPane.showMessageDialog(this, nextPanel, "Compliance Report", JOptionPane.PLAIN_MESSAGE); return; }
        container.removeAll(); container.setLayout(new java.awt.CardLayout()); container.add(nextPanel, "ComplianceReport");
        ((java.awt.CardLayout) container.getLayout()).show(container, "ComplianceReport"); container.revalidate(); container.repaint();
    }

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {
        refreshManagerData();
        tblRequests.clearSelection();
    }

    private void btnAssignActionPerformed(java.awt.event.ActionEvent evt) {
        int row = tblRequests.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this,
                    "Select a request from the table first.",
                    "No Request Selected",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        ComplianceAnalyst analyst =
                (ComplianceAnalyst) cmbAnalyst.getSelectedItem();
        if (analyst == null) {
            JOptionPane.showMessageDialog(this,
                    "No active Compliance Analyst is available.",
                    "Analyst Required",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        Object requestId = tblRequests.getValueAt(row, 0);
        for (VerificationReview review
                : complianceDirectory.getReviewList()) {
            if (String.valueOf(review.getRequest()
                    .getVerificationRequestId())
                    .equals(String.valueOf(requestId))) {
                try {
                    review.assignAnalyst(analyst);
                    refreshManagerData();
                    JOptionPane.showMessageDialog(this,
                            "Assigned to " + analyst.getName()
                            + ". The case is now available in the analyst's active queue.");
                } catch (IllegalArgumentException
                        | IllegalStateException ex) {
                    JOptionPane.showMessageDialog(this,
                            ex.getMessage(), "Assignment",
                            JOptionPane.WARNING_MESSAGE);
                }
                return;
            }
        }
    }

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {
        if (container != null && previousPanel != null) {
            container.removeAll(); container.setLayout(new java.awt.CardLayout()); container.add(previousPanel, "PreviousPanel");
            ((java.awt.CardLayout) container.getLayout()).show(container, "PreviousPanel"); container.revalidate(); container.repaint();
        }
    }

    private void viewRequest() {
        int row = tblRequests.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Select a request from the table first.", "No Request Selected", JOptionPane.WARNING_MESSAGE); return; }
        Object requestId = tblRequests.getValueAt(row, 0);
        for (VerificationReview review : complianceDirectory.getReviewList()) {
            if (String.valueOf(review.getRequest().getVerificationRequestId()).equals(String.valueOf(requestId))) {
                javax.swing.JPanel nextPanel = new ComplianceRequestDetailsJPanel(container, review, this);
                if (container == null) { JOptionPane.showMessageDialog(this, nextPanel, "Request Details", JOptionPane.PLAIN_MESSAGE); return; }
                container.removeAll(); container.setLayout(new java.awt.CardLayout()); container.add(nextPanel, "RequestDetails");
                ((java.awt.CardLayout) container.getLayout()).show(container, "RequestDetails"); container.revalidate(); container.repaint(); return;
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAssign, btnBack, btnManage, btnQueue, btnRefresh, btnReports, btnView;
    private javax.swing.JComboBox<ComplianceAnalyst> cmbAnalyst;
    private javax.swing.JScrollPane jScrollPane1, jScrollPane2;
    private javax.swing.JLabel lblAssign, lblCaseStatus, lblNextStep, lblOrganization, lblTitle;
    private javax.swing.JTable tblRequests, tblWorkload;
    // End of variables declaration//GEN-END:variables
}
