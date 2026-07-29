/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UserInterface1.StaffingAgency;

import Business.Network;
import ComplianceEnterprise.ComplianceIntegrationService;
/**
 *
 * @author abhit
 */

import StaffingAgency.Enums.CandidateStatus;
import StaffingAgency.Enums.RequestStatus;
import StaffingAgency.People.Candidate;
import StaffingAgency.Request.CandidateSubmission;
import WorkOrders.StaffingRequest;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;
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

public class CandidateSubmissionsJPanel extends JPanel {

    private final List<Candidate> candidateList;
    private final List<StaffingRequest> staffingRequestList;
    private final List<CandidateSubmission> submissionList;
    private final Network network; // @janet - shared enterprise network

    private JTable tblSubmissions;
    private DefaultTableModel tableModel;

    private JComboBox<Candidate> cmbCandidate;
    private JComboBox<StaffingRequest> cmbStaffingRequest;
    private JTextArea txtRecruiterNotes;

    private JButton btnSubmit;
    private JButton btnSendToClient;
    private JButton btnWithdraw;
    private JButton btnSendToCompliance;
    private JButton btnRefresh;
    private JButton btnClear;

    public CandidateSubmissionsJPanel(
            List<Candidate> candidateList,
            List<StaffingRequest> staffingRequestList,
            List<CandidateSubmission> submissionList,
            Network network
    ) {
        if (candidateList == null) {
            throw new IllegalArgumentException(
                    "Candidate list cannot be null."
            );
        }

        if (staffingRequestList == null) {
            throw new IllegalArgumentException(
                    "Staffing-request list cannot be null."
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

        this.candidateList = candidateList;
        this.staffingRequestList = staffingRequestList;
        this.submissionList = submissionList;
        this.network = network;

        initComponents();
        loadComboBoxes();
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
        add(createSubmissionForm(), BorderLayout.SOUTH);
    }

    private JPanel createHeaderPanel() {

        JPanel headerPanel =
                new JPanel(new GridLayout(2, 1, 0, 5));

        headerPanel.setOpaque(false);

        JLabel lblTitle =
                new JLabel("Candidate Submissions");

        lblTitle.setFont(
                new Font(
                        "Myanmar Sangam MN",
                        Font.ITALIC,
                        24
                )
        );

        JLabel lblSubtitle = new JLabel(
                "Submit qualified candidates for open staffing requests"
        );

        lblSubtitle.setFont(
                new Font(
                        "Myanmar MN",
                        Font.PLAIN,
                        14
                )
        );

        lblSubtitle.setForeground(
                new Color(102, 102, 102)
        );

        headerPanel.add(lblTitle);
        headerPanel.add(lblSubtitle);

        return headerPanel;
    }

    private JPanel createTablePanel() {

        JPanel tablePanel =
                new JPanel(new BorderLayout(10, 10));

        tablePanel.setOpaque(false);

        String[] columns = {
            "Submission ID",
            "Date",
            "Candidate",
            "Request ID",
            "Job Title",
            "Status",
            "Recruiter Notes",
            "Client Feedback"
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

        tblSubmissions = new JTable(tableModel);
        tblSubmissions.setRowHeight(26);

        tblSubmissions.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        tblSubmissions
                .getTableHeader()
                .setReorderingAllowed(false);

        JScrollPane scrollPane =
                new JScrollPane(tblSubmissions);

        JLabel lblTableTitle =
                new JLabel("Submission History");

        lblTableTitle.setFont(
                new Font("Arial", Font.BOLD, 16)
        );

        tablePanel.add(
                lblTableTitle,
                BorderLayout.NORTH
        );

        tablePanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        return tablePanel;
    }

    private JPanel createSubmissionForm() {

        JPanel outerPanel =
                new JPanel(new BorderLayout(10, 10));

        outerPanel.setOpaque(false);

        outerPanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Create Candidate Submission"
                )
        );

        JPanel formPanel =
                new JPanel(new GridLayout(3, 2, 10, 10));

        formPanel.setOpaque(false);

        cmbCandidate = new JComboBox<>();
        cmbStaffingRequest = new JComboBox<>();

        txtRecruiterNotes = new JTextArea(3, 30);
        txtRecruiterNotes.setLineWrap(true);
        txtRecruiterNotes.setWrapStyleWord(true);

        formPanel.add(new JLabel("Candidate:"));
        formPanel.add(cmbCandidate);

        formPanel.add(new JLabel("Staffing Request:"));
        formPanel.add(cmbStaffingRequest);

        formPanel.add(new JLabel("Recruiter Notes:"));
        formPanel.add(
                new JScrollPane(txtRecruiterNotes)
        );

        JPanel buttonPanel =
                new JPanel(new FlowLayout(FlowLayout.RIGHT));

        buttonPanel.setOpaque(false);

        btnSubmit = new JButton("Create Submission");
        btnSendToClient = new JButton("Send to Client");
        btnWithdraw = new JButton("Withdraw");
        btnSendToCompliance = new JButton("Send to Compliance");
        btnRefresh = new JButton("Refresh");
        btnClear = new JButton("Clear");

        buttonPanel.add(btnSubmit);
        buttonPanel.add(btnSendToClient);
        buttonPanel.add(btnWithdraw);
        buttonPanel.add(btnSendToCompliance);
        buttonPanel.add(btnRefresh);
        buttonPanel.add(btnClear);

        btnSubmit.addActionListener(
                event -> createSubmission()
        );

        btnSendToClient.addActionListener(
                event -> sendSelectedToClient()
        );

        btnWithdraw.addActionListener(
                event -> withdrawSelectedSubmission()
        );

        // @janet - Staffing sends its existing assignment to Compliance.
        btnSendToCompliance.addActionListener(
                event -> sendSelectedToCompliance()
        );

        btnRefresh.addActionListener(
                event -> {
                    loadComboBoxes();
                    populateTable();
                }
        );

        btnClear.addActionListener(
                event -> clearForm()
        );

        outerPanel.add(formPanel, BorderLayout.CENTER);
        outerPanel.add(buttonPanel, BorderLayout.SOUTH);

        return outerPanel;
    }

    private void loadComboBoxes() {

        Candidate selectedCandidate =
                (Candidate) cmbCandidate.getSelectedItem();

        StaffingRequest selectedRequest =
                (StaffingRequest)
                        cmbStaffingRequest.getSelectedItem();

        cmbCandidate.removeAllItems();

        for (Candidate candidate : candidateList) {
            if (candidate.getCandidateStatus()
                    != CandidateStatus.REJECTED
                    && candidate.getCandidateStatus()
                    != CandidateStatus.WITHDRAWN
                    && candidate.getCandidateStatus()
                    != CandidateStatus.PLACED) {

                cmbCandidate.addItem(candidate);
            }
        }

        cmbStaffingRequest.removeAllItems();

        for (StaffingRequest request : staffingRequestList) {
            if (request.getStatus()
                    != WorkOrders.RequestStatus.COMPLETED
                    && request.getStatus()
                    != WorkOrders.RequestStatus.REJECTED) {

                cmbStaffingRequest.addItem(request);
            }
        }

        if (selectedCandidate != null) {
            cmbCandidate.setSelectedItem(selectedCandidate);
        }

        if (selectedRequest != null) {
            cmbStaffingRequest.setSelectedItem(selectedRequest);
        }
    }

    private void populateTable() {

        tableModel.setRowCount(0);

        for (CandidateSubmission submission : submissionList) {

            Object[] row = {
                submission.getSubmissionId(),
                submission.getSubmissionDate(),
                submission.getCandidate().getFullName(),
                submission.getStaffingRequest().getRequestId(),
                submission.getStaffingRequest().getJobTitle(),
                submission.getStatus(),
                submission.getRecruiterNotes(),
                submission.getClientFeedback() == null
                        ? ""
                        : submission.getClientFeedback()
            };

            tableModel.addRow(row);
        }
    }

    private void createSubmission() {

        Candidate candidate =
                (Candidate) cmbCandidate.getSelectedItem();

        StaffingRequest request =
                (StaffingRequest)
                        cmbStaffingRequest.getSelectedItem();

        String notes =
                txtRecruiterNotes.getText().trim();

        if (candidate == null) {
            showError(
                    "Create a candidate before making a submission."
            );
            return;
        }

        if (request == null) {
            showError(
                    "No eligible staffing request is available."
            );
            return;
        }

        if (notes.isEmpty()) {
            showError(
                    "Enter recruiter notes before submitting."
            );
            return;
        }

        if (submissionAlreadyExists(candidate, request)) {
            showError(
                    "This candidate has already been submitted "
                    + "for the selected staffing request."
            );
            return;
        }

        try {
            CandidateSubmission submission =
                    new CandidateSubmission(
                            candidate,
                            request,
                            notes
                    );

            submissionList.add(submission);
            request.addSubmission(submission);

            candidate.setCandidateStatus(
                    CandidateStatus.SUBMITTED
            );

            populateTable();
            clearForm();

            JOptionPane.showMessageDialog(
                    this,
                    "Candidate submission created successfully."
            );

        } catch (IllegalArgumentException ex) {
            showError(ex.getMessage());
        }
    }

    private boolean submissionAlreadyExists(
            Candidate candidate,
            StaffingRequest request
    ) {
        for (CandidateSubmission submission : submissionList) {

            boolean sameCandidate =
                    submission.getCandidate()
                            .getCandidateId()
                    == candidate.getCandidateId();

            boolean sameRequest =
                    submission.getStaffingRequest()
                            .getRequestId()
                    == request.getRequestId();

            boolean stillActive =
                    submission.getStatus()
                    != RequestStatus.REJECTED;

            if (sameCandidate
                    && sameRequest
                    && stillActive) {
                return true;
            }
        }

        return false;
    }

    private void sendSelectedToClient() {

        CandidateSubmission submission =
                getSelectedSubmission();

        if (submission == null) {
            return;
        }

        if (submission.getStatus()
                != RequestStatus.SUBMITTED) {

            showError(
                    "Only newly created submissions can be sent."
            );
            return;
        }

        submission.submitToClient();

        populateTable();

        JOptionPane.showMessageDialog(
                this,
                "Submission sent to the client for review."
        );
    }

    private void withdrawSelectedSubmission() {

        CandidateSubmission submission =
                getSelectedSubmission();

        if (submission == null) {
            return;
        }

        if (submission.getStatus()
                == RequestStatus.APPROVED
                || submission.getStatus()
                == RequestStatus.COMPLETED) {

            showError(
                    "An approved or completed submission "
                    + "cannot be withdrawn."
            );
            return;
        }

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Withdraw the selected candidate submission?",
                "Confirm Withdrawal",
                JOptionPane.YES_NO_OPTION
        );

        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        submission.withdrawSubmission();

        submission.getCandidate().setCandidateStatus(
                CandidateStatus.WITHDRAWN
        );

        populateTable();
        loadComboBoxes();

        JOptionPane.showMessageDialog(
                this,
                "Submission withdrawn."
        );
    }

    // @janet - This is the cross-enterprise handoff. No duplicate
    // contractor or assignment record is created here.
    private void sendSelectedToCompliance() {
        CandidateSubmission submission = getSelectedSubmission();
        if (submission == null) {
            return;
        }
        if (submission.getStatus() != RequestStatus.APPROVED
                || submission.getResultingAssignment() == null) {
            showError(
                    "The client must approve the submission and create "
                    + "the assignment before it can be sent to Compliance."
            );
            return;
        }
        if (!submission.getResultingAssignment()
                .getVerificationRequests().isEmpty()) {
            showError("This assignment has already been sent to Compliance.");
            return;
        }
        try {
            ComplianceIntegrationService.submitForVerification(
                    network,
                    submission.getResultingAssignment(),
                    "Background and Credential Verification",
                    "Verify contractor before the assignment start date."
            );
            populateTable();
            JOptionPane.showMessageDialog(
                    this,
                    "Assignment sent to the Compliance queue."
            );
        } catch (IllegalArgumentException | IllegalStateException ex) {
            showError(ex.getMessage());
        }
    }

    private CandidateSubmission getSelectedSubmission() {

        int selectedRow =
                tblSubmissions.getSelectedRow();

        if (selectedRow < 0) {
            showError(
                    "Select a submission from the table."
            );
            return null;
        }

        return submissionList.get(selectedRow);
    }

    private void clearForm() {

        if (cmbCandidate.getItemCount() > 0) {
            cmbCandidate.setSelectedIndex(0);
        }

        if (cmbStaffingRequest.getItemCount() > 0) {
            cmbStaffingRequest.setSelectedIndex(0);
        }

        txtRecruiterNotes.setText("");
        tblSubmissions.clearSelection();
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
