package UserInterface.Compliance;

import ComplianceEnterprise.Model.ComplianceDirectory;
import ComplianceEnterprise.Model.CredentialRecord;
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
        populateTable(false);
    }

    private void populateTable(boolean expiringOnly) {
        DefaultTableModel model = (DefaultTableModel) tblRequests.getModel();
        model.setRowCount(0);
        LocalDate warningDate = LocalDate.now().plusDays(60);
        for (CredentialRecord credential : complianceDirectory.getCredentialList()) {
            if (!expiringOnly || !credential.getExpirationDate().isAfter(warningDate)) {
                model.addRow(new Object[]{credential.getCredentialId(), credential.getCredentialType(),
                    credential.getStatus(), credential.getContractor().getFullName(),
                    specialist.getName(), credential.getExpirationDate()});
            }
        }
    }

    private CredentialRecord getSelectedCredential() {
        int row = tblRequests.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a credential record from the table first.",
                    "No Record Selected", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        int credentialId = (Integer) tblRequests.getValueAt(row, 0);
        for (CredentialRecord credential : complianceDirectory.getCredentialList()) {
            if (credential.getCredentialId() == credentialId) {
                return credential;
            }
        }
        JOptionPane.showMessageDialog(this, "The selected credential could not be found. Click Refresh and try again.",
                "Record Not Found", JOptionPane.ERROR_MESSAGE);
        return null;
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

        setBackground(new java.awt.Color(255, 255, 204));

        lblTitle.setFont(new java.awt.Font("Myanmar Sangam MN", 3, 18)); // NOI18N
        lblTitle.setText("Credential Specialist Work Area");

        lblOrganization.setFont(new java.awt.Font("Myanmar MN", 0, 13)); // NOI18N
        lblOrganization.setForeground(new java.awt.Color(102, 102, 102));
        lblOrganization.setText("Compliance Enterprise - Credential Management Organization");

        btnView.setText("View Credential");

        btnExpiring.setText("Expiring Records");

        btnManage.setText("Manage Credentials");

        btnBack.setText("Back");

        btnRefresh.setText("Refresh");

        btnReports.setText("Reports");

        tblRequests.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Credential ID", "Credential Type", "Status", "Contractor", "Specialist", "Expiration Date"
            }
        ));
        tblRequests.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblRequests);

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
                .addGap(42, 42, 42)
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
    private void btnExpiringActionPerformed(java.awt.event.ActionEvent evt) { populateTable(true); }
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
    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) { populateTable(false); tblRequests.clearSelection(); }
    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {
        if (container != null && previousPanel != null) {
            container.removeAll(); container.setLayout(new java.awt.CardLayout()); container.add(previousPanel, "PreviousPanel");
            ((java.awt.CardLayout) container.getLayout()).show(container, "PreviousPanel"); container.revalidate(); container.repaint();
        }
    }

    private void viewCredential() {
        CredentialRecord credential = getSelectedCredential();
        if (credential == null) return;
        javax.swing.JPanel nextPanel = new CredentialDetailsJPanel(container, credential, this);
        if (container == null) { JOptionPane.showMessageDialog(this, nextPanel, "Credential Details", JOptionPane.PLAIN_MESSAGE); return; }
        container.removeAll(); container.setLayout(new java.awt.CardLayout()); container.add(nextPanel, "CredentialDetails");
        ((java.awt.CardLayout) container.getLayout()).show(container, "CredentialDetails"); container.revalidate(); container.repaint();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnExpiring;
    private javax.swing.JButton btnManage;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnReports;
    private javax.swing.JButton btnView;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblOrganization;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JTable tblRequests;
    // End of variables declaration//GEN-END:variables
}
