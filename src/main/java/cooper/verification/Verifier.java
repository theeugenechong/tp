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

    public Verifier(HashMap<String, UserRole> registeredUsers, CommandParser commandParser) {
        this.registeredUsers = registeredUsers;
        this.commandParser = commandParser;
        this.isSuccessfullySignedIn = false;
    }

    public void setSuccessfullySignedIn(boolean successfullySignedIn) {
        this.isSuccessfullySignedIn = successfullySignedIn;
    }

    public boolean isSuccessfullySignedIn() {
        return this.isSuccessfullySignedIn;
    }

    public UserRole verify(String input) {
        UserRole userRole = null;
        try {
            SignInProtocol signInProtocol = commandParser.parseLoginRegisterDetails(input);
            signInProtocol.executeSignIn(this, registeredUsers);
            userRole = signInProtocol.signInDetails.getUserRole();
        } catch (UnrecognisedCommandException e) {
            Ui.showUnrecognisedCommandError();
        } catch (InvalidArgumentException | NoSuchElementException e) {
            Ui.showInvalidCommandArgumentError();
        } catch (InvalidUserRoleException e) {
            Ui.showInvalidUserRoleError();
        }
        return userRole;
    }
}
