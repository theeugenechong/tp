package cooper.verification;

import cooper.exceptions.InvalidArgumentException;
import cooper.exceptions.InvalidUserRoleException;
import cooper.exceptions.UnrecognisedCommandException;
import cooper.parser.CommandParser;
import cooper.ui.Ui;

import java.util.HashMap;
import java.util.NoSuchElementException;

public class Verifier {

    private final HashMap<String, UserRole> registeredUsers;
    private boolean isSuccessfullySignedIn;

    public Verifier() {
        this.registeredUsers = new HashMap<>();
        this.isSuccessfullySignedIn = false;
    }

    public HashMap<String, UserRole> getRegisteredUsers() {
        return registeredUsers;
    }

    public void setSuccessfullySignedIn(boolean successfullySignedIn) {
        this.isSuccessfullySignedIn = successfullySignedIn;
    }

    public boolean isSuccessfullySignedIn() {
        return this.isSuccessfullySignedIn;
    }

    public SignInDetails verify(String input) {
        SignInDetails signInDetails = null;
        try {
            SignInProtocol signInProtocol = CommandParser.parseSignInDetails(input);
            signInProtocol.executeSignIn(this, registeredUsers);
            signInDetails = signInProtocol.signInDetails;
        } catch (UnrecognisedCommandException e) {
            isSuccessfullySignedIn = false;
            Ui.showLoginRegisterMessage(false);
        } catch (InvalidArgumentException | NoSuchElementException e) {
            isSuccessfullySignedIn = false;
            Ui.showInvalidCommandArgumentError();
        } catch (InvalidUserRoleException e) {
            isSuccessfullySignedIn = false;
            Ui.showInvalidUserRoleError();
        }
        return signInDetails;
    }
}
