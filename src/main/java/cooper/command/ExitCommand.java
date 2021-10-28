package cooper.command;

import cooper.storage.StorageManager;
import cooper.ui.Ui;
import cooper.verification.SignInDetails;
import cooper.resources.ResourcesManager;

//@@author theeugenechong

public class ExitCommand extends Command {

    @Override
    public void execute(SignInDetails signInDetails, ResourcesManager resourcesManager, StorageManager storageManager) {
        Ui.showBye();
        Ui.closeStreams();
        System.exit(0);
    }
}


