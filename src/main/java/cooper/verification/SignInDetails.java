package cooper.verification;

/**
 * Class representing the sign in details of a user.
 */
public class SignInDetails {

    private final String username;
    private final UserRole userRole;

    public SignInDetails(String username, UserRole userRole) {
        this.username = username;
        this.userRole = userRole;
    }

    public String getUsername() {
        return username;
    }

    public UserRole getUserRole() {
        return userRole;
    }
}
