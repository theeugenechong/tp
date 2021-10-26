package cooper.command;

import cooper.finance.FinanceManager;
import cooper.forum.ForumManager;
import cooper.storage.StorageManager;
import cooper.ui.Ui;
import cooper.verification.SignInDetails;
import cooper.resources.ResourcesManager;

public class ExitCommand extends Command {

    @Override
    public void execute(SignInDetails signInDetails, ResourcesManager resourcesManager, StorageManager storageManager) {
        FinanceManager financeManager = resourcesManager.getFinanceManager();
        ForumManager forumManager = resourcesManager.getForumManager();
        storageManager.saveForum(forumManager);

        saveBsAndCfDataAccordingly(storageManager, financeManager);
        Ui.showBye();
        Ui.closeStreams();
        System.exit(0);
    }

    @SuppressWarnings("ConstantConditions")
    private void saveBsAndCfDataAccordingly(StorageManager storageManager, FinanceManager financeManager) {
        boolean isEmptyBs = isEmptyFinancialStatement(financeManager.cooperBalanceSheet.getBalanceSheet());
        boolean isEmptyCf = isEmptyFinancialStatement(financeManager.cooperCashFlowStatement.getCashFlowStatement());

        if (!isEmptyBs && !isEmptyCf) {
            /* Only when both have numbers added, it is saved to the storage file */
            storageManager.saveBalanceSheet(financeManager.cooperBalanceSheet);
            storageManager.saveCashFlowStatement(financeManager.cooperCashFlowStatement);
        } else if (isEmptyBs && !isEmptyCf) {
            /* Cash flow statement has numbers added, so it is saved */
            storageManager.saveCashFlowStatement(financeManager.cooperCashFlowStatement);
        } else if (isEmptyCf && !isEmptyBs) {
            /* Balance sheet has numbers added so it is saved */
            storageManager.saveBalanceSheet(financeManager.cooperBalanceSheet);
        }
    }
}


