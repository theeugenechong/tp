package cooper.command;

import cooper.CooperState;
import cooper.exceptions.InvalidAccessException;
import cooper.finance.CashFlow;
import cooper.finance.FinanceManager;
import cooper.parser.CommandParser;
import cooper.resources.ResourcesManager;
import cooper.storage.StorageManager;
import cooper.ui.FinanceUi;
import cooper.verification.SignInDetails;
import cooper.verification.UserRole;

//@@author ChrisLangton

public class CfCommand extends Command {

    public CfCommand() {
        super();
    }

    @Override
    public void execute(SignInDetails signInDetails, ResourcesManager resourcesManager,
                        StorageManager storageManager) throws InvalidAccessException {
        UserRole userRole = signInDetails.getUserRole();
        FinanceManager financeManager = resourcesManager.getFinanceManager(userRole);

        if (financeManager == null) {
            throw new InvalidAccessException();
        }

        CommandParser.setCooperState(CooperState.CF);
        resetCashFlowStatement();
        FinanceUi.initiateCashFlowStatement();
    }

    private void resetCashFlowStatement() {
        CashFlow.cashFlowStage = 0;
        FinanceManager.netOA = 0;
        FinanceManager.netIA = 0;
        FinanceManager.netFA = 0;
    }
}
