package UserInterface.Compliance;

import ComplianceEnterprise.Model.ComplianceDirectory;
import ComplianceEnterprise.Model.VerificationReview;
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

    public ComplianceManagerWorkAreaJPanel(ComplianceDirectory complianceDirectory,
            ComplianceManager manager, java.util.List<Contractor> contractorList) {
        this(null, complianceDirectory, manager, contractorList, null);
    }

    public ComplianceManagerWorkAreaJPanel(javax.swing.JPanel container,
            ComplianceDirectory complianceDirectory, ComplianceManager manager,
            java.util.List<Contractor> contractorList, javax.swing.JPanel previousPanel) {
        this.container = container;
        this.previousPanel = previousPanel;
        this.complianceDirectory = complianceDirectory; this.manager = manager; this.contractorList = contractorList;
        initComponents(); lblOrganization.setText("Compliance Enterprise - Credential Management Organization"); populateTable();
    }

    private void populateTable() {
        DefaultTableModel model = (DefaultTableModel) tblRequests.getModel(); model.setRowCount(0);
        for (VerificationReview review : complianceDirectory.getReviewList()) {
            model.addRow(new Object[]{review.getRequest().getVerificationRequestId(),
                review.getRequest().getAssignment().getAssignmentId(),
                review.getRequest().getAssignment().getContractor().getContractorId(),
                review.getRequest().getAssignment().getContractor().getFullName(),
                review.getDecision(), review.getRequest().getVerificationType()});
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        lblTitle = new javax.swing.JLabel(); lblOrganization = new javax.swing.JLabel(); btnView = new javax.swing.JButton(); btnManage = new javax.swing.JButton();
        btnQueue = new javax.swing.JButton(); btnReports = new javax.swing.JButton(); jScrollPane1 = new javax.swing.JScrollPane(); tblRequests = new javax.swing.JTable();
        btnBack = new javax.swing.JButton(); btnRefresh = new javax.swing.JButton();
        setBackground(new java.awt.Color(255, 255, 204));
        lblTitle.setFont(new java.awt.Font("Myanmar Sangam MN", 3, 18)); lblTitle.setText("Compliance Manager Work Area");
        lblOrganization.setFont(new java.awt.Font("Myanmar MN", 0, 13));
        lblOrganization.setForeground(new java.awt.Color(102, 102, 102));
        lblOrganization.setText("Compliance Enterprise - Credential Management Organization"); btnView.setText("View Request"); btnQueue.setText("Review Queue"); btnManage.setText("Manage Records"); btnReports.setText("Reports");
        tblRequests.setModel(new DefaultTableModel(new Object[][]{}, new String[]{"Request ID", "Assignment ID", "Contractor ID", "Contractor", "Status", "Verification Type"}) { public boolean isCellEditable(int r, int c) { return false; }});
        tblRequests.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION); jScrollPane1.setViewportView(tblRequests);
        btnView.addActionListener(evt -> btnViewActionPerformed(evt));
        btnQueue.addActionListener(evt -> btnQueueActionPerformed(evt));
        btnManage.addActionListener(evt -> btnManageActionPerformed(evt));
        btnReports.addActionListener(evt -> btnReportsActionPerformed(evt));
        btnRefresh.addActionListener(evt -> btnRefreshActionPerformed(evt));
        btnBack.addActionListener(evt -> btnBackActionPerformed(evt));
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this); setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(32).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTitle).addComponent(lblOrganization).addGroup(layout.createSequentialGroup().addComponent(btnView, 130, 130, 130).addGap(18).addComponent(btnQueue, 130, 130, 130))
            .addGroup(layout.createSequentialGroup().addComponent(btnManage, 130, 130, 130).addGap(18).addComponent(btnReports, 130, 130, 130))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 820, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup().addComponent(btnBack, 390, 390, Short.MAX_VALUE).addGap(18).addComponent(btnRefresh, 390, 390, Short.MAX_VALUE))).addGap(32)));
        layout.setVerticalGroup(layout.createSequentialGroup().addGap(22).addComponent(lblTitle).addGap(4).addComponent(lblOrganization).addGap(18)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(btnView).addComponent(btnQueue)).addGap(12)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(btnManage).addComponent(btnReports)).addGap(18)
            .addComponent(jScrollPane1, 260, 260, Short.MAX_VALUE).addGap(18)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(btnBack).addComponent(btnRefresh)).addGap(18));
    }// </editor-fold>//GEN-END:initComponents

    private void btnViewActionPerformed(java.awt.event.ActionEvent evt) { viewRequest(); }

    private void btnQueueActionPerformed(java.awt.event.ActionEvent evt) {
        javax.swing.JPanel nextPanel = new VerificationQueueJPanel(container, complianceDirectory, null, this);
        if (container == null) { JOptionPane.showMessageDialog(this, nextPanel, "Verification Queue", JOptionPane.PLAIN_MESSAGE); return; }
        container.removeAll(); container.setLayout(new java.awt.CardLayout()); container.add(nextPanel, "VerificationQueue");
        ((java.awt.CardLayout) container.getLayout()).show(container, "VerificationQueue"); container.revalidate(); container.repaint();
    }

    private void btnManageActionPerformed(java.awt.event.ActionEvent evt) {
        javax.swing.JPanel nextPanel = new CredentialManagementJPanel(container, complianceDirectory, contractorList, this);
        if (container == null) { JOptionPane.showMessageDialog(this, nextPanel, "Credential Management", JOptionPane.PLAIN_MESSAGE); return; }
        container.removeAll(); container.setLayout(new java.awt.CardLayout()); container.add(nextPanel, "CredentialManagement");
        ((java.awt.CardLayout) container.getLayout()).show(container, "CredentialManagement"); container.revalidate(); container.repaint();
    }

    private void btnReportsActionPerformed(java.awt.event.ActionEvent evt) {
        javax.swing.JPanel nextPanel = new ComplianceReportJPanel(container, complianceDirectory, this);
        if (container == null) { JOptionPane.showMessageDialog(this, nextPanel, "Compliance Report", JOptionPane.PLAIN_MESSAGE); return; }
        container.removeAll(); container.setLayout(new java.awt.CardLayout()); container.add(nextPanel, "ComplianceReport");
        ((java.awt.CardLayout) container.getLayout()).show(container, "ComplianceReport"); container.revalidate(); container.repaint();
    }

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) { populateTable(); tblRequests.clearSelection(); }

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
    private javax.swing.JButton btnBack, btnManage, btnQueue, btnRefresh, btnReports, btnView; private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblOrganization, lblTitle; private javax.swing.JTable tblRequests;
    // End of variables declaration//GEN-END:variables
}
