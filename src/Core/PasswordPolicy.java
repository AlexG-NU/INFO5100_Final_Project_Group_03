package Core;

/**
 * Shared password rules used by login account creation screens.
 *
 * @author janet
 */
public final class PasswordPolicy {

    public static final String REQUIREMENTS =
            "Password must be at least 8 characters.";

    private PasswordPolicy() {
    }

    public static boolean isValid(String password) {
        return password != null
                && password.length() >= 8;
    }

    public static void validate(String password) {
        if (!isValid(password)) {
            throw new IllegalArgumentException(REQUIREMENTS);
        }
    }
}
