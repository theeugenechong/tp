package cooper.verification;

import cooper.exceptions.InvalidCommandFormatException;
import cooper.exceptions.InvalidUserRoleException;
import cooper.exceptions.UnrecognisedCommandException;
import cooper.parser.SignInDetailsParser;
import cooper.ui.Ui;

import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * Class which verifies the sign in details of a user upon entry to the program.
 */
public class Verifier {

    private final HashMap<String, SignInDetails> registeredUsers;
    private boolean isSuccessfullySignedIn;

    public Verifier() {
        this.registeredUsers = new HashMap<>();
        this.isSuccessfullySignedIn = false;
    }

    public HashMap<String, SignInDetails> getRegisteredUsers() {
        return registeredUsers;
    }

    public void setSuccessfullySignedIn(boolean successfullySignedIn) {
        this.isSuccessfullySignedIn = successfullySignedIn;
    }

    public boolean isSuccessfullySignedIn() {
        return this.isSuccessfullySignedIn;
    }

    /**
     * Verifies that the sign in details provided by the user is valid. A user must log in with the same
     * role with which he was registered with. Prints error messages for the user if the sign in details provided are
     * of the wrong format or are lacking arguments. A message will also be printed if the user tries to log in
     * with the wrong role.
     *
     * @param input A string representing the sign in details input by the user
     * @return a {@code SignInDetails} object representing the sign in details of {@code input} if it
     *         was successfully parsed. Returns {@code null} if parsing was unsuccessful.
     */
    public SignInDetails verify(String input) {
        SignInDetails signInDetails = null;
        try {
            SignInProtocol signInProtocol = SignInDetailsParser.parse(input);
            String rawPassword = SignInDetailsParser.parseRawPassword(input);
            signInProtocol.executeSignIn(this, rawPassword);
            signInDetails = signInProtocol.signInDetails;
        } catch (UnrecognisedCommandException e) {
            isSuccessfullySignedIn = false;
            Ui.showLoginRegisterMessage(false);
        } catch (NoSuchElementException | InvalidCommandFormatException e) {
            isSuccessfullySignedIn = false;
            Ui.showInvalidCommandFormatError();
        } catch (InvalidUserRoleException e) {
            isSuccessfullySignedIn = false;
            Ui.showInvalidUserRoleError();
        }
        return signInDetails;
    }
}
