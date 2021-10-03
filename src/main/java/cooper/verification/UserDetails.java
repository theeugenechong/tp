package cooper.verification;

import cooper.verification.roles.UserRole;

public class UserDetails {

    private final String username;
    private final UserRole userRole;

    public UserDetails(String username, UserRole userRole) {
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
