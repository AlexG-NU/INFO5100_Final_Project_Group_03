/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UserInterface1;

/**
 *
 * @author abhit
 */


import StaffingAgency.Enums.CandidateStatus;
import StaffingAgency.People.Candidate;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;
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

public class ManageCandidatesJPanel extends JPanel {

    private final List<Candidate> candidateList;
    private final JPanel mainContentPanel;
    private final JPanel recruiterDashboardPanel;

    private JTable candidateTable;
    private DefaultTableModel tableModel;

    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JTextField txtEmail;
    private JTextField txtPhone;
    private JTextField txtSkills;
    private JTextField txtExperience;

    private JComboBox<CandidateStatus> cmbStatus;

    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnClear;
    private JButton btnBack;

    public ManageCandidatesJPanel(
            List<Candidate> candidateList,
            JPanel mainContentPanel,
            JPanel recruiterDashboardPanel
    ) {
        if (candidateList == null
                || mainContentPanel == null
                || recruiterDashboardPanel == null) {
            throw new IllegalArgumentException(
                    "Candidate list and navigation panels are required."
            );
        }

        this.candidateList = candidateList;
        this.mainContentPanel = mainContentPanel;
        this.recruiterDashboardPanel = recruiterDashboardPanel;

        initComponents();
        populateTable();
    }

    private void initComponents() {

        setLayout(new BorderLayout(10, 10));

        JLabel titleLabel = new JLabel(
                "Manage Candidates",
                JLabel.CENTER
        );

        titleLabel.setFont(
                new java.awt.Font(
                        "Arial",
                        java.awt.Font.BOLD,
                        26
                )
        );

        add(titleLabel, BorderLayout.NORTH);

        createTable();
        createForm();
    }

    private void createTable() {

        String[] columns = {
            "Candidate ID",
            "First Name",
            "Last Name",
            "Email",
            "Phone",
            "Skills",
            "Experience",
            "Status"
        };

        tableModel = new DefaultTableModel(
                columns,
                0
        ) {
            @Override
            public boolean isCellEditable(
                    int row,
                    int column
            ) {
                return false;
            }
        };

        candidateTable = new JTable(tableModel);

        candidateTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        candidateTable.getSelectionModel()
                .addListSelectionListener(event -> {
                    if (!event.getValueIsAdjusting()) {
                        loadSelectedCandidate();
                    }
                });

        add(
                new JScrollPane(candidateTable),
                BorderLayout.CENTER
        );
    }

    private void createForm() {

        JPanel bottomPanel =
                new JPanel(new BorderLayout(10, 10));

        JPanel formPanel =
                new JPanel(new GridLayout(4, 4, 10, 10));

        txtFirstName = new JTextField();
        txtLastName = new JTextField();
        txtEmail = new JTextField();
        txtPhone = new JTextField();
        txtSkills = new JTextField();
        txtExperience = new JTextField();

        cmbStatus = new JComboBox<>(
                CandidateStatus.values()
        );

        formPanel.add(new JLabel("First Name:"));
        formPanel.add(txtFirstName);

        formPanel.add(new JLabel("Last Name:"));
        formPanel.add(txtLastName);

        formPanel.add(new JLabel("Email:"));
        formPanel.add(txtEmail);

        formPanel.add(new JLabel("Phone:"));
        formPanel.add(txtPhone);

        formPanel.add(new JLabel("Skills:"));
        formPanel.add(txtSkills);

        formPanel.add(new JLabel("Years of Experience:"));
        formPanel.add(txtExperience);

        formPanel.add(new JLabel("Status:"));
        formPanel.add(cmbStatus);

        formPanel.add(new JLabel());

        JPanel buttonPanel =
                new JPanel(new FlowLayout(FlowLayout.CENTER));

        btnAdd = new JButton("Add Candidate");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnClear = new JButton("Clear");
        btnBack = new JButton("<< Back");

        buttonPanel.add(btnBack);
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);

        btnAdd.addActionListener(
                event -> addCandidate()
        );

        btnBack.addActionListener(
                event -> goBack()
        );

        btnUpdate.addActionListener(
                event -> updateCandidate()
        );

        btnDelete.addActionListener(
                event -> deleteCandidate()
        );

        btnClear.addActionListener(
                event -> clearFields()
        );

        bottomPanel.add(formPanel, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void populateTable() {

        tableModel.setRowCount(0);

        for (Candidate candidate : candidateList) {

            Object[] row = {
                candidate.getCandidateId(),
                candidate.getFirstName(),
                candidate.getLastName(),
                candidate.getEmail(),
                candidate.getPhone(),
                candidate.getSkills(),
                candidate.getYearsOfExperience(),
                candidate.getCandidateStatus()
            };

            tableModel.addRow(row);
        }
    }

    private void addCandidate() {

        if (candidateTable.getSelectedRow() >= 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "This candidate is already selected. "
                    + "Use Update to save changes, or click Clear "
                    + "before adding a new candidate.",
                    "Candidate Already Exists",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {
            String email = txtEmail.getText().trim();

            for (Candidate existingCandidate : candidateList) {
                if (existingCandidate.getEmail() != null
                        && existingCandidate.getEmail()
                                .equalsIgnoreCase(email)) {
                    JOptionPane.showMessageDialog(
                            this,
                            "A candidate with this email already exists.",
                            "Duplicate Candidate",
                            JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }
            }

            int yearsOfExperience =
                    Integer.parseInt(
                            txtExperience.getText().trim()
                    );

            Candidate candidate = new Candidate(
                    txtFirstName.getText(),
                    txtLastName.getText(),
                    txtEmail.getText(),
                    txtPhone.getText(),
                    txtSkills.getText(),
                    yearsOfExperience
            );

            candidate.setCandidateStatus(
                    (CandidateStatus)
                            cmbStatus.getSelectedItem()
            );

            candidateList.add(candidate);

            populateTable();
            clearFields();

            JOptionPane.showMessageDialog(
                    this,
                    "Candidate added successfully."
            );

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Years of experience must be a number.",
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

    private void updateCandidate() {

        int selectedRow =
                candidateTable.getSelectedRow();

        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Select a candidate to update."
            );
            return;
        }

        try {
            Candidate candidate =
                    candidateList.get(selectedRow);

            int yearsOfExperience =
                    Integer.parseInt(
                            txtExperience.getText().trim()
                    );

            candidate.updateInformation(
                    txtFirstName.getText(),
                    txtLastName.getText(),
                    txtEmail.getText(),
                    txtPhone.getText(),
                    txtSkills.getText(),
                    yearsOfExperience,
                    (CandidateStatus)
                            cmbStatus.getSelectedItem()
            );

            populateTable();
            clearFields();

            JOptionPane.showMessageDialog(
                    this,
                    "Candidate updated successfully."
            );

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Years of experience must be a number.",
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

    private void deleteCandidate() {

        int selectedRow =
                candidateTable.getSelectedRow();

        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Select a candidate to delete."
            );
            return;
        }

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this candidate?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            candidateList.remove(selectedRow);

            populateTable();
            clearFields();

            JOptionPane.showMessageDialog(
                    this,
                    "Candidate deleted successfully."
            );
        }
    }

    private void loadSelectedCandidate() {

        int selectedRow =
                candidateTable.getSelectedRow();

        if (selectedRow < 0) {
            return;
        }

        Candidate candidate =
                candidateList.get(selectedRow);

        txtFirstName.setText(
                candidate.getFirstName()
        );

        txtLastName.setText(
                candidate.getLastName()
        );

        txtEmail.setText(
                candidate.getEmail()
        );

        txtPhone.setText(
                candidate.getPhone()
        );

        txtSkills.setText(
                candidate.getSkills()
        );

        txtExperience.setText(
                String.valueOf(
                        candidate.getYearsOfExperience()
                )
        );

        cmbStatus.setSelectedItem(
                candidate.getCandidateStatus()
        );
    }

    private void clearFields() {

        txtFirstName.setText("");
        txtLastName.setText("");
        txtEmail.setText("");
        txtPhone.setText("");
        txtSkills.setText("");
        txtExperience.setText("");

        cmbStatus.setSelectedItem(
                CandidateStatus.APPLIED
        );

        candidateTable.clearSelection();
    }

    private void goBack() {

        mainContentPanel.removeAll();
        mainContentPanel.setLayout(new BorderLayout());
        mainContentPanel.add(
                recruiterDashboardPanel,
                BorderLayout.CENTER
        );
        mainContentPanel.revalidate();
        mainContentPanel.repaint();

        if (recruiterDashboardPanel
                instanceof UserInterface1.StaffingAgency.RecruiterWorkAreaJPanel) {
            ((UserInterface1.StaffingAgency.RecruiterWorkAreaJPanel)
                    recruiterDashboardPanel).refreshDashboard();
        }
    }
}
