package cooper.command;

import cooper.finance.FinanceManager;
import cooper.meetings.MeetingManager;
import cooper.verification.SignInDetails;

public abstract class Command {

    public Command() {
    }

    /**
     * Child classes are required to implement how to execute on itself.
     */
    public abstract void execute(SignInDetails signInDetails, FinanceManager financeManager, MeetingManager meetingManager);
}
