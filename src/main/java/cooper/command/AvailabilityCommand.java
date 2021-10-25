package cooper.command;

import cooper.resources.ResourcesManager;
import cooper.ui.MeetingsUi;
import cooper.verification.SignInDetails;
import cooper.verification.UserRole;

public class AvailabilityCommand extends Command {

    @Override
    public void execute(SignInDetails signInDetails, ResourcesManager resourcesManager) {
        UserRole userRole = signInDetails.getUserRole();
        MeetingsUi.printAvailabilities(resourcesManager.getMeetingManager(userRole).getAvailability());
    }
}
