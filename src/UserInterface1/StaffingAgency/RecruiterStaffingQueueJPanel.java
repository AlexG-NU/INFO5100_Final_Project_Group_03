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
import Core.NetworkUtils;
import Core.Organization;
import Core.UserAccount;
import Core.WorkOrder;
import Core.WorkOrderStatus;
import Core.WorkOrders.StaffingReqWorkOrder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class RecruiterStaffingQueueJPanel extends JPanel {

    private final JPanel mainContentPanel;
    private final JPanel recruiterDashboardPanel;
    private final UserAccount recruiterAccount;
    private final Network network;

    private JTable tblRequests;
    private DefaultTableModel tableModel;

    private JButton btnClaim;
    private JButton btnUnderReview;
    private JButton btnRefresh;
    private JButton btnBack;

    public RecruiterStaffingQueueJPanel(
            JPanel mainContentPanel,
            JPanel recruiterDashboardPanel,
            UserAccount recruiterAccount,
            Network network
    ) {
        if (mainContentPanel == null) {
            throw new IllegalArgumentException(
                    "Main content panel is required."
            );
        }

        if (recruiterDashboardPanel == null) {
            throw new IllegalArgumentException(
                    "Recruiter dashboard panel is required."
            );
        }

        if (recruiterAccount == null) {
            throw new IllegalArgumentException(
                    "Recruiter account is required."
            );
        }

        if (network == null) {
            throw new IllegalArgumentException(
                    "Network is required."
            );
        }

        this.mainContentPanel = mainContentPanel;
        this.recruiterDashboardPanel =
                recruiterDashboardPanel;
        this.recruiterAccount = recruiterAccount;
        this.network = network;

        initComponents();
        refreshTable();
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
        add(createButtonPanel(), BorderLayout.SOUTH);
    }

    private JPanel createHeaderPanel() {

        JPanel headerPanel =
                new JPanel(new GridLayout(2, 1, 0, 5));

        headerPanel.setOpaque(false);

        JLabel titleLabel =
                new JLabel("Recruiting Staffing Queue");

        titleLabel.setFont(
                new Font(
                        "Myanmar Sangam MN",
                        Font.ITALIC,
                        24
                )
        );

        JLabel subtitleLabel = new JLabel(
                "Staffing Agency Enterprise - "
                + "Recruiting Organization"
        );

        subtitleLabel.setFont(
                new Font(
                        "Myanmar MN",
                        Font.PLAIN,
                        14
                )
        );

        subtitleLabel.setForeground(
                new Color(102, 102, 102)
        );

        headerPanel.add(titleLabel);
        headerPanel.add(subtitleLabel);

        return headerPanel;
    }

    private JScrollPane createTablePanel() {

        String[] columns = {
            "Work Order ID",
            "Job Title",
            "Positions",
            "Required Skills",
            "Start Date",
            "Status",
            "Sender",
            "Assigned Recruiter"
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

        tblRequests = new JTable(tableModel);
        tblRequests.setRowHeight(26);

        tblRequests.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        tblRequests.getTableHeader()
                .setReorderingAllowed(false);

        return new JScrollPane(tblRequests);
    }

    private JPanel createButtonPanel() {

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(FlowLayout.RIGHT)
                );

        buttonPanel.setOpaque(false);

        btnBack = new JButton("Back");
        btnClaim = new JButton("Claim Request");
        btnUnderReview =
                new JButton("Mark Under Review");
        btnRefresh = new JButton("Refresh");

        buttonPanel.add(btnBack);
        buttonPanel.add(btnClaim);
        buttonPanel.add(btnUnderReview);
        buttonPanel.add(btnRefresh);

        btnBack.addActionListener(
                event -> goBack()
        );

        btnClaim.addActionListener(
                event -> claimSelectedRequest()
        );

        btnUnderReview.addActionListener(
                event -> markSelectedUnderReview()
        );

        btnRefresh.addActionListener(
                event -> refreshTable()
        );

        return buttonPanel;
    }

    private Organization getRecruitingOrganization() {

        return NetworkUtils.findOrganizationByName(
                network,
                "Staffing Agency Enterprise",
                "Recruiting Organization"
        );
    }

    private void refreshTable() {

        tableModel.setRowCount(0);

        Organization recruitingOrganization =
                getRecruitingOrganization();

        if (recruitingOrganization == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Recruiting Organization was not found.",
                    "Routing Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

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

            tableModel.addRow(
                    new Object[]{
                        request,
                        request.getJobTitle(),
                        request.getNumberOfPositions(),
                        request.getRequiredSkills(),
                        request.getStartDate(),
                        request.getStatus(),
                        request.getSender() == null
                                ? ""
                                : request.getSender()
                                        .getUsername(),
                        request.getReceiver() == null
                                ? "Unassigned"
                                : request.getReceiver()
                                        .getUsername()
                    }
            );
        }
    }

    private StaffingReqWorkOrder
            getSelectedRequest() {

        int selectedRow =
                tblRequests.getSelectedRow();

        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Select a staffing request first.",
                    "Selection Required",
                    JOptionPane.WARNING_MESSAGE
            );
            return null;
        }

        int modelRow =
                tblRequests.convertRowIndexToModel(
                        selectedRow
                );

        return (StaffingReqWorkOrder)
                tableModel.getValueAt(modelRow, 0);
    }

    private void claimSelectedRequest() {

        StaffingReqWorkOrder request =
                getSelectedRequest();

        if (request == null) {
            return;
        }

        if (request.getStatus().isDone()) {
            JOptionPane.showMessageDialog(
                    this,
                    "A completed, rejected, or cancelled "
                    + "request cannot be claimed.",
                    "Claim Not Allowed",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (request.getReceiver() != null
                && !request.getReceiver()
                        .getUsername()
                        .equalsIgnoreCase(
                                recruiterAccount.getUsername()
                        )) {

            JOptionPane.showMessageDialog(
                    this,
                    "This request is already assigned to "
                    + request.getReceiver().getUsername()
                    + ".",
                    "Already Assigned",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        request.setReceiver(recruiterAccount);
        request.setStatus(
                WorkOrderStatus.IN_PROGRESS
        );

        if (!recruiterAccount
                .getWorkQueue()
                .getWorkOrderList()
                .contains(request)) {

            recruiterAccount
                    .getWorkQueue()
                    .addWorkOrder(request);
        }

        refreshTable();

        JOptionPane.showMessageDialog(
                this,
                "Staffing Work Order #"
                + request.getWorkOrderId()
                + " assigned to "
                + recruiterAccount.getUsername()
                + "."
        );
    }

    private void markSelectedUnderReview() {

        StaffingReqWorkOrder request =
                getSelectedRequest();

        if (request == null) {
            return;
        }

        if (request.getReceiver() == null
                || !request.getReceiver()
                        .getUsername()
                        .equalsIgnoreCase(
                                recruiterAccount.getUsername()
                        )) {

            JOptionPane.showMessageDialog(
                    this,
                    "Claim the request before reviewing it.",
                    "Request Not Assigned",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (request.getStatus().isDone()) {
            JOptionPane.showMessageDialog(
                    this,
                    "This request is already closed.",
                    "Status Update Not Allowed",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        request.setStatus(
                WorkOrderStatus.UNDER_REVIEW
        );

        refreshTable();

        JOptionPane.showMessageDialog(
                this,
                "Staffing Work Order #"
                + request.getWorkOrderId()
                + " is now under review."
        );
    }

    private void goBack() {

        mainContentPanel.removeAll();
        mainContentPanel.setLayout(
                new BorderLayout()
        );

        mainContentPanel.add(
                recruiterDashboardPanel,
                BorderLayout.CENTER
        );

        mainContentPanel.revalidate();
        mainContentPanel.repaint();

        if (recruiterDashboardPanel
                instanceof RecruiterWorkAreaJPanel) {

            RecruiterWorkAreaJPanel recruiterPanel =
                    (RecruiterWorkAreaJPanel)
                            recruiterDashboardPanel;
/*
            recruiterPanel.refreshDashboard(); 
          */
        }
    }
}