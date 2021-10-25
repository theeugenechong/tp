package cooper.command;

import cooper.exceptions.CannotScheduleMeetingException;
import cooper.exceptions.DuplicateMeetingException;
import cooper.exceptions.InvalidAccessException;
import cooper.exceptions.InvalidTimeException;
import cooper.meetings.MeetingManager;
import cooper.resources.ResourcesManager;
import cooper.storage.StorageManager;
import cooper.ui.Ui;
import cooper.verification.SignInDetails;
import cooper.verification.UserRole;

import java.util.ArrayList;

public class ScheduleCommand extends Command {
    private final String meetingName;
    private final String time;
    private final ArrayList<String> usernames;

    public ScheduleCommand(String meetingName, ArrayList<String> usernames, String time) {
        this.meetingName = meetingName;
        this.usernames = usernames;
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
    public void execute(SignInDetails signInDetails, ResourcesManager resourcesManager,
                        StorageManager storageManager) throws InvalidAccessException {
        UserRole userRole = signInDetails.getUserRole();
        MeetingManager meetingManager = resourcesManager.getMeetingManager(userRole);

        if (userRole.equals(UserRole.ADMIN)) {
            // if time field is not entered, proceed to auto schedule a meeting at the earliest time
            if (time == null) {
                try {
                    meetingManager.autoScheduleMeeting(meetingName, usernames);
                    storageManager.saveMeetings(meetingManager);
                } catch (CannotScheduleMeetingException e1) {
                    Ui.showCannotScheduleMeetingException();
                } catch (DuplicateMeetingException e2) {
                    Ui.showDuplicateMeetingException();
                }
            } else {
                try {
                    meetingManager.manualScheduleMeeting(meetingName, usernames, time);
                    storageManager.saveMeetings(meetingManager);
                } catch (InvalidTimeException e1) {
                    Ui.showInvalidTimeException();
                } catch (CannotScheduleMeetingException e2) {
                    Ui.showCannotScheduleMeetingException();
                } catch (DuplicateMeetingException e3) {
                    Ui.showDuplicateMeetingException();
                }
            }
        } else {
            Ui.printEmployeeHelp();
            Ui.printGeneralHelp();
            throw new InvalidAccessException();
        }
    }
}
