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
    private final CommandParser commandParser;
    private boolean isSuccessfullySignedIn;

    public Verifier(CommandParser commandParser) {
        this.registeredUsers = new HashMap<>();
        this.commandParser = commandParser;
        this.isSuccessfullySignedIn = false;
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
            SignInProtocol signInProtocol = commandParser.parseSignInDetails(input);
            signInProtocol.executeSignIn(this, registeredUsers);
            signInDetails = signInProtocol.signInDetails;
        } catch (UnrecognisedCommandException e) {
            Ui.showLoginRegisterMessage(false);
        } catch (InvalidArgumentException | NoSuchElementException e) {
            Ui.showInvalidCommandArgumentError();
        } catch (InvalidUserRoleException e) {
            Ui.showInvalidUserRoleError();
        }
        return signInDetails;
    }
}
