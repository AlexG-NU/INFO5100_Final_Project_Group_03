package UserInterface.Client;

import Business.Network;
import Core.NetworkUtils;
import Core.Organization;
import Core.UserAccount;
import Core.WorkOrder;
import Core.WorkOrderStatus;
import StaffingAgency.Enums.CandidateStatus;
import StaffingAgency.People.Candidate;
import StaffingAgency.People.Contractor;
import StaffingAgency.Request.CandidateSubmission;
import StaffingAgency.Request.ContractorAssignment;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.math.BigDecimal;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 * Client HR review queue for candidate submissions sent by Staffing.
 *
 * @author janet
 */
public class HiringManagerCandidateSubmissionsJPanel
        extends JPanel {

    private final JPanel container;
    private final UserAccount account;
    private final Network network;

    private JTable tblSubmissions;
    private DefaultTableModel tableModel;
    private JTextArea txtFeedback;

    public HiringManagerCandidateSubmissionsJPanel(
            JPanel container,
            UserAccount account,
            Network network
    ) {
        if (container == null
                || account == null
                || network == null) {
            throw new IllegalArgumentException(
                    "Container, account, and network are required."
            );
        }

        this.container = container;
        this.account = account;
        this.network = network;

        initComponents();
        refreshTable();
    }

    private void initComponents() {

        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(255, 255, 204));
        setBorder(
                BorderFactory.createEmptyBorder(
                        25, 35, 25, 35
                )
        );

        JPanel headerPanel =
                new JPanel(new GridLayout(2, 1, 0, 5));
        headerPanel.setOpaque(false);

        JLabel lblTitle =
                new JLabel("Candidate Submission Review");
        lblTitle.setFont(
                new Font(
                        "Myanmar Sangam MN",
                        Font.ITALIC,
                        24
                )
        );

        JLabel lblSubtitle = new JLabel(
                "Client Enterprise - Human Resources Organization"
        );
        lblSubtitle.setForeground(
                new Color(102, 102, 102)
        );

        headerPanel.add(lblTitle);
        headerPanel.add(lblSubtitle);

        String[] columns = {
            "Submission ID",
            "Candidate",
            "Job Title",
            "Staffing Work Order",
            "Status",
            "Recruiter",
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

        JPanel actionPanel =
                new JPanel(new BorderLayout(10, 10));
        actionPanel.setOpaque(false);

        txtFeedback = new JTextArea(3, 35);
        txtFeedback.setLineWrap(true);
        txtFeedback.setWrapStyleWord(true);

        JPanel feedbackPanel =
                new JPanel(new BorderLayout(10, 10));
        feedbackPanel.setOpaque(false);
        feedbackPanel.add(
                new JLabel("Client Feedback:"),
                BorderLayout.WEST
        );
        feedbackPanel.add(
                new JScrollPane(txtFeedback),
                BorderLayout.CENTER
        );

        JPanel buttonPanel =
                new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);

        JButton btnBack = new JButton("<< Back");
        JButton btnApprove = new JButton("Approve Candidate");
        JButton btnReject = new JButton("Reject Candidate");
        JButton btnRefresh = new JButton("Refresh");

        buttonPanel.add(btnBack);
        buttonPanel.add(btnApprove);
        buttonPanel.add(btnReject);
        buttonPanel.add(btnRefresh);

        btnBack.addActionListener(event -> goBack());
        btnApprove.addActionListener(
                event -> approveSelectedSubmission()
        );
        btnReject.addActionListener(
                event -> rejectSelectedSubmission()
        );
        btnRefresh.addActionListener(
                event -> refreshTable()
        );

        actionPanel.add(
                feedbackPanel,
                BorderLayout.CENTER
        );
        actionPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        add(headerPanel, BorderLayout.NORTH);
        add(
                new JScrollPane(tblSubmissions),
                BorderLayout.CENTER
        );
        add(actionPanel, BorderLayout.SOUTH);
    }

    private Organization getHrOrganization() {

        return NetworkUtils.findOrganizationByName(
                network,
                "Client Enterprise",
                "Human Resources Organization"
        );
    }

    private void refreshTable() {

        tableModel.setRowCount(0);

        Organization hrOrganization =
                getHrOrganization();

        if (hrOrganization == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Human Resources Organization was not found.",
                    "Routing Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        for (WorkOrder workOrder
                : hrOrganization
                        .getWorkQueue()
                        .getWorkOrderList()) {

            if (!(workOrder
                    instanceof CandidateSubmission)) {
                continue;
            }

            CandidateSubmission submission =
                    (CandidateSubmission) workOrder;

            tableModel.addRow(
                    new Object[]{
                        submission,
                        submission.getCandidate().getFullName(),
                        submission.getStaffingRequest()
                                .getJobTitle(),
                        submission.getStaffingRequest()
                                .getWorkOrderId(),
                        submission.getStatus(),
                        submission.getSender() == null
                                ? ""
                                : submission.getSender()
                                        .getUsername(),
                        submission.getRecruiterNotes(),
                        submission.getClientFeedback() == null
                                ? ""
                                : submission.getClientFeedback()
                    }
            );
        }
    }

    private CandidateSubmission
            getSelectedSubmission() {

        int selectedRow =
                tblSubmissions.getSelectedRow();

        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Select a candidate submission first.",
                    "Selection Required",
                    JOptionPane.WARNING_MESSAGE
            );
            return null;
        }

        int modelRow =
                tblSubmissions.convertRowIndexToModel(
                        selectedRow
                );

        return (CandidateSubmission)
                tableModel.getValueAt(modelRow, 0);
    }

    private void approveSelectedSubmission() {

        CandidateSubmission submission =
                getSelectedSubmission();

        if (submission == null) {
            return;
        }

        if (submission.getStatus()
                != WorkOrderStatus.UNDER_REVIEW) {
            JOptionPane.showMessageDialog(
                    this,
                    "Only a submission under review can be approved.",
                    "Approval Not Allowed",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String feedback =
                txtFeedback.getText().trim();

        if (feedback.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Enter client feedback before approving.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        Candidate candidate =
                submission.getCandidate();

        Contractor contractor = new Contractor(
                candidate.getFirstName(),
                candidate.getLastName(),
                candidate.getEmail(),
                candidate.getPhone(),
                candidate.getSkills(),
                new BigDecimal("55.00")
        );

        ContractorAssignment assignment =
                new ContractorAssignment(
                        contractor,
                        submission.getStaffingRequest()
                                .getStartDate()
                );

        submission.addClientFeedback(feedback);
        submission.setReceiver(account);
        submission.linkAssignment(assignment);
        candidate.setCandidateStatus(
                CandidateStatus.PLACED
        );

        refreshTable();
        txtFeedback.setText("");

        JOptionPane.showMessageDialog(
                this,
                "Candidate approved. Staffing can now "
                + "send the assignment to Compliance."
        );
    }

    private void rejectSelectedSubmission() {

        CandidateSubmission submission =
                getSelectedSubmission();

        if (submission == null) {
            return;
        }

        if (submission.getStatus()
                != WorkOrderStatus.UNDER_REVIEW) {
            JOptionPane.showMessageDialog(
                    this,
                    "Only a submission under review can be rejected.",
                    "Rejection Not Allowed",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String feedback =
                txtFeedback.getText().trim();

        if (feedback.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Enter client feedback before rejecting.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        submission.addClientFeedback(feedback);
        submission.setReceiver(account);
        submission.updateStatus(
                WorkOrderStatus.REJECTED
        );
        submission.getCandidate()
                .setCandidateStatus(
                        CandidateStatus.REJECTED
                );

        refreshTable();
        txtFeedback.setText("");

        JOptionPane.showMessageDialog(
                this,
                "Candidate submission rejected."
        );
    }

    private void goBack() {

        container.remove(this);

        if (container.getLayout()
                instanceof java.awt.CardLayout) {
            ((java.awt.CardLayout)
                    container.getLayout())
                    .previous(container);
        }

        container.revalidate();
        container.repaint();
    }
}
