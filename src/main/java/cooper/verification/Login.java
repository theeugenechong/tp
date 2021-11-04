package cooper.verification;

import cooper.ui.VerificationUi;

import java.util.HashMap;

//@@author theeugenechong
/**
 * Represents the sign in protocol for user already registered in cOOPer's system.
 * <p></p>
 * User enters their login details (username, password and role) which is then verified
 * to determine if the details provided allow for a successful login. A successful
 * login is when the username is already registered, the user enters the correct password,
 * and the user is logging in with the correct role.
 */
public class Login extends SignInProtocol {

    /* Log messages */
    private static final String FAILED_SIGN_IN_ATTEMPT = "Failed sign in attempt by user ";
    private static final String UNREGISTERED_USERNAME = "with unregistered username, ";
    private static final String INCORRECT_PASSWORD = " with incorrect password.";
    private static final String INCORRECT_ROLE = " with incorrect role.";
    private static final String USER_WITH_USERNAME = "User with username ";
    private static final String SUCCESSFULLY_SIGNED_IN = " successfully signed in.";

    public Login(SignInDetails signInDetails) {
        super(signInDetails);
    }

    /**
     * Executes the logging in of a user to access cOOPer's features.
     * @param verifier A flag in {@code verifier} is set only upon successful login which allows the program
     *                 to proceed to the next stage - accessing cOOPer's features.
     *                 cOOPer's list of registered users is also obtained from {@code verifier}.
     * @param rawPassword User's raw password without any hashing.
     */
    @Override
    public void executeSignIn(Verifier verifier, String rawPassword) {
        HashMap<String, SignInDetails> registeredUsers = verifier.getRegisteredUsers();
        if (!isRegisteredUser(registeredUsers)) {
            askUserToRegister();
            verifier.setSuccessfullySignedIn(false);
            LOGGER.info(FAILED_SIGN_IN_ATTEMPT + UNREGISTERED_USERNAME + signInDetails.getUsername());
            return;
        }
        assert isRegisteredUser(registeredUsers);

        if (!hasCorrectPassword(registeredUsers, rawPassword)) {
            VerificationUi.showIncorrectPasswordMessage();
            verifier.setSuccessfullySignedIn(false);
            LOGGER.info(FAILED_SIGN_IN_ATTEMPT + signInDetails.getUsername() + INCORRECT_PASSWORD);
            return;
        }
        assert (isRegisteredUser(registeredUsers) && hasCorrectPassword(registeredUsers, rawPassword));

        if (!hasCorrectRole(registeredUsers)) {
            VerificationUi.showIncorrectRoleMessage();
            verifier.setSuccessfullySignedIn(false);
            LOGGER.info(FAILED_SIGN_IN_ATTEMPT + signInDetails.getUsername() + INCORRECT_ROLE);
            return;
        }
        assert (isRegisteredUser(registeredUsers) && hasCorrectPassword(registeredUsers, rawPassword)
                && hasCorrectRole(registeredUsers));

        verifier.setSuccessfullySignedIn(true);
        VerificationUi.showLoggedInSuccessfullyMessage(signInDetails.getUsername());
        LOGGER.info(USER_WITH_USERNAME + signInDetails.getUsername() + SUCCESSFULLY_SIGNED_IN);
    }

    /**
     * When the username of {@code signInDetails} is confirmed to exist in the {@code registeredUsers},
     * the role of {@code signInDetails} is checked so that it matches the role with which the user registered.
     *
     * @param registeredUsers A list of users already registered with cOOPer along with their respective
     *                        roles.
     * @return true if the role of {@code signInDetails} matches the role with which the user registered
     */
    private boolean hasCorrectRole(HashMap<String, SignInDetails> registeredUsers) {
        // compares user role which is already in hashmap tp user role of current am object
        SignInDetails user = registeredUsers.get(signInDetails.getUsername());
        UserRole userRoleInHashMap = user.getUserRole();
        return userRoleInHashMap.equals(signInDetails.getUserRole());
    }

    /**
     * Hashes {@code rawPassword} with the registered user salt and compares it with the registered user's
     * encrypted password. Returns true if the hash obtained is same as the registered user's encrypted password.
     *
     * @param registeredUsers A list of users already registered with cOOPer along with their respective
     *                        roles.
     * @param rawPassword the raw password (unencrypted) entered for this sign in instance
     * @return true if the password hash matches the one stored in {@code registeredUsers}
     */
    private boolean hasCorrectPassword(HashMap<String, SignInDetails> registeredUsers, String rawPassword) {
        SignInDetails user = registeredUsers.get(signInDetails.getUsername());
        String userSalt = user.getUserSalt();
        String calculatedHash = PasswordHasher.generatePasswordHash(rawPassword, userSalt);
        return calculatedHash.equals(user.getUserEncryptedPassword());
    }

    /**
     * In the event that an unregistered user tries to log in, a message will be printed requesting the
     * user to register instead.
     */
    private void askUserToRegister() {
        VerificationUi.showPleaseRegisterMessage();
    }
}
