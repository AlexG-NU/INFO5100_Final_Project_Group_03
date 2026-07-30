/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UserInterface1.StaffingAgency;

import Business.Network;
import ComplianceEnterprise.ComplianceIntegrationService;
import Core.NetworkUtils;
import Core.Organization;
import Core.UserAccount;
import Core.WorkOrder;
import Core.WorkOrderQueue;
import Core.WorkOrderStatus;
import Core.WorkOrders.StaffingReqWorkOrder;
import StaffingAgency.Enums.CandidateStatus;
import StaffingAgency.People.Candidate;
import StaffingAgency.Request.CandidateSubmission;
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
    private final List<CandidateSubmission> submissionList;
    private final UserAccount recruiterAccount;
    private final Network network;

    private JTable tblSubmissions;
    private DefaultTableModel tableModel;

    private JComboBox<Candidate> cmbCandidate;
    private JComboBox<StaffingReqWorkOrder> cmbStaffingRequest;
    private JTextArea txtRecruiterNotes;

    private JButton btnSubmit;
    private JButton btnSendToClient;
    private JButton btnWithdraw;
    private JButton btnSendToCompliance;
    private JButton btnRefresh;
    private JButton btnClear;

    public CandidateSubmissionsJPanel(
            List<Candidate> candidateList,
            List<CandidateSubmission> submissionList,
            UserAccount recruiterAccount,
            Network network
    ) {
        if (candidateList == null) {
            throw new IllegalArgumentException(
                    "Candidate list cannot be null."
            );
        }

        if (submissionList == null) {
            throw new IllegalArgumentException(
                    "Submission list cannot be null."
            );
        }

        if (recruiterAccount == null) {
            throw new IllegalArgumentException(
                    "Recruiter account cannot be null."
            );
        }

        if (network == null) {
            throw new IllegalArgumentException(
                    "Network cannot be null."
            );
        }

        this.candidateList = candidateList;
        this.submissionList = submissionList;
        this.recruiterAccount = recruiterAccount;
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
                "Route qualified candidates to the "
                + "Client Human Resources queue"
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
            "Staffing Work Order",
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

        tblSubmissions.getTableHeader()
                .setReorderingAllowed(false);

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
                new JScrollPane(tblSubmissions),
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

        formPanel.add(
                new JLabel("Staffing Work Order:")
        );
        formPanel.add(cmbStaffingRequest);

        formPanel.add(new JLabel("Recruiter Notes:"));
        formPanel.add(
                new JScrollPane(txtRecruiterNotes)
        );

        JPanel buttonPanel =
                new JPanel(new FlowLayout(FlowLayout.RIGHT));

        buttonPanel.setOpaque(false);

        btnSubmit =
                new JButton("Create Submission");

        btnSendToClient =
                new JButton("Send to Client HR");

        btnWithdraw =
                new JButton("Withdraw");

        btnSendToCompliance =
                new JButton("Send to Compliance");

        btnRefresh =
                new JButton("Refresh");

        btnClear =
                new JButton("Clear");

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

    private Organization getRecruitingOrganization() {

        return NetworkUtils.findOrganizationByName(
                network,
                "Staffing Agency Enterprise",
                "Recruiting Organization"
        );
    }

    private Organization getClientHrOrganization() {

        return NetworkUtils.findOrganizationByName(
                network,
                "Client Enterprise",
                "Human Resources Organization"
        );
    }

    private void loadComboBoxes() {

        Candidate selectedCandidate =
                (Candidate) cmbCandidate.getSelectedItem();

        StaffingReqWorkOrder selectedRequest =
                (StaffingReqWorkOrder)
                        cmbStaffingRequest.getSelectedItem();

        cmbCandidate.removeAllItems();

        for (Candidate candidate : candidateList) {

            CandidateStatus status =
                    candidate.getCandidateStatus();

            if (status != CandidateStatus.REJECTED
                    && status != CandidateStatus.WITHDRAWN
                    && status != CandidateStatus.PLACED) {

                cmbCandidate.addItem(candidate);
            }
        }

        cmbStaffingRequest.removeAllItems();

        Organization recruitingOrganization =
                getRecruitingOrganization();

        if (recruitingOrganization != null) {

            for (WorkOrder workOrder
                    : recruitingOrganization
                            .getWorkQueue()
                            .getWorkOrderList()) {

                if (!(workOrder
                        instanceof StaffingReqWorkOrder)) {
                    continue;
                }

                StaffingReqWorkOrder request =
                        (StaffingReqWorkOrder) workOrder;

                if (request.getStatus() == null
                        || request.getStatus().isDone()) {
                    continue;
                }

                UserAccount receiver =
                        request.getReceiver();

                /*
                 * Only show work orders claimed by the
                 * currently logged-in recruiter.
                 */
                if (receiver != null
                        && receiver.getUsername()
                                .equalsIgnoreCase(
                                        recruiterAccount
                                                .getUsername()
                                )) {

                    cmbStaffingRequest.addItem(request);
                }
            }
        }

        if (selectedCandidate != null) {
            cmbCandidate.setSelectedItem(
                    selectedCandidate
            );
        }

        if (selectedRequest != null) {
            cmbStaffingRequest.setSelectedItem(
                    selectedRequest
            );
        }
    }

    private void populateTable() {

        tableModel.setRowCount(0);

        for (CandidateSubmission submission
                : submissionList) {

            StaffingReqWorkOrder request =
                    submission.getStaffingRequest();

            tableModel.addRow(
                    new Object[]{
                        submission.getSubmissionId(),
                        submission.getSubmissionDate(),
                        submission.getCandidate()
                                .getFullName(),
                        request.getWorkOrderId(),
                        request.getJobTitle(),
                        submission.getStatus(),
                        submission.getRecruiterNotes(),
                        submission.getClientFeedback()
                                == null
                                ? ""
                                : submission
                                        .getClientFeedback()
                    }
            );
        }
    }

    private void createSubmission() {

        Candidate candidate =
                (Candidate)
                        cmbCandidate.getSelectedItem();

        StaffingReqWorkOrder request =
                (StaffingReqWorkOrder)
                        cmbStaffingRequest.getSelectedItem();

        String notes =
                txtRecruiterNotes.getText().trim();

        if (candidate == null) {
            showError(
                    "Create or select a candidate first."
            );
            return;
        }

        if (request == null) {
            showError(
                    "Claim a staffing work order before "
                    + "creating a submission."
            );
            return;
        }

        if (notes.isEmpty()) {
            showError(
                    "Enter recruiter notes before "
                    + "creating the submission."
            );
            return;
        }

        if (submissionAlreadyExists(
                candidate,
                request
        )) {
            showError(
                    "This candidate already has an active "
                    + "submission for the selected staffing "
                    + "work order."
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

            submission.setSender(
                    recruiterAccount
            );

            submissionList.add(submission);

            /*
             * Save the pending submission in the
             * recruiter's personal queue.
             */
            addToQueueIfMissing(
                    recruiterAccount.getWorkQueue(),
                    submission
            );

            candidate.setCandidateStatus(
                    CandidateStatus.SUBMITTED
            );

            populateTable();
            clearForm();

            JOptionPane.showMessageDialog(
                    this,
                    "Candidate submission created. "
                    + "Select it and send it to Client HR."
            );

        } catch (IllegalArgumentException ex) {
            showError(ex.getMessage());
        }
    }

    private boolean submissionAlreadyExists(
            Candidate candidate,
            StaffingReqWorkOrder request
    ) {
        for (CandidateSubmission submission
                : submissionList) {

            boolean sameCandidate =
                    submission.getCandidate()
                            .getCandidateId()
                    == candidate.getCandidateId();

            boolean sameRequest =
                    submission.getStaffingRequest()
                            .getWorkOrderId()
                    == request.getWorkOrderId();

            boolean active =
                    submission.getStatus()
                    != WorkOrderStatus.REJECTED
                    && submission.getStatus()
                    != WorkOrderStatus.CANCELLED;

            if (sameCandidate
                    && sameRequest
                    && active) {
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
                != WorkOrderStatus.PENDING) {

            showError(
                    "Only a pending submission can "
                    + "be sent to Client HR."
            );
            return;
        }

        Organization hrOrganization =
                getClientHrOrganization();

        if (hrOrganization == null) {
            showError(
                    "Client Enterprise - Human Resources "
                    + "Organization was not found."
            );
            return;
        }

        try {
            /*
             * Update the same WorkOrder object.
             */
            submission.submitToClient();

            /*
             * Route the same object to Client HR.
             */
            addToQueueIfMissing(
                    hrOrganization.getWorkQueue(),
                    submission
            );

            /*
             * Add it to the network queue for reporting.
             */
            addToQueueIfMissing(
                    network.getWorkOrderQueue(),
                    submission
            );

            addToQueueIfMissing(
                    recruiterAccount.getWorkQueue(),
                    submission
            );

            populateTable();

            JOptionPane.showMessageDialog(
                    this,
                    "Candidate Submission #"
                    + submission.getWorkOrderId()
                    + " sent to the Client HR queue."
            );

        } catch (IllegalStateException ex) {
            showError(ex.getMessage());
        }
    }

    private void withdrawSelectedSubmission() {

        CandidateSubmission submission =
                getSelectedSubmission();

        if (submission == null) {
            return;
        }

        if (submission.getStatus()
                == WorkOrderStatus.APPROVED
                || submission.getStatus()
                == WorkOrderStatus.COMPLETED) {

            showError(
                    "An approved or completed submission "
                    + "cannot be withdrawn."
            );
            return;
        }

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Withdraw the selected candidate submission?",
                        "Confirm Withdrawal",
                        JOptionPane.YES_NO_OPTION
                );

        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            /*
             * Because Client HR has the same object,
             * they will also see the CANCELLED status.
             */
            submission.withdrawSubmission();

            submission.getCandidate()
                    .setCandidateStatus(
                            CandidateStatus.WITHDRAWN
                    );

            populateTable();
            loadComboBoxes();

            JOptionPane.showMessageDialog(
                    this,
                    "Submission withdrawn."
            );

        } catch (IllegalStateException ex) {
            showError(ex.getMessage());
        }
    }

    private void sendSelectedToCompliance() {

        CandidateSubmission submission =
                getSelectedSubmission();

        if (submission == null) {
            return;
        }

        if (submission.getStatus()
                != WorkOrderStatus.APPROVED
                || submission
                        .getResultingAssignment()
                == null) {

            showError(
                    "Client HR must approve the submission "
                    + "and create the assignment before it "
                    + "can be sent to Compliance."
            );
            return;
        }

        if (!submission
                .getResultingAssignment()
                .getVerificationRequests()
                .isEmpty()) {

            showError(
                    "This assignment has already been "
                    + "sent to Compliance."
            );
            return;
        }

        try {
            ComplianceIntegrationService
                    .submitForVerification(
                            network,
                            submission
                                    .getResultingAssignment(),
                            "Background and Credential Verification",
                            "Verify contractor before the "
                            + "assignment start date."
                    );

            populateTable();

            JOptionPane.showMessageDialog(
                    this,
                    "Assignment sent to the Compliance queue."
            );

        } catch (
                IllegalArgumentException
                | IllegalStateException ex
        ) {
            showError(ex.getMessage());
        }
    }

    private CandidateSubmission
            getSelectedSubmission() {

        int selectedRow =
                tblSubmissions.getSelectedRow();

        if (selectedRow < 0) {
            showError(
                    "Select a submission from the table."
            );
            return null;
        }

        int modelRow =
                tblSubmissions
                        .convertRowIndexToModel(
                                selectedRow
                        );

        int submissionId =
                (Integer)
                        tableModel.getValueAt(
                                modelRow,
                                0
                        );

        for (CandidateSubmission submission
                : submissionList) {

            if (submission.getSubmissionId()
                    == submissionId) {

                return submission;
            }
        }

        showError(
                "The selected submission could not be found."
        );

        return null;
    }

    private void addToQueueIfMissing(
            WorkOrderQueue queue,
            WorkOrder workOrder
    ) {
        if (!queue.getWorkOrderList()
                .contains(workOrder)) {

            queue.addWorkOrder(workOrder);
        }
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