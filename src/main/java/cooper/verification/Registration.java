package cooper.verification;

import cooper.ui.VerificationUi;

import java.util.HashMap;

//@@author theeugenechong
/**
 * Represents the sign in protocol for a new user registering for cOOPer.
 * <p></p>
 * User enters their registration details (username and role) which is then verified
 * to determine if the details provided allow for the user to be successfully registered in
 * the system. A successful registration happens when the user has not been registered in the system.
 */
public class Registration extends SignInProtocol {

    /* Log messages */
    private static final String USER_WITH_USERNAME = "User with username ";
    private static final String SUCCESSFULLY_REGISTERED = " has been successfully registered.";

    public Registration(SignInDetails signInDetails) {
        super(signInDetails);
    }

    /**
     * Executes the addition/registration of a user into cOOPer's list of registered users.
     * @param verifier A flag in {@code verifier} is set only upon successful login which allows the program
     *                 to proceed to the next stage - accessing cOOPer's features.
     * @param rawPassword User's raw password without any hashing/encryption.
     */
    @Override
    public void executeSignIn(Verifier verifier, String rawPassword) {
        HashMap<String, SignInDetails> registeredUsers = verifier.getRegisteredUsers();
        if (isRegisteredUser(registeredUsers)) {
            askUserToLogin();
        } else {
            registerUser(registeredUsers);
            LOGGER.info(USER_WITH_USERNAME + signInDetails.getUsername() + SUCCESSFULLY_REGISTERED);
        }
        verifier.setSuccessfullySignedIn(false);
    }

    /**
     * Registers a user into the system by adding the user sign in details to {@code registeredUsers}.
     *
     * @param registeredUsers A list of details of registered users to which the username and role of
     *                        {@code signInDetails} will be added to upon successful registration.
     */
    private void registerUser(HashMap<String, SignInDetails> registeredUsers) {
        String usernameToRegister = signInDetails.getUsername();
        UserRole userRoleToRegister = signInDetails.getUserRole();
        registeredUsers.put(usernameToRegister, signInDetails);
        VerificationUi.showRegisteredSuccessfullyMessage(usernameToRegister, userRoleToRegister);
    }

    /**
     * In the event that a user who is already registered tries to log in, a message will be printed requesting
     * the user to log in instead.
     */
    private void askUserToLogin() {
        VerificationUi.showPleaseLoginMessage();
    }
}
