package cooper.command;

import cooper.finance.FinanceManager;
import cooper.meetings.MeetingManager;
import cooper.ui.Ui;
import cooper.verification.SignInDetails;

public class MeetingsCommand extends Command {

    public MeetingsCommand() {
        super();
    }

    @Override
    public void execute(SignInDetails signInDetails, FinanceManager financeManager, MeetingManager meetingManager) {
        Ui.printAvailabilities(meetingManager.getMeetings());
    }

}


