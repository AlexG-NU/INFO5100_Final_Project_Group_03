package UserInterface.Compliance;

import ComplianceEnterprise.Model.CredentialRecord;
import java.awt.CardLayout;

public class CredentialDetailsJPanel extends javax.swing.JPanel {

    private final javax.swing.JPanel container;
    private final javax.swing.JPanel previousPanel;
    private final CredentialRecord credential;

    public CredentialDetailsJPanel(javax.swing.JPanel container,
            CredentialRecord credential, javax.swing.JPanel previousPanel) {
        this.container = container;
        this.credential = credential;
        this.previousPanel = previousPanel;
        initComponents();
        displayCredential();
    }

    private void displayCredential() {
        if (credential == null) {
            return;
        }
        txtCredentialId.setText(String.valueOf(credential.getCredentialId()));
        txtType.setText(credential.getCredentialType());
        txtDocument.setText(credential.getDocumentNumber());
        txtExpiration.setText(String.valueOf(credential.getExpirationDate()));
        txtStatus.setText(String.valueOf(credential.getStatus()));

        if (credential.getContractor() != null) {
            txtContractorId.setText(String.valueOf(
                    credential.getContractor().getContractorId()));
            txtContractor.setText(credential.getContractor().getFullName());
        }
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tblCredentials.getModel();
        model.setRowCount(0);
        model.addRow(new Object[]{txtCredentialId.getText(), txtContractorId.getText(),
            txtContractor.getText(), txtType.getText(), txtDocument.getText(),
            txtExpiration.getText(), txtStatus.getText()});
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        lblTitle = new javax.swing.JLabel();
        jScrollPaneTable = new javax.swing.JScrollPane();
        tblCredentials = new javax.swing.JTable();
        pnlDetails = new javax.swing.JPanel();
        lblCredentialId = new javax.swing.JLabel();
        txtCredentialId = new javax.swing.JTextField();
        lblContractorId = new javax.swing.JLabel();
        txtContractorId = new javax.swing.JTextField();
        lblContractor = new javax.swing.JLabel();
        txtContractor = new javax.swing.JTextField();
        lblType = new javax.swing.JLabel();
        txtType = new javax.swing.JTextField();
        lblDocument = new javax.swing.JLabel();
        txtDocument = new javax.swing.JTextField();
        lblExpiration = new javax.swing.JLabel();
        txtExpiration = new javax.swing.JTextField();
        lblStatus = new javax.swing.JLabel();
        txtStatus = new javax.swing.JTextField();
        btnBack = new javax.swing.JButton();

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 18));
        lblTitle.setText("Credential Details");
        tblCredentials.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {"Credential ID", "Contractor ID", "Contractor", "Credential Type", "Document Number", "Expiration Date", "Status"}
        ) {
            boolean[] canEdit = new boolean [] {false, false, false, false, false, false, false};
            public boolean isCellEditable(int rowIndex, int columnIndex) { return canEdit[columnIndex]; }
        });
        jScrollPaneTable.setBorder(javax.swing.BorderFactory.createTitledBorder("Credential Record"));
        jScrollPaneTable.setViewportView(tblCredentials);
        pnlDetails.setBorder(javax.swing.BorderFactory.createTitledBorder("Selected Credential"));
        lblCredentialId.setText("Credential ID:");
        lblContractorId.setText("Contractor ID:");
        lblContractor.setText("Contractor:");
        lblType.setText("Credential Type:");
        lblDocument.setText("Document Number:");
        lblExpiration.setText("Expiration Date:");
        lblStatus.setText("Status:");

        javax.swing.JTextField[] fields = {txtCredentialId, txtContractorId,
            txtContractor, txtType, txtDocument, txtExpiration, txtStatus};
        for (javax.swing.JTextField field : fields) {
            field.setEditable(false);
        }

        javax.swing.GroupLayout pnlDetailsLayout = new javax.swing.GroupLayout(pnlDetails);
        pnlDetails.setLayout(pnlDetailsLayout);
        pnlDetailsLayout.setHorizontalGroup(
            pnlDetailsLayout.createSequentialGroup()
                .addGap(18)
                .addGroup(pnlDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblCredentialId).addComponent(lblContractorId)
                    .addComponent(lblContractor).addComponent(lblType)
                    .addComponent(lblDocument).addComponent(lblExpiration)
                    .addComponent(lblStatus))
                .addGap(18)
                .addGroup(pnlDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCredentialId, 280, 280, 280)
                    .addComponent(txtContractorId, 280, 280, 280)
                    .addComponent(txtContractor, 280, 280, 280)
                    .addComponent(txtType, 280, 280, 280)
                    .addComponent(txtDocument, 280, 280, 280)
                    .addComponent(txtExpiration, 280, 280, 280)
                    .addComponent(txtStatus, 280, 280, 280))
                .addGap(18));
        pnlDetailsLayout.setVerticalGroup(
            pnlDetailsLayout.createSequentialGroup().addGap(12)
                .addGroup(pnlDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(lblCredentialId).addComponent(txtCredentialId))
                .addGap(8).addGroup(pnlDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(lblContractorId).addComponent(txtContractorId))
                .addGap(8).addGroup(pnlDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(lblContractor).addComponent(txtContractor))
                .addGap(8).addGroup(pnlDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(lblType).addComponent(txtType))
                .addGap(8).addGroup(pnlDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(lblDocument).addComponent(txtDocument))
                .addGap(8).addGroup(pnlDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(lblExpiration).addComponent(txtExpiration))
                .addGap(8).addGroup(pnlDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(lblStatus).addComponent(txtStatus))
                .addGap(12));

        btnBack.setText("<< Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(layout.createSequentialGroup().addGap(35)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(lblTitle).addComponent(jScrollPaneTable, 820, 820, Short.MAX_VALUE).addComponent(pnlDetails).addComponent(btnBack))
            .addGap(35));
        layout.setVerticalGroup(layout.createSequentialGroup().addGap(25)
            .addComponent(lblTitle).addGap(15).addComponent(jScrollPaneTable, 150, 150, 150).addGap(12).addComponent(pnlDetails)
            .addGap(12).addComponent(btnBack).addGap(25));
    }// </editor-fold>//GEN-END:initComponents

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
    private javax.swing.JButton btnBack;
    private javax.swing.JScrollPane jScrollPaneTable;
    private javax.swing.JTable tblCredentials;
    private javax.swing.JLabel lblContractor;
    private javax.swing.JLabel lblContractorId;
    private javax.swing.JLabel lblCredentialId;
    private javax.swing.JLabel lblDocument;
    private javax.swing.JLabel lblExpiration;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblType;
    private javax.swing.JPanel pnlDetails;
    private javax.swing.JTextField txtContractor;
    private javax.swing.JTextField txtContractorId;
    private javax.swing.JTextField txtCredentialId;
    private javax.swing.JTextField txtDocument;
    private javax.swing.JTextField txtExpiration;
    private javax.swing.JTextField txtStatus;
    private javax.swing.JTextField txtType;
    // End of variables declaration//GEN-END:variables
}
