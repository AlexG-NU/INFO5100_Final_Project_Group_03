/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UserInterface1.StaffingAgency;

/**
 *
 * @author abhit
 */

import Business.Network;
import ComplianceEnterprise.ComplianceIntegrationService;
import ComplianceEnterprise.Model.ComplianceData;
import ComplianceEnterprise.Model.VerificationReview;
import StaffingAgency.Enums.AssignmentStatus;
import StaffingAgency.Enums.RequestStatus;
import StaffingAgency.Request.CandidateSubmission;
import StaffingAgency.Request.ContractorAssignment;
import StaffingAgency.Request.CredentialVerificationRequest;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
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
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class CredentialVerificationJPanel extends JPanel {

    private final JPanel mainContentPanel;
    private final JPanel coordinatorDashboardPanel;
    private final List<CandidateSubmission> submissionList;
    private final Network network;

    private JTable tblVerification;
    private DefaultTableModel tableModel;

    private JComboBox<String> cmbVerificationType;
    private JTextArea txtNotes;

    private JButton btnSubmit;
    private JButton btnRefresh;
    private JButton btnClear;
    private JButton btnBack;

    public CredentialVerificationJPanel(
            JPanel mainContentPanel,
            JPanel coordinatorDashboardPanel,
            List<CandidateSubmission> submissionList,
            Network network
    ) {
        if (mainContentPanel == null) {
            throw new IllegalArgumentException(
                    "Main content panel cannot be null."
            );
        }

        if (coordinatorDashboardPanel == null) {
            throw new IllegalArgumentException(
                    "Coordinator dashboard cannot be null."
            );
        }

        if (submissionList == null) {
            throw new IllegalArgumentException(
                    "Submission list cannot be null."
            );
        }

        if (network == null) {
            throw new IllegalArgumentException(
                    "Network cannot be null."
            );
        }

        this.mainContentPanel = mainContentPanel;
        this.coordinatorDashboardPanel =
                coordinatorDashboardPanel;
        this.submissionList = submissionList;
        this.network = network;

        initComponents();
        populateTable();
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
                new JLabel("Credential Verification");

        title.setFont(
                new Font(
                        "Myanmar Sangam MN",
                        Font.ITALIC,
                        24
                )
        );

        JLabel subtitle = new JLabel(
                "Submit contractor assignments to Compliance "
                + "and track verification results"
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
            "Contractor",
            "Job Title",
            "Assignment Status",
            "Verification Request ID",
            "Verification Type",
            "Request Status",
            "Compliance Decision",
            "Assigned Analyst",
            "Review Date",
            "Findings"
        };

        tableModel = new DefaultTableModel(
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

        tblVerification = new JTable(tableModel);
        tblVerification.setRowHeight(26);
        tblVerification.setAutoCreateRowSorter(true);

        tblVerification.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        tblVerification.getTableHeader()
                .setReorderingAllowed(false);

        panel.add(
                new JScrollPane(tblVerification),
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
                        "Submit Verification Request"
                )
        );

        JPanel formPanel =
                new JPanel(new GridLayout(2, 2, 10, 10));

        formPanel.setOpaque(false);

        cmbVerificationType = new JComboBox<>(
                new String[]{
                    "Background and Credential Verification",
                    "Professional License Verification",
                    "Employment and Education Verification",
                    "Identity and Eligibility Verification"
                }
        );

        txtNotes = new JTextArea(3, 30);
        txtNotes.setLineWrap(true);
        txtNotes.setWrapStyleWord(true);

        formPanel.add(
                new JLabel("Verification Type:")
        );
        formPanel.add(cmbVerificationType);

        formPanel.add(
                new JLabel("Instructions / Notes:")
        );
        formPanel.add(new JScrollPane(txtNotes));

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(FlowLayout.RIGHT)
                );

        buttonPanel.setOpaque(false);

        btnBack = new JButton("Back");
        btnSubmit =
                new JButton("Send to Compliance");
        btnRefresh = new JButton("Refresh");
        btnClear = new JButton("Clear");

        buttonPanel.add(btnBack);
        buttonPanel.add(btnSubmit);
        buttonPanel.add(btnRefresh);
        buttonPanel.add(btnClear);

        btnBack.addActionListener(
                event -> goBack()
        );

        btnSubmit.addActionListener(
                event -> submitSelectedAssignment()
        );

        btnRefresh.addActionListener(
                event -> populateTable()
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

            CredentialVerificationRequest request =
                    getLatestVerificationRequest(
                            assignment
                    );

            VerificationReview review =
                    findReview(request);

            tableModel.addRow(
                    new Object[]{
                        assignment.getAssignmentId(),

                        assignment.getContractor()
                                .getFullName(),

                        submission == null
                                ? ""
                                : submission
                                        .getStaffingRequest()
                                        .getJobTitle(),

                        assignment.getStatus(),

                        request == null
                                ? ""
                                : request
                                        .getVerificationRequestId(),

                        request == null
                                ? ""
                                : request
                                        .getVerificationType(),

                        request == null
                                ? "Not Submitted"
                                : request.getStatus(),

                        review == null
                                ? ""
                                : review.getDecision(),

                        review == null
                                || review.getAssignedAnalyst()
                                == null
                                ? ""
                                : review
                                        .getAssignedAnalyst()
                                        .getName(),

                        review == null
                                || review.getReviewDate() == null
                                ? ""
                                : review.getReviewDate(),

                        review == null
                                ? ""
                                : review.getFindings()
                    }
            );
        }
    }

    private List<ContractorAssignment>
            getAssignments() {

        Map<Integer, ContractorAssignment> unique =
                new LinkedHashMap<>();

        for (CandidateSubmission submission
                : submissionList) {

            ContractorAssignment assignment =
                    submission.getResultingAssignment();

            if (assignment != null) {
                unique.put(
                        assignment.getAssignmentId(),
                        assignment
                );
            }
        }

        return new ArrayList<>(unique.values());
    }

    private CandidateSubmission
            findSubmissionForAssignment(
                    ContractorAssignment assignment
            ) {

        for (CandidateSubmission submission
                : submissionList) {

            if (submission.getResultingAssignment()
                    == assignment) {

                return submission;
            }
        }

        return null;
    }

    private CredentialVerificationRequest
            getLatestVerificationRequest(
                    ContractorAssignment assignment
            ) {

        List<CredentialVerificationRequest> requests =
                assignment.getVerificationRequests();

        if (requests.isEmpty()) {
            return null;
        }

        return requests.get(requests.size() - 1);
    }

    private VerificationReview findReview(
            CredentialVerificationRequest request
    ) {
        if (request == null
                || network.getComplianceData() == null) {
            return null;
        }

        for (VerificationReview review
                : network.getComplianceData()
                        .getComplianceDirectory()
                        .getReviewList()) {

            if (review.getRequest() == request
                    || review.getRequest()
                            .getVerificationRequestId()
                    == request.getVerificationRequestId()) {

                return review;
            }
        }

        return null;
    }

    private ContractorAssignment
            getSelectedAssignment() {

        int selectedViewRow =
                tblVerification.getSelectedRow();

        if (selectedViewRow < 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Select an assignment from the table.",
                    "Selection Required",
                    JOptionPane.WARNING_MESSAGE
            );

            return null;
        }

        int modelRow =
                tblVerification.convertRowIndexToModel(
                        selectedViewRow
                );

        int assignmentId =
                (Integer) tableModel.getValueAt(
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

    private void submitSelectedAssignment() {

        ContractorAssignment assignment =
                getSelectedAssignment();

        if (assignment == null) {
            return;
        }

        AssignmentStatus status =
                assignment.getStatus();

        if (status != AssignmentStatus.PENDING
                && status
                != AssignmentStatus.COMPLIANCE_REJECTED) {

            showError(
                    "Only a pending or Compliance-rejected "
                    + "assignment can be submitted."
            );

            return;
        }

        if (hasActiveVerificationRequest(assignment)) {

            showError(
                    "This assignment already has an active "
                    + "Compliance verification request."
            );

            return;
        }

        String verificationType =
                (String) cmbVerificationType
                        .getSelectedItem();

        String notes =
                txtNotes.getText().trim();

        if (verificationType == null
                || verificationType.isBlank()) {

            showError(
                    "Select a verification type."
            );

            return;
        }

        if (notes.isEmpty()) {
            showError(
                    "Enter verification instructions or notes."
            );

            return;
        }

        try {
            ComplianceIntegrationService
                    .submitForVerification(
                            network,
                            assignment,
                            verificationType,
                            notes
                    );

            populateTable();
            clearForm();

            JOptionPane.showMessageDialog(
                    this,
                    "Assignment #"
                    + assignment.getAssignmentId()
                    + " sent to Compliance."
            );

        } catch (
                IllegalArgumentException
                | IllegalStateException ex
        ) {
            showError(ex.getMessage());
        }
    }

    private boolean hasActiveVerificationRequest(
            ContractorAssignment assignment
    ) {
        for (CredentialVerificationRequest request
                : assignment.getVerificationRequests()) {

            if (request.getStatus()
                    == RequestStatus.SUBMITTED
                    || request.getStatus()
                    == RequestStatus.APPROVED) {

                return true;
            }
        }

        return false;
    }

    private void clearForm() {

        tblVerification.clearSelection();

        if (cmbVerificationType.getItemCount() > 0) {
            cmbVerificationType.setSelectedIndex(0);
        }

        txtNotes.setText("");
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

            ContractorCoordinatorWorkAreaJPanel dashboard =
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
