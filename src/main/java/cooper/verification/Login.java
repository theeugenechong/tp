package cooper.verification;

import cooper.ui.Ui;

import java.util.HashMap;

/**
 * Represents the sign in protocol for user already registered in cOOPer's system.
 * <p></p>
 * User enters their login details (username, password and role) which is then verified
 * to determine if the details provided allow for a successful login. A successful
 * login is when the username is already registered, the user enters the correct password,
 * and the user is logging in with the correct role.
 */
public class Login extends SignInProtocol {

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
            LOGGER.info("Failed sign in attempt by user with unregistered username, " + signInDetails.getUsername());
            return;
        }
        assert isRegisteredUser(registeredUsers);

        if (!hasCorrectRole(registeredUsers)) {
            Ui.showIncorrectRoleMessage();
            verifier.setSuccessfullySignedIn(false);
            LOGGER.info("Failed sign in attempt by user " + signInDetails.getUsername() + " with incorrect role.");
            return;
        }
        assert (isRegisteredUser(registeredUsers) && hasCorrectRole(registeredUsers));

        if (!hasCorrectPassword(registeredUsers, rawPassword)) {
            Ui.showIncorrectPasswordError();
            verifier.setSuccessfullySignedIn(false);
            LOGGER.info("Failed sign in attempt by user " + signInDetails.getUsername() + " with incorrect password.");
            return;
        }

        verifier.setSuccessfullySignedIn(true);
        Ui.showLoggedInSuccessfullyMessage(signInDetails.getUsername());
        LOGGER.info("User with username " + signInDetails.getUsername() + " successfully signed in.");
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
        Ui.showPleaseRegisterMessage();
    }
}
