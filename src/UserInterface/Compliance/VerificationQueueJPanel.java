package UserInterface.Compliance;

import ComplianceEnterprise.Enums.ComplianceDecision;
import ComplianceEnterprise.Model.ComplianceDirectory;
import ComplianceEnterprise.Model.VerificationReview;
import ComplianceEnterprise.Role.ComplianceAnalyst;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author janet
 */
public class VerificationQueueJPanel extends javax.swing.JPanel {

    private final ComplianceDirectory complianceDirectory;
    private final ComplianceAnalyst analyst;
    private final javax.swing.JPanel container;
    private final javax.swing.JPanel previousPanel;

    public VerificationQueueJPanel(ComplianceDirectory complianceDirectory,
            ComplianceAnalyst analyst) {
        this(null, complianceDirectory, analyst, null);
    }

    public VerificationQueueJPanel(javax.swing.JPanel container,
            ComplianceDirectory complianceDirectory, ComplianceAnalyst analyst,
            javax.swing.JPanel previousPanel) {
        this.container = container;
        this.complianceDirectory = complianceDirectory;
        this.analyst = analyst;
        this.previousPanel = previousPanel;
        initComponents();
        populateTable();
    }

    private void populateTable() {
        DefaultTableModel model = (DefaultTableModel) tblRequests.getModel();
        model.setRowCount(0);

        for (VerificationReview review : complianceDirectory.getReviewList()) {
            Object[] row = new Object[8];
            row[0] = review;
            row[1] = review.getRequest().getVerificationRequestId();
            row[2] = review.getRequest().getAssignment().getAssignmentId();
            row[3] = review.getRequest().getAssignment().getContractor().getContractorId();
            row[4] = review.getRequest().getAssignment().getContractor().getFullName();
            row[5] = review.getRequest().getVerificationType();
            row[6] = review.getAssignedAnalyst() == null
                    ? "Unassigned" : review.getAssignedAnalyst().getName();
            row[7] = review.getDecision();
            model.addRow(row);
        }
    }

    private VerificationReview getSelectedReview() {
        int selectedRow = tblRequests.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Select a verification request.");
            return null;
        }
        return (VerificationReview) tblRequests.getValueAt(selectedRow, 0);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitle = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblRequests = new javax.swing.JTable();
        btnAssignToMe = new javax.swing.JButton();
        lblDecision = new javax.swing.JLabel();
        cmbDecision = new javax.swing.JComboBox<>();
        lblFindings = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtFindings = new javax.swing.JTextArea();
        btnCompleteReview = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        pnlReviewDetails = new javax.swing.JPanel();

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 18));
        lblTitle.setText("Credential Verification Request Queue");

        tblRequests.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {"Review", "Request ID", "Assignment ID", "Contractor ID", "Contractor", "Verification Type", "Assigned Analyst", "Decision"}
        ) {
            boolean[] canEdit = new boolean [] {false, false, false, false, false, false, false, false};
            public boolean isCellEditable(int rowIndex, int columnIndex) { return canEdit[columnIndex]; }
        });
        jScrollPane1.setViewportView(tblRequests);
        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder("Verification Requests"));
        tblRequests.getColumnModel().getColumn(0).setMinWidth(0);
        tblRequests.getColumnModel().getColumn(0).setPreferredWidth(0);
        tblRequests.getColumnModel().getColumn(0).setMaxWidth(0);
        tblRequests.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblRequests.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadSelectedReview();
            }
        });

        btnAssignToMe.setText("Assign to Me");
        btnAssignToMe.addActionListener(evt -> btnAssignToMeActionPerformed(evt));
        lblDecision.setText("Decision:");
        cmbDecision.setModel(new javax.swing.DefaultComboBoxModel<>(ComplianceDecision.values()));
        lblFindings.setText("Findings:");
        txtFindings.setColumns(20);
        txtFindings.setRows(5);
        jScrollPane2.setViewportView(txtFindings);
        btnCompleteReview.setText("Complete Review");
        btnCompleteReview.addActionListener(evt -> btnCompleteReviewActionPerformed(evt));
        btnRefresh.setText("Refresh");
        btnRefresh.addActionListener(evt -> btnRefreshActionPerformed(evt));
        btnBack.setText("<< Back");
        btnBack.addActionListener(evt -> btnBackActionPerformed(evt));
        if (analyst == null) {
            btnAssignToMe.setEnabled(false);
            btnCompleteReview.setEnabled(false);
            cmbDecision.setEnabled(false);
            txtFindings.setEditable(false);
        }

        pnlReviewDetails.setBorder(javax.swing.BorderFactory.createTitledBorder("Selected Verification Details"));
        javax.swing.GroupLayout detailsLayout = new javax.swing.GroupLayout(pnlReviewDetails);
        pnlReviewDetails.setLayout(detailsLayout);
        detailsLayout.setHorizontalGroup(detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detailsLayout.createSequentialGroup().addGap(18, 18, 18)
                .addGroup(detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblDecision).addComponent(lblFindings))
                .addGap(18, 18, 18)
                .addGroup(detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbDecision, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(detailsLayout.createSequentialGroup().addComponent(btnAssignToMe)
                        .addGap(12, 12, 12).addComponent(btnCompleteReview)))
                .addContainerGap(18, Short.MAX_VALUE)));
        detailsLayout.setVerticalGroup(detailsLayout.createSequentialGroup().addGap(12, 12, 12)
            .addGroup(detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(lblDecision).addComponent(cmbDecision, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(10, 10, 10)
            .addGroup(detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(lblFindings).addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(12, 12, 12)
            .addGroup(detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btnAssignToMe).addComponent(btnCompleteReview))
            .addContainerGap(12, Short.MAX_VALUE));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup().addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitle)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 820, Short.MAX_VALUE)
                    .addComponent(pnlReviewDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 610, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup().addComponent(btnBack).addGap(12, 12, 12).addComponent(btnRefresh)))
                .addGap(30, 30, 30)));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup().addGap(25, 25, 25).addComponent(lblTitle).addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(14, 14, 14)
                .addComponent(pnlReviewDetails, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(btnBack).addComponent(btnRefresh)).addContainerGap(25, Short.MAX_VALUE)));
    }// </editor-fold>//GEN-END:initComponents

    private void btnAssignToMeActionPerformed(java.awt.event.ActionEvent evt) {
        if (analyst == null) {
            JOptionPane.showMessageDialog(this, "Managers may monitor this queue, but only a Compliance Analyst can process a request.");
            return;
        }
        VerificationReview review = getSelectedReview();
        if (review != null) {
            try {
                review.assignAnalyst(analyst);
                populateTable();
                JOptionPane.showMessageDialog(this, "Request assigned successfully.");
            } catch (IllegalStateException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Request Cannot Be Assigned", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void btnCompleteReviewActionPerformed(java.awt.event.ActionEvent evt) {
        VerificationReview review = getSelectedReview();
        if (review == null) {
            return;
        }
        if (review.getAssignedAnalyst() != analyst) {
            JOptionPane.showMessageDialog(this, "Assign the request to yourself before completing the review.");
            return;
        }
        try {
            ComplianceDecision decision = (ComplianceDecision) cmbDecision.getSelectedItem();
            review.completeReview(decision, txtFindings.getText());
            txtFindings.setText("");
            populateTable();
            JOptionPane.showMessageDialog(this, "Verification review completed.");
        } catch (IllegalArgumentException | IllegalStateException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {
        populateTable();
        tblRequests.clearSelection();
        txtFindings.setText("");
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
            if (window instanceof javax.swing.JDialog) {
                window.dispose();
            }
        }
    }

    private void loadSelectedReview() {
        int row = tblRequests.getSelectedRow();
        if (row < 0) {
            txtFindings.setText("");
            return;
        }
        VerificationReview review = (VerificationReview) tblRequests.getValueAt(row, 0);
        txtFindings.setText(review.getFindings());
        cmbDecision.setSelectedItem(review.getDecision());
        boolean pending = review.getDecision() == ComplianceDecision.PENDING && analyst != null;
        txtFindings.setEditable(pending);
        cmbDecision.setEnabled(pending);
        btnAssignToMe.setEnabled(pending);
        btnCompleteReview.setEnabled(pending);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAssignToMe;
    private javax.swing.JButton btnCompleteReview;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnBack;
    private javax.swing.JComboBox<ComplianceDecision> cmbDecision;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblDecision;
    private javax.swing.JLabel lblFindings;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel pnlReviewDetails;
    private javax.swing.JTable tblRequests;
    private javax.swing.JTextArea txtFindings;
    // End of variables declaration//GEN-END:variables
}
