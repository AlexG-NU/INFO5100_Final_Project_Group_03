/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
 package UserInterface1.StaffingAgency;

import StaffingAgency.People.Candidate;
import UserInterface.Client.ManageStaffingRequestsJPanel;
import UserInterface1.ManageCandidatesJPanel;
import WorkOrders.StaffingRequest;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
 import StaffingAgency.Request.CandidateSubmission;

public class RecruiterWorkAreaJPanel extends JPanel {

    private final JPanel mainContentPanel;
    private final List<StaffingRequest> masterRequestList;
    private final List<Candidate> candidateList;
    private final List<CandidateSubmission> submissionList;

    private JTable tblMain;
    private DefaultTableModel tableModel;

    private JButton btnViewRequests;
    private JButton btnCandidateSubmissions;
    private JButton btnManageCandidates;
    private JButton btnReports;
    private JButton btnRefresh;

    public RecruiterWorkAreaJPanel(
        JPanel mainContentPanel,
        List<StaffingRequest> masterRequestList,
        List<Candidate> candidateList,
        List<CandidateSubmission> submissionList
) {
    if (mainContentPanel == null) {
        throw new IllegalArgumentException(
                "Main content panel cannot be null."
        );
    }

    if (masterRequestList == null) {
        throw new IllegalArgumentException(
                "Staffing request list cannot be null."
        );
    }

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

    this.mainContentPanel = mainContentPanel;
    this.masterRequestList = masterRequestList;
    this.candidateList = candidateList;
    this.submissionList = submissionList;

    initComponents();
    populateDashboardTable();
}

    private void initComponents() {

        setLayout(new BorderLayout(20, 20));
        setBackground(new Color(255, 255, 204));
        setBorder(BorderFactory.createEmptyBorder(
                25,
                35,
                25,
                35
        ));

        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createCenterPanel(), BorderLayout.CENTER);
        add(createBottomPanel(), BorderLayout.SOUTH);
    }

    private JPanel createHeaderPanel() {

        JPanel headerPanel = new JPanel(
                new BorderLayout(10, 15)
        );

        headerPanel.setOpaque(false);

        JPanel titlePanel = new JPanel(
                new GridLayout(2, 1, 0, 5)
        );

        titlePanel.setOpaque(false);

        JLabel lblTitle = new JLabel(
                "Recruiter Work Area"
        );

        lblTitle.setFont(
                new Font(
                        "Myanmar Sangam MN",
                        Font.ITALIC,
                        24
                )
        );

        JLabel lblSubtitle = new JLabel(
                "Staffing Agency Enterprise - Recruiting Organization"
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

        titlePanel.add(lblTitle);
        titlePanel.add(lblSubtitle);

        headerPanel.add(
                titlePanel,
                BorderLayout.NORTH
        );

        JPanel buttonPanel = new JPanel(
                new GridLayout(2, 2, 40, 20)
        );

        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        80,
                        10,
                        80
                )
        );

        btnViewRequests =
                new JButton("View Staffing Requests");

        btnCandidateSubmissions =
                new JButton("Candidate Submissions");

        btnManageCandidates =
                new JButton("Manage Candidates");

        btnReports =
                new JButton("Recruiter Reports");

        buttonPanel.add(btnViewRequests);
        buttonPanel.add(btnCandidateSubmissions);
        buttonPanel.add(btnManageCandidates);
        buttonPanel.add(btnReports);

        headerPanel.add(
                buttonPanel,
                BorderLayout.CENTER
        );

        addButtonActions();

        return headerPanel;
    }

    private JPanel createCenterPanel() {

        JPanel centerPanel = new JPanel(
                new BorderLayout(10, 10)
        );

        centerPanel.setOpaque(false);

        JLabel lblSummary = new JLabel(
                "Recruiter Dashboard Summary",
                SwingConstants.LEFT
        );

        lblSummary.setFont(
                new Font("Arial", Font.BOLD, 16)
        );

        String[] columns = {
            "Category",
            "Total",
            "Description"
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

        tblMain = new JTable(tableModel);
        tblMain.setRowHeight(28);
        tblMain.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane =
                new JScrollPane(tblMain);

        centerPanel.add(
                lblSummary,
                BorderLayout.NORTH
        );

        centerPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        return centerPanel;
    }

    private JPanel createBottomPanel() {

        JPanel bottomPanel = new JPanel(
                new FlowLayout(FlowLayout.RIGHT)
        );

        bottomPanel.setOpaque(false);

        btnRefresh = new JButton("Refresh");

        btnRefresh.addActionListener(
                event -> populateDashboardTable()
        );

        bottomPanel.add(btnRefresh);

        return bottomPanel;
    }

    private void addButtonActions() {

        btnViewRequests.addActionListener(
                event -> openStaffingRequests()
        );

        btnManageCandidates.addActionListener(
                event -> openManageCandidates()
        );

        btnCandidateSubmissions.addActionListener(
        event -> openCandidateSubmissions()
);

        btnReports.addActionListener(
                event -> JOptionPane.showMessageDialog(
                        this,
                        "Recruiter reports will be added later."
                )
        );
    }

    private void populateDashboardTable() {

        tableModel.setRowCount(0);

        tableModel.addRow(
                new Object[]{
                    "Staffing Requests",
                    masterRequestList.size(),
                    "Requests available for recruiter review"
                }
        );

        tableModel.addRow(
                new Object[]{
                    "Candidates",
                    candidateList.size(),
                    "Candidate records in the staffing system"
                }
        );

        long candidatesWithStatus =
                candidateList.stream()
                        .filter(candidate ->
                                candidate.getCandidateStatus() != null
                        )
                        .count();

        tableModel.addRow(
                new Object[]{
                    "Candidates With Status",
                    candidatesWithStatus,
                    "Candidates assigned to a workflow status"
                }
        );
        tableModel.addRow(
        new Object[]{
            "Candidate Submissions",
            submissionList.size(),
            "Candidates submitted to client companies"
        }
);
    }

    private void openStaffingRequests() {

        ManageStaffingRequestsJPanel staffingPanel =
                new ManageStaffingRequestsJPanel(
                        mainContentPanel,
                        masterRequestList
                );

        displayPanel(staffingPanel);
    }

    private void openManageCandidates() {

        ManageCandidatesJPanel candidatePanel =
                new ManageCandidatesJPanel(
                        candidateList
                );

        displayPanel(candidatePanel);
    }

    private void displayPanel(JPanel panel) {

        mainContentPanel.removeAll();
        mainContentPanel.setLayout(new BorderLayout());

        mainContentPanel.add(
                panel,
                BorderLayout.CENTER
        );

        mainContentPanel.revalidate();
        mainContentPanel.repaint();
    }
    private void openCandidateSubmissions() {

    CandidateSubmissionsJPanel submissionPanel =
            new CandidateSubmissionsJPanel(
                    candidateList,
                    masterRequestList,
                    submissionList
            );

    displayPanel(submissionPanel);
}
}