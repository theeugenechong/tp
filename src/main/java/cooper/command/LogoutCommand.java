package cooper.command;

import cooper.exceptions.LogoutException;
import cooper.resources.ResourcesManager;
import cooper.verification.SignInDetails;

public class LogoutCommand extends Command {

    @Override
    public void execute(SignInDetails signInDetails, ResourcesManager resourcesManager) throws LogoutException {
        throw new LogoutException();
    }

}
