package cooper.command;

import cooper.exceptions.EmptyFinancialStatementException;
import cooper.exceptions.InvalidAccessException;
import cooper.finance.FinanceManager;
import cooper.resources.ResourcesManager;
import cooper.storage.StorageManager;
import cooper.verification.SignInDetails;
import cooper.verification.UserRole;

//@@author theeugenechong

/**
 * Generates cOOPer's financial statement, either the balance sheet or cash flow statement in the form of a PDF using
 * LaTeX. Creates a backup text file in the event that there is no internet connection to perform the online
 * compilation of the LaTeX file.
 */
public class GenerateCommand extends Command {

    private final String documentToGenerate;

    private static final String BS = "bs";
    private static final String CF = "cf";

    public GenerateCommand(String documentToGenerate) {
        this.documentToGenerate = documentToGenerate;
    }

    /**
     * The override function for executing the 'generate' command, which is executed if the {@code UserRole} of
     * {@code signInDetails} is admin. Generates the financial statement specified in the form of a PDF.
     *
     * @param signInDetails Sign in details of user to provide correct access
     */
    @Override
    public void execute(SignInDetails signInDetails, ResourcesManager resourcesManager, StorageManager storageManager)
            throws InvalidAccessException, EmptyFinancialStatementException {
        UserRole userRole = signInDetails.getUserRole();
        FinanceManager financeManager = resourcesManager.getFinanceManager(userRole);

        if (financeManager == null) {
            throw new InvalidAccessException();
        }

        boolean isEmptyBs = isEmptyFinancialStatement(financeManager.cooperBalanceSheet.getBalanceSheet());
        boolean isEmptyCf = isEmptyFinancialStatement(financeManager.cooperCashFlowStatement.getCashFlowStatement());

        if (documentToGenerate.equals(BS)) {
            if (isEmptyBs) {
                throw new EmptyFinancialStatementException();
            }
            financeManager.generateBalanceSheetAsPdf();
        } else if (documentToGenerate.equals(CF)) {
            if (isEmptyCf) {
                throw new EmptyFinancialStatementException();
            }
            financeManager.generateCashFlowStatementAsPdf();
        }
    }
}
