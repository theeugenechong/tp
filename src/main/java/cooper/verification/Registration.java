package cooper.verification;

import cooper.ui.Ui;

import java.util.HashMap;

public class Registration extends SignIn {

    public Registration(UserDetails userDetails) {
        super(userDetails);
    }

    @Override
    public boolean isRegisteredUser(HashMap<String, UserRole> registeredUsers) {
        return registeredUsers.containsKey(userDetails.getUsername());
    }

    public void registerUser(HashMap<String, UserRole> registeredUsers) {
        String usernameToRegister = userDetails.getUsername();
        UserRole userRoleToRegister = userDetails.getUserRole();
        registeredUsers.put(usernameToRegister, userRoleToRegister);
        Ui.showRegisteredSuccessfullyMessage(usernameToRegister, userRoleToRegister);
    }

    public void askUserToLogin() {
        Ui.showPleaseLoginMessage();
    }
}
