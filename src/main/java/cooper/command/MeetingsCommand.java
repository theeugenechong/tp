package cooper.command;

import cooper.exceptions.InvalidAccessException;
import cooper.meetings.MeetingManager;
import cooper.ui.Ui;
import cooper.verification.SignInDetails;
import cooper.verification.UserRole;
import cooper.resources.ResourcesManager;

public class MeetingsCommand extends Command {

    @Override
    public void execute(SignInDetails signInDetails, 
            ResourcesManager resourcesManager) throws InvalidAccessException {
        UserRole userRole = signInDetails.getUserRole();
        MeetingManager meetingManager = resourcesManager.getMeetingManager(userRole);
        if (meetingManager != null) {
            Ui.printAvailabilities(meetingManager.getMeetings());
        } else {
            Ui.printEmployeeHelp();
            Ui.printGeneralHelp();
            Ui.printAdminHelp();
            throw new InvalidAccessException();
        }
    }
}


