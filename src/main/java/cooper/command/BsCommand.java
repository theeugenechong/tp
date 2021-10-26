package cooper.command;

import cooper.exceptions.InvalidAccessException;
import cooper.finance.BalanceSheet;
import cooper.finance.FinanceCommand;
import cooper.finance.FinanceManager;
import cooper.parser.CommandParser;
import cooper.resources.ResourcesManager;
import cooper.ui.Ui;
import cooper.verification.SignInDetails;
import cooper.verification.UserRole;

public class BsCommand extends Command {

    public BsCommand() {
        super();
    }

    @Override
    public void execute(SignInDetails signInDetails, ResourcesManager resourcesManager,
                        StorageManager storageManager) throws InvalidAccessException {
        CommandParser.financeFlag = FinanceCommand.BS;
        UserRole userRole = signInDetails.getUserRole();
        FinanceManager financeManager = resourcesManager.getFinanceManager(userRole);
        if (financeManager == null) {
            Ui.printAdminHelp();
            Ui.printGeneralHelp();
            throw new InvalidAccessException();
        }
        resetBalanceSheet(financeManager);
        Ui.initiateBalanceSheet();
    }

    private void resetBalanceSheet(FinanceManager financeManager) {
        BalanceSheet balanceSheet = financeManager.cooperBalanceSheet;
        BalanceSheet.balanceSheetStage = 0;
        balanceSheet.getBalanceSheet().clear();
    }
}
