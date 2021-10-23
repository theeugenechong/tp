package cooper.command;

import cooper.exceptions.InvalidAccessException;
import cooper.storage.StorageManager;
import cooper.ui.Ui;
import cooper.finance.FinanceManager;
import cooper.verification.SignInDetails;
import cooper.verification.UserRole;
import cooper.resources.ResourcesManager;

/**
 * The child class of Command that handles the 'list' function specifically.
 */
public class ListCommand extends Command {


    /**
     * The override function for executing the 'list' command. Prints the balance sheet
     * to the command line if and only if
     * the command is being accessed by an 'admin' level user.
     * @param signInDetails access role
     * @param financeManager access balance sheet
     * @param meetingManager access meetings
     * @param storageManager save to storage
     */
    @Override
    public void execute(SignInDetails signInDetails, 
            ResourcesManager resourcesManager, StorageManager storageManager) throws InvalidAccessException {
        UserRole userRole = signInDetails.getUserRole();
        FinanceManager financeManager = resourcesManager.getFinanceManager(userRole);
        if (financeManager != null) {
            if (userRole.equals(UserRole.ADMIN)) {
                Ui.printBalanceSheet(financeManager.getBalanceSheet());
            } else {
                Ui.printEmployeeHelp();
                Ui.printGeneralHelp();
                throw new InvalidAccessException();
            }
        } else {
            Ui.printAdminHelp();
            Ui.printGeneralHelp();
            throw new InvalidAccessException();
        }
    }
}


