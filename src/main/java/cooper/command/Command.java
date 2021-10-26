package cooper.command;

import cooper.exceptions.EmptyFinancialStatementException;
import cooper.exceptions.InvalidAccessException;
import cooper.exceptions.LogoutException;
import cooper.storage.StorageManager;
import cooper.verification.SignInDetails;
import cooper.resources.ResourcesManager;

public abstract class Command {

    /**
     * Executes the command specified.
     */
    public abstract void execute(SignInDetails signInDetails, ResourcesManager resourcesManager,
                                 StorageManager storageManager) throws InvalidAccessException, LogoutException, EmptyFinancialStatementException;
}
