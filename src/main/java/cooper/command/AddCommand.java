package cooper.command;

import cooper.exceptions.InvalidAccessException;
import cooper.meetings.MeetingManager;
import cooper.ui.Ui;
import cooper.finance.FinanceManager;
import cooper.verification.SignInDetails;
import cooper.verification.UserRole;

/**
 * The child class of Command that handles the 'add' command specifically.
 */
public class AddCommand extends Command {

    public boolean isInflow;
    public int amount;

    public AddCommand(int amount, boolean isInflow) {
        super();
        this.amount = amount;
        this.isInflow = isInflow;
    }

    /**
     * The override function for executing the 'add' command, calls for 'add' and subsequently
     * printing the status to the command line if and only if
     * the command is being accessed by an 'admin' level user.
     * @param signInDetails access role
     * @param financeManager access balance sheet
     * @param meetingManager access meetings
     */
    @Override
    public void execute(SignInDetails signInDetails, FinanceManager financeManager, MeetingManager meetingManager)
            throws InvalidAccessException {
        UserRole userRole = signInDetails.getUserRole();
        if (userRole.equals(UserRole.ADMIN)) {
            financeManager.addBalance(amount, isInflow);
            Ui.printAddCommand(amount, isInflow);
        } else {
            Ui.printEmployeeHelp();
            Ui.printGeneralHelp();
            throw new InvalidAccessException();
        }
    }
}


