/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UserInterface.Client;

import Business.Network;
import Core.UserAccount;
import Core.WorkOrder;
import Core.WorkOrderStatus;
import Core.WorkOrders.TimecardWorkOrder;
import WorkOrders.StaffingRequest;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Alex
 */
public class ContractorTimecardsJPanel extends javax.swing.JPanel {

    private JPanel container;
    private UserAccount userAccount;
    private Network network;
    private TimecardWorkOrder selectedRecord;

    // Define the columns you actually want visible in the JTable
    private final String[] COLUMN_NAMES = {
        "Timecard ID", "Week Ending", "Total Hours", "Status"
    };

    public ContractorTimecardsJPanel(JPanel container, UserAccount userAccount, Network network) {
        initComponents(); 
        this.container = container;
        this.userAccount = userAccount;
        this.network = network;
        //setupComboBox();
        refreshTable();
    }

    // Map object to the table row
    private Object[] mapObjectToRow(TimecardWorkOrder item) {
        return new Object[]{
            item,                      // Column 0: The object (displays ID via toString)
            item.getWeekEndingDate(),        // Column 1
            item.getTotalHours(),   // Column 2
            item.getStatus()           // Column 3
        };
    }

    // Create a new blank object and pass it to the update method
    private TimecardWorkOrder buildObjectFromFields() throws NumberFormatException {
        Date utilDate = (Date) spinWeekEnding.getValue();
        LocalDate weekEnding = utilDate.toInstant()
                                       .atZone(ZoneId.systemDefault())
                                       .toLocalDate();
        TimecardWorkOrder newItem = new TimecardWorkOrder(weekEnding, 0.0);
        newItem.setSender(userAccount);
        
        
        //newItem.setStatus("Submitted");
        //newItem.setSubmittedDate(java.time.LocalDate.now().toString()); 
        
        updateObjectFromFields(newItem);
        return newItem;
    }

    // Update an object with text fields
    private void updateObjectFromFields(TimecardWorkOrder item) throws NumberFormatException {

        Date utilDate = (Date) spinWeekEnding.getValue();
        if (utilDate != null) {
            LocalDate localDate = utilDate.toInstant()
                                          .atZone(ZoneId.systemDefault())
                                          .toLocalDate();
            item.setWeekEndingDate(localDate);
        }
        double[] hours = new double[7];
        hours[0]=parseHours(txtSun);
        hours[1]=parseHours(txtMon);
        hours[2]=parseHours(txtTues);
        hours[3]=parseHours(txtWed);
        hours[4]=parseHours(txtThurs);
        hours[5]=parseHours(txtFri);
        hours[6]=parseHours(txtSat);
        item.setDailyHours(hours);
        item.setWorkSummary(txtareaDescription.getText().trim());
        //item.setStatus((WorkOrderStatus) cmbStatus.getSelectedItem());
        // Status and SubmittedDate are usually handled by system logic 
        
    }

    // Fill text fields when a table row is clicked
    private void populateForm(TimecardWorkOrder item) {
        //txtId.setText(String.valueOf(item.getId()));
        if (item.getWeekEndingDate() != null) {
            Date utilDate = Date.from(item.getWeekEndingDate()
                                          .atStartOfDay(ZoneId.systemDefault())
                                          .toInstant());
            spinWeekEnding.setValue(utilDate);
        }
        double[] hours = item.getDailyHours();
        if (hours != null && hours.length == 7) {
            txtSun.setText(String.valueOf(hours[0]));
            txtMon.setText(String.valueOf(hours[1]));
            txtTues.setText(String.valueOf(hours[2]));
            txtWed.setText(String.valueOf(hours[3]));
            txtThurs.setText(String.valueOf(hours[4]));
            txtFri.setText(String.valueOf(hours[5]));
            txtSat.setText(String.valueOf(hours[6]));
        }
        txtareaDescription.setText(item.getWorkSummary());
        //cmbStatus.setSelectedItem(item.getStatus());
    }

    // Check for empty string fields before saving
    private boolean validateInput() {

        return true;

    }
    
    /*private void setupComboBox() {
        cmbStatus.removeAllItems();

        WorkOrderStatus[] allowedStatuses = {
            WorkOrderStatus.PENDING
        };

        cmbStatus.setModel(new DefaultComboBoxModel<>(allowedStatuses));
    }*/
    private double parseHours(JTextField field) {
        String text = field.getText().trim();
    return text.isEmpty() ? 0.0 : Double.parseDouble(text);
}
    //-------------You should not need to edit below this line--------------------

    private void refreshTable() {
        DefaultTableModel model = new DefaultTableModel(COLUMN_NAMES, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        for (WorkOrder workOrder : userAccount.getWorkQueue().getWorkOrderList()) {
            if (workOrder instanceof TimecardWorkOrder) {
                TimecardWorkOrder timecard = (TimecardWorkOrder) workOrder;
                model.addRow(mapObjectToRow(timecard));
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
        spinWeekEnding.setValue(new Date());
        txtareaDescription.setText("");
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
        jLabel2 = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtareaDescription = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtSun = new javax.swing.JTextField();
        txtMon = new javax.swing.JTextField();
        txtTues = new javax.swing.JTextField();
        txtWed = new javax.swing.JTextField();
        txtThurs = new javax.swing.JTextField();
        txtFri = new javax.swing.JTextField();
        txtSat = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        spinWeekEnding = new javax.swing.JSpinner();

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

        jLabel2.setText("Description:");

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

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel6.setText("Timecards");

        txtareaDescription.setColumns(20);
        txtareaDescription.setRows(5);
        jScrollPane2.setViewportView(txtareaDescription);

        jLabel1.setText("Sun");

        jLabel3.setText("Mon");

        jLabel4.setText("Tues");

        jLabel5.setText("Wed");

        jLabel7.setText("Thurs");

        jLabel8.setText("Fri");

        jLabel9.setText("Sat");

        jLabel10.setText("Week Ending:");

        spinWeekEnding.setModel(new javax.swing.SpinnerDateModel());

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
                        .addGap(147, 147, 147)
                        .addComponent(jLabel6))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(79, 79, 79)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtSun, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtMon, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addGap(30, 30, 30)
                                        .addComponent(jLabel3)))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTues, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtWed, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtThurs, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtFri, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtSat, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel10))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(spinWeekEnding, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(78, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnSave)
                .addGap(52, 52, 52)
                .addComponent(btnDelete)
                .addGap(80, 80, 80)
                .addComponent(btnClear)
                .addGap(122, 122, 122))
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
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSun, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTues, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtWed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtThurs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(spinWeekEnding, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave)
                    .addComponent(btnDelete)
                    .addComponent(btnClear))
                .addGap(42, 42, 42))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        container.remove(this);
        java.awt.CardLayout layout = (java.awt.CardLayout) container.getLayout();
        layout.previous(container);
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if (!validateInput()) return;

        TimecardWorkOrder timecard = (selectedRecord != null) ? selectedRecord : new TimecardWorkOrder();
        updateObjectFromFields(timecard);

        
        timecard.setSender(userAccount);
        timecard.setSubmitter(userAccount);

        UserAccount supervisor = userAccount.getSupervisor();
        
        if (supervisor != null) {
            timecard.setReceiver(supervisor);
            timecard.setStatus(WorkOrderStatus.PENDING); 

            
            if (!supervisor.getWorkQueue().getWorkOrderList().contains(timecard)) {
                supervisor.getWorkQueue().getWorkOrderList().add(timecard);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No supervisor assigned. Contact Admin.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

       
        if (!userAccount.getWorkQueue().getWorkOrderList().contains(timecard)) {
            userAccount.getWorkQueue().getWorkOrderList().add(timecard);
        }

        refreshTable();
        clearFormAndSelection();
        JOptionPane.showMessageDialog(this, "Timecard submitted to supervisor successfully!");
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if (selectedRecord != null) {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this record?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                userAccount.getWorkQueue().getWorkOrderList().remove(selectedRecord);                
                refreshTable();
                clearFormAndSelection();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row from the table first.", "Warning", JOptionPane.WARNING_MESSAGE);
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
            TimecardWorkOrder castRecord = (TimecardWorkOrder) tblData.getValueAt(modelRow, 0);
            
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
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSpinner spinWeekEnding;
    private javax.swing.JTable tblData;
    private javax.swing.JTextField txtFri;
    private javax.swing.JTextField txtMon;
    private javax.swing.JTextField txtSat;
    private javax.swing.JTextField txtSun;
    private javax.swing.JTextField txtThurs;
    private javax.swing.JTextField txtTues;
    private javax.swing.JTextField txtWed;
    private javax.swing.JTextArea txtareaDescription;
    // End of variables declaration//GEN-END:variables
}
