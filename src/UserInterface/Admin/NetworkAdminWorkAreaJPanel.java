package UserInterface.Admin;

import Business.Network;
import Core.Enterprise;
import Core.EnterpriseAdminRole;
import Core.ManagedEnterprise;
import Core.Person;
import Core.UserAccount;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 * Network-level administration for enterprises and enterprise administrators.
 *
 * @author janet
 */
public class NetworkAdminWorkAreaJPanel extends JPanel {

    private final Network network;
    private final JTable enterpriseTable = new JTable();
    private final JTable adminTable = new JTable();
    private final JTextField enterpriseNameField = new JTextField(18);
    private final JTextField adminNameField = new JTextField(18);
    private final JTextField usernameField = new JTextField(14);
    private final JPasswordField passwordField = new JPasswordField(14);

    public NetworkAdminWorkAreaJPanel(JPanel container,
            UserAccount account, Network network) {
        if (network == null) {
            throw new IllegalArgumentException("Network is required.");
        }
        this.network = network;
        buildScreen();
        refreshTables();
    }

    private void buildScreen() {
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JLabel title = new JLabel("Network Administrator Work Area");
        title.setFont(title.getFont().deriveFont(24f));
        add(title, BorderLayout.NORTH);

        enterpriseTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        adminTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JPanel enterprisePanel = new JPanel(new BorderLayout(8, 8));
        enterprisePanel.setBorder(
                BorderFactory.createTitledBorder("Manage Enterprises"));
        enterprisePanel.add(new JScrollPane(enterpriseTable), BorderLayout.CENTER);
        enterprisePanel.add(buildEnterpriseControls(), BorderLayout.SOUTH);

        JPanel adminPanel = new JPanel(new BorderLayout(8, 8));
        adminPanel.setBorder(
                BorderFactory.createTitledBorder("Enterprise Administrators"));
        adminPanel.add(new JScrollPane(adminTable), BorderLayout.CENTER);
        adminPanel.add(buildAdminControls(), BorderLayout.SOUTH);

        JSplitPane split = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT, enterprisePanel, adminPanel);
        split.setResizeWeight(0.52);
        add(split, BorderLayout.CENTER);
    }

    private JPanel buildEnterpriseControls() {
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton add = new JButton("Add");
        JButton rename = new JButton("Rename");
        JButton delete = new JButton("Delete");
        JButton refresh = new JButton("Refresh");

        controls.add(new JLabel("Enterprise Name:"));
        controls.add(enterpriseNameField);
        controls.add(add);
        controls.add(rename);
        controls.add(delete);
        controls.add(refresh);

        add.addActionListener(event -> addEnterprise());
        rename.addActionListener(event -> renameEnterprise());
        delete.addActionListener(event -> deleteEnterprise());
        refresh.addActionListener(event -> refreshTables());
        return controls;
    }

    private JPanel buildAdminControls() {
        JPanel wrapper = new JPanel(new BorderLayout(6, 6));
        JPanel fields = new JPanel(new GridLayout(2, 4, 8, 6));
        fields.add(new JLabel("Admin Name:"));
        fields.add(adminNameField);
        fields.add(new JLabel("Username:"));
        fields.add(usernameField);
        fields.add(new JLabel("Password:"));
        fields.add(passwordField);
        fields.add(new JLabel("Uses selected enterprise"));

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton create = new JButton("Create Enterprise Admin");
        JButton delete = new JButton("Delete Admin");
        buttons.add(create);
        buttons.add(delete);

        create.addActionListener(event -> createEnterpriseAdmin());
        delete.addActionListener(event -> deleteEnterpriseAdmin());
        wrapper.add(fields, BorderLayout.CENTER);
        wrapper.add(buttons, BorderLayout.SOUTH);
        return wrapper;
    }

    private void addEnterprise() {
        String name = enterpriseNameField.getText().trim();
        if (name.isEmpty()) {
            showError("Enter an enterprise name.");
            return;
        }
        if (findEnterprise(name) != null) {
            showError("An enterprise with this name already exists.");
            return;
        }
        network.addEnterprise(new ManagedEnterprise(name));
        enterpriseNameField.setText("");
        refreshTables();
    }

    private void renameEnterprise() {
        Enterprise selected = getSelectedEnterprise();
        if (selected == null) {
            return;
        }
        String name = enterpriseNameField.getText().trim();
        if (name.isEmpty()) {
            showError("Enter the new enterprise name.");
            return;
        }
        Enterprise duplicate = findEnterprise(name);
        if (duplicate != null && duplicate != selected) {
            showError("An enterprise with this name already exists.");
            return;
        }
        selected.setName(name);
        enterpriseNameField.setText("");
        refreshTables();
    }

    private void deleteEnterprise() {
        Enterprise selected = getSelectedEnterprise();
        if (selected == null) {
            return;
        }
        for (UserAccount user : network.getUserAccountDirectory()
                .getUserAccountList()) {
            if (user.getRole() instanceof EnterpriseAdminRole
                    && ((EnterpriseAdminRole) user.getRole())
                            .getEnterprise() == selected) {
                showError("Delete this enterprise's admin accounts first.");
                return;
            }
        }
        int result = JOptionPane.showConfirmDialog(
                this,
                "Delete " + selected.getName() + "?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            network.getEnterpriseList().remove(selected);
            refreshTables();
        }
    }

    private void createEnterpriseAdmin() {
        Enterprise enterprise = getSelectedEnterprise();
        if (enterprise == null) {
            return;
        }
        String name = adminNameField.getText().trim();
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        if (name.isEmpty() || username.isEmpty() || password.isBlank()) {
            showError("Admin name, username, and password are required.");
            return;
        }
        UserAccount created;
        try {
            created = network.getUserAccountDirectory()
                    .createUserAccount(
                            username,
                            password,
                            new Person(name),
                            new EnterpriseAdminRole(enterprise));
        } catch (IllegalArgumentException ex) {
            showError(ex.getMessage());
            return;
        }
        if (created == null) {
            showError("That username is already in use.");
            return;
        }
        enterprise.getUserAccountDirectory()
                .getUserAccountList().add(created);
        adminNameField.setText("");
        usernameField.setText("");
        passwordField.setText("");
        refreshTables();
    }

    private void deleteEnterpriseAdmin() {
        int row = adminTable.getSelectedRow();
        if (row < 0) {
            showError("Select an enterprise administrator.");
            return;
        }
        UserAccount selected = (UserAccount) adminTable.getValueAt(row, 0);
        network.getUserAccountDirectory()
                .getUserAccountList().remove(selected);
        Enterprise enterprise =
                ((EnterpriseAdminRole) selected.getRole()).getEnterprise();
        enterprise.getUserAccountDirectory()
                .getUserAccountList().remove(selected);
        refreshTables();
    }

    private Enterprise getSelectedEnterprise() {
        int row = enterpriseTable.getSelectedRow();
        if (row < 0) {
            showError("Select an enterprise.");
            return null;
        }
        return (Enterprise) enterpriseTable.getValueAt(row, 0);
    }

    private Enterprise findEnterprise(String name) {
        for (Enterprise enterprise : network.getEnterpriseList()) {
            if (enterprise.getName().equalsIgnoreCase(name)) {
                return enterprise;
            }
        }
        return null;
    }

    private void refreshTables() {
        DefaultTableModel enterpriseModel = readOnlyModel(
                new String[]{"Enterprise", "Type", "Organizations", "Users"});
        for (Enterprise enterprise : network.getEnterpriseList()) {
            enterpriseModel.addRow(new Object[]{
                enterprise,
                enterprise.getClass().getSimpleName(),
                enterprise.getOrganizationDirectory()
                        .getOrganizationList().size(),
                enterprise.getUserAccountDirectory()
                        .getUserAccountList().size()
            });
        }
        enterpriseTable.setModel(enterpriseModel);

        DefaultTableModel adminModel = readOnlyModel(
                new String[]{"Username", "Admin Name", "Enterprise"});
        for (UserAccount user : network.getUserAccountDirectory()
                .getUserAccountList()) {
            if (user.getRole() instanceof EnterpriseAdminRole) {
                Enterprise enterprise =
                        ((EnterpriseAdminRole) user.getRole()).getEnterprise();
                adminModel.addRow(new Object[]{
                    user,
                    user.getPerson() == null ? "" : user.getPerson(),
                    enterprise.getName()
                });
            }
        }
        adminTable.setModel(adminModel);
    }

    private DefaultTableModel readOnlyModel(String[] columns) {
        return new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(
                this, message, "Network Administration",
                JOptionPane.WARNING_MESSAGE);
    }
}
