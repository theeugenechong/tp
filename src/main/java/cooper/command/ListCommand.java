package cooper.command;

import cooper.exceptions.InvalidAccessException;

import cooper.storage.StorageManager;
import cooper.ui.Ui;
import cooper.finance.FinanceManager;
import cooper.finance.FinanceCommand;
import cooper.verification.SignInDetails;
import cooper.verification.UserRole;
import cooper.resources.ResourcesManager;

/**
 * The child class of Command that handles the 'list' function specifically.
 */
public class ListCommand extends Command {

    public FinanceCommand financeFlag;

    public ListCommand(FinanceCommand financeFlag) {
        this.financeFlag = financeFlag;
    }

    /**
     * The override function for executing the 'add' command, calls for 'add' and subsequently
     * printing the status to the command line if and only if
     * the command is being accessed by an 'admin' level user.
     * @param signInDetails Sign in details of user to provide correct access
     * @param resourcesManager Provides access to manipulate data in the cOOPer's {@code FinanceManager},
     *                         {@code MeetingsManager} and {@code ForumManager}
     * @param storageManager Stores data which has just been added
     */
    @Override
    public void execute(SignInDetails signInDetails, ResourcesManager resourcesManager,
                        StorageManager storageManager) throws InvalidAccessException {
        UserRole userRole = signInDetails.getUserRole();
        FinanceManager financeManager = resourcesManager.getFinanceManager(userRole);
        if (financeManager == null) {
            Ui.printEmployeeHelp();
            Ui.printGeneralHelp();
            throw new InvalidAccessException();
        }
        
        if (financeFlag == FinanceCommand.BS) {
            Ui.printBalanceSheet(financeManager.cooperBalanceSheet.getBalanceSheet());
        } else if (financeFlag == FinanceCommand.CF) {
            Ui.printCashFlowStatement(financeManager.cooperCashFlowStatement.getCashFlowStatement());
        }
    }
}


