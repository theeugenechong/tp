package cooper.command;

import cooper.exceptions.LogoutException;
import cooper.resources.ResourcesManager;
import cooper.storage.StorageManager;
import cooper.verification.SignInDetails;

//@@author Rrraaaeee

public class LogoutCommand extends Command {

    @Override
    public void execute(SignInDetails signInDetails, ResourcesManager resourcesManager,
                        StorageManager storageManager) {
    }

}
