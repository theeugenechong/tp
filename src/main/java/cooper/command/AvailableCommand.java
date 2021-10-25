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

    public AvailableCommand(String time) {
        super();
        this.time = time;
    }

    @Override
    public void execute(SignInDetails signInDetails, ResourcesManager resourcesManager) throws InvalidAccessException {
        String username = signInDetails.getUsername();
        UserRole userRole = signInDetails.getUserRole();
        MeetingManager meetingManager = resourcesManager.getMeetingManager(userRole);
        StorageManager storageManager = resourcesManager.getStorageManager();
        if (meetingManager != null) {
            try {
                meetingManager.addAvailability(time, username);
                storageManager.saveAvailability(meetingManager);
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



