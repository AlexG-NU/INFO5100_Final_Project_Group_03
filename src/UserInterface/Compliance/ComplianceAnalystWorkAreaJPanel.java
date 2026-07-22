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
        this.complianceDirectory = complianceDirectory; this.analyst = analyst; initComponents(); populateTable();
    }

    private void populateTable() {
        DefaultTableModel model = (DefaultTableModel) tblRequests.getModel(); model.setRowCount(0);
        for (VerificationReview review : complianceDirectory.getReviewList()) {
            model.addRow(new Object[]{review.getRequest().getVerificationRequestId(),
                review.getRequest().getAssignment().getAssignmentId(),
                review.getRequest().getAssignment().getContractor().getContractorId(),
                review.getRequest().getAssignment().getContractor().getFullName(),
                review.getDecision(), review.getAssignedAnalyst() == null ? "Unassigned" : review.getAssignedAnalyst().getName()});
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        lblTitle = new javax.swing.JLabel(); lblOrganization = new javax.swing.JLabel(); btnView = new javax.swing.JButton(); btnProcess = new javax.swing.JButton();
        btnAssigned = new javax.swing.JButton(); btnReports = new javax.swing.JButton(); jScrollPane1 = new javax.swing.JScrollPane(); tblRequests = new javax.swing.JTable(); btnBack = new javax.swing.JButton(); btnRefresh = new javax.swing.JButton();
        setBackground(new java.awt.Color(255, 255, 204));
        lblTitle.setFont(new java.awt.Font("Myanmar Sangam MN", 3, 18)); lblTitle.setText("Compliance Analyst Work Area");
        lblOrganization.setFont(new java.awt.Font("Myanmar MN", 0, 13));
        lblOrganization.setForeground(new java.awt.Color(102, 102, 102));
        lblOrganization.setText("Compliance Enterprise - Compliance Verification Organization");
        btnView.setText("View Request"); btnProcess.setText("Process Request"); btnAssigned.setText("My Assigned Work"); btnReports.setText("Reports");
        tblRequests.setModel(new DefaultTableModel(new Object[][]{}, new String[]{"Request ID", "Assignment ID", "Contractor ID", "Contractor", "Status", "Assigned Analyst"}) { public boolean isCellEditable(int r, int c) { return false; }});
        tblRequests.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION); jScrollPane1.setViewportView(tblRequests);
        btnView.addActionListener(evt -> btnViewActionPerformed(evt));
        btnProcess.addActionListener(evt -> btnProcessActionPerformed(evt));
        btnAssigned.addActionListener(evt -> btnAssignedActionPerformed(evt));
        btnReports.addActionListener(evt -> btnReportsActionPerformed(evt));
        btnRefresh.addActionListener(evt -> btnRefreshActionPerformed(evt));
        btnBack.addActionListener(evt -> btnBackActionPerformed(evt));
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this); setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(32).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTitle).addComponent(lblOrganization).addGroup(layout.createSequentialGroup().addComponent(btnView, 135, 135, 135).addGap(18).addComponent(btnProcess, 135, 135, 135))
            .addGroup(layout.createSequentialGroup().addComponent(btnAssigned, 135, 135, 135).addGap(18).addComponent(btnReports, 135, 135, 135))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 820, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addComponent(btnBack, 390, 390, Short.MAX_VALUE).addGap(18).addComponent(btnRefresh, 390, 390, Short.MAX_VALUE))).addGap(32)));
        layout.setVerticalGroup(layout.createSequentialGroup().addGap(22).addComponent(lblTitle).addGap(4).addComponent(lblOrganization).addGap(18)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(btnView).addComponent(btnProcess)).addGap(12)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(btnAssigned).addComponent(btnReports)).addGap(18)
            .addComponent(jScrollPane1, 260, 260, Short.MAX_VALUE).addGap(18).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(btnBack).addComponent(btnRefresh)).addGap(18));
    }// </editor-fold>//GEN-END:initComponents

    private void btnViewActionPerformed(java.awt.event.ActionEvent evt) { viewRequest(); }
    private void btnProcessActionPerformed(java.awt.event.ActionEvent evt) {
        javax.swing.JPanel nextPanel = new VerificationQueueJPanel(container, complianceDirectory, analyst, this);
        if (container == null) { JOptionPane.showMessageDialog(this, nextPanel, "Verification Queue", JOptionPane.PLAIN_MESSAGE); return; }
        container.removeAll(); container.setLayout(new java.awt.CardLayout()); container.add(nextPanel, "VerificationQueue");
        ((java.awt.CardLayout) container.getLayout()).show(container, "VerificationQueue"); container.revalidate(); container.repaint();
    }
    private void btnAssignedActionPerformed(java.awt.event.ActionEvent evt) {
        javax.swing.JPanel nextPanel = new VerificationQueueJPanel(container, complianceDirectory, analyst, this);
        if (container == null) { JOptionPane.showMessageDialog(this, nextPanel, "Assigned Verification Queue", JOptionPane.PLAIN_MESSAGE); return; }
        container.removeAll(); container.setLayout(new java.awt.CardLayout()); container.add(nextPanel, "AssignedVerificationQueue");
        ((java.awt.CardLayout) container.getLayout()).show(container, "AssignedVerificationQueue"); container.revalidate(); container.repaint();
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
    private javax.swing.JButton btnAssigned, btnBack, btnProcess, btnRefresh, btnReports, btnView; private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblOrganization, lblTitle; private javax.swing.JTable tblRequests;
    // End of variables declaration//GEN-END:variables
}
