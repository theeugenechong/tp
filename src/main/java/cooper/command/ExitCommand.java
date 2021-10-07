package cooper.command;

import cooper.finance.FinanceManager;
import cooper.meetings.MeetingManager;
import cooper.ui.Ui;
import cooper.verification.SignInDetails;

public class ExitCommand extends Command {

    @Override
    public void execute(SignInDetails signInDetails, FinanceManager financeManager, MeetingManager meetingManager) {
        Ui.showBye();
        Ui.closeStreams();
        System.exit(0);
    }
}


