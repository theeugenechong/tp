package cooper.verification;

import cooper.ui.Ui;

import java.util.HashMap;

public class Registration extends SignInProtocol {

    public Registration(SignInDetails signInDetails) {
        super(signInDetails);
    }

    @Override
    public void executeSignIn(Verifier verifier, HashMap<String, UserRole> registeredUsers) {
        if (isRegisteredUser(registeredUsers)) {
            askUserToLogin();
        } else {
            registerUser(registeredUsers);
        }
    }

    private void registerUser(HashMap<String, UserRole> registeredUsers) {
        String usernameToRegister = signInDetails.getUsername();
        UserRole userRoleToRegister = signInDetails.getUserRole();
        registeredUsers.put(usernameToRegister, userRoleToRegister);
        Ui.showRegisteredSuccessfullyMessage(usernameToRegister, userRoleToRegister);
    }

    private void askUserToLogin() {
        Ui.showPleaseLoginMessage();
    }
}
