package UserInterface.Compliance;

import ComplianceEnterprise.Enums.ComplianceDecision;
import ComplianceEnterprise.Enums.CredentialStatus;
import ComplianceEnterprise.Model.ComplianceDirectory;
import ComplianceEnterprise.Model.CredentialRecord;
import ComplianceEnterprise.Model.VerificationReview;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author janet
 */
public class ComplianceReportJPanel extends javax.swing.JPanel {

    private final ComplianceDirectory complianceDirectory;
    private final javax.swing.JPanel container;
    private final javax.swing.JPanel previousPanel;

    public ComplianceReportJPanel(ComplianceDirectory complianceDirectory) {
        this(null, complianceDirectory, null);
    }

    public ComplianceReportJPanel(javax.swing.JPanel container,
            ComplianceDirectory complianceDirectory, javax.swing.JPanel previousPanel) {
        this.container = container;
        this.complianceDirectory = complianceDirectory;
        this.previousPanel = previousPanel;
        initComponents();
        refreshReport();
    }

    private void refreshReport() {
        int pending = 0;
        int approved = 0;
        int rejected = 0;
        int expired = 0;

        DefaultTableModel model = (DefaultTableModel) tblSummary.getModel();
        model.setRowCount(0);

        for (VerificationReview review : complianceDirectory.getReviewList()) {
            if (review.getDecision() == ComplianceDecision.PENDING) pending++;
            if (review.getDecision() == ComplianceDecision.APPROVED) approved++;
            if (review.getDecision() == ComplianceDecision.REJECTED) rejected++;
        }
        for (CredentialRecord credential : complianceDirectory.getCredentialList()) {
            if (credential.getStatus() == CredentialStatus.EXPIRED) expired++;
        }

        int completed = approved + rejected;
        int total = complianceDirectory.getReviewList().size();
        double completionRate = total == 0 ? 0 : (completed * 100.0) / total;

        model.addRow(new Object[]{"Total Verification Requests", total});
        model.addRow(new Object[]{"Pending Reviews", pending});
        model.addRow(new Object[]{"Approved Reviews", approved});
        model.addRow(new Object[]{"Rejected Reviews", rejected});
        model.addRow(new Object[]{"Expired Credentials", expired});
        model.addRow(new Object[]{"Review Completion Rate", String.format("%.1f%%", completionRate)});
        txtPending.setText(String.valueOf(pending));
        txtApproved.setText(String.valueOf(approved));
        txtRejected.setText(String.valueOf(rejected));
        txtCompletionRate.setText(String.format("%.1f%%", completionRate));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        lblTitle = new javax.swing.JLabel(); jScrollPane1 = new javax.swing.JScrollPane(); tblSummary = new javax.swing.JTable(); pnlReportDetails = new javax.swing.JPanel();
        lblPending = new javax.swing.JLabel(); lblApproved = new javax.swing.JLabel(); lblRejected = new javax.swing.JLabel(); lblCompletionRate = new javax.swing.JLabel();
        txtPending = new javax.swing.JTextField(); txtApproved = new javax.swing.JTextField(); txtRejected = new javax.swing.JTextField(); txtCompletionRate = new javax.swing.JTextField(); btnRefresh = new javax.swing.JButton(); btnBack = new javax.swing.JButton();
        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 18)); lblTitle.setText("Compliance Performance Summary");
        tblSummary.setModel(new DefaultTableModel(new Object[][]{}, new String[]{"Performance Measure", "Value"}) { public boolean isCellEditable(int r, int c) { return false; } });
        jScrollPane1.setViewportView(tblSummary);
        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder("Compliance Metrics"));
        pnlReportDetails.setBorder(javax.swing.BorderFactory.createTitledBorder("Report Details"));
        lblPending.setText("Pending Reviews:"); lblApproved.setText("Approved Reviews:"); lblRejected.setText("Rejected Reviews:"); lblCompletionRate.setText("Completion Rate:");
        txtPending.setEditable(false); txtApproved.setEditable(false); txtRejected.setEditable(false); txtCompletionRate.setEditable(false);
        javax.swing.GroupLayout detailsLayout = new javax.swing.GroupLayout(pnlReportDetails); pnlReportDetails.setLayout(detailsLayout);
        detailsLayout.setHorizontalGroup(detailsLayout.createSequentialGroup().addGap(18)
            .addGroup(detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addComponent(lblPending).addComponent(lblApproved).addComponent(lblRejected).addComponent(lblCompletionRate))
            .addGap(18).addGroup(detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(txtPending, 140, 140, 140).addComponent(txtApproved, 140, 140, 140).addComponent(txtRejected, 140, 140, 140).addComponent(txtCompletionRate, 140, 140, 140))
            .addContainerGap(18, Short.MAX_VALUE));
        detailsLayout.setVerticalGroup(detailsLayout.createSequentialGroup().addGap(10)
            .addGroup(detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(lblPending).addComponent(txtPending)).addGap(8)
            .addGroup(detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(lblApproved).addComponent(txtApproved)).addGap(8)
            .addGroup(detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(lblRejected).addComponent(txtRejected)).addGap(8)
            .addGroup(detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(lblCompletionRate).addComponent(txtCompletionRate)).addContainerGap(12, Short.MAX_VALUE));
        btnRefresh.setText("Refresh Report"); btnRefresh.addActionListener(evt -> btnRefreshActionPerformed(evt));
        btnBack.setText("<< Back"); btnBack.addActionListener(evt -> btnBackActionPerformed(evt));
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this); setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(40)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(lblTitle).addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(pnlReportDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE).addGroup(layout.createSequentialGroup().addComponent(btnBack).addGap(12).addComponent(btnRefresh))).addContainerGap(40, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createSequentialGroup().addGap(30).addComponent(lblTitle).addGap(20).addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(15).addComponent(pnlReportDetails, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(12).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(btnBack).addComponent(btnRefresh)).addContainerGap(30, Short.MAX_VALUE));
    }// </editor-fold>//GEN-END:initComponents

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {
        refreshReport();
        tblSummary.clearSelection();
    }

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {
        if (container != null && previousPanel != null) {
            container.removeAll();
            container.setLayout(new java.awt.CardLayout());
            container.add(previousPanel, "PreviousPanel");
            ((java.awt.CardLayout) container.getLayout()).show(container, "PreviousPanel");
            container.revalidate();
            container.repaint();
        } else {
            java.awt.Window window = javax.swing.SwingUtilities.getWindowAncestor(this);
            if (window instanceof javax.swing.JDialog) window.dispose();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack, btnRefresh; private javax.swing.JScrollPane jScrollPane1; private javax.swing.JPanel pnlReportDetails;
    private javax.swing.JLabel lblTitle, lblPending, lblApproved, lblRejected, lblCompletionRate; private javax.swing.JTable tblSummary;
    private javax.swing.JTextField txtPending, txtApproved, txtRejected, txtCompletionRate;
    // End of variables declaration//GEN-END:variables
}
