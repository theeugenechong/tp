package cooper.command;

import cooper.exceptions.InvalidAccessException;
import cooper.exceptions.InvalidTimeException;
import cooper.exceptions.DuplicateUsernameException;
import cooper.exceptions.InvalidDateTimeFormatException;
import cooper.meetings.MeetingManager;
import cooper.storage.StorageManager;
import cooper.ui.MeetingsUi;
import cooper.ui.Ui;
import cooper.verification.SignInDetails;
import cooper.verification.UserRole;
import cooper.resources.ResourcesManager;

//@@author fansxx

public class AvailableCommand extends Command {
    private final String dateTime;

    public AvailableCommand(String time) {
        super();
        this.dateTime = time;
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
    public void execute(SignInDetails signInDetails, ResourcesManager resourcesManager, StorageManager storageManager)
            throws InvalidAccessException {
        String username = signInDetails.getUsername();
        UserRole userRole = signInDetails.getUserRole();
        MeetingManager meetingManager = resourcesManager.getMeetingManager(userRole);

        if (meetingManager == null) {
            Ui.printEmployeeHelp();
            Ui.printGeneralHelp();
            Ui.printAdminHelp();
            throw new InvalidAccessException();
        } 


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



