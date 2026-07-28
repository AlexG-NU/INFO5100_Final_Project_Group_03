/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UserInterface.Client;


import Business.Network;
import Client.Roles.ContractorRole;
import Core.NetworkUtils;
import Core.Organization;
import Core.UserAccount;
import Core.WorkOrder;
import Core.WorkOrderStatus;
import Core.WorkOrders.StaffingReqWorkOrder;
import java.awt.CardLayout;
import java.time.LocalDate;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Alex
 */
public class HiringManagerContractorsJPanel extends javax.swing.JPanel {

    private JPanel container;
    private UserAccount account;
    private Network network;

    // Define the columns you actually want visible in the JTable
    private final String[] COLUMN_NAMES = {
        "Contractor Name", "Role/Title", "Assigned Supervisor"
    };

    public HiringManagerContractorsJPanel(JPanel container, UserAccount account, Network network) {
        initComponents(); 
        this.container = container;
        this.account = account;
        this.network = network;
        populateSupervisorComboBox();
        refreshTable();
    }

    // Map object to the table row
    private Object[] mapObjectToRow(StaffingReqWorkOrder item) {
        return new Object[]{
            item,                      // Column 0: The object (displays ID via toString)
            item.getJobTitle(),        // Column 1
            item.getNumberOfPositions(),   // Column 2
            item.getStartDate(),       // Column 3
            item.getStatus()           // Column 4
        };
    }

    // Create a new blank object and pass it to the update method
    /*private StaffingReqWorkOrder buildObjectFromFields() throws NumberFormatException {
        StaffingReqWorkOrder newItem = new StaffingReqWorkOrder();
        
        
        newItem.setSender(account);                        
        //newItem.setRequestDate(LocalDate.now());           
        newItem.setStatus(WorkOrderStatus.PENDING);       
        //newItem.setJobTitle(txtJobTitle.getText().trim()); 
        
        updateObjectFromFields(newItem);
        return newItem;
    }*/

    // Update an object with text fields
    /*private void updateObjectFromFields(StaffingReqWorkOrder item) throws NumberFormatException {
        // Parse numbers
        //item.setId(Integer.parseInt(txtId.getText().trim()));
        item.setNumberOfPositions(Integer.parseInt(txtQuantity.getText().trim()));
        
        // Set Strings
        item.setJobTitle(txtJobTitle.getText().trim());
        item.setDescription(txtDescription.getText().trim());
        item.setRequiredSkills(txtSkills.getText().trim());
        
        java.util.Date spinnerDate = (java.util.Date) spnStartDate.getValue();
        LocalDate startDate = spinnerDate.toInstant()
                                         .atZone(java.time.ZoneId.systemDefault())
                                         .toLocalDate();
        item.setStartDate(startDate);
        
        // Status and SubmittedDate are usually handled by system logic 
        
    }*/

    // Fill text fields when a table row is clicked
    /*private void populateForm(StaffingReqWorkOrder item) {
        //txtId.setText(String.valueOf(item.getId()));
        txtQuantity.setText(String.valueOf(item.getNumberOfPositions()));
        
        txtJobTitle.setText(item.getJobTitle());
        txtDescription.setText(item.getDescription());
        txtSkills.setText(item.getRequiredSkills());
        
        if (item.getStartDate() != null) {
            java.util.Date spinnerDate = java.util.Date.from(
                item.getStartDate().atStartOfDay(java.time.ZoneId.systemDefault()).toInstant()
            );
            spnStartDate.setValue(spinnerDate);
        }
    }*/

    // Check for empty string fields before saving
    /*private boolean validateInput() {
        //if (txtId.getText().trim().isEmpty() || 
        if    (txtJobTitle.getText().trim().isEmpty() || 
            txtQuantity.getText().trim().isEmpty()) {
            
            JOptionPane.showMessageDialog(this, "ID, Job Title, Number of Hires are required.", "Warning", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }*/

    private void populateSupervisorComboBox() {
        cmbSupervisors.removeAllItems();
    
        
        cmbSupervisors.addItem(null);

        for (UserAccount ua : network.getUserAccountDirectory().getUserAccountList()) {
            if (ua.getRole().toString().contains("Supervisor")) {
                cmbSupervisors.addItem(ua);
            }
        }
}
    //-------------You should not need to edit below this line--------------------

    private void refreshTable() {
        DefaultTableModel model = new DefaultTableModel(COLUMN_NAMES, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { 
                return false; 
            }
        };

        for (UserAccount ua : network.getUserAccountDirectory().getUserAccountList()) {
            // 1. Check the object type directly instead of parsing strings
            if (ua.getRole() instanceof ContractorRole) { 

                model.addRow(new Object[]{
                    ua, 
                    ua.getRole(),
                    ua.getSupervisor() 
                });
            }
        }
    tblData.setModel(model);
    }


    private void clearFormAndSelection() {
        // Clear visually (add specific fields here if needed in Zone 1, 
        // but looping components keeps it generic)
        /*java.awt.Component[] components = getComponents();
        for (java.awt.Component component : components) {
            if (component instanceof javax.swing.JTextField) {
                ((javax.swing.JTextField) component).setText("");
            }
        }
        spnStartDate.setValue(new java.util.Date());*/
        cmbSupervisors.setSelectedItem(null);
        //selectedRecord = null;
        tblData.clearSelection();
    }



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblData = new javax.swing.JTable();
        btnBack = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cmbSupervisors = new javax.swing.JComboBox<>();

        tblData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDataMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblData);

        btnBack.setText("<< Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete");
        btnDelete.setEnabled(false);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnClear.setText("Clear");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel6.setText("Manage Contractors");

        jLabel7.setText("Assign Supervisor:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(btnBack)
                        .addGap(88, 88, 88)
                        .addComponent(jLabel6))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(134, 134, 134)
                                .addComponent(btnSave))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(189, 189, 189)
                                .addComponent(jLabel7)))
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbSupervisors, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnDelete)
                                .addGap(80, 80, 80)
                                .addComponent(btnClear)))))
                .addContainerGap(59, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBack)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cmbSupervisors, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(154, 154, 154)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave)
                    .addComponent(btnDelete)
                    .addComponent(btnClear))
                .addContainerGap(90, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        container.remove(this);
        CardLayout layout = (CardLayout) container.getLayout();
        layout.previous(container);
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        int selectedRow = tblData.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a contractor from the table.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        UserAccount selectedContractor = (UserAccount) tblData.getValueAt(selectedRow, 0);
        // Directly retrieve the selected UserAccount object (or null)
        UserAccount selectedSupervisor = (UserAccount) cmbSupervisors.getSelectedItem();

        selectedContractor.setSupervisor(selectedSupervisor);

        String msg = (selectedSupervisor != null) 
            ? "Supervisor assigned successfully!" 
            : "Supervisor unassigned successfully.";

        JOptionPane.showMessageDialog(this, msg);
        refreshTable();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        /*if (selectedRecord != null) {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this work order?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                
                // Remove from Hiring Manager's local queue
                account.getWorkQueue().getWorkOrderList().remove(selectedRecord);
                
                // Remove from target Staffing queue if not yet processed
                Organization staffingOrg = NetworkUtils.findOrganizationByName(network, "Staffing Enterprise", "Recruiting");
                if (staffingOrg != null) {
                    staffingOrg.getWorkQueue().getWorkOrderList().remove(selectedRecord);
                }
                
                refreshTable();
                clearFormAndSelection();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a work order from the table first.", "Warning", JOptionPane.WARNING_MESSAGE);
        }*/
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        clearFormAndSelection();
    }//GEN-LAST:event_btnClearActionPerformed

    private void tblDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDataMouseClicked
        int viewRow = tblData.getSelectedRow();
        if (viewRow >= 0) {
            int modelRow = tblData.convertRowIndexToModel(viewRow);

            UserAccount selectedContractor = (UserAccount) tblData.getValueAt(modelRow, 0);

            cmbSupervisors.setSelectedItem(selectedContractor.getSupervisor());
        }
    }//GEN-LAST:event_tblDataMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox<UserAccount> cmbSupervisors;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblData;
    // End of variables declaration//GEN-END:variables
}
