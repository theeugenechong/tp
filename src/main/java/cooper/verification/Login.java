package cooper.verification;

import cooper.ui.Ui;

import java.util.HashMap;

public class Login extends SignInProtocol {

    public Login(SignInDetails signInDetails) {
        super(signInDetails);
    }

    @Override
    public void executeSignIn(Verifier verifier, HashMap<String, UserRole> registeredUsers) {
        if (!isRegisteredUser(registeredUsers)) {
            askUserToRegister();
            verifier.setSuccessfullySignedIn(false);
            return;
        }

        if (!hasCorrectRole(registeredUsers)) {
            Ui.showIncorrectRoleMessage();
            verifier.setSuccessfullySignedIn(false);
            return;
        }

        verifier.setSuccessfullySignedIn(true);
        Ui.showLoggedInSuccessfullyMessage(signInDetails.getUsername());
    }

    private boolean hasCorrectRole(HashMap<String, UserRole> registeredUsers) {
        // compares user role which is already in hashmap tp user role of current am object
        UserRole userRoleInHashMap = registeredUsers.get(signInDetails.getUsername());
        return userRoleInHashMap.equals(signInDetails.getUserRole());
    }

    private void askUserToRegister() {
        Ui.showPleaseRegisterMessage();
    }
}
