package cooper.command;

import cooper.storage.StorageManager;
import cooper.ui.Ui;
import cooper.verification.SignInDetails;
import cooper.resources.ResourcesManager;

//@@author theeugenechong

/**
 * Exits the program upon execution.
 */
public class ExitCommand extends Command {

    protected static final int SUCCESS = 0;

    @Override
    public void execute(SignInDetails signInDetails, ResourcesManager resourcesManager, StorageManager storageManager) {
        Ui.showBye();
        Ui.closeStreams();
        System.exit(SUCCESS);
    }
}


