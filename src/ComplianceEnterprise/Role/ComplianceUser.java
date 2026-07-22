package ComplianceEnterprise.Role;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author janet
 */
public class ComplianceUser {

    private static final AtomicInteger ID_SEQUENCE = new AtomicInteger(9000);

    private final int employeeId;
    private String name;
    private String email;
    private String username;
    private String role;
    private boolean active;

    public ComplianceUser(String name, String email, String username, String role) {
        this.employeeId = ID_SEQUENCE.incrementAndGet();
        setName(name);
        setEmail(email);
        setUsername(username);
        setRole(role);
        this.active = true;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || !name.trim().matches("[A-Za-z .'-]{2,50}")) {
            throw new IllegalArgumentException("Enter a valid employee name.");
        }
        this.name = name.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || !email.matches("^[\\w.+-]+@[\\w.-]+\\.[A-Za-z]{2,}$")) {
            throw new IllegalArgumentException("Enter a valid email address.");
        }
        this.email = email.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username == null || username.trim().length() < 5) {
            throw new IllegalArgumentException("Username must contain at least 5 characters.");
        }
        this.username = username.trim();
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        if (!"Compliance Manager".equals(role)
                && !"Compliance Analyst".equals(role)
                && !"Credential Specialist".equals(role)) {
            throw new IllegalArgumentException("Select a valid compliance role.");
        }
        this.role = role;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return name;
    }
}
