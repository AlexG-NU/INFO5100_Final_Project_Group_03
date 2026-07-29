package UserInterface.Admin;

import Business.Network;
import Core.Enterprise;
import Core.ManagedOrganization;
import Core.Organization;
import Core.Person;
import Core.Role;
import Core.UserAccount;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
 * Enterprise-level administration for organizations and enterprise users.
 *
 * @author janet
 */
public class EnterpriseAdminWorkAreaJPanel extends JPanel {

    private final Network network;
    private final Enterprise enterprise;
    private final JTable organizationTable = new JTable();
    private final JTable userTable = new JTable();
    private final JTextField organizationNameField = new JTextField(18);
    private final JTextField personNameField = new JTextField(16);
    private final JTextField usernameField = new JTextField(14);
    private final JPasswordField passwordField = new JPasswordField(14);
    private final JComboBox<Role> roleComboBox = new JComboBox<>();

    public EnterpriseAdminWorkAreaJPanel(JPanel container,
            UserAccount account, Network network, Enterprise enterprise) {
        if (network == null || enterprise == null) {
            throw new IllegalArgumentException(
                    "Network and enterprise are required.");
        }
        this.network = network;
        this.enterprise = enterprise;
        buildScreen();
        loadRoles();
        refreshTables();
    }

    private void buildScreen() {
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JLabel title = new JLabel(
                enterprise.getName() + " - Enterprise Administrator");
        title.setFont(title.getFont().deriveFont(24f));
        add(title, BorderLayout.NORTH);

        organizationTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION);
        userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JPanel organizationPanel = new JPanel(new BorderLayout(8, 8));
        organizationPanel.setBorder(
                BorderFactory.createTitledBorder("Manage Organizations"));
        organizationPanel.add(
                new JScrollPane(organizationTable), BorderLayout.CENTER);
        organizationPanel.add(
                buildOrganizationControls(), BorderLayout.SOUTH);

        JPanel userPanel = new JPanel(new BorderLayout(8, 8));
        userPanel.setBorder(
                BorderFactory.createTitledBorder("Manage Enterprise Users"));
        userPanel.add(new JScrollPane(userTable), BorderLayout.CENTER);
        userPanel.add(buildUserControls(), BorderLayout.SOUTH);

        JSplitPane split = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT, organizationPanel, userPanel);
        split.setResizeWeight(0.45);
        add(split, BorderLayout.CENTER);
    }

    private JPanel buildOrganizationControls() {
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton add = new JButton("Add");
        JButton rename = new JButton("Rename");
        JButton delete = new JButton("Delete");
        JButton refresh = new JButton("Refresh");

        controls.add(new JLabel("Organization Name:"));
        controls.add(organizationNameField);
        controls.add(add);
        controls.add(rename);
        controls.add(delete);
        controls.add(refresh);

        add.addActionListener(event -> addOrganization());
        rename.addActionListener(event -> renameOrganization());
        delete.addActionListener(event -> deleteOrganization());
        refresh.addActionListener(event -> refreshTables());
        return controls;
    }

    private JPanel buildUserControls() {
        JPanel wrapper = new JPanel(new BorderLayout(6, 6));
        JPanel fields = new JPanel(new GridLayout(2, 4, 8, 6));
        fields.add(new JLabel("Person Name:"));
        fields.add(personNameField);
        fields.add(new JLabel("Role:"));
        fields.add(roleComboBox);
        fields.add(new JLabel("Username:"));
        fields.add(usernameField);
        fields.add(new JLabel("Password:"));
        fields.add(passwordField);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton create = new JButton("Create User");
        JButton delete = new JButton("Delete User");
        buttons.add(create);
        buttons.add(delete);

        create.addActionListener(event -> createUser());
        delete.addActionListener(event -> deleteUser());
        wrapper.add(fields, BorderLayout.CENTER);
        wrapper.add(buttons, BorderLayout.SOUTH);
        return wrapper;
    }

    private void loadRoles() {
        roleComboBox.removeAllItems();
        for (Role role : enterprise.getSupportedRoles()) {
            roleComboBox.addItem(role);
        }
    }

    private void addOrganization() {
        String name = organizationNameField.getText().trim();
        if (name.isEmpty()) {
            showError("Enter an organization name.");
            return;
        }
        if (findOrganization(name) != null) {
            showError("An organization with this name already exists.");
            return;
        }
        enterprise.getOrganizationDirectory()
                .addOrganization(new ManagedOrganization(name));
        organizationNameField.setText("");
        refreshTables();
    }

    private void renameOrganization() {
        Organization selected = getSelectedOrganization();
        if (selected == null) {
            return;
        }
        String name = organizationNameField.getText().trim();
        if (name.isEmpty()) {
            showError("Enter the new organization name.");
            return;
        }
        Organization duplicate = findOrganization(name);
        if (duplicate != null && duplicate != selected) {
            showError("An organization with this name already exists.");
            return;
        }
        selected.setName(name);
        organizationNameField.setText("");
        refreshTables();
    }

    private void deleteOrganization() {
        Organization selected = getSelectedOrganization();
        if (selected == null) {
            return;
        }
        int result = JOptionPane.showConfirmDialog(
                this,
                "Delete " + selected.getName() + "?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            enterprise.getOrganizationDirectory()
                    .removeOrganization(selected);
            refreshTables();
        }
    }

    private void createUser() {
        String name = personNameField.getText().trim();
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        Role role = (Role) roleComboBox.getSelectedItem();
        if (name.isEmpty() || username.isEmpty()
                || password.isBlank() || role == null) {
            showError("Name, username, password, and role are required.");
            return;
        }
        UserAccount created;
        try {
            created = network.getUserAccountDirectory()
                    .createUserAccount(
                            username, password, new Person(name), role);
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
        personNameField.setText("");
        usernameField.setText("");
        passwordField.setText("");
        refreshTables();
    }

    private void deleteUser() {
        int row = userTable.getSelectedRow();
        if (row < 0) {
            showError("Select an enterprise user.");
            return;
        }
        UserAccount selected = (UserAccount) userTable.getValueAt(row, 0);
        network.getUserAccountDirectory()
                .getUserAccountList().remove(selected);
        enterprise.getUserAccountDirectory()
                .getUserAccountList().remove(selected);
        refreshTables();
    }

    private Organization getSelectedOrganization() {
        int row = organizationTable.getSelectedRow();
        if (row < 0) {
            showError("Select an organization.");
            return null;
        }
        return (Organization) organizationTable.getValueAt(row, 0);
    }

    private Organization findOrganization(String name) {
        for (Organization organization : enterprise
                .getOrganizationDirectory().getOrganizationList()) {
            if (organization.getName().equalsIgnoreCase(name)) {
                return organization;
            }
        }
        return null;
    }

    private void refreshTables() {
        DefaultTableModel organizationModel = readOnlyModel(
                new String[]{"Organization", "Type", "Work Orders"});
        for (Organization organization : enterprise
                .getOrganizationDirectory().getOrganizationList()) {
            organizationModel.addRow(new Object[]{
                organization,
                organization.getClass().getSimpleName(),
                organization.getWorkQueue().getWorkOrderList().size()
            });
        }
        organizationTable.setModel(organizationModel);

        DefaultTableModel userModel = readOnlyModel(
                new String[]{"Username", "Person", "Role"});
        for (UserAccount user : enterprise.getUserAccountDirectory()
                .getUserAccountList()) {
            userModel.addRow(new Object[]{
                user,
                user.getPerson() == null ? "" : user.getPerson(),
                user.getRole()
            });
        }
        userTable.setModel(userModel);
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
                this, message, "Enterprise Administration",
                JOptionPane.WARNING_MESSAGE);
    }
}
