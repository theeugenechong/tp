package cooper.command;

import cooper.CooperState;
import cooper.exceptions.InvalidAccessException;
import cooper.finance.BalanceSheet;
import cooper.finance.FinanceManager;
import cooper.parser.CommandParser;
import cooper.resources.ResourcesManager;
import cooper.storage.StorageManager;
import cooper.ui.FinanceUi;
import cooper.verification.SignInDetails;
import cooper.verification.UserRole;

//@@author ChrisLangton

/**
 * The command to initiate the balance sheet function.
 */
public class BsCommand extends Command {

    public BsCommand() {
        super();
    }

    /**
     * The override function to reset and initiate the balance sheet state.
     * @param signInDetails the sign in details
     * @param resourcesManager the resources manager
     * @param storageManager the storage manager
     * @throws InvalidAccessException the exception
     */
    @Override
    public void execute(SignInDetails signInDetails, ResourcesManager resourcesManager,
                        StorageManager storageManager) throws InvalidAccessException {
        UserRole userRole = signInDetails.getUserRole();
        FinanceManager financeManager = resourcesManager.getFinanceManager(userRole);

        if (financeManager == null) {
            throw new InvalidAccessException();
        }

        CommandParser.setCooperState(CooperState.BS);
        resetBalanceSheet();
        FinanceUi.initiateBalanceSheet();
    }

    /**
     * Resets the balance sheet stage and values.
     */
    private void resetBalanceSheet() {
        BalanceSheet.balanceSheetStage = 0;
        FinanceManager.netAssets = 0;
        FinanceManager.netLiabilities = 0;
        FinanceManager.netSE = 0;
    }
}
