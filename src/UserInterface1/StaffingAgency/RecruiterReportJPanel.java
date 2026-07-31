package UserInterface1.StaffingAgency;

import Business.Network;
import Core.NetworkUtils;
import Core.Organization;
import Core.WorkOrder;
import Core.WorkOrderStatus;
import Core.WorkOrders.StaffingReqWorkOrder;
import StaffingAgency.Enums.CandidateStatus;
import StaffingAgency.People.Candidate;
import StaffingAgency.Request.CandidateSubmission;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 * Displays current Staffing request, candidate, and submission metrics.
 *
 * @author janet
 */
public class RecruiterReportJPanel extends JPanel {

    private final JPanel container;
    private final JPanel previousPanel;
    private final Network network;
    private final List<Candidate> candidates;
    private final List<CandidateSubmission> submissions;

    private JTable tblSummary;
    private DefaultTableModel tableModel;
    private JTextField txtOpenRequests;
    private JTextField txtActiveCandidates;
    private JTextField txtSubmissions;
    private JTextField txtPlacementRate;

    public RecruiterReportJPanel(
            JPanel container,
            JPanel previousPanel,
            Network network,
            List<Candidate> candidates,
            List<CandidateSubmission> submissions
    ) {
        this.container = container;
        this.previousPanel = previousPanel;
        this.network = network;
        this.candidates = candidates;
        this.submissions = submissions;

        initComponents();
        refreshReport();
    }

    private void initComponents() {

        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JLabel lblTitle = new JLabel("Recruiter Performance Summary");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        add(lblTitle, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Performance Measure", "Value"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblSummary = new JTable(tableModel);
        tblSummary.setRowHeight(26);
        tblSummary.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(tblSummary);
        scrollPane.setBorder(
                BorderFactory.createTitledBorder("Recruiting Metrics")
        );

        JPanel detailsPanel = new JPanel(new GridLayout(4, 2, 12, 8));
        detailsPanel.setBorder(
                BorderFactory.createTitledBorder("Report Details")
        );

        txtOpenRequests = createReadOnlyField();
        txtActiveCandidates = createReadOnlyField();
        txtSubmissions = createReadOnlyField();
        txtPlacementRate = createReadOnlyField();

        detailsPanel.add(new JLabel("Open Staffing Requests:"));
        detailsPanel.add(txtOpenRequests);
        detailsPanel.add(new JLabel("Active Candidates:"));
        detailsPanel.add(txtActiveCandidates);
        detailsPanel.add(new JLabel("Candidate Submissions:"));
        detailsPanel.add(txtSubmissions);
        detailsPanel.add(new JLabel("Candidate Placement Rate:"));
        detailsPanel.add(txtPlacementRate);

        JPanel centerPanel = new JPanel(new BorderLayout(15, 15));
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        centerPanel.add(detailsPanel, BorderLayout.SOUTH);
        add(centerPanel, BorderLayout.CENTER);

        JButton btnBack = new JButton("<< Back");
        JButton btnRefresh = new JButton("Refresh Report");

        btnBack.addActionListener(event -> returnToRecruiterWorkArea());
        btnRefresh.addActionListener(event -> {
            refreshReport();
            tblSummary.clearSelection();
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(btnBack);
        buttonPanel.add(btnRefresh);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JTextField createReadOnlyField() {
        JTextField field = new JTextField();
        field.setEditable(false);
        return field;
    }

    private void refreshReport() {

        Organization recruitingOrganization =
                NetworkUtils.findOrganizationByName(
                        network,
                        "Staffing Agency Enterprise",
                        "Recruiting Organization"
                );

        long totalRequests = 0;
        long openRequests = 0;

        if (recruitingOrganization != null) {
            for (WorkOrder workOrder
                    : recruitingOrganization
                            .getWorkQueue()
                            .getWorkOrderList()) {
                if (workOrder
                        instanceof StaffingReqWorkOrder) {
                    totalRequests++;

                    if (workOrder.getStatus() == null
                            || !workOrder.getStatus()
                                    .isDone()) {
                        openRequests++;
                    }
                }
            }
        }

        long screeningCandidates = candidates.stream()
                .filter(candidate -> candidate.getCandidateStatus()
                == CandidateStatus.SCREENING)
                .count();

        long submittedCandidates = candidates.stream()
                .filter(candidate -> candidate.getCandidateStatus()
                == CandidateStatus.SUBMITTED)
                .count();

        long placedCandidates = candidates.stream()
                .filter(candidate -> candidate.getCandidateStatus()
                == CandidateStatus.PLACED)
                .count();

        long activeCandidates = candidates.stream()
                .filter(candidate -> candidate.getCandidateStatus()
                != CandidateStatus.REJECTED
                && candidate.getCandidateStatus()
                != CandidateStatus.WITHDRAWN)
                .count();

        long approvedSubmissions = submissions.stream()
                .filter(submission -> submission.getStatus()
                == WorkOrderStatus.APPROVED)
                .count();

        long pendingSubmissions = submissions.stream()
                .filter(submission -> !submission.getStatus().isDone()
                && submission.getStatus() != WorkOrderStatus.APPROVED)
                .count();

        double placementRate = candidates.isEmpty()
                ? 0.0 : placedCandidates * 100.0 / candidates.size();

        tableModel.setRowCount(0);
        tableModel.addRow(new Object[]{
            "Total Staffing Requests", totalRequests
        });
        tableModel.addRow(new Object[]{
            "Open Staffing Requests", openRequests
        });
        tableModel.addRow(new Object[]{
            "Total Candidates", candidates.size()
        });
        tableModel.addRow(new Object[]{
            "Candidates in Screening", screeningCandidates
        });
        tableModel.addRow(new Object[]{
            "Candidates Submitted", submittedCandidates
        });
        tableModel.addRow(new Object[]{
            "Candidates Placed", placedCandidates
        });
        tableModel.addRow(new Object[]{
            "Total Candidate Submissions", submissions.size()
        });
        tableModel.addRow(new Object[]{
            "Pending Candidate Submissions", pendingSubmissions
        });
        tableModel.addRow(new Object[]{
            "Approved Candidate Submissions", approvedSubmissions
        });
        tableModel.addRow(new Object[]{
            "Candidate Placement Rate",
            String.format("%.1f%%", placementRate)
        });

        txtOpenRequests.setText(String.valueOf(openRequests));
        txtActiveCandidates.setText(String.valueOf(activeCandidates));
        txtSubmissions.setText(String.valueOf(submissions.size()));
        txtPlacementRate.setText(String.format("%.1f%%", placementRate));
    }

    private void returnToRecruiterWorkArea() {

        container.removeAll();
        container.setLayout(new BorderLayout());
        container.add(previousPanel, BorderLayout.CENTER);
        container.revalidate();
        container.repaint();
    }
}
