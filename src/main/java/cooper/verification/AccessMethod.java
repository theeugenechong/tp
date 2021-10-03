package cooper.verification;

import java.util.HashMap;

public abstract class AccessMethod {

    protected final UserDetails userDetails;

    public AccessMethod(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public abstract boolean isRegisteredUser(HashMap<String, UserRole> registeredUsers);
}
