/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UserInterface1;

/**
 *
 * @author abhit
 */


import Business.Network;
import StaffingAgency.Enums.AssignmentStatus;
import StaffingAgency.Enums.AvailabilityStatus;
import StaffingAgency.Enums.EmploymentStatus;
import StaffingAgency.People.Contractor;
import StaffingAgency.Request.ContractExtensionRequest;
import StaffingAgency.Request.ContractorAssignment;
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

public class ContractorCoordinatorWorkAreaJPanel extends JPanel {

    private final JPanel mainContentPanel;
    private final List<Contractor> contractorList;
    private final List<ContractorAssignment> assignmentList;
    private final List<ContractExtensionRequest> extensionRequestList;
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
            List<Contractor> contractorList,
            List<ContractorAssignment> assignmentList,
            List<ContractExtensionRequest> extensionRequestList,
            Network network
    ) {
        if (mainContentPanel == null) {
            throw new IllegalArgumentException(
                    "Main content panel cannot be null."
            );
        }

        if (contractorList == null) {
            throw new IllegalArgumentException(
                    "Contractor list cannot be null."
            );
        }

        if (assignmentList == null) {
            throw new IllegalArgumentException(
                    "Assignment list cannot be null."
            );
        }

        if (extensionRequestList == null) {
            throw new IllegalArgumentException(
                    "Extension request list cannot be null."
            );
        }

        if (network == null) {
            throw new IllegalArgumentException(
                    "Network cannot be null."
            );
        }

        this.mainContentPanel = mainContentPanel;
        this.contractorList = contractorList;
        this.assignmentList = assignmentList;
        this.extensionRequestList = extensionRequestList;
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
                new JLabel("Contractor Coordinator Work Area");

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
        tblMain.getTableHeader().setReorderingAllowed(false);

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
                new JPanel(new FlowLayout(FlowLayout.RIGHT));

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
                event -> JOptionPane.showMessageDialog(
                        this,
                        "Manage Contractors will be built next."
                )
        );

        btnManageAssignments.addActionListener(
                event -> JOptionPane.showMessageDialog(
                        this,
                        "Assignment management will be built next."
                )
        );

        btnCredentialVerification.addActionListener(
                event -> JOptionPane.showMessageDialog(
                        this,
                        "Credential verification will be connected "
                        + "after assignment management."
                )
        );

        btnContractExtensions.addActionListener(
                event -> JOptionPane.showMessageDialog(
                        this,
                        "Contract extension management "
                        + "will be added later."
                )
        );
    }

    private void populateDashboardTable() {

        tableModel.setRowCount(0);

        tableModel.addRow(
                new Object[]{
                    "Contractors",
                    contractorList.size(),
                    "Contractors managed by the staffing agency"
                }
        );

        long availableContractors =
                contractorList.stream()
                        .filter(contractor ->
                                contractor.getAvailabilityStatus()
                                == AvailabilityStatus.AVAILABLE
                        )
                        .count();

        tableModel.addRow(
                new Object[]{
                    "Available Contractors",
                    availableContractors,
                    "Contractors available for a new assignment"
                }
        );

        long activeContractors =
                contractorList.stream()
                        .filter(contractor ->
                                contractor.getEmploymentStatus()
                                == EmploymentStatus.ACTIVE
                        )
                        .count();

        tableModel.addRow(
                new Object[]{
                    "Active Contractors",
                    activeContractors,
                    "Contractors with active employment status"
                }
        );

        tableModel.addRow(
                new Object[]{
                    "Assignments",
                    assignmentList.size(),
                    "Total contractor assignments"
                }
        );

        long activeAssignments =
                assignmentList.stream()
                        .filter(assignment ->
                                assignment.getStatus()
                                == AssignmentStatus.ACTIVE
                        )
                        .count();

        tableModel.addRow(
                new Object[]{
                    "Active Assignments",
                    activeAssignments,
                    "Assignments currently active"
                }
        );

        tableModel.addRow(
                new Object[]{
                    "Extension Requests",
                    extensionRequestList.size(),
                    "Contract extension requests"
                }
        );
    }
}