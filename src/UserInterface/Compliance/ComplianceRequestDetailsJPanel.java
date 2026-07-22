package UserInterface.Compliance;

import ComplianceEnterprise.Model.VerificationReview;
import java.awt.CardLayout;

/** Physical read-only panel for a selected cross-enterprise verification request. */
public class ComplianceRequestDetailsJPanel extends javax.swing.JPanel {
    private final javax.swing.JPanel container;
    private final javax.swing.JPanel previousPanel;
    private final VerificationReview review;

    public ComplianceRequestDetailsJPanel(javax.swing.JPanel container, VerificationReview review,
            javax.swing.JPanel previousPanel) {
        this.container = container;
        this.review = review;
        this.previousPanel = previousPanel;
        initComponents();
        displayRequest();
    }

    private void displayRequest() {
        if (review == null || review.getRequest() == null) {
            return;
        }
        txtRequestId.setText(String.valueOf(review.getRequest().getVerificationRequestId()));
        if (review.getRequest().getAssignment() != null) {
            txtAssignmentId.setText(String.valueOf(
                    review.getRequest().getAssignment().getAssignmentId()));
            if (review.getRequest().getAssignment().getContractor() != null) {
                txtContractorId.setText(String.valueOf(review.getRequest()
                        .getAssignment().getContractor().getContractorId()));
                txtContractor.setText(review.getRequest().getAssignment()
                        .getContractor().getFullName());
            }
        }
        txtVerificationType.setText(review.getRequest().getVerificationType());
        txtAnalyst.setText(review.getAssignedAnalyst() == null ? "Unassigned" : review.getAssignedAnalyst().getName());
        txtStatus.setText(String.valueOf(review.getDecision()));
        txtFindings.setText(review.getFindings());
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tblRequests.getModel();
        model.setRowCount(0);
        model.addRow(new Object[]{txtRequestId.getText(), txtAssignmentId.getText(),
            txtContractorId.getText(), txtContractor.getText(), txtVerificationType.getText(),
            txtAnalyst.getText(), txtStatus.getText()});
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        lblTitle = new javax.swing.JLabel();
        jScrollPaneTable = new javax.swing.JScrollPane();
        tblRequests = new javax.swing.JTable();
        pnlDetails = new javax.swing.JPanel();
        lblRequestId = new javax.swing.JLabel(); lblAssignmentId = new javax.swing.JLabel(); lblContractorId = new javax.swing.JLabel();
        lblContractor = new javax.swing.JLabel(); lblVerificationType = new javax.swing.JLabel(); lblAnalyst = new javax.swing.JLabel();
        lblStatus = new javax.swing.JLabel(); lblFindings = new javax.swing.JLabel();
        txtRequestId = new javax.swing.JTextField(); txtAssignmentId = new javax.swing.JTextField();
        txtContractorId = new javax.swing.JTextField(); txtContractor = new javax.swing.JTextField();
        txtVerificationType = new javax.swing.JTextField(); txtAnalyst = new javax.swing.JTextField();
        txtStatus = new javax.swing.JTextField(); txtFindings = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane(); btnBack = new javax.swing.JButton();
        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 18)); lblTitle.setText("Verification Request Details");
        tblRequests.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {"Request ID", "Assignment ID", "Contractor ID", "Contractor", "Verification Type", "Assigned Analyst", "Status"}
        ) {
            boolean[] canEdit = new boolean [] {false, false, false, false, false, false, false};
            public boolean isCellEditable(int rowIndex, int columnIndex) { return canEdit[columnIndex]; }
        });
        jScrollPaneTable.setBorder(javax.swing.BorderFactory.createTitledBorder("Verification Request"));
        jScrollPaneTable.setViewportView(tblRequests);
        lblRequestId.setText("Request ID:"); lblAssignmentId.setText("Assignment ID:"); lblContractorId.setText("Contractor ID:");
        lblContractor.setText("Contractor:"); lblVerificationType.setText("Verification Type:"); lblAnalyst.setText("Assigned Analyst:");
        lblStatus.setText("Status:"); lblFindings.setText("Findings:"); pnlDetails.setBorder(javax.swing.BorderFactory.createTitledBorder("Selected Request"));
        txtRequestId.setEditable(false); txtAssignmentId.setEditable(false);
        txtContractorId.setEditable(false); txtContractor.setEditable(false);
        txtVerificationType.setEditable(false); txtAnalyst.setEditable(false);
        txtStatus.setEditable(false); txtFindings.setEditable(false);
        txtFindings.setColumns(25); txtFindings.setRows(4);
        jScrollPane1.setViewportView(txtFindings);
        btnBack.setText("<< Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });
        javax.swing.GroupLayout p = new javax.swing.GroupLayout(pnlDetails); pnlDetails.setLayout(p);
        p.setHorizontalGroup(p.createSequentialGroup().addGap(18).addGroup(p.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(lblRequestId).addComponent(lblAssignmentId).addComponent(lblContractorId).addComponent(lblContractor)
            .addComponent(lblVerificationType).addComponent(lblAnalyst).addComponent(lblStatus).addComponent(lblFindings)).addGap(18)
            .addGroup(p.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(txtRequestId,260,260,260).addComponent(txtAssignmentId,260,260,260)
            .addComponent(txtContractorId,260,260,260).addComponent(txtContractor,260,260,260).addComponent(txtVerificationType,260,260,260)
            .addComponent(txtAnalyst,260,260,260).addComponent(txtStatus,260,260,260).addComponent(jScrollPane1,390,390,390)).addGap(18));
        p.setVerticalGroup(p.createSequentialGroup().addGap(12).addGroup(row(p,lblRequestId,txtRequestId)).addGap(8).addGroup(row(p,lblAssignmentId,txtAssignmentId))
            .addGap(8).addGroup(row(p,lblContractorId,txtContractorId)).addGap(8).addGroup(row(p,lblContractor,txtContractor))
            .addGap(8).addGroup(row(p,lblVerificationType,txtVerificationType)).addGap(8).addGroup(row(p,lblAnalyst,txtAnalyst))
            .addGap(8).addGroup(row(p,lblStatus,txtStatus)).addGap(8).addGroup(p.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(lblFindings).addComponent(jScrollPane1,80,80,80)).addGap(12));
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this); setLayout(layout);
        layout.setHorizontalGroup(layout.createSequentialGroup().addGap(35).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(lblTitle).addComponent(jScrollPaneTable, 820, 820, Short.MAX_VALUE).addComponent(pnlDetails).addComponent(btnBack)).addGap(35));
        layout.setVerticalGroup(layout.createSequentialGroup().addGap(25).addComponent(lblTitle).addGap(15).addComponent(jScrollPaneTable, 150, 150, 150).addGap(12).addComponent(pnlDetails).addGap(12).addComponent(btnBack).addGap(25));
    }// </editor-fold>//GEN-END:initComponents
    private javax.swing.GroupLayout.ParallelGroup row(javax.swing.GroupLayout p,
            javax.swing.JComponent label, javax.swing.JComponent field) {
        return p.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(label).addComponent(field);
    }
    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {
        if (container == null || previousPanel == null) {
            return;
        }
        container.removeAll();
        container.setLayout(new CardLayout());
        container.add(previousPanel, "PreviousPanel");
        ((CardLayout) container.getLayout()).show(container, "PreviousPanel");
        container.revalidate();
        container.repaint();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack; private javax.swing.JScrollPane jScrollPane1; private javax.swing.JScrollPane jScrollPaneTable; private javax.swing.JTable tblRequests; private javax.swing.JPanel pnlDetails;
    private javax.swing.JLabel lblTitle,lblRequestId,lblAssignmentId,lblContractorId,lblContractor,lblVerificationType,lblAnalyst,lblStatus,lblFindings;
    private javax.swing.JTextField txtRequestId,txtAssignmentId,txtContractorId,txtContractor,txtVerificationType,txtAnalyst,txtStatus; private javax.swing.JTextArea txtFindings;
    // End of variables declaration//GEN-END:variables
}
