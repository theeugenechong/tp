package cooper.command;

import cooper.exceptions.InvalidAccessException;
import cooper.exceptions.InvalidTimeException;
import cooper.exceptions.DuplicateUsernameException;
import cooper.meetings.MeetingManager;
import cooper.storage.StorageManager;
import cooper.ui.Ui;
import cooper.verification.SignInDetails;
import cooper.verification.UserRole;
import cooper.resources.ResourcesManager;

public class AvailableCommand extends Command {
    private final String time;
    private final String username;

    public AvailableCommand(String time, String username) {
        super();
        this.time = time;
        this.username = username;
    }

    @Override
    public void execute(SignInDetails signInDetails, 
            ResourcesManager resourcesManager, StorageManager storageManager) throws InvalidAccessException {
        UserRole userRole = signInDetails.getUserRole();
        MeetingManager meetingManager = resourcesManager.getMeetingManager(userRole);
        if (meetingManager != null) {
            try {
                meetingManager.addAvailability(time, username);
                storageManager.saveMeetings(meetingManager);
                Ui.printAvailableCommand(time, username);
            } catch (InvalidTimeException e1) {
                Ui.showInvalidTimeException();
            } catch (DuplicateUsernameException e2) {
                Ui.showDuplicateUsernameException();
            }
        } else {
            Ui.printEmployeeHelp();
            Ui.printGeneralHelp();
            Ui.printAdminHelp();
            throw new InvalidAccessException();
        }
    }
}



