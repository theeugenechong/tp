package cooper.command;

import cooper.meetings.MeetingManager;
import cooper.ui.Ui;
import cooper.finance.FinanceManager;
import cooper.verification.SignInDetails;

/**
 * The child class of Command that handles the 'add' command specifically.
 */
public class AddCommand extends Command {

    public boolean isInflow;
    public String amount;

    public AddCommand(String amount, boolean isInflow) {
        super();
        this.amount = amount;
        this.isInflow = isInflow;
    }

    /**
     * The override function for executing the 'add' command, calls for 'add' and subsequently printing the status
     * to the command line.
     */
    @Override
    public void execute(SignInDetails signInDetails, FinanceManager financeManager, MeetingManager meetingManager) {
        financeManager.addBalance(Integer.parseInt(amount), isInflow);
        Ui.printAddCommand(amount, isInflow);
    }
}


