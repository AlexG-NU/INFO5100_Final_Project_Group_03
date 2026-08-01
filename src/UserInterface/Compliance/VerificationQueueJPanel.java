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
    private final boolean assignedOnly;

    public VerificationQueueJPanel(ComplianceDirectory complianceDirectory,
            ComplianceAnalyst analyst) {
        this(null, complianceDirectory, analyst, null, false);
    }

    public VerificationQueueJPanel(javax.swing.JPanel container,
            ComplianceDirectory complianceDirectory, ComplianceAnalyst analyst,
            javax.swing.JPanel previousPanel) {
        this(container, complianceDirectory, analyst, previousPanel, false);
    }

    public VerificationQueueJPanel(javax.swing.JPanel container,
            ComplianceDirectory complianceDirectory, ComplianceAnalyst analyst,
            javax.swing.JPanel previousPanel, boolean assignedOnly) {
        this.container = container;
        this.complianceDirectory = complianceDirectory;
        this.analyst = analyst;
        this.previousPanel = previousPanel;
        this.assignedOnly = assignedOnly;
        initComponents();
        if (assignedOnly) {
            lblTitle.setText("Active Compliance Case");
            btnAssignToMe.setVisible(false);
        }
        ComplianceTableUI.configure(tblRequests,
                0, 90, 100, 100, 170, 190, 150, 170, 130, 210, 150, 150);
        applyFittedLayout();
        populateTable();
    }

    /**
     * Keeps navigation fixed while allowing the table to consume only the
     * remaining space. This replaces the fixed-height GUI-builder page that
     * pushed Back and Refresh below the visible work area.
     */
    private void applyFittedLayout() {
        java.awt.Color background = new java.awt.Color(255, 255, 204);
        ComplianceTableUI.styleScrollPane(jScrollPane1, 220);

        javax.swing.JPanel header = new javax.swing.JPanel();
        header.setBackground(background);
        header.setLayout(new javax.swing.BoxLayout(
                header, javax.swing.BoxLayout.Y_AXIS));
        header.add(lblTitle);
        header.add(javax.swing.Box.createVerticalStrut(4));
        header.add(lblCaseStatus);

        javax.swing.JPanel center = new javax.swing.JPanel(
                new java.awt.BorderLayout(0, 8));
        center.setBackground(background);
        center.add(jScrollPane1, java.awt.BorderLayout.CENTER);
        center.add(pnlReviewDetails, java.awt.BorderLayout.SOUTH);

        javax.swing.JPanel footer = new javax.swing.JPanel(
                new java.awt.GridLayout(1, 2, 12, 0));
        footer.setBackground(background);
        footer.add(btnBack);
        footer.add(btnRefresh);

        removeAll();
        setLayout(new java.awt.BorderLayout(0, 10));
        setBackground(background);
        setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 18, 12, 18));
        add(header, java.awt.BorderLayout.NORTH);
        add(center, java.awt.BorderLayout.CENTER);
        add(footer, java.awt.BorderLayout.SOUTH);
    }

    private void populateTable() {
        DefaultTableModel model = (DefaultTableModel) tblRequests.getModel();
        model.setRowCount(0);

        for (VerificationReview review : complianceDirectory.getReviewList()) {
            if (assignedOnly && review.getAssignedAnalyst() != analyst) {
                continue;
            }
            if (assignedOnly && review.getDecision() != ComplianceDecision.PENDING) {
                continue;
            }
            Object[] row = new Object[12];
            row[0] = review;
            row[1] = review.getRequest().getVerificationRequestId();
            row[2] = review.getRequest().getAssignment().getAssignmentId();
            row[3] = review.getRequest().getAssignment().getContractor().getContractorId();
            row[4] = review.getRequest().getAssignment().getContractor().getFullName();
            row[5] = review.getRequest().getVerificationType();
            row[6] = review.getAssignedAnalyst() == null
                    ? "Unassigned" : review.getAssignedAnalyst().getName();
            row[7] = review.getCredentialStatusText();
            row[8] = review.getDecision();
            row[9] = review.getWorkflowStatus();
            row[10] = review.getWaitingOn();
            row[11] = review.getCompletedByText();
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
        lblNextStep = new javax.swing.JLabel();
        lblCaseStatus = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblRequests = new javax.swing.JTable();
        btnAssignToMe = new javax.swing.JButton();
        btnRequestCredential = new javax.swing.JButton();
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
        lblTitle.setText("Compliance Review");
        lblNextStep.setFont(new java.awt.Font("Segoe UI", 1, 13));
        lblNextStep.setVisible(false);
        lblCaseStatus.setForeground(new java.awt.Color(0, 102, 153));
        lblCaseStatus.setText("Selected case: none");

        tblRequests.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {"Review", "Request ID", "Assignment ID", "Contractor ID", "Contractor", "Verification Type", "Assigned Analyst", "Credential Status", "Decision", "Workflow Status", "Waiting On", "Completed By"}
        ) {
            boolean[] canEdit = new boolean [] {false, false, false, false, false, false, false, false, false, false, false, false};
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
        btnRequestCredential.setText("Request Credential Check");
        btnRequestCredential.addActionListener(evt -> btnRequestCredentialActionPerformed(evt));
        btnRequestCredential.setEnabled(false);
        lblDecision.setText("Decision:");
        cmbDecision.setModel(new javax.swing.DefaultComboBoxModel<>(ComplianceDecision.values()));
        lblFindings.setText("Analyst Assessment:");
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
            btnRequestCredential.setEnabled(false);
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
                        .addGap(12, 12, 12).addComponent(btnRequestCredential)
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
                .addComponent(btnAssignToMe).addComponent(btnRequestCredential).addComponent(btnCompleteReview))
            .addContainerGap(12, Short.MAX_VALUE));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup().addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitle).addComponent(lblNextStep).addComponent(lblCaseStatus)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 820, Short.MAX_VALUE)
                    .addComponent(pnlReviewDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 610, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup().addComponent(btnBack).addGap(12, 12, 12).addComponent(btnRefresh)))
                .addGap(30, 30, 30)));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup().addGap(25, 25, 25).addComponent(lblTitle).addGap(8, 8, 8)
                .addComponent(lblNextStep).addGap(4, 4, 4).addComponent(lblCaseStatus).addGap(12, 12, 12)
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
            JOptionPane.showMessageDialog(this,
                    "This request must be assigned to you by the Compliance Manager before you can complete it.");
            return;
        }
        try {
            ComplianceDecision decision = (ComplianceDecision) cmbDecision.getSelectedItem();
            review.completeReview(decision, txtFindings.getText());
            txtFindings.setText("");
            populateTable();
            JOptionPane.showMessageDialog(this,
                    "Compliance decision completed. The result is available to the Contractor Coordinator.");
        } catch (IllegalArgumentException | IllegalStateException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnRequestCredentialActionPerformed(java.awt.event.ActionEvent evt) {
        VerificationReview review = getSelectedReview();
        if (review == null) {
            return;
        }
        if (review.getAssignedAnalyst() != analyst) {
            JOptionPane.showMessageDialog(this,
                    "This request must be assigned to you by the Compliance Manager first.");
            return;
        }
        try {
            review.recordAnalystAssessment(txtFindings.getText());
            complianceDirectory.requestCredentialVerification(review);
            populateTable();
            JOptionPane.showMessageDialog(this,
                    "Assessment saved and credential verification requested.");
        } catch (IllegalArgumentException | IllegalStateException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Credential Check", JOptionPane.WARNING_MESSAGE);
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
            lblCaseStatus.setText("Selected case: none");
            return;
        }
        VerificationReview review = (VerificationReview) tblRequests.getValueAt(row, 0);
        lblCaseStatus.setText("Current step: " + review.getWorkflowStatus()
                + "  |  Waiting on: " + review.getWaitingOn());
        txtFindings.setText(review.getFindings());
        cmbDecision.setSelectedItem(review.getDecision());
        boolean pending = review.getDecision() == ComplianceDecision.PENDING && analyst != null;
        txtFindings.setEditable(pending);
        cmbDecision.setEnabled(pending);
        btnAssignToMe.setEnabled(pending && !assignedOnly);
        btnRequestCredential.setEnabled(pending
                && review.getAssignedAnalyst() == analyst
                && review.getCredentialTask() == null);
        btnCompleteReview.setEnabled(pending);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAssignToMe;
    private javax.swing.JButton btnRequestCredential;
    private javax.swing.JButton btnCompleteReview;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnBack;
    private javax.swing.JComboBox<ComplianceDecision> cmbDecision;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblDecision;
    private javax.swing.JLabel lblFindings;
    private javax.swing.JLabel lblCaseStatus;
    private javax.swing.JLabel lblNextStep;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel pnlReviewDetails;
    private javax.swing.JTable tblRequests;
    private javax.swing.JTextArea txtFindings;
    // End of variables declaration//GEN-END:variables
}
