package UserInterface1;

import Business.ConfigureABusiness;
import Business.Network;
import Core.UserAccount;
import StaffingAgency.People.Candidate;
import UserInterface1.StaffingAgency.RecruiterWorkAreaJPanel;
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
import StaffingAgency.Request.CandidateSubmission;

public class MainJFrame extends JFrame {

    /*
     * Shared application data.
     *
     * These lists are created once in MainJFrame and passed to
     * the Recruiter work area so data is not recreated every time
     * the user changes screens.
     */
    private List<StaffingRequest> masterRequestList =
            new ArrayList<>();

    private List<Candidate> candidateList =
            new ArrayList<>();
    private List<CandidateSubmission> submissionList =
        new ArrayList<>();

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
    //private JLabel lblQuickSwitch;
    //private javax.swing.JComboBox<UserAccount> cmbQuickSwitch;
    private Network network;
    
    public MainJFrame() {

        initComponents();

        /*
         * Load existing staffing-request sample data.
         */
        masterRequestList =
                ConfigureABusiness.populateStaffingRequests();
        //this.network = ConfigureABusiness.configure();
        

        /*
         * Load Faker candidates into the shared Staffing lists.
         */
        candidateList = ConfigureABusiness.populateCandidates();
        this.network = ConfigureABusiness.configure(
                masterRequestList,
                candidateList,
                submissionList);
        
        //populateQuickSwitchComboBox();

        setTitle("Global Workforce Staffing Network");
        setSize(1100, 700);
        setLocationRelativeTo(null);

        btnLogout.setVisible(false);

        showWelcomePanel();
    }

    /**
     * Displays a panel inside the right-side content area.
     */
    public void showPanel(JPanel panel) {

        contentPanel.removeAll();
        contentPanel.setLayout(new CardLayout());

        contentPanel.add(
                panel,
                "currentPanel"
        );

        CardLayout cardLayout =
                (CardLayout) contentPanel.getLayout();

        cardLayout.show(
                contentPanel,
                "currentPanel"
        );

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
                "<html>"
                + "<div style='text-align:center;'>"
                + "Global Workforce Staffing Network"
                + "<br><br>"
                + "<span style='font-size:16px;'>"
                + "Please log in to continue"
                + "</span>"
                + "</div>"
                + "</html>",
                SwingConstants.CENTER
        );

        lblWelcome.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        28
                )
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
     * Handles login.
     */
    private void login() {

        String username =
                txtUsername.getText().trim();

        String password =
                new String(
                        txtPassword.getPassword()
                );

        if (username.isEmpty()
                || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter both username and password.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }
        
        UserAccount userAccount = network.getUserAccountDirectory().authenticateUser(username, password);
        if (userAccount == null) {
            JOptionPane.showMessageDialog(this, "Invalid credentials. Please try again.");
            return;
        }
        JPanel workArea = userAccount.getRole().createWorkArea(contentPanel, userAccount, network);
        showPanel(workArea);
        /*
         * Temporary recruiter authentication.
         *
         * Later, replace this with UserAccountDirectory
         * authentication and role-based routing.
         */
        /*if (!username.equalsIgnoreCase("recruiter")
                || !password.equals("password")) {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid username or password.",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }*/

        /*
         * Open the Recruiter work area using the shared lists.
         */
      /*RecruiterWorkAreaJPanel recruiterPanel =
        new RecruiterWorkAreaJPanel(
                contentPanel,
                masterRequestList,
                candidateList,
                submissionList
        );

        showPanel(recruiterPanel);
        */
        setLoginFieldsVisible(false);
        btnLogout.setVisible(true);
    }
    //Demo ONLY!!!
    /*private void quickSwitchLogin() {
 
        Object selected = cmbQuickSwitch.getSelectedItem();
 
        if (!(selected instanceof UserAccount)) {
            return;
        }
 
        UserAccount userAccount = (UserAccount) selected;
 
        JPanel workArea = userAccount.getRole()
                .createWorkArea(contentPanel, userAccount, network);
 
        showPanel(workArea);
        setLoginFieldsVisible(false);
        btnLogout.setVisible(true);
    }*/
    
    /*private void populateQuickSwitchComboBox() {
 
        cmbQuickSwitch.removeAllItems();
        cmbQuickSwitch.addItem(null);
 
        for (UserAccount ua : network.getUserAccountDirectory().getUserAccountList()) {
            cmbQuickSwitch.addItem(ua);
        }
    }*/

    /**
     * Handles logout.
     */
    private void logout() {

        txtUsername.setText("");
        txtPassword.setText("");
        //cmbQuickSwitch.setSelectedIndex(0);

        setLoginFieldsVisible(true);
        btnLogout.setVisible(false);

        showWelcomePanel();
    }

    /**
     * Shows or hides the login controls.
     */
    private void setLoginFieldsVisible(
            boolean visible
    ) {

        lblUsername.setVisible(visible);
        lblPassword.setVisible(visible);

        txtUsername.setVisible(visible);
        txtPassword.setVisible(visible);

        btnLogin.setVisible(visible);
        
        //lblQuickSwitch.setVisible(visible);
        //cmbQuickSwitch.setVisible(visible);
    }

    /**
     * Creates the complete MainJFrame interface.
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
        //lblQuickSwitch = new JLabel();
        //cmbQuickSwitch = new javax.swing.JComboBox<>();
 
        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );
 
        /*
         * Split-pane settings.
         */
        splitPane.setDividerLocation(230);
        splitPane.setDividerSize(4);
        splitPane.setEnabled(false);
 
        /*
         * Left login panel.
         */
        loginPanel.setBackground(
                new Color(0, 153, 153)
        );
 
        loginPanel.setMinimumSize(
                new java.awt.Dimension(
                        230,
                        700
                )
        );
 
        loginPanel.setPreferredSize(
                new java.awt.Dimension(
                        230,
                        700
                )
        );
 
        /*
         * Portal title.
         */
        lblPortalTitle.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18
                )
        );
 
        lblPortalTitle.setForeground(
                Color.WHITE
        );
 
        lblPortalTitle.setHorizontalAlignment(
                SwingConstants.CENTER
        );
 
        lblPortalTitle.setText(
                "<html>"
                + "<center>"
                + "Global Workfoce Staffing Network"
                + "<br>"
                + "Portal"
                + "</center>"
                + "</html>"
        );
 
        /*
         * Username field.
         */
        lblUsername.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );
 
        lblUsername.setForeground(
                Color.WHITE
        );
 
        lblUsername.setText(
                "Username"
        );
 
        txtUsername.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );
 
        /*
         * //janet - I removed the default username.
         * 
         */
        txtUsername.setText(
                ""
        );
 
        /*
         * Password field.
         */
        lblPassword.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );
 
        lblPassword.setForeground(
                Color.WHITE
        );
 
        lblPassword.setText(
                "Password"
        );
 
        txtPassword.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        14
                )
        );
 
        /*
         * Pressing Enter in the password field
         * will attempt login.
         */
        txtPassword.addActionListener(
                event -> login()
        );
 
        /*
         * Quick Switch (Demo) dropdown.
         * Bypasses typing a password so switching accounts
         * during a demo is fast. Real login above still works
         * as normal.
         */
        //lblQuickSwitch.setFont(
        //        new Font("Arial", Font.BOLD, 14)
        //);
        //lblQuickSwitch.setForeground(Color.WHITE);
        //lblQuickSwitch.setText("Quick Switch (Demo)");
 
        //cmbQuickSwitch.setFont(
        //        new Font("Arial", Font.PLAIN, 12)
        //);
        /*cmbQuickSwitch.setRenderer(
                new javax.swing.DefaultListCellRenderer() {
                    @Override
                    public java.awt.Component getListCellRendererComponent(
                            javax.swing.JList<?> list, Object value, int index,
                            boolean isSelected, boolean cellHasFocus) {
                        super.getListCellRendererComponent(
                                list, value, index, isSelected, cellHasFocus);
                        if (value instanceof UserAccount) {
                            UserAccount ua = (UserAccount) value;
                            setText(ua.getUsername() + " ("
                                    + ua.getRole().getClass().getSimpleName() + ")");
                        } else {
                            setText("-- Select account --");
                        }
                        return this;
                    }
                }
        );*/
        //cmbQuickSwitch.addActionListener(
        //        event -> quickSwitchLogin()
        //);
 
        /*
         * Login button.
         */
        btnLogin.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );
 
        btnLogin.setText(
                "Login"
        );
 
        btnLogin.addActionListener(
                event -> login()
        );
 
        
        /*
         * Logout button.
         */
        btnLogout.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );
 
        btnLogout.setText(
                "Logout"
        );
 
        btnLogout.addActionListener(
                event -> logout()
        );
 
        /*
         * Left-panel layout.
         */
        javax.swing.GroupLayout loginPanelLayout =
                new javax.swing.GroupLayout(
                        loginPanel
                );
 
        loginPanel.setLayout(
                loginPanelLayout
        );
 
        loginPanelLayout.setHorizontalGroup(
                loginPanelLayout
                        .createParallelGroup(
                                javax.swing.GroupLayout.Alignment.LEADING
                        )
                        .addGroup(
                                loginPanelLayout
                                        .createSequentialGroup()
                                        .addGap(
                                                25,
                                                25,
                                                25
                                        )
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
                                                                180,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE
                                                        )
                                                        .addComponent(
                                                                btnLogout,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                100,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE
                                                        )
                                                        //.addComponent(
                                                        //        lblQuickSwitch
                                                        //)
                                                        //.addComponent(
                                                        //        cmbQuickSwitch,
                                                        //        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        //        180,
                                                        //        javax.swing.GroupLayout.PREFERRED_SIZE
                                                        //)
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
                                        .addGap(
                                                45,
                                                45,
                                                45
                                        )
                                        .addComponent(
                                                lblPortalTitle,
                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                60,
                                                javax.swing.GroupLayout.PREFERRED_SIZE
                                        )
                                        .addGap(
                                                55,
                                                55,
                                                55
                                        )
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
                                        .addGap(
                                                18,
                                                18,
                                                18
                                        )
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
                                        .addGap(
                                                25,
                                                25,
                                                25
                                        )
                                        .addComponent(
                                                btnLogin,
                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                35,
                                                javax.swing.GroupLayout.PREFERRED_SIZE
                                        )
                                        .addGap(
                                                18,
                                                18,
                                                18
                                        )
                                        .addComponent(
                                                btnLogout,
                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                35,
                                                javax.swing.GroupLayout.PREFERRED_SIZE
                                        )
                                        .addGap(
                                                25,
                                                25,
                                                25
                                        )
                                        //.addComponent(
                                        //        lblQuickSwitch
                                        //)
                                        .addPreferredGap(
                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED
                                        )
                                        //.addComponent(
                                        //        cmbQuickSwitch,
                                        //        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        //        30,
                                        //        javax.swing.GroupLayout.PREFERRED_SIZE
                                        //)
                                        .addContainerGap(
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE
                                        )
                        )
        );
 
        splitPane.setLeftComponent(
                loginPanel
        );
 
        /*
         * Right-side content panel.
         */
        contentPanel.setBackground(
                Color.WHITE
        );
 
        contentPanel.setLayout(
                new CardLayout()
        );
 
        splitPane.setRightComponent(
                contentPanel
        );
 
        /*
         * Add split pane to frame.
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

    public static void main(
            String[] args
    ) {

        try {

            for (
                    javax.swing.UIManager.LookAndFeelInfo info
                    : javax.swing.UIManager
                            .getInstalledLookAndFeels()
            ) {

                if ("Nimbus".equals(
                        info.getName()
                )) {

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
                    .getLogger(
                            MainJFrame.class.getName()
                    )
                    .log(
                            java.util.logging.Level.SEVERE,
                            null,
                            ex
                    );
        }

        java.awt.EventQueue.invokeLater(
                () -> new MainJFrame()
                        .setVisible(true)
        );
    }
}
