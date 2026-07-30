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
import Core.UserAccount;
import StaffingAgency.Enums.AssignmentStatus;
import StaffingAgency.Enums.AvailabilityStatus;
import StaffingAgency.People.Contractor;
import StaffingAgency.Request.CandidateSubmission;
import StaffingAgency.Request.ContractExtensionRequest;
import StaffingAgency.Request.ContractorAssignment;
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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class ContractorCoordinatorWorkAreaJPanel
        extends JPanel {

    private final JPanel mainContentPanel;
    private final List<CandidateSubmission> submissionList;
    private final UserAccount coordinatorAccount;
    private final Network network;

    private JTable tblMain;
    private DefaultTableModel tableModel;

    private JButton btnManageContractors;
    private JButton btnManageAssignments;
    private JButton btnCredentialVerification;
    private JButton btnContractExtensions;
    private JButton btnRefresh;

    public ContractorCoordinatorWorkAreaJPanel(
            JPanel mainContentPanel,
            List<CandidateSubmission> submissionList,
            UserAccount coordinatorAccount,
            Network network
    ) {
        if (mainContentPanel == null) {
            throw new IllegalArgumentException(
                    "Main content panel cannot be null."
            );
        }

        if (submissionList == null) {
            throw new IllegalArgumentException(
                    "Submission list cannot be null."
            );
        }

        if (coordinatorAccount == null) {
            throw new IllegalArgumentException(
                    "Coordinator account cannot be null."
            );
        }

        if (network == null) {
            throw new IllegalArgumentException(
                    "Network cannot be null."
            );
        }

        this.mainContentPanel = mainContentPanel;
        this.submissionList = submissionList;
        this.coordinatorAccount = coordinatorAccount;
        this.network = network;

        initComponents();
        populateDashboardTable();
    }

    private void initComponents() {

        setLayout(new BorderLayout(20, 20));
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
        add(createCenterPanel(), BorderLayout.CENTER);
        add(createBottomPanel(), BorderLayout.SOUTH);
    }

    private JPanel createHeaderPanel() {

        JPanel headerPanel =
                new JPanel(new BorderLayout(10, 15));

        headerPanel.setOpaque(false);

        JPanel titlePanel =
                new JPanel(new GridLayout(2, 1, 0, 5));

        titlePanel.setOpaque(false);

        JLabel lblTitle =
                new JLabel(
                        "Contractor Coordinator Work Area"
                );

        lblTitle.setFont(
                new Font(
                        "Myanmar Sangam MN",
                        Font.ITALIC,
                        24
                )
        );

        JLabel lblSubtitle = new JLabel(
                "Staffing Agency Enterprise - "
                + "Contractor Management Organization"
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

        JPanel buttonPanel =
                new JPanel(new GridLayout(2, 2, 40, 20));

        buttonPanel.setOpaque(false);

        buttonPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20,
                        80,
                        10,
                        80
                )
        );

        btnManageContractors =
                new JButton("Manage Contractors");

        btnManageAssignments =
                new JButton("Manage Assignments");

        btnCredentialVerification =
                new JButton("Credential Verification");

        btnContractExtensions =
                new JButton("Contract Extensions");

        buttonPanel.add(btnManageContractors);
        buttonPanel.add(btnManageAssignments);
        buttonPanel.add(btnCredentialVerification);
        buttonPanel.add(btnContractExtensions);

        headerPanel.add(
                buttonPanel,
                BorderLayout.CENTER
        );

        addButtonActions();

        return headerPanel;
    }

    private JPanel createCenterPanel() {

        JPanel centerPanel =
                new JPanel(new BorderLayout(10, 10));

        centerPanel.setOpaque(false);

        JLabel lblSummary = new JLabel(
                "Contractor Management Summary",
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

        tblMain.getTableHeader()
                .setReorderingAllowed(false);

        centerPanel.add(
                lblSummary,
                BorderLayout.NORTH
        );

        centerPanel.add(
                new JScrollPane(tblMain),
                BorderLayout.CENTER
        );

        return centerPanel;
    }

    private JPanel createBottomPanel() {

        JPanel bottomPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT
                        )
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

        btnManageContractors.addActionListener(
                event -> showComingNext(
                        "Manage Contractors"
                )
        );

        btnManageAssignments.addActionListener(
                event -> showComingNext(
                        "Manage Assignments"
                )
        );

        btnCredentialVerification.addActionListener(
                event -> showComingNext(
                        "Credential Verification"
                )
        );

        btnContractExtensions.addActionListener(
                event -> showComingNext(
                        "Contract Extensions"
                )
        );
    }

    private void showComingNext(String moduleName) {

        JOptionPane.showMessageDialog(
                this,
                moduleName + " will be built next."
        );
    }

    private void populateDashboardTable() {

        tableModel.setRowCount(0);

        List<ContractorAssignment> assignments =
                getAssignments();

        List<Contractor> contractors =
                getContractors(assignments);

        List<ContractExtensionRequest> extensions =
                getExtensionRequests(assignments);

        tableModel.addRow(
                new Object[]{
                    "Contractors",
                    contractors.size(),
                    "Contractors created from approved submissions"
                }
        );

        long availableContractors =
                contractors.stream()
                        .filter(contractor ->
                                contractor.getAvailabilityStatus()
                                == AvailabilityStatus.AVAILABLE
                        )
                        .count();

        tableModel.addRow(
                new Object[]{
                    "Available Contractors",
                    availableContractors,
                    "Contractors available for assignment"
                }
        );

        tableModel.addRow(
                new Object[]{
                    "Assignments",
                    assignments.size(),
                    "Assignments linked to approved submissions"
                }
        );

        long inCompliance =
                assignments.stream()
                        .filter(assignment ->
                                assignment.getStatus()
                                == AssignmentStatus.IN_COMPLIANCE
                        )
                        .count();

        tableModel.addRow(
                new Object[]{
                    "In Compliance",
                    inCompliance,
                    "Assignments under Compliance review"
                }
        );

        long cleared =
                assignments.stream()
                        .filter(assignment ->
                                assignment.getStatus()
                                == AssignmentStatus.CLEARED
                        )
                        .count();

        tableModel.addRow(
                new Object[]{
                    "Cleared Assignments",
                    cleared,
                    "Assignments ready for activation"
                }
        );

        tableModel.addRow(
                new Object[]{
                    "Extension Requests",
                    extensions.size(),
                    "Contract extension requests"
                }
        );
    }

    private List<ContractorAssignment> getAssignments() {

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

    private List<Contractor> getContractors(
            List<ContractorAssignment> assignments
    ) {
        Map<Integer, Contractor> unique =
                new LinkedHashMap<>();

        for (ContractorAssignment assignment
                : assignments) {

            Contractor contractor =
                    assignment.getContractor();

            if (contractor != null) {
                unique.put(
                        contractor.getContractorId(),
                        contractor
                );
            }
        }

        return new ArrayList<>(unique.values());
    }

    private List<ContractExtensionRequest>
            getExtensionRequests(
                    List<ContractorAssignment> assignments
            ) {

        List<ContractExtensionRequest> requests =
                new ArrayList<>();

        for (ContractorAssignment assignment
                : assignments) {

            if (assignment.getContract() != null) {
                requests.addAll(
                        assignment.getContract()
                                .getExtensionRequests()
                );
            }
        }

        return requests;
    }
}
