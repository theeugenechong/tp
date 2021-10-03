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

    public Verifier(HashMap<String, UserRole> registeredUsers, CommandParser commandParser) {
        this.registeredUsers = registeredUsers;
        this.commandParser = commandParser;
    }

    public void verify() {
        boolean canAccess = false;
        while (!canAccess) {
            String input = Ui.getInput();
            try {
                SignIn signIn = commandParser.parseLoginRegisterDetails(input);
                if (signIn.isRegisteredUser(registeredUsers)) {
                    if (signIn instanceof Login) {
                        canAccess = ((Login) signIn).hasCorrectRole(registeredUsers);
                    } else if (signIn instanceof Registration) {
                        ((Registration) signIn).askUserToLogin();
                    }
                } else {
                    if (signIn instanceof Login) {
                        ((Login) signIn).askUserToRegister();
                    } else if (signIn instanceof Registration) {
                        ((Registration) signIn).registerUser(registeredUsers);
                    }
                }
            } catch (UnrecognisedCommandException e) {
                Ui.showUnrecognisedCommandError();
            } catch (InvalidArgumentException | NoSuchElementException e) {
                Ui.showInvalidCommandArgumentError();
            } catch (InvalidUserRoleException e) {
                Ui.showInvalidUserRoleError();
            }
        }
    }
}
