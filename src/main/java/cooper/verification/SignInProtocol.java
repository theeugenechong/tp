package cooper.verification;

import java.util.HashMap;
import java.util.logging.Logger;

//@@author theeugenechong
/**
 * Class representing protocols which allow a user to gain access to cOOPer's features, namely
 * login and registration.
 */
public abstract class SignInProtocol {

    /* Represents the sign-in details related to this sign in protocol instance. */
    protected final SignInDetails signInDetails;
    protected static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public SignInProtocol(SignInDetails signInDetails) {
        this.signInDetails = signInDetails;
    }

    public abstract void executeSignIn(Verifier verifier, String rawPassword);

    /**
     * Checks if the username in {@code signInDetails} is already present in the list of registered
     * users.
     *
     * @param registeredUsers A list of users already registered with cOOPer along with their respective
     *                        roles.
     * @return true if the username of {@code signInDetails} is already present as a key in {@code registeredUsers}
     */
    protected boolean isRegisteredUser(HashMap<String, SignInDetails> registeredUsers) {
        return registeredUsers.containsKey(signInDetails.getUsername());
    }
}
