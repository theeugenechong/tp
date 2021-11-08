package cooper.command;

import cooper.exceptions.AmountOutOfRangeException;
import cooper.exceptions.InvalidAccessException;
import cooper.exceptions.InvalidAssetException;
import cooper.exceptions.InvalidLiabilityException;
import cooper.finance.BalanceSheet;
import cooper.finance.CashFlow;
import cooper.storage.StorageManager;
import cooper.ui.FinanceUi;
import cooper.finance.FinanceManager;
import cooper.verification.SignInDetails;
import cooper.verification.UserRole;
import cooper.finance.FinanceCommand;
import cooper.resources.ResourcesManager;

//@@author ChrisLangton

/**
 * The child class of Command that handles the 'add' command specifically.
 */
public class AddCommand extends Command {

    public boolean isInflow;
    public int amount;
    public FinanceCommand financeFlag;
    private static final int AMOUNT_UPPER_LIMIT = 300_000_001;
    private static final int AMOUNT_LOWER_LIMIT = -300_000_001;

    public AddCommand(int amount, boolean isInflow, FinanceCommand financeFlag) {
        super();
        this.amount = amount;
        this.isInflow = isInflow;
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
                        StorageManager storageManager) throws InvalidAccessException, AmountOutOfRangeException,
            InvalidAssetException, InvalidLiabilityException {
        UserRole userRole = signInDetails.getUserRole();
        FinanceManager financeManager = resourcesManager.getFinanceManager(userRole);

        if (financeManager == null) {
            throw new InvalidAccessException();
        }

        if ((amount >= AMOUNT_UPPER_LIMIT) || (amount <= AMOUNT_LOWER_LIMIT)) {
            throw new AmountOutOfRangeException();
        }

        if (financeFlag == FinanceCommand.IDLE) {
            FinanceUi.showPleaseSpecifyFinancialStatementToAdd();
        }
      
        if (financeFlag == FinanceCommand.BS) {
            if (BalanceSheet.balanceSheetStage <= FinanceManager.endOfAssets) {
                if (!isInflow) {
                    throw new InvalidAssetException();
                }
            } else if (BalanceSheet.balanceSheetStage <= FinanceManager.endOfLiabilities) {
                if (isInflow) {
                    throw new InvalidLiabilityException();
                }
            }
            if (BalanceSheet.balanceSheetStage <= FinanceManager.endOfSE) {
                financeManager.addBalance(amount, isInflow, BalanceSheet.balanceSheetStage);
                storageManager.saveBalanceSheet(financeManager.cooperBalanceSheet);
                FinanceUi.printAddBalanceCommand(amount, isInflow, BalanceSheet.balanceSheetStage);
                BalanceSheet.balanceSheetStage++;
            } else {
                FinanceUi.showCannotAddToBalanceSheet();
            }
        } else if (financeFlag == FinanceCommand.CF) {
            if (CashFlow.cashFlowStage <= FinanceManager.freeCashFlow) {
                financeManager.addCashFlow(amount, isInflow, CashFlow.cashFlowStage);
                storageManager.saveCashFlowStatement(financeManager.cooperCashFlowStatement);
                FinanceUi.printAddCashFlowCommand(amount, isInflow, CashFlow.cashFlowStage);
                CashFlow.cashFlowStage++;
            } else {
                FinanceUi.showCannotAddToCashFlow();
            }
        }
    }
}


