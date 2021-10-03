package cooper.verification;

import java.util.HashMap;

public abstract class SignIn {

    protected final UserDetails userDetails;

    public SignIn(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public abstract boolean isRegisteredUser(HashMap<String, UserRole> registeredUsers);
}
