package UserInterface1;

import Business.ConfigureABusiness;
import UserInterface.Client.ManageStaffingRequestsJPanel;
import WorkOrders.StaffingRequest;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class MainJFrame extends JFrame {

    /*
     * Current project data.
     * This is retained from your existing MainJFrame.
     */
    private List<StaffingRequest> masterRequestList = new ArrayList<>();

    /*
     * Main UI components.
     */
    private JSplitPane splitPane;
    private JPanel loginPanel;
    private JPanel contentPanel;

    private JLabel lblPortalTitle;
    private JLabel lblUsername;
    private JLabel lblPassword;
    private JLabel lblWelcome;

    private JTextField txtUsername;
    private JPasswordField txtPassword;

    private JButton btnLogin;
    private JButton btnLogout;

    public MainJFrame() {

        /*
         * Creates the MainJFrame UI.
         */
        initComponents();

        /*
         * Current code retained:
         * Load the sample staffing requests.
         */
        masterRequestList =
                ConfigureABusiness.populateStaffingRequests();

        /*
         * Frame settings.
         */
        setTitle("Global Workforce Staffing Network");
        setSize(1100, 700);
        setLocationRelativeTo(null);

        /*
         * User must log in before seeing the work area.
         */
        btnLogout.setVisible(false);

        showWelcomePanel();
    }

    /**
     * Displays a JPanel inside the main content area.
     *
     * This replaces the content shown on the right side of the frame.
     */
    public void showPanel(JPanel panel) {

        contentPanel.removeAll();
        contentPanel.add(panel, "currentPanel");

        CardLayout cardLayout =
                (CardLayout) contentPanel.getLayout();

        cardLayout.show(contentPanel, "currentPanel");

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    /**
     * Displays the initial welcome screen.
     */
    private void showWelcomePanel() {

        JPanel welcomePanel =
                new JPanel(new BorderLayout());

        welcomePanel.setBackground(Color.WHITE);

        lblWelcome = new JLabel(
                "<html><div style='text-align:center;'>"
                + "Global Workforce Staffing Network"
                + "<br><br>"
                + "<span style='font-size:16px;'>"
                + "Please log in to continue"
                + "</span>"
                + "</div></html>",
                SwingConstants.CENTER
        );

        lblWelcome.setFont(
                new Font("Arial", Font.BOLD, 28)
        );

        lblWelcome.setForeground(
                new Color(0, 102, 153)
        );

        welcomePanel.add(
                lblWelcome,
                BorderLayout.CENTER
        );

        showPanel(welcomePanel);
    }

    /**
     * Handles the login action.
     */
    private void login() {

        String username =
                txtUsername.getText().trim();

        String password =
                new String(txtPassword.getPassword());

        /*
         * Validate empty fields.
         */
        if (username.isEmpty() || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter both username and password.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        /*
         * Temporary authentication.
         *
         * Replace this later with UserAccountDirectory.
         */
        if (!username.equalsIgnoreCase("recruiter")
                || !password.equals("password")) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid username or password.",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        /*
         * Current project logic retained:
         * Open ManageStaffingRequestsJPanel after login.
         */
        ManageStaffingRequestsJPanel panel =
                new ManageStaffingRequestsJPanel(
                        contentPanel,
                        masterRequestList
                );

        showPanel(panel);

        setLoginFieldsVisible(false);
        btnLogout.setVisible(true);
    }

    /**
     * Handles logout.
     */
    private void logout() {

        txtUsername.setText("");
        txtPassword.setText("");

        setLoginFieldsVisible(true);
        btnLogout.setVisible(false);

        showWelcomePanel();
    }

    /**
     * Shows or hides the login fields.
     */
    private void setLoginFieldsVisible(boolean visible) {

        lblUsername.setVisible(visible);
        lblPassword.setVisible(visible);

        txtUsername.setVisible(visible);
        txtPassword.setVisible(visible);

        btnLogin.setVisible(visible);
    }

    /**
     * Creates the full MainJFrame interface.
     */
    private void initComponents() {

        splitPane = new JSplitPane();

        loginPanel = new JPanel();
        contentPanel = new JPanel();

        lblPortalTitle = new JLabel();
        lblUsername = new JLabel();
        lblPassword = new JLabel();

        txtUsername = new JTextField();
        txtPassword = new JPasswordField();

        btnLogin = new JButton();
        btnLogout = new JButton();

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        /*
         * Split pane configuration.
         */
        splitPane.setDividerLocation(230);
        splitPane.setDividerSize(4);
        splitPane.setEnabled(false);

        /*
         * Left-side login panel.
         */
        loginPanel.setBackground(
                new Color(0, 153, 153)
        );

        loginPanel.setMinimumSize(
                new java.awt.Dimension(230, 700)
        );

        loginPanel.setPreferredSize(
                new java.awt.Dimension(230, 700)
        );

        /*
         * Portal title.
         */
        lblPortalTitle.setFont(
                new Font("Arial", Font.BOLD, 20)
        );

        lblPortalTitle.setForeground(Color.WHITE);

        lblPortalTitle.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        lblPortalTitle.setText(
                "<html><center>"
                + "Staffing Agency"
                + "<br>"
                + "Portal"
                + "</center></html>"
        );

        /*
         * Username label and field.
         */
        lblUsername.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        lblUsername.setForeground(Color.WHITE);
        lblUsername.setText("Username");

        txtUsername.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        /*
         * Optional temporary default username.
         * Remove this line later if desired.
         */
        txtUsername.setText("recruiter");

        /*
         * Password label and field.
         */
        lblPassword.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        lblPassword.setForeground(Color.WHITE);
        lblPassword.setText("Password");

        txtPassword.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        /*
         * Optional temporary default password.
         * Remove this line later if desired.
         */
        txtPassword.setText("password");

        /*
         * Pressing Enter in the password field logs in.
         */
        txtPassword.addActionListener(
                evt -> login()
        );

        /*
         * Login button.
         */
        btnLogin.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        btnLogin.setText("Login");

        btnLogin.addActionListener(
                evt -> login()
        );

        /*
         * Logout button.
         */
        btnLogout.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        btnLogout.setText("Logout");

        btnLogout.addActionListener(
                evt -> logout()
        );

        /*
         * Login panel layout.
         */
        javax.swing.GroupLayout loginPanelLayout =
                new javax.swing.GroupLayout(loginPanel);

        loginPanel.setLayout(loginPanelLayout);

        loginPanelLayout.setHorizontalGroup(
                loginPanelLayout
                        .createParallelGroup(
                                javax.swing.GroupLayout.Alignment.LEADING
                        )
                        .addGroup(
                                loginPanelLayout
                                        .createSequentialGroup()
                                        .addGap(25, 25, 25)
                                        .addGroup(
                                                loginPanelLayout
                                                        .createParallelGroup(
                                                                javax.swing.GroupLayout.Alignment.LEADING
                                                        )
                                                        .addComponent(
                                                                lblPortalTitle,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                180,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE
                                                        )
                                                        .addComponent(
                                                                lblUsername
                                                        )
                                                        .addComponent(
                                                                txtUsername,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                180,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE
                                                        )
                                                        .addComponent(
                                                                lblPassword
                                                        )
                                                        .addComponent(
                                                                txtPassword,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                180,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE
                                                        )
                                                        .addComponent(
                                                                btnLogin,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                100,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE
                                                        )
                                                        .addComponent(
                                                                btnLogout,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                100,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE
                                                        )
                                        )
                                        .addContainerGap(
                                                25,
                                                Short.MAX_VALUE
                                        )
                        )
        );

        loginPanelLayout.setVerticalGroup(
                loginPanelLayout
                        .createParallelGroup(
                                javax.swing.GroupLayout.Alignment.LEADING
                        )
                        .addGroup(
                                loginPanelLayout
                                        .createSequentialGroup()
                                        .addGap(45, 45, 45)
                                        .addComponent(
                                                lblPortalTitle,
                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                60,
                                                javax.swing.GroupLayout.PREFERRED_SIZE
                                        )
                                        .addGap(55, 55, 55)
                                        .addComponent(
                                                lblUsername
                                        )
                                        .addPreferredGap(
                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED
                                        )
                                        .addComponent(
                                                txtUsername,
                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                35,
                                                javax.swing.GroupLayout.PREFERRED_SIZE
                                        )
                                        .addGap(18, 18, 18)
                                        .addComponent(
                                                lblPassword
                                        )
                                        .addPreferredGap(
                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED
                                        )
                                        .addComponent(
                                                txtPassword,
                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                35,
                                                javax.swing.GroupLayout.PREFERRED_SIZE
                                        )
                                        .addGap(25, 25, 25)
                                        .addComponent(
                                                btnLogin,
                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                35,
                                                javax.swing.GroupLayout.PREFERRED_SIZE
                                        )
                                        .addGap(18, 18, 18)
                                        .addComponent(
                                                btnLogout,
                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                35,
                                                javax.swing.GroupLayout.PREFERRED_SIZE
                                        )
                                        .addContainerGap(
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE
                                        )
                        )
        );

        splitPane.setLeftComponent(loginPanel);

        /*
         * Right-side work area.
         */
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setLayout(new CardLayout());

        splitPane.setRightComponent(contentPanel);

        /*
         * Add split pane to the frame.
         */
        getContentPane().setLayout(
                new BorderLayout()
        );

        getContentPane().add(
                splitPane,
                BorderLayout.CENTER
        );

        pack();
    }

    public static void main(String[] args) {

        /*
         * Use Nimbus look and feel when available.
         */
        try {

            for (
                    javax.swing.UIManager.LookAndFeelInfo info
                    : javax.swing.UIManager
                            .getInstalledLookAndFeels()
            ) {

                if ("Nimbus".equals(info.getName())) {

                    javax.swing.UIManager.setLookAndFeel(
                            info.getClassName()
                    );

                    break;
                }
            }

        } catch (
                ClassNotFoundException
                | InstantiationException
                | IllegalAccessException
                | javax.swing.UnsupportedLookAndFeelException ex
        ) {

            java.util.logging.Logger
                    .getLogger(MainJFrame.class.getName())
                    .log(
                            java.util.logging.Level.SEVERE,
                            null,
                            ex
                    );
        }

        java.awt.EventQueue.invokeLater(
                () -> new MainJFrame().setVisible(true)
        );
    }
}