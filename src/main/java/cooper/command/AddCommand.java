package cooper.command;

import cooper.meetings.MeetingManager;
import cooper.ui.Ui;
import cooper.finance.FinanceManager;
import cooper.verification.SignInDetails;

public class AddCommand extends Command {

    public boolean isInflow;
    public String amount;

    public AddCommand(String amount, boolean isInflow) {
        super();
        this.amount = amount;
        this.isInflow = isInflow;
    }

    @Override
    public void execute(SignInDetails signInDetails, FinanceManager financeManager, MeetingManager meetingManager) {
        financeManager.addBalance(Integer.parseInt(amount));
        Ui.printAddCommand(amount, isInflow);
    }
}


