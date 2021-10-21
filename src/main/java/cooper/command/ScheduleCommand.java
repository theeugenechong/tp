package cooper.command;

import cooper.exceptions.CannotScheduleMeetingException;
import cooper.exceptions.DuplicateMeetingException;
import cooper.exceptions.InvalidTimeException;
import cooper.finance.FinanceManager;
import cooper.meetings.MeetingManager;
import cooper.storage.StorageManager;
import cooper.ui.Ui;
import cooper.verification.SignInDetails;

import java.util.ArrayList;

public class ScheduleCommand extends Command {
    private String time = null;
    private final ArrayList<String> usernames;

    public ScheduleCommand(ArrayList<String> usernamesAndTime) {
        /**
        if (usernamesAndTime.contains("at")) {
            this.time = usernamesAndTime.get(usernamesAndTime.size() - 1);
            usernamesAndTime.remove(usernamesAndTime.size() - 1);
            usernamesAndTime.remove(usernamesAndTime.size() - 1);
        }
         **/
        this.usernames = usernamesAndTime;
    }

    @Override
    public void execute(SignInDetails signInDetails, FinanceManager financeManager, MeetingManager meetingManager, StorageManager storageManager) {
        if (time == null) {
            try {
                meetingManager.autoScheduleMeeting(usernames);
            } catch (CannotScheduleMeetingException e1) {
                Ui.showCannotScheduleMeetingException();
            } catch (DuplicateMeetingException e2) {
                Ui.showDuplicateMeetingException();
            }
        } else {
            try {
                meetingManager.manualScheduleMeeting(usernames, time);
            } catch (InvalidTimeException e1) {
                Ui.showInvalidTimeException();
            } catch (CannotScheduleMeetingException e2) {
                Ui.showCannotScheduleMeetingException();
            }
        }
    }
}
