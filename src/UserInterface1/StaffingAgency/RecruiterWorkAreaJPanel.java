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
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class RecruiterWorkAreaJPanel extends JPanel {

    /*
     * Shared application data.
     * These lists are created outside this panel and passed in.
     */
    private final List<StaffingRequest> masterRequestList;
    private final List<Candidate> candidateList;

    /*
     * Main UI panels.
     */
    private JPanel navigationPanel;
    private JPanel workAreaPanel;

    /*
     * Navigation buttons.
     */
    private JButton btnDashboard;
    private JButton btnStaffingRequests;
    private JButton btnManageCandidates;
    private JButton btnCandidateSubmissions;
    private JButton btnCredentialVerification;
    private JButton btnReports;

    private JLabel lblTitle;

    public RecruiterWorkAreaJPanel(
            List<StaffingRequest> masterRequestList,
            List<Candidate> candidateList
    ) {
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

        this.masterRequestList = masterRequestList;
        this.candidateList = candidateList;

        initComponents();
        showDashboard();
    }

    /**
     * Creates the main Recruiter work-area layout.
     */
    private void initComponents() {

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        createNavigationPanel();
        createWorkAreaPanel();

        add(navigationPanel, BorderLayout.WEST);
        add(workAreaPanel, BorderLayout.CENTER);
    }

    /**
     * Creates the left-side navigation menu.
     */
    private void createNavigationPanel() {

        navigationPanel = new JPanel(
                new GridBagLayout()
        );

        navigationPanel.setBackground(
                new Color(44, 62, 80)
        );

        navigationPanel.setPreferredSize(
                new java.awt.Dimension(240, 700)
        );

        lblTitle = new JLabel(
                "<html><center>"
                + "Recruiter"
                + "<br>"
                + "Work Area"
                + "</center></html>",
                SwingConstants.CENTER
        );

        lblTitle.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        lblTitle.setForeground(Color.WHITE);

        btnDashboard = createMenuButton(
                "Dashboard"
        );

        btnStaffingRequests = createMenuButton(
                "View Staffing Requests"
        );

        btnManageCandidates = createMenuButton(
                "Manage Candidates"
        );

        btnCandidateSubmissions = createMenuButton(
                "Candidate Submissions"
        );

        btnCredentialVerification = createMenuButton(
                "Credential Verification"
        );

        btnReports = createMenuButton(
                "Recruiter Reports"
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        /*
         * Title.
         */
        gbc.gridy = 0;
        gbc.insets = new Insets(
                35,
                20,
                35,
                20
        );

        navigationPanel.add(lblTitle, gbc);

        /*
         * Menu buttons.
         */
        gbc.insets = new Insets(
                8,
                20,
                8,
                20
        );

        gbc.gridy = 1;
        navigationPanel.add(
                btnDashboard,
                gbc
        );

        gbc.gridy = 2;
        navigationPanel.add(
                btnStaffingRequests,
                gbc
        );

        gbc.gridy = 3;
        navigationPanel.add(
                btnManageCandidates,
                gbc
        );

        gbc.gridy = 4;
        navigationPanel.add(
                btnCandidateSubmissions,
                gbc
        );

        gbc.gridy = 5;
        navigationPanel.add(
                btnCredentialVerification,
                gbc
        );

        gbc.gridy = 6;
        navigationPanel.add(
                btnReports,
                gbc
        );

        /*
         * Push menu items toward the top.
         */
        gbc.gridy = 7;
        gbc.weighty = 1;

        navigationPanel.add(
                new JLabel(),
                gbc
        );

        /*
         * Button actions.
         */
        btnDashboard.addActionListener(
                event -> showDashboard()
        );

        btnStaffingRequests.addActionListener(
                event -> openStaffingRequests()
        );

        btnManageCandidates.addActionListener(
                event -> openManageCandidates()
        );

        btnCandidateSubmissions.addActionListener(
                event -> showPlaceholder(
                        "Candidate Submissions",
                        "The candidate submission workflow "
                        + "will be added next."
                )
        );

        btnCredentialVerification.addActionListener(
                event -> showPlaceholder(
                        "Credential Verification",
                        "Credential verification requests "
                        + "will appear here."
                )
        );

        btnReports.addActionListener(
                event -> showPlaceholder(
                        "Recruiter Reports",
                        "Recruiter analytics and summary "
                        + "reports will appear here."
                )
        );
    }

    /**
     * Creates a consistently styled navigation button.
     */
    private JButton createMenuButton(
            String buttonText
    ) {

        JButton button =
                new JButton(buttonText);

        button.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        button.setFocusPainted(false);

        button.setPreferredSize(
                new java.awt.Dimension(
                        195,
                        42
                )
        );

        return button;
    }

    /**
     * Creates the right-side CardLayout panel.
     */
    private void createWorkAreaPanel() {

        workAreaPanel = new JPanel(
                new CardLayout()
        );

        workAreaPanel.setBackground(
                Color.WHITE
        );
    }

    /**
     * Displays the Recruiter dashboard.
     */
    private void showDashboard() {

        JPanel dashboardPanel =
                new JPanel(
                        new BorderLayout()
                );

        dashboardPanel.setBackground(
                Color.WHITE
        );

        JLabel welcomeLabel =
                new JLabel(
                        "<html>"
                        + "<div style='text-align:center;'>"
                        + "Welcome to the Recruiter Work Area"
                        + "<br><br>"
                        + "<span style='font-size:16px;'>"
                        + "Select an option from the left menu."
                        + "</span>"
                        + "<br><br>"
                        + "<span style='font-size:14px;'>"
                        + "Staffing Requests: "
                        + masterRequestList.size()
                        + "&nbsp;&nbsp;&nbsp;"
                        + "Candidates: "
                        + candidateList.size()
                        + "</span>"
                        + "</div>"
                        + "</html>",
                        SwingConstants.CENTER
                );

        welcomeLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        28
                )
        );

        welcomeLabel.setForeground(
                new Color(44, 62, 80)
        );

        dashboardPanel.add(
                welcomeLabel,
                BorderLayout.CENTER
        );

        showWorkAreaPanel(
                "RecruiterDashboard",
                dashboardPanel
        );
    }

    /**
     * Opens the existing staffing-request screen.
     */
    private void openStaffingRequests() {

        ManageStaffingRequestsJPanel staffingPanel =
                new ManageStaffingRequestsJPanel(
                        workAreaPanel,
                        masterRequestList
                );

        showWorkAreaPanel(
                "StaffingRequests",
                staffingPanel
        );
    }

    /**
     * Opens the candidate CRUD screen.
     */
    private void openManageCandidates() {

        ManageCandidatesJPanel candidatePanel =
                new ManageCandidatesJPanel(
                        candidateList
                );

        showWorkAreaPanel(
                "ManageCandidates",
                candidatePanel
        );
    }

    /**
     * Displays a temporary screen for modules that have not yet been built.
     */
    private void showPlaceholder(
            String title,
            String message
    ) {

        JPanel placeholderPanel =
                new JPanel(
                        new BorderLayout()
                );

        placeholderPanel.setBackground(
                Color.WHITE
        );

        JLabel placeholderLabel =
                new JLabel(
                        "<html>"
                        + "<div style='text-align:center;'>"
                        + title
                        + "<br><br>"
                        + "<span style='font-size:16px;'>"
                        + message
                        + "</span>"
                        + "</div>"
                        + "</html>",
                        SwingConstants.CENTER
                );

        placeholderLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        26
                )
        );

        placeholderLabel.setForeground(
                new Color(44, 62, 80)
        );

        placeholderPanel.add(
                placeholderLabel,
                BorderLayout.CENTER
        );

        showWorkAreaPanel(
                title,
                placeholderPanel
        );
    }

    /**
     * Replaces the current panel shown inside the Recruiter work area.
     */
    private void showWorkAreaPanel(
            String cardName,
            JPanel panel
    ) {

        workAreaPanel.removeAll();

        workAreaPanel.add(
                panel,
                cardName
        );

        CardLayout cardLayout =
                (CardLayout)
                        workAreaPanel.getLayout();

        cardLayout.show(
                workAreaPanel,
                cardName
        );

        workAreaPanel.revalidate();
        workAreaPanel.repaint();
    }
}