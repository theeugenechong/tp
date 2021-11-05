package cooper.command;

import cooper.storage.StorageManager;
import cooper.ui.Ui;
import cooper.verification.SignInDetails;
import cooper.verification.UserRole;
import cooper.resources.ResourcesManager;

//@@author theeugenechong
/**
 * Prints help for the user according to their role.
 */
public class HelpCommand extends Command {

    /**
     * Prints a list of commands along with the formats of the command for the user according to their role in
     * {@code signInDetails}.
     * @param signInDetails Sign in details of user to print out the correct list of commands.
     */
    @Override
    public void execute(SignInDetails signInDetails, ResourcesManager resourcesManager, StorageManager storageManager) {
        UserRole userRole = signInDetails.getUserRole();
        if (userRole.equals(UserRole.ADMIN)) {
            Ui.printAdminHelp();
        } else if (userRole.equals(UserRole.EMPLOYEE)) {
            Ui.printEmployeeHelp();
        }
        Ui.printGeneralHelp();
    }
}


