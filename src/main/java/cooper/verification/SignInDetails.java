package cooper.verification;

//@@author theeugenechong
/**
 * Class representing the sign in details of a user.
 */
public class SignInDetails {

    private final String username;
    private final String userEncryptedPassword;
    private final String userSalt;
    private final UserRole userRole;

    public SignInDetails(String username, String userEncryptedPassword, String userSalt, UserRole userRole) {
        this.username = username;
        this.userEncryptedPassword = userEncryptedPassword;
        this.userSalt = userSalt;
        this.userRole = userRole;
    }

    public String getUsername() {
        return username;
    }

    public String getUserSalt() {
        return userSalt;
    }

    public String getUserEncryptedPassword() {
        return userEncryptedPassword;
    }

    public UserRole getUserRole() {
        return userRole;
    }
}
