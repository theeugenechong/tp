package cooper.command;

import cooper.exceptions.InvalidAccessException;
import cooper.finance.CashFlow;
import cooper.meetings.MeetingManager;
import cooper.storage.StorageManager;
import cooper.ui.Ui;
import cooper.ui.FinanceUI;
import cooper.finance.FinanceManager;
import cooper.verification.SignInDetails;
import cooper.verification.UserRole;
import cooper.finance.FinanceCommand;


/**
 * The child class of Command that handles the 'add' command specifically.
 */
public class AddCommand extends Command {

    public boolean isInflow;
    public int amount;
    public int balanceSheetStage = 1;
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
     * @param financeManager access balance sheet
     * @param meetingManager access meetings
     * @param storageManager save to storage
     */
    @Override
    public void execute(SignInDetails signInDetails, FinanceManager financeManager, MeetingManager meetingManager,
                        StorageManager storageManager) throws InvalidAccessException {
        UserRole userRole = signInDetails.getUserRole();
        if (userRole.equals(UserRole.ADMIN)) {
            if (financeFlag == FinanceCommand.BS) {
                financeManager.addBalance(amount, isInflow);
                storageManager.saveBalanceSheet(financeManager.cooperBalanceSheet);
                Ui.printAddBalanceCommand(amount, isInflow);
                balanceSheetStage++;
            } else if (financeFlag == FinanceCommand.CF) {
                financeManager.addCashFlow(amount, isInflow, CashFlow.cashFlowStage);
                storageManager.saveCashFlowStatement(financeManager.cooperCashFlowStatement);
                Ui.printAddCashFlowCommand(amount, isInflow, CashFlow.cashFlowStage);
                CashFlow.cashFlowStage++;
            }
        } else {
            Ui.printEmployeeHelp();
            Ui.printGeneralHelp();
            throw new InvalidAccessException();
        }
    }
}


