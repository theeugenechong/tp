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

    /**
     * The override function for executing the 'add' command, calls for 'add' and subsequently
     * printing the status to the command line if and only if
     * the command is being accessed by an 'admin' level user.
     * @param signInDetails Sign in details of user to provide correct access
     * @param resourcesManager Provides access to manipulate data in the cOOPer's {@code FinanceManager},
     *                         {@code MeetingsManager} and {@code ForumManager}
     * @param storageManager Stores data which has just been added
     */
    @Override
    public void execute(SignInDetails signInDetails, ResourcesManager resourcesManager, StorageManager storageManager) throws InvalidAccessException {
        String username = signInDetails.getUsername();
        UserRole userRole = signInDetails.getUserRole();
        MeetingManager meetingManager = resourcesManager.getMeetingManager(userRole);
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



