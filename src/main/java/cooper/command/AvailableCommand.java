package cooper.command;

import cooper.exceptions.DuplicateUsernameException;
import cooper.finance.FinanceManager;
import cooper.meetings.MeetingManager;
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
    public void execute(SignInDetails signInDetails, FinanceManager financeManager, MeetingManager meetingManager) {
        try {
            meetingManager.addAvailability(time, username);
            Ui.printAvailableCommand(time, username);
        } catch (DuplicateUsernameException e) {
            Ui.showDuplicateUsernameException();
        }
    }
}



