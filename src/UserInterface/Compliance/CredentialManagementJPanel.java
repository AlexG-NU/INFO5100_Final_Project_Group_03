package UserInterface.Compliance;

import ComplianceEnterprise.Enums.CredentialStatus;
import ComplianceEnterprise.Model.ComplianceDirectory;
import ComplianceEnterprise.Model.CredentialRecord;
import StaffingAgency.People.Contractor;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author janet
 */
public class CredentialManagementJPanel extends javax.swing.JPanel {

    private final ComplianceDirectory complianceDirectory;
    private final java.util.List<Contractor> contractorList;
    private CredentialRecord selectedCredential;
    private boolean addingRecord;
    private final javax.swing.JPanel container;
    private final javax.swing.JPanel previousPanel;

    public CredentialManagementJPanel(ComplianceDirectory complianceDirectory,
            java.util.List<Contractor> contractorList) {
        this(null, complianceDirectory, contractorList, null);
    }

    public CredentialManagementJPanel(javax.swing.JPanel container,
            ComplianceDirectory complianceDirectory, java.util.List<Contractor> contractorList,
            javax.swing.JPanel previousPanel) {
        this.container = container;
        this.complianceDirectory = complianceDirectory;
        this.contractorList = contractorList;
        this.previousPanel = previousPanel;
        initComponents();
        cmbContractor.setModel(new DefaultComboBoxModel<>(contractorList.toArray(new Contractor[0])));
        cmbType.setModel(new DefaultComboBoxModel<>(new String[]{"Background Check", "Drug Screening",
            "Identity Verification", "Employment Verification", "Professional License"}));
        cmbStatus.setModel(new DefaultComboBoxModel<>(CredentialStatus.values()));
        populateTable();
        setFormMode(false);
    }

    private void populateTable() {
        DefaultTableModel model = (DefaultTableModel) tblCredentials.getModel();
        model.setRowCount(0);
        for (CredentialRecord credential : complianceDirectory.getCredentialList()) {
            model.addRow(new Object[]{credential, credential.getCredentialId(),
                credential.getContractor().getFullName(), credential.getCredentialType(),
                credential.getDocumentNumber(), credential.getExpirationDate(), credential.getStatus()});
        }
    }

    private void setFormMode(boolean editable) {
        cmbContractor.setEnabled(editable && addingRecord);
        cmbType.setEnabled(editable);
        txtDocument.setEditable(editable);
        txtExpiration.setEditable(editable);
        cmbStatus.setEnabled(editable);
        btnSave.setEnabled(editable);
        btnCancel.setEnabled(editable);
        btnAdd.setEnabled(!editable && !contractorList.isEmpty());
        btnEdit.setEnabled(!editable && selectedCredential != null);
        btnDelete.setEnabled(!editable && selectedCredential != null);
        tblCredentials.setEnabled(!editable);
    }

    private void clearForm() {
        selectedCredential = null;
        addingRecord = false;
        tblCredentials.clearSelection();
        if (cmbContractor.getItemCount() > 0) {
            cmbContractor.setSelectedIndex(0);
        }
        cmbType.setSelectedIndex(0);
        txtDocument.setText("");
        txtExpiration.setText("");
        cmbStatus.setSelectedItem(CredentialStatus.SUBMITTED);
        setFormMode(false);
    }

    private void loadSelectedCredential() {
        if (!tblCredentials.isEnabled()) {
            return;
        }
        int row = tblCredentials.getSelectedRow();
        selectedCredential = row < 0 ? null : (CredentialRecord) tblCredentials.getValueAt(row, 0);
        if (selectedCredential != null) {
            cmbContractor.setSelectedItem(selectedCredential.getContractor());
            cmbType.setSelectedItem(selectedCredential.getCredentialType());
            txtDocument.setText(selectedCredential.getDocumentNumber());
            txtExpiration.setText(selectedCredential.getExpirationDate().toString());
            cmbStatus.setSelectedItem(selectedCredential.getStatus());
        }
        setFormMode(false);
    }

    private LocalDate validateForm() {
        if (cmbContractor.getSelectedItem() == null) {
            throw new IllegalArgumentException("Select a contractor.");
        }
        if (txtDocument.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("Enter the document number.");
        }
        LocalDate expirationDate;
        try {
            expirationDate = LocalDate.parse(txtExpiration.getText().trim());
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Enter the expiration date as YYYY-MM-DD, for example 2027-12-31.");
        }
        if (expirationDate.isBefore(LocalDate.now())
                && cmbStatus.getSelectedItem() != CredentialStatus.EXPIRED) {
            throw new IllegalArgumentException("A past expiration date must use Expired status.");
        }
        return expirationDate;
    }

    private void saveRecord() {
        try {
            LocalDate expirationDate = validateForm();
            if (addingRecord) {
                CredentialRecord record = new CredentialRecord((Contractor) cmbContractor.getSelectedItem(),
                        cmbType.getSelectedItem().toString(), txtDocument.getText(), expirationDate);
                record.setStatus((CredentialStatus) cmbStatus.getSelectedItem());
                complianceDirectory.addCredential(record);
                JOptionPane.showMessageDialog(this, "Credential record added successfully.",
                        "Record Saved", JOptionPane.INFORMATION_MESSAGE);
            } else {
                if (complianceDirectory.documentNumberExists(txtDocument.getText(), selectedCredential)) {
                    throw new IllegalArgumentException("This document number already belongs to another credential record.");
                }
                selectedCredential.setCredentialType(cmbType.getSelectedItem().toString());
                selectedCredential.setDocumentNumber(txtDocument.getText());
                selectedCredential.setExpirationDate(expirationDate);
                selectedCredential.setStatus((CredentialStatus) cmbStatus.getSelectedItem());
                JOptionPane.showMessageDialog(this, "Credential record updated successfully.",
                        "Record Updated", JOptionPane.INFORMATION_MESSAGE);
            }
            populateTable();
            clearForm();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Check Your Entry", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        lblTitle = new javax.swing.JLabel(); jScrollPane1 = new javax.swing.JScrollPane(); tblCredentials = new javax.swing.JTable();
        lblContractor = new javax.swing.JLabel(); cmbContractor = new javax.swing.JComboBox<>(); lblType = new javax.swing.JLabel(); cmbType = new javax.swing.JComboBox<>();
        lblDocument = new javax.swing.JLabel(); txtDocument = new javax.swing.JTextField(); lblExpiration = new javax.swing.JLabel(); txtExpiration = new javax.swing.JTextField();
        lblStatus = new javax.swing.JLabel(); cmbStatus = new javax.swing.JComboBox<>(); pnlCredentialDetails = new javax.swing.JPanel(); btnAdd = new javax.swing.JButton(); btnEdit = new javax.swing.JButton();
        btnSave = new javax.swing.JButton(); btnDelete = new javax.swing.JButton(); btnCancel = new javax.swing.JButton(); btnRefresh = new javax.swing.JButton(); btnBack = new javax.swing.JButton();
        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 18)); lblTitle.setText("Manage Credential Records");
        tblCredentials.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblCredentials.setModel(new DefaultTableModel(new Object[][]{}, new String[]{"Record", "Credential ID", "Contractor", "Type", "Document Number", "Expiration Date", "Status"}) {
            public boolean isCellEditable(int row, int column) { return false; }
        });
        tblCredentials.getSelectionModel().addListSelectionListener(e -> { if (!e.getValueIsAdjusting()) loadSelectedCredential(); });
        jScrollPane1.setViewportView(tblCredentials); jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder("Credential Records")); tblCredentials.getColumnModel().getColumn(0).setMinWidth(0); tblCredentials.getColumnModel().getColumn(0).setMaxWidth(0);
        lblContractor.setText("Contractor:"); lblType.setText("Credential Type:"); lblDocument.setText("Document Number:");
        lblExpiration.setText("Expiration Date (YYYY-MM-DD):"); lblStatus.setText("Status:");
        btnAdd.setText("Add New"); btnAdd.addActionListener(evt -> btnAddActionPerformed(evt));
        btnEdit.setText("Edit Selected"); btnEdit.addActionListener(evt -> btnEditActionPerformed(evt));
        btnSave.setText("Save"); btnSave.addActionListener(evt -> btnSaveActionPerformed(evt));
        btnDelete.setText("Delete"); btnDelete.addActionListener(evt -> btnDeleteActionPerformed(evt));
        btnCancel.setText("Cancel"); btnCancel.addActionListener(evt -> btnCancelActionPerformed(evt));
        btnRefresh.setText("Refresh"); btnRefresh.addActionListener(evt -> btnRefreshActionPerformed(evt));
        btnBack.setText("<< Back"); btnBack.addActionListener(evt -> btnBackActionPerformed(evt));
        pnlCredentialDetails.setBorder(javax.swing.BorderFactory.createTitledBorder("Credential Details"));
        javax.swing.GroupLayout detailsLayout = new javax.swing.GroupLayout(pnlCredentialDetails); pnlCredentialDetails.setLayout(detailsLayout);
        detailsLayout.setHorizontalGroup(detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(detailsLayout.createSequentialGroup().addGap(18)
                .addGroup(detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblContractor).addComponent(lblType).addComponent(lblDocument).addComponent(lblExpiration).addComponent(lblStatus))
                .addGap(18)
                .addGroup(detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cmbContractor, 0, 280, Short.MAX_VALUE).addComponent(cmbType, 0, 280, Short.MAX_VALUE)
                    .addComponent(txtDocument).addComponent(txtExpiration).addComponent(cmbStatus, 0, 280, Short.MAX_VALUE))
                .addContainerGap(18, Short.MAX_VALUE))
            .addGroup(detailsLayout.createSequentialGroup().addGap(18).addComponent(btnAdd).addGap(10).addComponent(btnEdit).addGap(10)
                .addComponent(btnSave).addGap(10).addComponent(btnDelete).addGap(10).addComponent(btnCancel).addContainerGap()));
        detailsLayout.setVerticalGroup(detailsLayout.createSequentialGroup().addGap(10)
            .addGroup(detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(lblContractor).addComponent(cmbContractor)).addGap(8)
            .addGroup(detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(lblType).addComponent(cmbType)).addGap(8)
            .addGroup(detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(lblDocument).addComponent(txtDocument)).addGap(8)
            .addGroup(detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(lblExpiration).addComponent(txtExpiration)).addGap(8)
            .addGroup(detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(lblStatus).addComponent(cmbStatus)).addGap(14)
            .addGroup(detailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(btnAdd).addComponent(btnEdit).addComponent(btnSave).addComponent(btnDelete).addComponent(btnCancel)).addContainerGap(12, Short.MAX_VALUE));
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this); setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(30).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTitle).addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 840, Short.MAX_VALUE)
            .addComponent(pnlCredentialDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 610, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layout.createSequentialGroup().addComponent(btnBack).addGap(12).addComponent(btnRefresh))).addGap(30)));
        layout.setVerticalGroup(layout.createSequentialGroup().addGap(22).addComponent(lblTitle).addGap(15).addComponent(jScrollPane1, 210, 210, 210).addGap(18)
            .addComponent(pnlCredentialDetails, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(10).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(btnBack).addComponent(btnRefresh)).addGap(25));
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) { clearForm(); addingRecord = true; setFormMode(true); }
    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {
        if (selectedCredential == null) {
            JOptionPane.showMessageDialog(this, "Select a credential record before editing.", "No Record Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }
        addingRecord = false; setFormMode(true);
    }
    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) { saveRecord(); }
    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) { deleteRecord(); }
    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) { clearForm(); }
    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) { populateTable(); clearForm(); }
    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {
        if (container != null && previousPanel != null) {
            container.removeAll(); container.setLayout(new java.awt.CardLayout()); container.add(previousPanel, "PreviousPanel");
            ((java.awt.CardLayout) container.getLayout()).show(container, "PreviousPanel"); container.revalidate(); container.repaint();
        } else {
            java.awt.Window window = javax.swing.SwingUtilities.getWindowAncestor(this);
            if (window instanceof javax.swing.JDialog) window.dispose();
        }
    }

    private void deleteRecord() {
        if (selectedCredential == null) {
            JOptionPane.showMessageDialog(this, "Select a credential record before deleting.", "No Record Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int answer = JOptionPane.showConfirmDialog(this,
                "Delete credential " + selectedCredential.getCredentialId() + "? This action cannot be undone.",
                "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (answer == JOptionPane.YES_OPTION) {
            complianceDirectory.removeCredential(selectedCredential); populateTable(); clearForm();
            JOptionPane.showMessageDialog(this, "Credential record deleted.");
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd, btnBack, btnCancel, btnDelete, btnEdit, btnRefresh, btnSave;
    private javax.swing.JComboBox<Contractor> cmbContractor; private javax.swing.JComboBox<CredentialStatus> cmbStatus; private javax.swing.JComboBox<String> cmbType;
    private javax.swing.JScrollPane jScrollPane1; private javax.swing.JLabel lblContractor, lblDocument, lblExpiration, lblStatus, lblTitle, lblType;
    private javax.swing.JPanel pnlCredentialDetails; private javax.swing.JTable tblCredentials; private javax.swing.JTextField txtDocument, txtExpiration;
    // End of variables declaration//GEN-END:variables
}
