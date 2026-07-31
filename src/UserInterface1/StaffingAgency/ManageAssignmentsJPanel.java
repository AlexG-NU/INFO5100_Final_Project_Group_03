/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UserInterface1.StaffingAgency;

/**
 *
 * @author abhit
 */

import StaffingAgency.Enums.AssignmentStatus;
import StaffingAgency.Enums.AvailabilityStatus;
import StaffingAgency.Enums.EmploymentStatus;
import StaffingAgency.People.Contractor;
import StaffingAgency.Request.CandidateSubmission;
import StaffingAgency.Request.Contract;
import StaffingAgency.Request.ContractorAssignment;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class ManageAssignmentsJPanel extends JPanel {

    private final JPanel mainContentPanel;
    private final JPanel coordinatorDashboardPanel;
    private final List<CandidateSubmission> submissionList;

    private JTable tblAssignments;
    private DefaultTableModel tableModel;

    private JTextField txtAssignmentId;
    private JTextField txtSubmissionId;
    private JTextField txtContractor;
    private JTextField txtJobTitle;
    private JTextField txtStartDate;
    private JTextField txtEndDate;
    private JTextField txtStatus;
    private JTextField txtPayRate;
    private JTextField txtBillRate;

    private JButton btnSaveContract;
    private JButton btnActivate;
    private JButton btnComplete;
    private JButton btnTerminate;
    private JButton btnRefresh;
    private JButton btnClear;
    private JButton btnBack;

    public ManageAssignmentsJPanel(
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
        setBackground(new Color(255, 255, 204));

        setBorder(
                BorderFactory.createEmptyBorder(
                        25,
                        35,
                        25,
                        35
                )
        );

        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createTablePanel(), BorderLayout.CENTER);
        add(createFormPanel(), BorderLayout.SOUTH);
    }

    private JPanel createHeaderPanel() {

        JPanel panel =
                new JPanel(new GridLayout(2, 1, 0, 5));

        panel.setOpaque(false);

        JLabel title =
                new JLabel("Manage Contractor Assignments");

        title.setFont(
                new Font(
                        "Myanmar Sangam MN",
                        Font.ITALIC,
                        24
                )
        );

        JLabel subtitle = new JLabel(
                "Create contract details and manage assignment "
                + "status after Compliance review"
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
                new JPanel(new BorderLayout(10, 10));

        panel.setOpaque(false);

        String[] columns = {
            "Assignment ID",
            "Submission ID",
            "Contractor",
            "Job Title",
            "Start Date",
            "End Date",
            "Assignment Status",
            "Contract ID",
            "Contract Status",
            "Pay Rate",
            "Bill Rate"
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

        tblAssignments = new JTable(tableModel);
        tblAssignments.setRowHeight(26);
        tblAssignments.setAutoCreateRowSorter(true);

        tblAssignments.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        tblAssignments
                .getTableHeader()
                .setReorderingAllowed(false);

        tblAssignments
                .getSelectionModel()
                .addListSelectionListener(
                        event -> {
                            if (!event.getValueIsAdjusting()) {
                                loadSelectedAssignment();
                            }
                        }
                );

        panel.add(
                new JScrollPane(tblAssignments),
                BorderLayout.CENTER
        );

        return panel;
    }

    private JPanel createFormPanel() {

        JPanel outerPanel =
                new JPanel(new BorderLayout(10, 10));

        outerPanel.setOpaque(false);

        outerPanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Assignment and Contract Details"
                )
        );

        JPanel formPanel =
                new JPanel(new GridLayout(5, 4, 10, 10));

        formPanel.setOpaque(false);

        txtAssignmentId = new JTextField();
        txtSubmissionId = new JTextField();
        txtContractor = new JTextField();
        txtJobTitle = new JTextField();
        txtStartDate = new JTextField();
        txtEndDate = new JTextField();
        txtStatus = new JTextField();
        txtPayRate = new JTextField();
        txtBillRate = new JTextField();

        txtAssignmentId.setEditable(false);
        txtSubmissionId.setEditable(false);
        txtContractor.setEditable(false);
        txtJobTitle.setEditable(false);
        txtStartDate.setEditable(false);
        txtStatus.setEditable(false);

        formPanel.add(new JLabel("Assignment ID:"));
        formPanel.add(txtAssignmentId);

        formPanel.add(new JLabel("Submission ID:"));
        formPanel.add(txtSubmissionId);

        formPanel.add(new JLabel("Contractor:"));
        formPanel.add(txtContractor);

        formPanel.add(new JLabel("Job Title:"));
        formPanel.add(txtJobTitle);

        formPanel.add(new JLabel("Start Date:"));
        formPanel.add(txtStartDate);

        formPanel.add(
                new JLabel("End Date (YYYY-MM-DD):")
        );
        formPanel.add(txtEndDate);

        formPanel.add(new JLabel("Assignment Status:"));
        formPanel.add(txtStatus);

        formPanel.add(new JLabel("Pay Rate:"));
        formPanel.add(txtPayRate);

        formPanel.add(new JLabel("Bill Rate:"));
        formPanel.add(txtBillRate);

        formPanel.add(new JLabel());
        formPanel.add(new JLabel());

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(FlowLayout.RIGHT)
                );

        buttonPanel.setOpaque(false);

        btnBack = new JButton("Back");

        btnSaveContract =
                new JButton("Save Contract Details");

        btnActivate =
                new JButton("Activate Assignment");

        btnComplete =
                new JButton("Complete Assignment");

        btnTerminate =
                new JButton("Terminate Assignment");

        btnRefresh = new JButton("Refresh");
        btnClear = new JButton("Clear");

        buttonPanel.add(btnBack);
        buttonPanel.add(btnSaveContract);
        buttonPanel.add(btnActivate);
        buttonPanel.add(btnComplete);
        buttonPanel.add(btnTerminate);
        buttonPanel.add(btnRefresh);
        buttonPanel.add(btnClear);

        btnBack.addActionListener(
                event -> goBack()
        );

        btnSaveContract.addActionListener(
                event -> saveContractDetails()
        );

        btnActivate.addActionListener(
                event -> activateSelectedAssignment()
        );

        btnComplete.addActionListener(
                event -> completeSelectedAssignment()
        );

        btnTerminate.addActionListener(
                event -> terminateSelectedAssignment()
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

        for (ContractorAssignment assignment
                : getAssignments()) {

            CandidateSubmission submission =
                    findSubmissionForAssignment(
                            assignment
                    );

            Contract contract =
                    assignment.getContract();

            tableModel.addRow(
                    new Object[]{
                        assignment.getAssignmentId(),

                        submission == null
                                ? ""
                                : submission.getSubmissionId(),

                        assignment.getContractor()
                                .getFullName(),

                        submission == null
                                ? ""
                                : submission
                                        .getStaffingRequest()
                                        .getJobTitle(),

                        assignment.getStartDate(),

                        assignment.getEndDate() == null
                                ? ""
                                : assignment.getEndDate(),

                        assignment.getStatus(),

                        contract == null
                                ? ""
                                : contract.getContractId(),

                        contract == null
                                ? "Not Created"
                                : contract.getStatus(),

                        contract == null
                                ? assignment
                                        .getContractor()
                                        .getPayRate()
                                : contract.getPayRate(),

                        contract == null
                                ? ""
                                : contract.getBillRate()
                    }
            );
        }
    }

    private List<ContractorAssignment>
            getAssignments() {

        Map<Integer, ContractorAssignment>
                uniqueAssignments =
                new LinkedHashMap<>();

        for (CandidateSubmission submission
                : submissionList) {

            ContractorAssignment assignment =
                    submission
                            .getResultingAssignment();

            if (assignment != null) {
                uniqueAssignments.put(
                        assignment.getAssignmentId(),
                        assignment
                );
            }
        }

        return new ArrayList<>(
                uniqueAssignments.values()
        );
    }

    private CandidateSubmission
            findSubmissionForAssignment(
                    ContractorAssignment assignment
            ) {

        for (CandidateSubmission submission
                : submissionList) {

            if (submission
                    .getResultingAssignment()
                    == assignment) {

                return submission;
            }
        }

        return null;
    }

    private void loadSelectedAssignment() {

        ContractorAssignment assignment =
                getSelectedAssignmentSilently();

        if (assignment == null) {
            clearForm();
            return;
        }

        CandidateSubmission submission =
                findSubmissionForAssignment(
                        assignment
                );

        Contract contract =
                assignment.getContract();

        txtAssignmentId.setText(
                String.valueOf(
                        assignment.getAssignmentId()
                )
        );

        txtSubmissionId.setText(
                submission == null
                        ? ""
                        : String.valueOf(
                                submission.getSubmissionId()
                        )
        );

        txtContractor.setText(
                assignment.getContractor()
                        .getFullName()
        );

        txtJobTitle.setText(
                submission == null
                        ? ""
                        : submission
                                .getStaffingRequest()
                                .getJobTitle()
        );

        txtStartDate.setText(
                String.valueOf(
                        assignment.getStartDate()
                )
        );

        txtEndDate.setText(
                assignment.getEndDate() == null
                        ? ""
                        : assignment
                                .getEndDate()
                                .toString()
        );

        txtStatus.setText(
                String.valueOf(
                        assignment.getStatus()
                )
        );

        txtPayRate.setText(
                contract == null
                        ? assignment
                                .getContractor()
                                .getPayRate()
                                .toPlainString()
                        : contract
                                .getPayRate()
                                .toPlainString()
        );

        txtBillRate.setText(
                contract == null
                        ? ""
                        : contract
                                .getBillRate()
                                .toPlainString()
        );

        setFormEnabled(true);
        updateActionButtons(assignment);
    }

    private ContractorAssignment
            getSelectedAssignment() {

        ContractorAssignment assignment =
                getSelectedAssignmentSilently();

        if (assignment == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Select an assignment from the table.",
                    "Selection Required",
                    JOptionPane.WARNING_MESSAGE
            );
        }

        return assignment;
    }

    private ContractorAssignment
            getSelectedAssignmentSilently() {

        int selectedViewRow =
                tblAssignments.getSelectedRow();

        if (selectedViewRow < 0) {
            return null;
        }

        int modelRow =
                tblAssignments
                        .convertRowIndexToModel(
                                selectedViewRow
                        );

        int assignmentId =
                (Integer)
                        tableModel.getValueAt(
                                modelRow,
                                0
                        );

        for (ContractorAssignment assignment
                : getAssignments()) {

            if (assignment.getAssignmentId()
                    == assignmentId) {

                return assignment;
            }
        }

        return null;
    }

    private void saveContractDetails() {

        ContractorAssignment assignment =
                getSelectedAssignment();

        if (assignment == null) {
            return;
        }

        if (assignment.getStatus()
                == AssignmentStatus.COMPLETED
                || assignment.getStatus()
                == AssignmentStatus.TERMINATED) {

            showError(
                    "A completed or terminated assignment "
                    + "cannot be edited."
            );

            return;
        }

        try {
            String endDateText =
                    txtEndDate.getText().trim();

            String payRateText =
                    txtPayRate.getText().trim();

            String billRateText =
                    txtBillRate.getText().trim();

            if (endDateText.isEmpty()) {
                throw new IllegalArgumentException(
                        "End date is required."
                );
            }

            if (payRateText.isEmpty()) {
                throw new IllegalArgumentException(
                        "Pay rate is required."
                );
            }

            if (billRateText.isEmpty()) {
                throw new IllegalArgumentException(
                        "Bill rate is required."
                );
            }

            LocalDate endDate =
                    LocalDate.parse(endDateText);

            BigDecimal payRate =
                    new BigDecimal(payRateText);

            BigDecimal billRate =
                    new BigDecimal(billRateText);

            if (endDate.isBefore(
                    assignment.getStartDate()
            )) {
                throw new IllegalArgumentException(
                        "End date cannot be before "
                        + "the assignment start date."
                );
            }

            if (payRate.signum() < 0) {
                throw new IllegalArgumentException(
                        "Pay rate cannot be negative."
                );
            }

            if (billRate.compareTo(payRate) < 0) {
                throw new IllegalArgumentException(
                        "Bill rate cannot be lower "
                        + "than pay rate."
                );
            }

            Contract contract =
                    assignment.getContract();

            if (contract == null) {

                contract = new Contract(
                        assignment,
                        assignment.getStartDate(),
                        endDate,
                        payRate,
                        billRate
                );

            } else {

                if (endDate.isBefore(
                        contract.getEndDate()
                )) {
                    throw new IllegalArgumentException(
                            "End date cannot be earlier than "
                            + "the current contract end date."
                    );
                }

                contract.setPayRate(payRate);
                contract.setBillRate(billRate);

                if (endDate.isAfter(
                        contract.getEndDate()
                )) {
                    contract.extendContract(
                            endDate
                    );
                }
            }

            if (assignment.getEndDate() == null
                    || endDate.isAfter(
                            assignment.getEndDate()
                    )) {

                assignment.extendAssignment(
                        endDate
                );
            }

            assignment.getContractor()
                    .setPayRate(payRate);

            populateTable();

            selectAssignmentRow(
                    assignment.getAssignmentId()
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Assignment contract details "
                    + "saved successfully."
            );

        } catch (DateTimeParseException ex) {

            showError(
                    "Enter the end date in "
                    + "YYYY-MM-DD format."
            );

        } catch (NumberFormatException ex) {

            showError(
                    "Pay rate and bill rate must "
                    + "be valid numbers."
            );

        } catch (IllegalArgumentException ex) {

            showError(ex.getMessage());
        }
    }

    private void activateSelectedAssignment() {

        ContractorAssignment assignment =
                getSelectedAssignment();

        if (assignment == null) {
            return;
        }

        if (assignment.getContract() == null) {

            showError(
                    "Create the contract before "
                    + "activating the assignment."
            );

            return;
        }

        try {
            assignment.activateAssignment();

            assignment.getContract()
                    .activateContract();

            assignment.getContractor()
                    .updateAvailability(
                            AvailabilityStatus.ASSIGNED
                    );

            populateTable();

            selectAssignmentRow(
                    assignment.getAssignmentId()
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Assignment activated successfully."
            );

        } catch (IllegalStateException ex) {

            showError(ex.getMessage());
        }
    }

    private void completeSelectedAssignment() {

        ContractorAssignment assignment =
                getSelectedAssignment();

        if (assignment == null) {
            return;
        }

        if (assignment.getStatus()
                != AssignmentStatus.ACTIVE) {

            showError(
                    "Only an active assignment "
                    + "can be completed."
            );

            return;
        }

        assignment.completeAssignment();

        makeContractorAvailableAfterAssignment(
                assignment.getContractor()
        );

        populateTable();

        selectAssignmentRow(
                assignment.getAssignmentId()
        );

        JOptionPane.showMessageDialog(
                this,
                "Assignment completed successfully."
        );
    }

    private void terminateSelectedAssignment() {

        ContractorAssignment assignment =
                getSelectedAssignment();

        if (assignment == null) {
            return;
        }

        if (assignment.getStatus()
                == AssignmentStatus.COMPLETED
                || assignment.getStatus()
                == AssignmentStatus.TERMINATED) {

            showError(
                    "This assignment is already closed."
            );

            return;
        }

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Terminate the selected assignment?",
                        "Confirm Termination",
                        JOptionPane.YES_NO_OPTION
                );

        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        assignment.terminateAssignment();

        if (assignment.getContract() != null) {
            assignment.getContract()
                    .terminateContract();
        }

        makeContractorAvailableAfterAssignment(
                assignment.getContractor()
        );

        populateTable();

        selectAssignmentRow(
                assignment.getAssignmentId()
        );

        JOptionPane.showMessageDialog(
                this,
                "Assignment terminated."
        );
    }

    private void makeContractorAvailableAfterAssignment(
            Contractor contractor
    ) {
        if (contractor.getEmploymentStatus()
                == EmploymentStatus.ACTIVE) {

            contractor.updateAvailability(
                    AvailabilityStatus.AVAILABLE
            );

        } else {

            contractor.updateAvailability(
                    AvailabilityStatus.UNAVAILABLE
            );
        }
    }

    private void selectAssignmentRow(
            int assignmentId
    ) {
        for (
                int row = 0;
                row < tableModel.getRowCount();
                row++
        ) {
            int rowAssignmentId =
                    (Integer)
                            tableModel.getValueAt(
                                    row,
                                    0
                            );

            if (rowAssignmentId
                    == assignmentId) {

                int viewRow =
                        tblAssignments
                                .convertRowIndexToView(
                                        row
                                );

                tblAssignments
                        .setRowSelectionInterval(
                                viewRow,
                                viewRow
                        );

                break;
            }
        }
    }

    private void updateActionButtons(
            ContractorAssignment assignment
    ) {
        AssignmentStatus status =
                assignment.getStatus();

        boolean closed =
                status == AssignmentStatus.COMPLETED
                || status == AssignmentStatus.TERMINATED;

        btnSaveContract.setEnabled(!closed);

        btnActivate.setEnabled(
                status == AssignmentStatus.CLEARED
        );

        btnComplete.setEnabled(
                status == AssignmentStatus.ACTIVE
        );

        btnTerminate.setEnabled(!closed);
    }

    private void clearForm() {

        tblAssignments.clearSelection();

        txtAssignmentId.setText("");
        txtSubmissionId.setText("");
        txtContractor.setText("");
        txtJobTitle.setText("");
        txtStartDate.setText("");
        txtEndDate.setText("");
        txtStatus.setText("");
        txtPayRate.setText("");
        txtBillRate.setText("");

        setFormEnabled(false);
    }

    private void setFormEnabled(
            boolean enabled
    ) {
        txtEndDate.setEnabled(enabled);
        txtPayRate.setEnabled(enabled);
        txtBillRate.setEnabled(enabled);

        btnSaveContract.setEnabled(enabled);
        btnActivate.setEnabled(false);
        btnComplete.setEnabled(false);
        btnTerminate.setEnabled(enabled);
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

    private void showError(String message) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "Validation Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}