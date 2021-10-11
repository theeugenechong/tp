package cooper.command;

import cooper.exceptions.InvalidAccessException;
import cooper.meetings.MeetingManager;
import cooper.ui.Ui;
import cooper.finance.FinanceManager;
import cooper.verification.SignInDetails;
import cooper.verification.UserRole;
import cooper.exceptions.InvalidAccessException;

/**
 * The child class of Command that handles the 'list' function specifically.
 */
public class ListCommand extends Command {


    /**
     * The override function for executing the 'list' command. Prints the balance sheet to the command line if and only if
     * the command is being accessed by an 'admin' level user.
     * @param signInDetails
     * @param financeManager
     * @param meetingManager
     */
    @Override
    public void execute(SignInDetails signInDetails, FinanceManager financeManager, MeetingManager meetingManager) throws InvalidAccessException {
        UserRole userRole = signInDetails.getUserRole();
        if (userRole.equals(UserRole.ADMIN)) {
            Ui.printBalanceSheet(financeManager.getBalanceSheet());
        } else {
            Ui.printEmployeeHelp();
            Ui.printGeneralHelp();
            throw new InvalidAccessException();
        }
    }
}


