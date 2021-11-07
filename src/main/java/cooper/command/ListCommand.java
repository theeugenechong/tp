package cooper.command;

import cooper.exceptions.EmptyFinancialStatementException;
import cooper.exceptions.InvalidAccessException;
import cooper.storage.StorageManager;
import cooper.ui.FinanceUi;
import cooper.finance.FinanceManager;
import cooper.finance.FinanceCommand;
import cooper.verification.SignInDetails;
import cooper.verification.UserRole;
import cooper.resources.ResourcesManager;

import java.util.ArrayList;

//@@author ChrisLangton
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
                        StorageManager storageManager) throws InvalidAccessException, EmptyFinancialStatementException {
        UserRole userRole = signInDetails.getUserRole();
        FinanceManager financeManager = resourcesManager.getFinanceManager(userRole);
        if (financeManager == null) {
            throw new InvalidAccessException();
        }

        if (financeFlag == FinanceCommand.IDLE) {
            FinanceUi.showPleaseSpecifyFinancialStatementToView();
        }

        ArrayList<Integer> balanceSheet = financeManager.cooperBalanceSheet.getBalanceSheet();
        boolean isEmptyBs = isEmptyFinancialStatement(balanceSheet);

        ArrayList<Integer> cashFlowStatement = financeManager.cooperCashFlowStatement.getCashFlowStatement();
        boolean isEmptyCf = isEmptyFinancialStatement(cashFlowStatement);

        if (financeFlag == FinanceCommand.BS) {
            if (isEmptyBs) {
                throw new EmptyFinancialStatementException();
            }
            FinanceManager.runTotalAmountsCheck(balanceSheet);
            FinanceUi.printBalanceSheet(balanceSheet);
        } else if (financeFlag == FinanceCommand.CF) {
            if (isEmptyCf) {
                throw new EmptyFinancialStatementException();
            }
            FinanceManager.runNetAmountsCheck(cashFlowStatement);
            FinanceUi.printCashFlowStatement(cashFlowStatement);
        }
    }
}


