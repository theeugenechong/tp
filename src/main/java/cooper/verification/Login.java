package cooper.verification;

import cooper.ui.Ui;

import java.util.HashMap;

/**
 * Represents the sign in protocol for user already registered in cOOPer's system.
 * <p></p>
 * User enters their login details (username and role) which is then verified
 * to determine if the details provided allow for a successful login. A successful
 * login happens when the user is already registered and the user is logging in with
 * the role they registered with.
 */
public class Login extends SignInProtocol {

    public Login(SignInDetails signInDetails) {
        super(signInDetails);
    }

    /**
     * Executes the logging in of a user to access cOOPer's features.
     *
     * @param verifier A flag in {@code verifier} is set only upon successful login which allows the program
     *                 to proceed to the next stage - accessing cOOPer's features.
     * @param registeredUsers A list of users already registered with cOOPer along with their respective
     *                        roles.
     */
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

        assert (isRegisteredUser(registeredUsers) && hasCorrectRole(registeredUsers));

        verifier.setSuccessfullySignedIn(true);
        Ui.showLoggedInSuccessfullyMessage(signInDetails.getUsername());
    }

    /**
     * When the username of {@code signInDetails} is confirmed to exist in the {@code registeredUsers},
     * the role of {@code signInDetails} is checked so that it matches the role with which the user registered.
     *
     * @param registeredUsers A list of users already registered with cOOPer along with their respective
     *                        roles.
     * @return true if the role of {@code signInDetails} matches the role with which the user registered
     */
    private boolean hasCorrectRole(HashMap<String, UserRole> registeredUsers) {
        // compares user role which is already in hashmap tp user role of current am object
        UserRole userRoleInHashMap = registeredUsers.get(signInDetails.getUsername());
        return userRoleInHashMap.equals(signInDetails.getUserRole());
    }

    /**
     * In the event that an unregistered user tries to log in, a message will be printed requesting the
     * user to register instead.
     */
    private void askUserToRegister() {
        Ui.showPleaseRegisterMessage();
    }
}
