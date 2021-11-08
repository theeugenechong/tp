package cooper.command;

import cooper.exceptions.InvalidAccessException;
import cooper.meetings.MeetingManager;
import cooper.ui.MeetingsUi;
import cooper.storage.StorageManager;
import cooper.verification.SignInDetails;
import cooper.resources.ResourcesManager;
import cooper.verification.UserRole;

//@@author fansxx

public class MeetingsCommand extends Command {

    /**
     * The override function for executing the 'meetings' command, which all users have access to.
     *
     * @param signInDetails Sign in details of user to provide correct access
     * @param resourcesManager Provides access to manipulate data in the cOOPer's {@code FinanceManager},
     *                         {@code MeetingsManager} and {@code ForumManager}
     * @param storageManager Stores data which has just been added
     */
    @Override        
    public void execute(SignInDetails signInDetails, ResourcesManager resourcesManager,
                        StorageManager storageManager) throws InvalidAccessException {
        String username = signInDetails.getUsername();
        MeetingManager meetingManager = resourcesManager.getMeetingManager();
        MeetingsUi.printMeetings(meetingManager.getUserSpecificMeetings(username));
    }
}
