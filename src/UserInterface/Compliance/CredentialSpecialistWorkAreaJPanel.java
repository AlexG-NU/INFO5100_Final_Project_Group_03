package UserInterface.Compliance;

import ComplianceEnterprise.Model.ComplianceDirectory;
import ComplianceEnterprise.Model.CredentialRecord;
import ComplianceEnterprise.Model.CredentialVerificationTask;
import ComplianceEnterprise.Model.RegistryCredentialRecord;
import ComplianceEnterprise.Enums.CredentialStatus;
import ComplianceEnterprise.Role.CredentialSpecialist;
import StaffingAgency.People.Contractor;
import java.time.LocalDate;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author janet
 */
public class CredentialSpecialistWorkAreaJPanel extends javax.swing.JPanel {

    private final ComplianceDirectory complianceDirectory;
    private final CredentialSpecialist specialist;
    private final List<Contractor> contractorList;
    private final javax.swing.JPanel container;
    private final javax.swing.JPanel previousPanel;
    private boolean showingCompleted;

    public CredentialSpecialistWorkAreaJPanel(ComplianceDirectory complianceDirectory,
            CredentialSpecialist specialist, List<Contractor> contractorList) {
        this(null, complianceDirectory, specialist, contractorList, null);
    }

    public CredentialSpecialistWorkAreaJPanel(javax.swing.JPanel container,
            ComplianceDirectory complianceDirectory, CredentialSpecialist specialist,
            List<Contractor> contractorList, javax.swing.JPanel previousPanel) {
        this.container = container;
        this.previousPanel = previousPanel;
        this.complianceDirectory = complianceDirectory;
        this.specialist = specialist;
        this.contractorList = contractorList;
        initComponents();
        btnCredentialRecords = new javax.swing.JButton("Manage Credential Records");
        btnCredentialRecords.addActionListener(evt -> openCredentialManagement());
        btnBack.setVisible(previousPanel != null);
        configureTable();
        applyResponsiveLayout();
        populatePendingTasks();
    }

    private void configureTable() {
        ComplianceTableUI.configure(tblRequests,
                0, 90, 180, 190, 120, 120, 260);
        lblNextStep.setVisible(true);
        lblNextStep.setText("Purpose: check the requested credential evidence and return a result to the Compliance Analyst.");
        lblCaseStatus.setText("Selected task: none");
        tblRequests.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                int row = tblRequests.getSelectedRow();
                lblCaseStatus.setText(row < 0 ? "Selected task: none"
                        : "Current result: " + tblRequests.getValueAt(row, 5));
                if (row < 0) {
                    lblNextStep.setText("Select a pending task, view the evidence, then complete and return the result.");
                    btnView.setEnabled(false);
                    btnExpiring.setEnabled(false);
                } else {
                    CredentialVerificationTask selected =
                            (CredentialVerificationTask) tblRequests.getValueAt(row, 0);
                    lblNextStep.setText(selected.isComplete()
                            ? "This result was already returned to the Compliance Analyst."
                            : "Review the credential and submit the verification result.");
                    btnView.setEnabled(true);
                    btnExpiring.setEnabled(!selected.isComplete());
                }
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
        header.add(lblNextStep);
        header.add(javax.swing.Box.createVerticalStrut(4));
        header.add(lblCaseStatus);
        header.add(javax.swing.Box.createVerticalStrut(10));
        header.add(managerStyleGrid(
                btnView, btnExpiring, btnManage, btnReports,
                btnCredentialRecords));
        ComplianceTableUI.styleScrollPane(jScrollPane1, 360);
        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(
                "Credential Verification Queue"));
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
        int rows = (buttons.length + 1) / 2;
        panel.setMaximumSize(new java.awt.Dimension(
                Integer.MAX_VALUE, rows * 38 + Math.max(0, rows - 1) * 10));
        for (javax.swing.JButton button : buttons) {
            button.setPreferredSize(new java.awt.Dimension(210, 32));
            panel.add(button);
        }
        return panel;
    }

    private void populatePendingTasks() {
        showingCompleted = false;
        populateTable(false);
        lblNextStep.setText("Select a pending task, view the evidence, then complete and return the result.");
        lblCaseStatus.setText("Pending tasks: " + tblRequests.getRowCount());
        btnView.setEnabled(false);
        btnExpiring.setEnabled(false);
    }

    private void populateCompletedTasks() {
        showingCompleted = true;
        populateTable(true);
        lblNextStep.setText("Completed tasks are view-only. Select one to review the evidence and result.");
        lblCaseStatus.setText("Completed tasks: " + tblRequests.getRowCount());
        btnView.setEnabled(false);
        btnExpiring.setEnabled(false);
    }

    private void populateTable(boolean completedOnly) {
        DefaultTableModel model = (DefaultTableModel) tblRequests.getModel();
        model.setRowCount(0);
        for (CredentialVerificationTask task
                : complianceDirectory.getCredentialTaskList()) {
            if (task.isComplete() != completedOnly) {
                continue;
            }
            RegistryCredentialRecord registryRecord = task.getRegistryRecord();
            String analystInstructions = task.getReview().getFindings();
            model.addRow(new Object[]{task,
                task.getReview().getRequest().getVerificationRequestId(),
                task.getReview().getRequest().getAssignment().getContractor().getFullName(),
                task.getReview().getRequiredCredentialType(),
                registryRecord == null ? "Not found" : registryRecord.getExpirationDate(),
                task.getResult(),
                analystInstructions == null || analystInstructions.isBlank()
                        ? "No special instructions" : analystInstructions});
        }
    }

    private CredentialVerificationTask getSelectedTask() {
        int row = tblRequests.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a credential verification task first.",
                    "No Task Selected", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        return (CredentialVerificationTask) tblRequests.getValueAt(row, 0);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitle = new javax.swing.JLabel();
        lblOrganization = new javax.swing.JLabel();
        btnView = new javax.swing.JButton();
        btnExpiring = new javax.swing.JButton();
        btnManage = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        btnReports = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblRequests = new javax.swing.JTable();
        lblNextStep = new javax.swing.JLabel();
        lblCaseStatus = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 204));

        lblTitle.setFont(new java.awt.Font("Myanmar Sangam MN", 3, 18)); // NOI18N
        lblTitle.setText("Credential Specialist Work Area");

        lblOrganization.setFont(new java.awt.Font("Myanmar MN", 0, 13)); // NOI18N
        lblOrganization.setForeground(new java.awt.Color(102, 102, 102));
        lblOrganization.setText("Compliance Enterprise - Credential Management Organization");
        lblNextStep.setFont(new java.awt.Font("Segoe UI", 1, 13));
        lblCaseStatus.setForeground(new java.awt.Color(0, 102, 153));

        btnView.setText("View Registry Result");

        btnExpiring.setText("Check Registry and Submit Result");

        btnManage.setText("Pending Tasks");

        btnBack.setText("Back");

        btnRefresh.setText("Refresh");

        btnReports.setText("Completed Tasks (View Only)");

        tblRequests.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "Task", "Request ID", "Contractor", "Required Credential", "Registry Expiration", "Result", "Analyst Instructions"
            }
        ));
        tblRequests.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblRequests);
        tblRequests.getColumnModel().getColumn(0).setMinWidth(0);
        tblRequests.getColumnModel().getColumn(0).setPreferredWidth(0);
        tblRequests.getColumnModel().getColumn(0).setMaxWidth(0);

        btnView.addActionListener(evt -> btnViewActionPerformed(evt));
        btnExpiring.addActionListener(evt -> btnExpiringActionPerformed(evt));
        btnManage.addActionListener(evt -> btnManageActionPerformed(evt));
        btnReports.addActionListener(evt -> btnReportsActionPerformed(evt));
        btnRefresh.addActionListener(evt -> btnRefreshActionPerformed(evt));
        btnBack.addActionListener(evt -> btnBackActionPerformed(evt));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNextStep)
                            .addComponent(lblCaseStatus)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(98, 98, 98)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(lblOrganization)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btnView)
                                            .addComponent(btnExpiring))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(btnManage)
                                            .addComponent(btnReports)))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(lblTitle)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblOrganization)
                .addGap(8, 8, 8)
                .addComponent(lblNextStep)
                .addGap(4, 4, 4)
                .addComponent(lblCaseStatus)
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnView)
                    .addComponent(btnManage))
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnReports)
                    .addComponent(btnExpiring))
                .addGap(37, 37, 37)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBack)
                    .addComponent(btnRefresh))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnViewActionPerformed(java.awt.event.ActionEvent evt) { viewCredential(); }
    private void btnExpiringActionPerformed(java.awt.event.ActionEvent evt) { processSelectedTask(); }
    private void btnManageActionPerformed(java.awt.event.ActionEvent evt) {
        populatePendingTasks();
    }
    private void btnReportsActionPerformed(java.awt.event.ActionEvent evt) {
        populateCompletedTasks();
    }
    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {
        if (showingCompleted) populateCompletedTasks(); else populatePendingTasks();
        tblRequests.clearSelection();
    }
    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {
        if (container != null && previousPanel != null) {
            container.removeAll(); container.setLayout(new java.awt.CardLayout()); container.add(previousPanel, "PreviousPanel");
            ((java.awt.CardLayout) container.getLayout()).show(container, "PreviousPanel"); container.revalidate(); container.repaint();
        }
    }

    private void openCredentialManagement() {
        CredentialManagementJPanel managementPanel =
                new CredentialManagementJPanel(container, complianceDirectory,
                        contractorList, this);
        if (container != null) {
            container.removeAll();
            container.setLayout(new java.awt.CardLayout());
            container.add(managementPanel, "CredentialManagement");
            ((java.awt.CardLayout) container.getLayout()).show(
                    container, "CredentialManagement");
            container.revalidate();
            container.repaint();
        } else {
            javax.swing.JDialog dialog = new javax.swing.JDialog(
                    javax.swing.SwingUtilities.getWindowAncestor(this),
                    "Manage Credential Records",
                    java.awt.Dialog.ModalityType.APPLICATION_MODAL);
            dialog.setContentPane(managementPanel);
            dialog.setSize(980, 700);
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        }
    }

    private void viewCredential() {
        CredentialVerificationTask task = getSelectedTask();
        if (task == null) return;
        RegistryCredentialRecord record = task.getRegistryRecord();
        String message;
        if (record == null) {
            message = "Search name: "
                    + task.getReview().getRequest().getAssignment()
                            .getContractor().getFullName()
                    + "\nCredential requested: "
                    + task.getReview().getRequiredCredentialType()
                    + "\n\nNo matching record was found in the simulated registry."
                    + "\nResult: Record Not Found";
        } else {
            message = "Contractor: " + record.getContractorName()
                    + "\nCredential: " + record.getCredentialType()
                    + "\nCredential number: " + record.getCredentialNumber()
                    + "\nIssuing organization: " + record.getIssuingOrganization()
                    + "\nExpiration date: " + record.getExpirationDate()
                    + "\nRegistry status: " + record.getStatus()
                    + "\n\nSystem result: " + task.getRegistryResult();
        }
        JOptionPane.showMessageDialog(this, message,
                "Simulated Registry Result", JOptionPane.INFORMATION_MESSAGE);
    }

    private void processSelectedTask() {
        CredentialVerificationTask task = getSelectedTask();
        if (task == null) {
            return;
        }
        if (task.isComplete()) {
            JOptionPane.showMessageDialog(this,
                    "This credential verification task is already complete.",
                    "Task Complete", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        javax.swing.JTextArea notesArea = new javax.swing.JTextArea(5, 34);
        notesArea.setLineWrap(true);
        notesArea.setWrapStyleWord(true);
        javax.swing.JTextArea instructionsArea = new javax.swing.JTextArea(
                task.getReview().getFindings().isBlank()
                        ? "No special instructions were provided."
                        : task.getReview().getFindings(), 3, 34);
        instructionsArea.setEditable(false);
        instructionsArea.setLineWrap(true);
        instructionsArea.setWrapStyleWord(true);
        String analystName = task.getReview().getRequestedByText();
        String instructionsTitle = analystName == null || analystName.isBlank()
                ? "Instructions from Compliance Analyst"
                : "Instructions from Compliance Analyst: " + analystName;
        javax.swing.JScrollPane instructionsScrollPane =
                new javax.swing.JScrollPane(instructionsArea);
        instructionsScrollPane.setBorder(
                javax.swing.BorderFactory.createTitledBorder(instructionsTitle));
        javax.swing.JScrollPane notesScrollPane =
                new javax.swing.JScrollPane(notesArea);
        notesScrollPane.setBorder(javax.swing.BorderFactory.createTitledBorder(
                "Credential Specialist Notes"));
        javax.swing.JPanel reviewPanel = new javax.swing.JPanel(
                new java.awt.BorderLayout(8, 8));
        reviewPanel.add(instructionsScrollPane,
                java.awt.BorderLayout.SOUTH);
        javax.swing.JLabel resultLabel = new javax.swing.JLabel(
                "System result: " + task.getRegistryResult());
        reviewPanel.add(resultLabel, java.awt.BorderLayout.NORTH);
        reviewPanel.add(notesScrollPane,
                java.awt.BorderLayout.CENTER);
        reviewPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(
                "Verification Result and Notes"));

        int answer = JOptionPane.showConfirmDialog(this, reviewPanel,
                "Process Credential Task " + task.getTaskId(),
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (answer != JOptionPane.OK_OPTION) {
            return;
        }
        try {
            task.completeFromRegistry(specialist, notesArea.getText());
            populatePendingTasks();
            if (task.getResult() == CredentialStatus.VERIFIED) {
                JOptionPane.showMessageDialog(this, "Verification submitted.");
            } else if (task.getResult() == CredentialStatus.RECORD_NOT_FOUND) {
                JOptionPane.showMessageDialog(this, "Result submitted: Record Not Found.");
            } else {
                JOptionPane.showMessageDialog(this,
                        "Result submitted: " + task.getResult() + ".");
            }
        } catch (IllegalArgumentException | IllegalStateException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Check Verification Result", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnCredentialRecords;
    private javax.swing.JButton btnExpiring;
    private javax.swing.JButton btnManage;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnReports;
    private javax.swing.JButton btnView;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblOrganization;
    private javax.swing.JLabel lblCaseStatus;
    private javax.swing.JLabel lblNextStep;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JTable tblRequests;
    // End of variables declaration//GEN-END:variables
}
