package cooper.command;

import cooper.finance.FinanceManager;
import cooper.meetings.MeetingManager;
import cooper.verification.SignInDetails;

public class HelpCommand extends Command {

    public HelpCommand() {
        super();
    }

    @Override
    public void execute(SignInDetails signInDetails, FinanceManager financeManager, MeetingManager meetingManager) {
        System.out.println("I want to execute this [help] command, but I do not know how!");
    }

}


