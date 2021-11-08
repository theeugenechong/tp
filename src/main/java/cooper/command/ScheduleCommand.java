package cooper.command;

import cooper.exceptions.InvalidTimeException;
import cooper.exceptions.InvalidDateTimeFormatException;
import cooper.exceptions.DuplicateMeetingException;
import cooper.exceptions.CannotScheduleMeetingException;
import cooper.exceptions.InvalidAccessException;
import cooper.exceptions.TimeNotInAvailabilityException;
import cooper.meetings.MeetingManager;
import cooper.resources.ResourcesManager;
import cooper.storage.StorageManager;
import cooper.ui.MeetingsUi;
import cooper.verification.SignInDetails;
import cooper.verification.UserRole;

import java.util.ArrayList;

//@@author fansxx

public class ScheduleCommand extends Command {
    private final String meetingName;
    private final String dateTime;
    private final ArrayList<String> usernames;

    public ScheduleCommand(String meetingName, ArrayList<String> usernames, String dateTime) {
        this.meetingName = meetingName;
        this.usernames = usernames;
        this.dateTime = dateTime;
    }

    /**
     * The override function for executing the 'schedule' command, calls for 'schedule' and subsequently
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
        String username = signInDetails.getUsername();
        UserRole userRole = signInDetails.getUserRole();
        MeetingManager meetingManager = resourcesManager.getMeetingManager(userRole);
        if (meetingManager == null) {
            throw new InvalidAccessException();
        }

        // if time field is not entered, proceed to auto schedule a meeting at the earliest time
        if (dateTime == null) {
            try {
                usernames.add(username);
                meetingManager.autoScheduleMeeting(meetingName, usernames);
                storageManager.saveMeetings(meetingManager);
            } catch (CannotScheduleMeetingException e1) {
                MeetingsUi.showCannotScheduleMeetingException();
            }
        } else {
            try {
                usernames.add(username);
                meetingManager.manualScheduleMeeting(meetingName, usernames, dateTime);
                storageManager.saveMeetings(meetingManager);
            } catch (InvalidDateTimeFormatException e1) {
                MeetingsUi.showInvalidDateTimeFormatException();
            } catch (InvalidTimeException e2) {
                MeetingsUi.showInvalidTimeException();
            } catch (CannotScheduleMeetingException e3) {
                MeetingsUi.showCannotScheduleMeetingException();
            } catch (DuplicateMeetingException e4) {
                MeetingsUi.showDuplicateMeetingException();
            } catch (TimeNotInAvailabilityException e5) {
                MeetingsUi.showTimeNotInAvailabilityException();
            }
        }
    }
}
