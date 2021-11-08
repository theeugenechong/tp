package cooper.command;

import cooper.exceptions.InvalidAccessException;
import cooper.exceptions.InvalidTimeException;
import cooper.exceptions.DuplicateUsernameException;
import cooper.exceptions.InvalidDateTimeFormatException;
import cooper.meetings.MeetingManager;
import cooper.storage.StorageManager;
import cooper.ui.MeetingsUi;
import cooper.verification.SignInDetails;
import cooper.resources.ResourcesManager;
import cooper.verification.UserRole;

//@@author fansxx

public class AvailableCommand extends Command {
    private final String dateTime;

    public AvailableCommand(String time) {
        super();
        this.dateTime = time;
    }

    /**
     * The override function for executing the 'available' command, which all users have access to.
     *
     * @param signInDetails Sign in details of user to provide correct access
     * @param resourcesManager Provides access to manipulate data in the cOOPer's {@code FinanceManager},
     *                         {@code MeetingsManager} and {@code ForumManager}
     * @param storageManager Stores data which has just been added
     */
    @Override
    public void execute(SignInDetails signInDetails, ResourcesManager resourcesManager, StorageManager storageManager)
            throws InvalidAccessException {
        String username = signInDetails.getUsername();
        MeetingManager meetingManager = resourcesManager.getMeetingManager();

        try {
            meetingManager.addAvailability(dateTime, username);
            storageManager.saveAvailability(meetingManager);
            MeetingsUi.printAvailableCommand(dateTime, username);
        } catch (InvalidDateTimeFormatException e1) {
            MeetingsUi.showInvalidDateTimeFormatException();
        } catch (InvalidTimeException e2) {
            MeetingsUi.showInvalidTimeException();
        } catch (DuplicateUsernameException e3) {
            MeetingsUi.showDuplicateUsernameException();
        }
    }
}



