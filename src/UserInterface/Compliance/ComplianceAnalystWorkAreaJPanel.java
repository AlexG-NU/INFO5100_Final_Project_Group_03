package UserInterface.Compliance;

import ComplianceEnterprise.Model.ComplianceDirectory;
import ComplianceEnterprise.Model.VerificationReview;
import ComplianceEnterprise.Role.ComplianceAnalyst;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author janet
 */
public class ComplianceAnalystWorkAreaJPanel extends javax.swing.JPanel {

    private final ComplianceDirectory complianceDirectory;
    private final ComplianceAnalyst analyst;
    private final javax.swing.JPanel container;
    private final javax.swing.JPanel previousPanel;

    public ComplianceAnalystWorkAreaJPanel(ComplianceDirectory complianceDirectory, ComplianceAnalyst analyst) {
        this(null, complianceDirectory, analyst, null);
    }

    public ComplianceAnalystWorkAreaJPanel(javax.swing.JPanel container,
            ComplianceDirectory complianceDirectory, ComplianceAnalyst analyst,
            javax.swing.JPanel previousPanel) {
        this.container = container; this.previousPanel = previousPanel;
        this.complianceDirectory = complianceDirectory; this.analyst = analyst;
        initComponents();
        btnBack.setVisible(previousPanel != null);
        configureTable();
        applyResponsiveLayout();
        populateActiveCases();
    }

    private void configureTable() {
        ComplianceTableUI.configure(tblRequests,
                90, 100, 100, 170, 170, 210, 150, 150, 130);
        lblNextStep.setVisible(false);
        lblCaseStatus.setText("Selected case: none");
        tblRequests.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                int row = tblRequests.getSelectedRow();
                lblCaseStatus.setText(row < 0 ? "Selected case: none"
                        : "Current step: " + tblRequests.getValueAt(row, 5)
                        + "  |  Waiting on: " + tblRequests.getValueAt(row, 6));
            }
        });
    }

    private void applyResponsiveLayout() {
        java.awt.Color background = new java.awt.Color(255, 255, 204);
        javax.swing.JPanel header = new javax.swing.JPanel();
        header.setBackground(background);
        header.setLayout(new javax.swing.BoxLayout(header, javax.swing.BoxLayout.Y_AXIS));
        header.add(lblTitle);
        header.add(javax.swing.Box.createVerticalStrut(4));
        header.add(lblOrganization);
        header.add(javax.swing.Box.createVerticalStrut(8));
        header.add(lblCaseStatus);
        header.add(javax.swing.Box.createVerticalStrut(10));
        header.add(managerStyleGrid(
                btnView, btnProcess, btnAssigned, btnReports));
        ComplianceTableUI.styleScrollPane(jScrollPane1, 360);
        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(
                "Compliance Review Workspace"));
        javax.swing.JPanel bottom = managerStyleGrid(btnBack, btnRefresh);
        btnBack.setVisible(previousPanel != null);
        removeAll();
        setLayout(new java.awt.BorderLayout());
        setBackground(background);
        setBorder(javax.swing.BorderFactory.createEmptyBorder(16, 24, 16, 24));
        add(header, java.awt.BorderLayout.NORTH);
        add(jScrollPane1, java.awt.BorderLayout.CENTER);
        add(bottom, java.awt.BorderLayout.SOUTH);
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

    private void populateActiveCases() {
        populateTable(false);
    }

    private void populateCompletedCases() {
        populateTable(true);
    }

    private void populateTable(boolean completedOnly) {
        DefaultTableModel model = (DefaultTableModel) tblRequests.getModel(); model.setRowCount(0);
        for (VerificationReview review : complianceDirectory.getReviewList()) {
            if (review.getAssignedAnalyst() != analyst
                    || (completedOnly && review.getDecision() == ComplianceEnterprise.Enums.ComplianceDecision.PENDING)
                    || (!completedOnly && review.getDecision() != ComplianceEnterprise.Enums.ComplianceDecision.PENDING)) {
                continue;
            }
            model.addRow(new Object[]{review.getRequest().getVerificationRequestId(),
                review.getRequest().getAssignment().getAssignmentId(),
                review.getRequest().getAssignment().getContractor().getContractorId(),
                review.getRequest().getAssignment().getContractor().getFullName(),
                review.getCredentialStatusText(), review.getWorkflowStatus(),
                review.getWaitingOn(), review.getCompletedByText(), review.getDecision()});
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        lblTitle = new javax.swing.JLabel(); lblOrganization = new javax.swing.JLabel(); btnView = new javax.swing.JButton(); btnProcess = new javax.swing.JButton();
        btnAssigned = new javax.swing.JButton(); btnReports = new javax.swing.JButton(); jScrollPane1 = new javax.swing.JScrollPane(); tblRequests = new javax.swing.JTable(); btnBack = new javax.swing.JButton(); btnRefresh = new javax.swing.JButton();
        lblNextStep = new javax.swing.JLabel(); lblCaseStatus = new javax.swing.JLabel();
        setBackground(new java.awt.Color(255, 255, 204));
        lblTitle.setFont(new java.awt.Font("Myanmar Sangam MN", 3, 18)); lblTitle.setText("Compliance Analyst Work Area");
        lblOrganization.setFont(new java.awt.Font("Myanmar MN", 0, 13));
        lblOrganization.setForeground(new java.awt.Color(102, 102, 102));
        lblOrganization.setText("Compliance Enterprise - Compliance Verification Organization");
        lblNextStep.setFont(new java.awt.Font("Segoe UI", 1, 13));
        lblCaseStatus.setForeground(new java.awt.Color(0, 102, 153));
        btnView.setText("View Request Details"); btnProcess.setText("Open Active Case"); btnAssigned.setText("Awaiting Credential Results"); btnReports.setText("Completed Cases");
        // Keep these assignments in the generated initialization block so the
        // runtime view stays synchronized with the labels stored in the .form.
        // Without them Swing creates two blank buttons even though NetBeans
        // Design view displays "Back" and "Refresh".
        btnBack.setText("Back");
        btnRefresh.setText("Refresh");
        tblRequests.setModel(new DefaultTableModel(new Object[][]{}, new String[]{"Request ID", "Assignment ID", "Contractor ID", "Contractor", "Credential Status", "Workflow Status", "Waiting On", "Completed By", "Decision"}) { public boolean isCellEditable(int r, int c) { return false; }});
        tblRequests.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION); jScrollPane1.setViewportView(tblRequests);
        btnView.addActionListener(evt -> btnViewActionPerformed(evt));
        btnProcess.addActionListener(evt -> btnProcessActionPerformed(evt));
        btnAssigned.addActionListener(evt -> btnAssignedActionPerformed(evt));
        btnReports.addActionListener(evt -> btnReportsActionPerformed(evt));
        btnRefresh.addActionListener(evt -> btnRefreshActionPerformed(evt));
        btnBack.addActionListener(evt -> btnBackActionPerformed(evt));
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this); setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(32).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTitle).addComponent(lblOrganization).addComponent(lblNextStep).addComponent(lblCaseStatus).addGroup(layout.createSequentialGroup().addComponent(btnView, 190, 190, 190).addGap(18).addComponent(btnProcess, 190, 190, 190))
            .addGroup(layout.createSequentialGroup().addComponent(btnAssigned, 210, 210, 210).addGap(18).addComponent(btnReports, 190, 190, 190))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 820, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addComponent(btnBack, 390, 390, Short.MAX_VALUE).addGap(18).addComponent(btnRefresh, 390, 390, Short.MAX_VALUE))).addGap(32)));
        layout.setVerticalGroup(layout.createSequentialGroup().addGap(22).addComponent(lblTitle).addGap(4).addComponent(lblOrganization).addGap(8).addComponent(lblNextStep).addGap(4).addComponent(lblCaseStatus).addGap(12)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(btnView).addComponent(btnProcess)).addGap(12)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(btnAssigned).addComponent(btnReports)).addGap(18)
            .addComponent(jScrollPane1, 260, 260, Short.MAX_VALUE).addGap(18).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(btnBack).addComponent(btnRefresh)).addGap(18));
    }// </editor-fold>//GEN-END:initComponents

    private void btnViewActionPerformed(java.awt.event.ActionEvent evt) { viewRequest(); }
    private void btnProcessActionPerformed(java.awt.event.ActionEvent evt) {
        javax.swing.JPanel nextPanel = new VerificationQueueJPanel(
                container, complianceDirectory, analyst, this, true);
        if (container == null) { JOptionPane.showMessageDialog(this, nextPanel, "Verification Queue", JOptionPane.PLAIN_MESSAGE); return; }
        container.removeAll(); container.setLayout(new java.awt.CardLayout()); container.add(nextPanel, "VerificationQueue");
        ((java.awt.CardLayout) container.getLayout()).show(container, "VerificationQueue"); container.revalidate(); container.repaint();
    }
    private void btnAssignedActionPerformed(java.awt.event.ActionEvent evt) {
        DefaultTableModel model = (DefaultTableModel) tblRequests.getModel();
        model.setRowCount(0);
        for (VerificationReview review : complianceDirectory.getReviewList()) {
            if (review.getAssignedAnalyst() == analyst
                    && review.getCredentialTask() != null
                    && !review.getCredentialTask().isComplete()) {
                model.addRow(new Object[]{
                    review.getRequest().getVerificationRequestId(),
                    review.getRequest().getAssignment().getAssignmentId(),
                    review.getRequest().getAssignment().getContractor().getContractorId(),
                    review.getRequest().getAssignment().getContractor().getFullName(),
                    review.getCredentialStatusText(), review.getWorkflowStatus(),
                    review.getWaitingOn(), review.getCompletedByText(), review.getDecision()
                });
            }
        }
        tblRequests.clearSelection();
        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this,
                    "None of your assigned reviews are waiting for a credential result.",
                    "No Pending Credential Results",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
    private void btnReportsActionPerformed(java.awt.event.ActionEvent evt) {
        populateCompletedCases();
        tblRequests.clearSelection();
    }
    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) { populateActiveCases(); tblRequests.clearSelection(); }
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

    private VerificationReview getSelectedReview() {
        int row = tblRequests.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a request from the table first.",
                    "No Request Selected", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        Object requestId = tblRequests.getValueAt(row, 0);
        for (VerificationReview review : complianceDirectory.getReviewList()) {
            if (String.valueOf(review.getRequest().getVerificationRequestId())
                    .equals(String.valueOf(requestId))) {
                return review;
            }
        }
        JOptionPane.showMessageDialog(this,
                "The selected request could not be found. Click Refresh and try again.",
                "Request Not Found", JOptionPane.ERROR_MESSAGE);
        return null;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAssigned, btnBack, btnProcess, btnRefresh, btnReports, btnView; private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCaseStatus, lblNextStep, lblOrganization, lblTitle; private javax.swing.JTable tblRequests;
    // End of variables declaration//GEN-END:variables
}
