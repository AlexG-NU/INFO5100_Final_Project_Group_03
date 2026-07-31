/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UserInterface.Client;


import Business.Network;
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
public class HiringManagerStaffingReqsJPanel extends javax.swing.JPanel {

    private JPanel container;
    private UserAccount account;
    private Network network;
    private StaffingReqWorkOrder selectedRecord = null; 

    // Define the columns you actually want visible in the JTable
    private final String[] COLUMN_NAMES = {
        "Req ID", "Job Title", "Target Hires", "Start Date", "Status"
    };

    public HiringManagerStaffingReqsJPanel(JPanel container, UserAccount account, Network network) {
        initComponents(); 
        this.container = container;
        this.account = account;
        this.network = network;
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
    private StaffingReqWorkOrder buildObjectFromFields() throws NumberFormatException {
        StaffingReqWorkOrder newItem = new StaffingReqWorkOrder();
        
        
        newItem.setSender(account);                        
        //newItem.setRequestDate(LocalDate.now());           
        newItem.setStatus(WorkOrderStatus.PENDING);       
        //newItem.setJobTitle(txtJobTitle.getText().trim()); 
        
        updateObjectFromFields(newItem);
        return newItem;
    }

    // Update an object with text fields
    private void updateObjectFromFields(StaffingReqWorkOrder item) throws NumberFormatException {
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
        
    }

    // Fill text fields when a table row is clicked
    private void populateForm(StaffingReqWorkOrder item) {
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
    }

    // Check for empty string fields before saving
    private boolean validateInput() {
        //if (txtId.getText().trim().isEmpty() || 
        if    (txtJobTitle.getText().trim().isEmpty() || 
            txtQuantity.getText().trim().isEmpty()) {
            
            JOptionPane.showMessageDialog(this, "ID, Job Title, Number of Hires are required.", "Warning", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
    
    //-------------You should not need to edit below this line--------------------

    private void refreshTable() {
        DefaultTableModel model = new DefaultTableModel(COLUMN_NAMES, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { 
                return false; 
            }
        };
        
        // Populate directly from the manager's WorkQueue
        if (account != null && account.getWorkQueue() != null) {
            for (WorkOrder wo : account.getWorkQueue().getWorkOrderList()) {
                if (wo instanceof StaffingReqWorkOrder) {
                    model.addRow(mapObjectToRow((StaffingReqWorkOrder) wo));
                }
            }
        }
        tblData.setModel(model);
    }

    private void clearFormAndSelection() {
        // Clear visually (add specific fields here if needed in Zone 1, 
        // but looping components keeps it generic)
        java.awt.Component[] components = getComponents();
        for (java.awt.Component component : components) {
            if (component instanceof javax.swing.JTextField) {
                ((javax.swing.JTextField) component).setText("");
            }
        }
        spnStartDate.setValue(new java.util.Date());
        selectedRecord = null;
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
        txtJobTitle = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtDescription = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtSkills = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtQuantity = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        spnStartDate = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();

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

        jLabel1.setText("Job Title:");

        jLabel2.setText("Description:");

        jLabel3.setText("Required Skills:");

        jLabel4.setText("Start Date:");

        jLabel5.setText("Quantity:");

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete");
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

        spnStartDate.setModel(new javax.swing.SpinnerDateModel());

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel6.setText("Staffing Requests");

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
                        .addGap(144, 144, 144)
                        .addComponent(jLabel6))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(134, 134, 134)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(btnSave, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addComponent(btnDelete)
                                .addGap(80, 80, 80)
                                .addComponent(btnClear))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(spnStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtJobTitle, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtDescription, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtSkills, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(78, Short.MAX_VALUE))
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
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtJobTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDescription, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSkills, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(spnStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
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
        if (!validateInput()) return;

        try {
            if (selectedRecord == null) {
                StaffingReqWorkOrder newWorkOrder = buildObjectFromFields();

                account.getWorkQueue().getWorkOrderList().add(newWorkOrder);

                Organization staffingOrg = NetworkUtils.findOrganizationByName(network, "Staffing Agency Enterprise", "Recruiting Organization");

                if (staffingOrg != null) {
                    staffingOrg.getWorkQueue().getWorkOrderList().add(newWorkOrder);
                    JOptionPane.showMessageDialog(this, "Staffing Work Order #" + newWorkOrder.getWorkOrderId() + " submitted to Recruiting queue.");
                } else {
                    JOptionPane.showMessageDialog(this, "Work Order saved locally, but target Recruiting queue was not found.", "Routing Warning", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                updateObjectFromFields(selectedRecord);
                JOptionPane.showMessageDialog(this, "Work Order updated successfully.");
            }

            refreshTable();
            clearFormAndSelection();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please ensure Quantity is a valid integer.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "An unexpected error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if (selectedRecord != null) {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this work order?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                
                // Remove from Hiring Manager's local queue
                account.getWorkQueue().getWorkOrderList().remove(selectedRecord);
                
                // Remove from target Staffing queue if not yet processed
                Organization staffingOrg = NetworkUtils.findOrganizationByName(network, "Staffing Agency Enterprise", "Recruiting Organization");
                if (staffingOrg != null) {
                    staffingOrg.getWorkQueue().getWorkOrderList().remove(selectedRecord);
                }
                
                refreshTable();
                clearFormAndSelection();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a work order from the table first.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        clearFormAndSelection();
    }//GEN-LAST:event_btnClearActionPerformed

    private void tblDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDataMouseClicked
        int viewRow = tblData.getSelectedRow();
        if (viewRow >= 0) {
            int modelRow = tblData.convertRowIndexToModel(viewRow);
            
            // Suppress warning here as we strictly control column 0 insertion
            @SuppressWarnings("unchecked") 
            StaffingReqWorkOrder castRecord = (StaffingReqWorkOrder) tblData.getValueAt(modelRow, 0);
            
            selectedRecord = castRecord;
            populateForm(selectedRecord);
        }
    }//GEN-LAST:event_tblDataMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnSave;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner spnStartDate;
    private javax.swing.JTable tblData;
    private javax.swing.JTextField txtDescription;
    private javax.swing.JTextField txtJobTitle;
    private javax.swing.JTextField txtQuantity;
    private javax.swing.JTextField txtSkills;
    // End of variables declaration//GEN-END:variables
}
