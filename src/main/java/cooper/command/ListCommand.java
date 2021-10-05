package cooper.command;

import cooper.meetings.MeetingManager;
import cooper.ui.Ui;
import cooper.finance.FinanceManager;
import cooper.verification.SignInDetails;

public class ListCommand extends Command {

    public ListCommand() {
        super();
    }

    @Override
    public void execute(SignInDetails signInDetails, FinanceManager financeManager, MeetingManager meetingManager) {
        Ui.printBalanceSheet(financeManager.getBalanceSheet());
    }

}


