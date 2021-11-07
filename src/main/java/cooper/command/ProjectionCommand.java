package cooper.command;

import cooper.exceptions.EmptyFinancialStatementException;
import cooper.exceptions.InvalidAccessException;
import cooper.exceptions.InvalidProjectionException;
import cooper.finance.FinanceManager;
import cooper.resources.ResourcesManager;
import cooper.storage.StorageManager;
import cooper.ui.FinanceUi;
import cooper.verification.SignInDetails;
import cooper.verification.UserRole;

import java.util.ArrayList;

//@@author ChrisLangton

public class ProjectionCommand extends Command {

    public int years;
    public int oldIndex = 9;

    public ProjectionCommand(int years) {
        super();
        this.years = years;
    }

    @Override
    public void execute(SignInDetails signInDetails, ResourcesManager resourcesManager,
                        StorageManager storageManager) throws InvalidAccessException, EmptyFinancialStatementException,
            InvalidProjectionException {
        UserRole userRole = signInDetails.getUserRole();
        FinanceManager financeManager = resourcesManager.getFinanceManager(userRole);
        if (financeManager == null) {
            throw new InvalidAccessException();
        }

        ArrayList<Integer> cashFlowStatement = financeManager.cooperCashFlowStatement.getCashFlowStatement();

        boolean isEmptyCf = isEmptyFinancialStatement(cashFlowStatement);
        if (isEmptyCf) {
            throw new EmptyFinancialStatementException();
        }

        FinanceManager.runNetAmountsCheck(cashFlowStatement);

        int newFreeCF = financeManager.calculateFreeCashFlow(cashFlowStatement);
        int oldFreeCF = cashFlowStatement.get(oldIndex);
        int differenceFreeCF = newFreeCF - oldFreeCF;

        double rateOfGrowth = (differenceFreeCF / (double) oldFreeCF) * 100.0;
        double finalGrowthValue = financeManager.createProjection(newFreeCF, rateOfGrowth, years);
        FinanceUi.printProjections(finalGrowthValue, financeManager.cooperProjection.getProjection());

        financeManager.cooperProjection.getProjection().clear();
    }
}
