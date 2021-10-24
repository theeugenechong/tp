package cooper.command;

import cooper.exceptions.InvalidAccessException;
import cooper.finance.BalanceSheet;
import cooper.finance.CashFlow;
import cooper.meetings.MeetingManager;
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
     * The override function for executing the 'list' command. Prints the balance sheet
     * to the command line if and only if
     * the command is being accessed by an 'admin' level user.
     * @param signInDetails access role
     * @param resourcesManager handles all manager classes and their access rights
     */
    @Override
    public void execute(SignInDetails signInDetails, ResourcesManager resourcesManager) throws InvalidAccessException {
        UserRole userRole = signInDetails.getUserRole();
        FinanceManager financeManager = resourcesManager.getFinanceManager(userRole);
        if (financeManager == null) {
            Ui.printEmployeeHelp();
            Ui.printGeneralHelp();
            throw new InvalidAccessException();
        }
        
        if (financeFlag == FinanceCommand.BS) {
            Ui.printBalanceSheet(BalanceSheet.getBalanceSheet());
        } else if (financeFlag == FinanceCommand.CF) {
            Ui.printCashFlowStatement(CashFlow.getCashFlowStatement());
        }
    }
}


