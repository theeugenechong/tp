package cooper.verification;

import cooper.ui.Ui;

import java.util.HashMap;

public class Login extends SignIn {

    public Login(UserDetails userDetails) {
        super(userDetails);
    }

    @Override
    public boolean isRegisteredUser(HashMap<String, UserRole> registeredUsers) {
        return registeredUsers.containsKey(userDetails.getUsername());
    }

    public boolean hasCorrectRole(HashMap<String, UserRole> registeredUsers) {
        // compares user role which is already in hashmap tp user role of current am object
        UserRole userRoleInHashMap = registeredUsers.get(userDetails.getUsername());
        boolean hasCorrectRole = userRoleInHashMap.equals(userDetails.getUserRole());
        if (hasCorrectRole) {
            Ui.showLoggedInSuccessfullyMessage(userDetails.getUsername());
        } else {
            Ui.showIncorrectRoleMessage();
        }
        return hasCorrectRole;
    }

    public void askUserToRegister() {
        Ui.showPleaseRegisterMessage();
    }
}
