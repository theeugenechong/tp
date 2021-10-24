package cooper.command;

import cooper.ui.Ui;
import cooper.verification.SignInDetails;
import cooper.resources.ResourcesManager;

public class ExitCommand extends Command {

    @Override
    public void execute(SignInDetails signInDetails, 
            ResourcesManager resourcesManager) {
        Ui.showBye();
        Ui.closeStreams();
        System.exit(0);
    }
}


