/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UserInterface1.StaffingAgency;

/**
 *
 * @author abhit
 */

import StaffingAgency.Enums.AvailabilityStatus;
import StaffingAgency.Enums.EmploymentStatus;
import StaffingAgency.People.Contractor;
import StaffingAgency.Request.CandidateSubmission;
import StaffingAgency.Request.ContractorAssignment;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class ManageContractorsJPanel extends JPanel {

    private final JPanel mainContentPanel;
    private final JPanel coordinatorDashboardPanel;
    private final List<CandidateSubmission> submissionList;

    private JTable tblContractors;
    private DefaultTableModel tableModel;

    private JTextField txtContractorId;
    private JTextField txtName;
    private JTextField txtEmail;
    private JTextField txtPhone;
    private JTextField txtSkills;
    private JTextField txtPayRate;

    private JComboBox<AvailabilityStatus> cmbAvailability;
    private JComboBox<EmploymentStatus> cmbEmploymentStatus;

    private JButton btnUpdate;
    private JButton btnRefresh;
    private JButton btnClear;
    private JButton btnBack;

    public ManageContractorsJPanel(
            JPanel mainContentPanel,
            JPanel coordinatorDashboardPanel,
            List<CandidateSubmission> submissionList
    ) {
        if (mainContentPanel == null) {
            throw new IllegalArgumentException(
                    "Main content panel cannot be null."
            );
        }

        if (coordinatorDashboardPanel == null) {
            throw new IllegalArgumentException(
                    "Coordinator dashboard panel cannot be null."
            );
        }

        if (submissionList == null) {
            throw new IllegalArgumentException(
                    "Submission list cannot be null."
            );
        }

        this.mainContentPanel = mainContentPanel;
        this.coordinatorDashboardPanel =
                coordinatorDashboardPanel;
        this.submissionList = submissionList;

        initComponents();
        populateTable();
        setFormEnabled(false);
    }

    private void initComponents() {

        setLayout(new BorderLayout(15, 15));

        setBackground(
                new Color(255, 255, 204)
        );

        setBorder(
                BorderFactory.createEmptyBorder(
                        25,
                        35,
                        25,
                        35
                )
        );

        add(
                createHeaderPanel(),
                BorderLayout.NORTH
        );

        add(
                createTablePanel(),
                BorderLayout.CENTER
        );

        add(
                createFormPanel(),
                BorderLayout.SOUTH
        );
    }

    private JPanel createHeaderPanel() {

        JPanel panel =
                new JPanel(
                        new GridLayout(2, 1, 0, 5)
                );

        panel.setOpaque(false);

        JLabel title =
                new JLabel("Manage Contractors");

        title.setFont(
                new Font(
                        "Myanmar Sangam MN",
                        Font.ITALIC,
                        24
                )
        );

        JLabel subtitle = new JLabel(
                "Update contractor contact, pay, "
                + "availability, and employment status"
        );

        subtitle.setFont(
                new Font(
                        "Myanmar MN",
                        Font.PLAIN,
                        14
                )
        );

        subtitle.setForeground(
                new Color(102, 102, 102)
        );

        panel.add(title);
        panel.add(subtitle);

        return panel;
    }

    private JPanel createTablePanel() {

        JPanel panel =
                new JPanel(
                        new BorderLayout(10, 10)
                );

        panel.setOpaque(false);

        String[] columns = {
            "Contractor ID",
            "Name",
            "Email",
            "Phone",
            "Skills",
            "Pay Rate",
            "Availability",
            "Employment Status"
        };

        tableModel =
                new DefaultTableModel(
                        new Object[][]{},
                        columns
                ) {
                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {
                        return false;
                    }
                };

        tblContractors =
                new JTable(tableModel);

        tblContractors.setRowHeight(26);

        tblContractors.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        tblContractors
                .getTableHeader()
                .setReorderingAllowed(false);

        tblContractors
                .getSelectionModel()
                .addListSelectionListener(
                        event -> {
                            if (!event.getValueIsAdjusting()) {
                                loadSelectedContractor();
                            }
                        }
                );

        panel.add(
                new JScrollPane(tblContractors),
                BorderLayout.CENTER
        );

        return panel;
    }

    private JPanel createFormPanel() {

        JPanel outerPanel =
                new JPanel(
                        new BorderLayout(10, 10)
                );

        outerPanel.setOpaque(false);

        outerPanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Contractor Details"
                )
        );

        JPanel formPanel =
                new JPanel(
                        new GridLayout(4, 4, 10, 10)
                );

        formPanel.setOpaque(false);

        txtContractorId = new JTextField();
        txtName = new JTextField();
        txtEmail = new JTextField();
        txtPhone = new JTextField();
        txtSkills = new JTextField();
        txtPayRate = new JTextField();

        /*
         * These values come from the approved candidate.
         * They are displayed here but are not edited.
         */
        txtContractorId.setEditable(false);
        txtName.setEditable(false);
        txtEmail.setEditable(false);
        txtSkills.setEditable(false);

        cmbAvailability =
                new JComboBox<>(
                        AvailabilityStatus.values()
                );

        cmbEmploymentStatus =
                new JComboBox<>(
                        EmploymentStatus.values()
                );

        formPanel.add(
                new JLabel("Contractor ID:")
        );
        formPanel.add(txtContractorId);

        formPanel.add(
                new JLabel("Name:")
        );
        formPanel.add(txtName);

        formPanel.add(
                new JLabel("Email:")
        );
        formPanel.add(txtEmail);

        formPanel.add(
                new JLabel("Phone:")
        );
        formPanel.add(txtPhone);

        formPanel.add(
                new JLabel("Skills:")
        );
        formPanel.add(txtSkills);

        formPanel.add(
                new JLabel("Pay Rate:")
        );
        formPanel.add(txtPayRate);

        formPanel.add(
                new JLabel("Availability:")
        );
        formPanel.add(cmbAvailability);

        formPanel.add(
                new JLabel("Employment Status:")
        );
        formPanel.add(cmbEmploymentStatus);

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT
                        )
                );

        buttonPanel.setOpaque(false);

        btnBack = new JButton("Back");
        btnUpdate =
                new JButton("Update Contractor");
        btnRefresh = new JButton("Refresh");
        btnClear = new JButton("Clear");

        buttonPanel.add(btnBack);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnRefresh);
        buttonPanel.add(btnClear);

        btnBack.addActionListener(
                event -> goBack()
        );

        btnUpdate.addActionListener(
                event -> updateSelectedContractor()
        );

        btnRefresh.addActionListener(
                event -> {
                    populateTable();
                    clearForm();
                }
        );

        btnClear.addActionListener(
                event -> clearForm()
        );

        outerPanel.add(
                formPanel,
                BorderLayout.CENTER
        );

        outerPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        return outerPanel;
    }

    private void populateTable() {

        tableModel.setRowCount(0);

        for (Contractor contractor
                : getContractors()) {

            tableModel.addRow(
                    new Object[]{
                        contractor.getContractorId(),
                        contractor.getFullName(),
                        contractor.getEmail(),
                        contractor.getPhone(),
                        contractor.getSkills(),
                        contractor.getPayRate(),
                        contractor.getAvailabilityStatus(),
                        contractor.getEmploymentStatus()
                    }
            );
        }
    }

    /**
     * Contractors are obtained from the assignments linked
     * to approved candidate submissions.
     */
    private List<Contractor> getContractors() {

        Map<Integer, Contractor> uniqueContractors =
                new LinkedHashMap<>();

        for (CandidateSubmission submission
                : submissionList) {

            ContractorAssignment assignment =
                    submission.getResultingAssignment();

            if (assignment == null
                    || assignment.getContractor() == null) {

                continue;
            }

            Contractor contractor =
                    assignment.getContractor();

            uniqueContractors.put(
                    contractor.getContractorId(),
                    contractor
            );
        }

        return new ArrayList<>(
                uniqueContractors.values()
        );
    }

    private void loadSelectedContractor() {

        Contractor contractor =
                getSelectedContractorSilently();

        if (contractor == null) {
            clearForm();
            return;
        }

        txtContractorId.setText(
                String.valueOf(
                        contractor.getContractorId()
                )
        );

        txtName.setText(
                contractor.getFullName()
        );

        txtEmail.setText(
                contractor.getEmail()
        );

        txtPhone.setText(
                contractor.getPhone()
        );

        txtSkills.setText(
                contractor.getSkills()
        );

        txtPayRate.setText(
                contractor.getPayRate()
                        .toPlainString()
        );

        cmbAvailability.setSelectedItem(
                contractor.getAvailabilityStatus()
        );

        cmbEmploymentStatus.setSelectedItem(
                contractor.getEmploymentStatus()
        );

        setFormEnabled(true);
    }

    private Contractor getSelectedContractor() {

        Contractor contractor =
                getSelectedContractorSilently();

        if (contractor == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Select a contractor from the table.",
                    "Selection Required",
                    JOptionPane.WARNING_MESSAGE
            );
        }

        return contractor;
    }

    private Contractor
            getSelectedContractorSilently() {

        int selectedViewRow =
                tblContractors.getSelectedRow();

        if (selectedViewRow < 0) {
            return null;
        }

        int modelRow =
                tblContractors
                        .convertRowIndexToModel(
                                selectedViewRow
                        );

        int contractorId =
                (Integer)
                        tableModel.getValueAt(
                                modelRow,
                                0
                        );

        for (Contractor contractor
                : getContractors()) {

            if (contractor.getContractorId()
                    == contractorId) {

                return contractor;
            }
        }

        return null;
    }

    private void updateSelectedContractor() {

        Contractor contractor =
                getSelectedContractor();

        if (contractor == null) {
            return;
        }

        try {
            String phone =
                    txtPhone.getText().trim();

            String payRateText =
                    txtPayRate.getText().trim();

            if (payRateText.isEmpty()) {
                throw new IllegalArgumentException(
                        "Pay rate is required."
                );
            }

            BigDecimal payRate =
                    new BigDecimal(payRateText);

            AvailabilityStatus availability =
                    (AvailabilityStatus)
                            cmbAvailability
                                    .getSelectedItem();

            EmploymentStatus employmentStatus =
                    (EmploymentStatus)
                            cmbEmploymentStatus
                                    .getSelectedItem();

            if (availability == null
                    || employmentStatus == null) {

                throw new IllegalArgumentException(
                        "Availability and employment "
                        + "status are required."
                );
            }

            contractor.updateInformation(
                    phone,
                    payRate
            );

            contractor.setEmploymentStatus(
                    employmentStatus
            );

            /*
             * A terminated contractor cannot remain
             * available or assigned.
             */
            if (employmentStatus
                    == EmploymentStatus.TERMINATED) {

                contractor.updateAvailability(
                        AvailabilityStatus.UNAVAILABLE
                );

            } else {

                contractor.updateAvailability(
                        availability
                );
            }

            populateTable();

            selectContractorRow(
                    contractor.getContractorId()
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Contractor updated successfully."
            );

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Pay rate must be a valid number.",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (IllegalArgumentException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void selectContractorRow(
            int contractorId
    ) {

        for (
                int row = 0;
                row < tableModel.getRowCount();
                row++
        ) {
            int rowContractorId =
                    (Integer)
                            tableModel.getValueAt(
                                    row,
                                    0
                            );

            if (rowContractorId
                    == contractorId) {

                tblContractors
                        .setRowSelectionInterval(
                                row,
                                row
                        );

                break;
            }
        }
    }

    private void clearForm() {

        tblContractors.clearSelection();

        txtContractorId.setText("");
        txtName.setText("");
        txtEmail.setText("");
        txtPhone.setText("");
        txtSkills.setText("");
        txtPayRate.setText("");

        if (cmbAvailability.getItemCount() > 0) {
            cmbAvailability.setSelectedIndex(0);
        }

        if (cmbEmploymentStatus.getItemCount() > 0) {
            cmbEmploymentStatus.setSelectedIndex(0);
        }

        setFormEnabled(false);
    }

    private void setFormEnabled(
            boolean enabled
    ) {

        txtPhone.setEnabled(enabled);
        txtPayRate.setEnabled(enabled);
        cmbAvailability.setEnabled(enabled);
        cmbEmploymentStatus.setEnabled(enabled);
        btnUpdate.setEnabled(enabled);
    }

    private void goBack() {

        mainContentPanel.removeAll();

        mainContentPanel.setLayout(
                new BorderLayout()
        );

        mainContentPanel.add(
                coordinatorDashboardPanel,
                BorderLayout.CENTER
        );

        mainContentPanel.revalidate();
        mainContentPanel.repaint();

        if (coordinatorDashboardPanel
                instanceof
                ContractorCoordinatorWorkAreaJPanel) {

            ContractorCoordinatorWorkAreaJPanel
                    dashboard =
                    (ContractorCoordinatorWorkAreaJPanel)
                            coordinatorDashboardPanel;

            dashboard.refreshDashboard();
        }
    }
}
