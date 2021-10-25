package cooper.command;

import cooper.exceptions.InvalidAccessException;
import cooper.meetings.MeetingManager;
import cooper.ui.MeetingsUi;
import cooper.ui.Ui;
import cooper.verification.SignInDetails;
import cooper.verification.UserRole;
import cooper.resources.ResourcesManager;

public class MeetingsCommand extends Command {

    @Override
    public void execute(SignInDetails signInDetails, ResourcesManager resourcesManager) throws InvalidAccessException {
        String username = signInDetails.getUsername();
        UserRole userRole = signInDetails.getUserRole();
        MeetingManager meetingManager = resourcesManager.getMeetingManager(userRole);
        if (meetingManager == null) {
            Ui.printEmployeeHelp();
            Ui.printGeneralHelp();
            Ui.printAdminHelp();
            throw new InvalidAccessException();
        } else {
            MeetingsUi.printMeetings(meetingManager.getUserSpecificMeetings(username));
        }
    }
}
