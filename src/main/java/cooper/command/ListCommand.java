package cooper.command;

import cooper.exceptions.InvalidAccessException;
import cooper.exceptions.ListNotFoundException;
import cooper.finance.BalanceSheet;
import cooper.finance.CashFlow;

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
        if (financeManager == null || financeFlag == FinanceCommand.IDLE) {
            Ui.printAdminHelp();
            Ui.printGeneralHelp();
            throw new InvalidAccessException();
        }
        
        if (financeFlag == FinanceCommand.BS && financeManager.cooperBalanceSheet != null) {
            Ui.printBalanceSheet(financeManager.cooperBalanceSheet.getBalanceSheet());
        } else if (financeFlag == FinanceCommand.CF && financeManager.cooperCashFlowStatement != null) {
            Ui.printCashFlowStatement(financeManager.cooperCashFlowStatement.getCashFlowStatement());
        } else {
            Ui.showListNotFoundException();
        }
    }
}


