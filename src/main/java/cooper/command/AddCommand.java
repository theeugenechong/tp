package cooper.command;

import cooper.exceptions.InvalidAccessException;
import cooper.finance.BalanceSheet;
import cooper.finance.CashFlow;
import cooper.meetings.MeetingManager;
import cooper.storage.StorageManager;
import cooper.ui.Ui;
import cooper.ui.FinanceUI;
import cooper.finance.FinanceManager;
import cooper.verification.SignInDetails;
import cooper.verification.UserRole;
import cooper.finance.FinanceCommand;
import cooper.resources.ResourcesManager;

/**
 * The child class of Command that handles the 'add' command specifically.
 */
public class AddCommand extends Command {

    public boolean isInflow;
    public int amount;
    public FinanceCommand financeFlag;

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
     * @param signInDetails access role
     * @param resourcesManager handles all manager classes and their access rights
     */
    @Override
    public void execute(SignInDetails signInDetails, ResourcesManager resourcesManager) throws InvalidAccessException {
        UserRole userRole = signInDetails.getUserRole();
        FinanceManager financeManager = resourcesManager.getFinanceManager(userRole);
        StorageManager storageManager = resourcesManager.getStorageManager();
        if (financeManager == null || financeFlag == FinanceCommand.IDLE) {
            Ui.printAdminHelp();
            Ui.printGeneralHelp();
            throw new InvalidAccessException();
        }
      
        if (financeFlag == FinanceCommand.BS) {
            financeManager.addBalance(amount, isInflow, BalanceSheet.balanceSheetStage);
            storageManager.saveBalanceSheet(financeManager.cooperBalanceSheet);
            Ui.printAddBalanceCommand(amount, isInflow, BalanceSheet.balanceSheetStage);
            BalanceSheet.balanceSheetStage++;
        } else if (financeFlag == FinanceCommand.CF) {
            financeManager.addCashFlow(amount, isInflow, CashFlow.cashFlowStage);
            storageManager.saveCashFlowStatement(financeManager.cooperCashFlowStatement);
            Ui.printAddCashFlowCommand(amount, isInflow, CashFlow.cashFlowStage);
            CashFlow.cashFlowStage++;
        }
    }
}


