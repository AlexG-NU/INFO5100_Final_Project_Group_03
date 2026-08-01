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
import StaffingAgency.Request.Contract;
import StaffingAgency.Request.ContractExtensionRequest;
import StaffingAgency.Request.ContractorAssignment;
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

public class ContractExtensionsJPanel extends JPanel {

    private final JPanel mainContentPanel;
    private final JPanel coordinatorDashboardPanel;
    private final UserAccount coordinatorAccount;
    private final Network network;

    private JTable tblExtensions;
    private DefaultTableModel tableModel;

    private JButton btnClaim;
    private JButton btnApprove;
    private JButton btnReject;
    private JButton btnApply;
    private JButton btnRefresh;
    private JButton btnBack;

    public ContractExtensionsJPanel(
            JPanel mainContentPanel,
            JPanel coordinatorDashboardPanel,
            UserAccount coordinatorAccount,
            Network network
    ) {
        if (mainContentPanel == null) {
            throw new IllegalArgumentException(
                    "Main content panel is required."
            );
        }

        if (coordinatorDashboardPanel == null) {
            throw new IllegalArgumentException(
                    "Coordinator dashboard is required."
            );
        }

        if (coordinatorAccount == null) {
            throw new IllegalArgumentException(
                    "Coordinator account is required."
            );
        }

        if (network == null) {
            throw new IllegalArgumentException(
                    "Network is required."
            );
        }

        this.mainContentPanel = mainContentPanel;
        this.coordinatorDashboardPanel =
                coordinatorDashboardPanel;
        this.coordinatorAccount = coordinatorAccount;
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
        add(createButtonPanel(), BorderLayout.SOUTH);
    }

    private JPanel createHeaderPanel() {

        JPanel panel =
                new JPanel(new GridLayout(2, 1, 0, 5));

        panel.setOpaque(false);

        JLabel title =
                new JLabel("Contract Extension Requests");

        title.setFont(
                new Font(
                        "Myanmar Sangam MN",
                        Font.ITALIC,
                        24
                )
        );

        JLabel subtitle = new JLabel(
                "Review Client requests and apply approved "
                + "contract extensions"
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

    private JScrollPane createTablePanel() {

        String[] columns = {
            "Request ID",
            "Contract ID",
            "Assignment ID",
            "Contractor",
            "Current End Date",
            "Requested End Date",
            "Reason",
            "Status",
            "Sender",
            "Assigned Coordinator"
        };

        tableModel =
                new DefaultTableModel(
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

        tblExtensions = new JTable(tableModel);
        tblExtensions.setRowHeight(26);
        tblExtensions.setAutoCreateRowSorter(true);

        tblExtensions.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        tblExtensions
                .getTableHeader()
                .setReorderingAllowed(false);

        return new JScrollPane(tblExtensions);
    }

    private JPanel createButtonPanel() {

        JPanel panel =
                new JPanel(
                        new FlowLayout(FlowLayout.RIGHT)
                );

        panel.setOpaque(false);

        btnBack = new JButton("Back");
        btnClaim = new JButton("Claim Request");
        btnApprove = new JButton("Approve");
        btnReject = new JButton("Reject");
        btnApply = new JButton("Apply Extension");
        btnRefresh = new JButton("Refresh");

        panel.add(btnBack);
        panel.add(btnClaim);
        panel.add(btnApprove);
        panel.add(btnReject);
        panel.add(btnApply);
        panel.add(btnRefresh);

        btnBack.addActionListener(
                event -> goBack()
        );

        btnClaim.addActionListener(
                event -> claimSelectedRequest()
        );

        btnApprove.addActionListener(
                event -> approveSelectedRequest()
        );

        btnReject.addActionListener(
                event -> rejectSelectedRequest()
        );

        btnApply.addActionListener(
                event -> applySelectedExtension()
        );

        btnRefresh.addActionListener(
                event -> populateTable()
        );

        return panel;
    }

    private Organization
            getContractorManagementOrganization() {

        return NetworkUtils.findOrganizationByName(
                network,
                "Staffing Agency Enterprise",
                "Contractor Management Organization"
        );
    }

    private void populateTable() {

        tableModel.setRowCount(0);

        Organization organization =
                getContractorManagementOrganization();

        if (organization == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Contractor Management Organization "
                    + "was not found.",
                    "Routing Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        for (
                WorkOrder workOrder
                : organization
                        .getWorkQueue()
                        .getWorkOrderList()
        ) {
            if (!(workOrder
                    instanceof ContractExtensionRequest)) {

                continue;
            }

            ContractExtensionRequest request =
                    (ContractExtensionRequest) workOrder;

            Contract contract =
                    request.getContract();

            ContractorAssignment assignment =
                    contract.getAssignment();

            tableModel.addRow(
                    new Object[]{
                        request,
                        contract.getContractId(),

                        assignment == null
                                ? ""
                                : assignment.getAssignmentId(),

                        assignment == null
                                || assignment.getContractor()
                                == null
                                ? ""
                                : assignment
                                        .getContractor()
                                        .getFullName(),

                        request.getCurrentEndDate(),
                        request.getRequestedEndDate(),
                        request.getReason(),
                        request.getStatus(),

                        request.getSender() == null
                                ? ""
                                : request
                                        .getSender()
                                        .getUsername(),

                        request.getReceiver() == null
                                ? "Unassigned"
                                : request
                                        .getReceiver()
                                        .getUsername()
                    }
            );
        }
    }

    private ContractExtensionRequest
            getSelectedRequest() {

        int selectedViewRow =
                tblExtensions.getSelectedRow();

        if (selectedViewRow < 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Select a contract extension "
                    + "request first.",
                    "Selection Required",
                    JOptionPane.WARNING_MESSAGE
            );

            return null;
        }

        int modelRow =
                tblExtensions.convertRowIndexToModel(
                        selectedViewRow
                );

        return (ContractExtensionRequest)
                tableModel.getValueAt(
                        modelRow,
                        0
                );
    }

    private boolean isAssignedToCurrentCoordinator(
            ContractExtensionRequest request
    ) {
        return request.getReceiver() != null
                && request
                        .getReceiver()
                        .getUsername()
                        .equalsIgnoreCase(
                                coordinatorAccount
                                        .getUsername()
                        );
    }

    private void claimSelectedRequest() {

        ContractExtensionRequest request =
                getSelectedRequest();

        if (request == null) {
            return;
        }

        if (request.getStatus().isDone()) {
            showError(
                    "A closed request cannot be claimed."
            );
            return;
        }

        if (request.getReceiver() != null
                && !isAssignedToCurrentCoordinator(
                        request
                )) {

            showError(
                    "This request is already assigned to "
                    + request
                            .getReceiver()
                            .getUsername()
                    + "."
            );

            return;
        }

        request.setReceiver(
                coordinatorAccount
        );

        request.setStatus(
                WorkOrderStatus.IN_PROGRESS
        );

        if (!coordinatorAccount
                .getWorkQueue()
                .getWorkOrderList()
                .contains(request)) {

            coordinatorAccount
                    .getWorkQueue()
                    .addWorkOrder(request);
        }

        populateTable();

        JOptionPane.showMessageDialog(
                this,
                "Extension request claimed."
        );
    }

    private void approveSelectedRequest() {

        ContractExtensionRequest request =
                getSelectedRequest();

        if (request == null) {
            return;
        }

        if (!isAssignedToCurrentCoordinator(
                request
        )) {
            showError(
                    "Claim the request before approving it."
            );
            return;
        }

        try {
            request.approveRequest();

            populateTable();

            JOptionPane.showMessageDialog(
                    this,
                    "Extension request approved."
            );

        } catch (IllegalStateException ex) {
            showError(ex.getMessage());
        }
    }

    private void rejectSelectedRequest() {

        ContractExtensionRequest request =
                getSelectedRequest();

        if (request == null) {
            return;
        }

        if (!isAssignedToCurrentCoordinator(
                request
        )) {
            showError(
                    "Claim the request before rejecting it."
            );
            return;
        }

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Reject the selected contract "
                        + "extension request?",
                        "Confirm Rejection",
                        JOptionPane.YES_NO_OPTION
                );

        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            request.rejectRequest();

            populateTable();

            JOptionPane.showMessageDialog(
                    this,
                    "Extension request rejected."
            );

        } catch (IllegalStateException ex) {
            showError(ex.getMessage());
        }
    }

    private void applySelectedExtension() {

        ContractExtensionRequest request =
                getSelectedRequest();

        if (request == null) {
            return;
        }

        if (!isAssignedToCurrentCoordinator(
                request
        )) {
            showError(
                    "Claim the request before applying it."
            );
            return;
        }

        try {
            request.applyExtension();

            populateTable();

            JOptionPane.showMessageDialog(
                    this,
                    "Contract and assignment end dates "
                    + "updated to "
                    + request.getRequestedEndDate()
                    + "."
            );

        } catch (
                IllegalStateException
                | IllegalArgumentException ex
        ) {
            showError(ex.getMessage());
        }
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
