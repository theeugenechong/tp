package cooper.command;

import cooper.exceptions.AmountOutOfRangeException;
import cooper.exceptions.EmptyFinancialStatementException;
import cooper.exceptions.InvalidAccessException;
import cooper.exceptions.InvalidAssetException;
import cooper.exceptions.InvalidLiabilityException;
import cooper.exceptions.InvalidProjectionException;
import cooper.storage.StorageManager;
import cooper.verification.SignInDetails;
import cooper.resources.ResourcesManager;

import java.util.ArrayList;

//@@author Rrraaaeee

public abstract class Command {

    /**
     * Executes the command specified.
     */
    public abstract void execute(SignInDetails signInDetails, ResourcesManager resourcesManager,
                                 StorageManager storageManager) throws
            InvalidAccessException, EmptyFinancialStatementException, InvalidProjectionException,
            AmountOutOfRangeException, InvalidAssetException, InvalidLiabilityException;

    //@@author ChrisLangton
    protected boolean isEmptyFinancialStatement(ArrayList<Integer> financialStatement) {
        return financialStatement.stream().allMatch(i -> i == 0);
    }
}
