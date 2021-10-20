package cooper.command;

import cooper.exceptions.DuplicateUsernameException;
import cooper.exceptions.InvalidTimeException;
import cooper.finance.FinanceManager;
import cooper.meetings.MeetingManager;
import cooper.storage.StorageManager;
import cooper.ui.Ui;
import cooper.verification.SignInDetails;

public class AvailableCommand extends Command {
    private final String time;
    private final String username;

    public AvailableCommand(String time, String username) {
        super();
        this.time = time;
        this.username = username;
    }

    @Override
    public void execute(SignInDetails signInDetails, FinanceManager financeManager, MeetingManager meetingManager,
                        StorageManager storageManager) {
        try {
            meetingManager.addAvailability(time, username);
            storageManager.saveMeetings(meetingManager);
            Ui.printAvailableCommand(time, username);
        } catch (InvalidTimeException e1) {
            Ui.showInvalidTimeException();
        } catch (DuplicateUsernameException e2) {
            Ui.showDuplicateUsernameException();
        }
    }
}



