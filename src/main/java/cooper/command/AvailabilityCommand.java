package cooper.command;

import cooper.exceptions.InvalidAccessException;
import cooper.meetings.MeetingManager;
import cooper.resources.ResourcesManager;
import cooper.storage.StorageManager;
import cooper.ui.MeetingsUi;
import cooper.verification.SignInDetails;
import cooper.verification.UserRole;

//@@author fansxx

public class AvailabilityCommand extends Command {

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
    public void execute(SignInDetails signInDetails, ResourcesManager resourcesManager, 
                        StorageManager storageManager) throws InvalidAccessException {
        MeetingManager meetingManager = resourcesManager.getMeetingManager();
        MeetingsUi.printAvailabilities(meetingManager.getAvailability());
    }
}
