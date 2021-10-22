package cooper.command;

import cooper.exceptions.InvalidAccessException;
import cooper.finance.BalanceSheet;
import cooper.meetings.MeetingManager;
import cooper.storage.StorageManager;
import cooper.ui.Ui;
import cooper.finance.FinanceManager;
import cooper.verification.SignInDetails;
import cooper.verification.UserRole;

/**
 * The child class of Command that handles the 'list' function specifically.
 */
public class ListCommand extends Command {


    /**
     * The override function for executing the 'list' command. Prints the balance sheet
     * to the command line if and only if
     * the command is being accessed by an 'admin' level user.
     * @param signInDetails access role
     * @param financeManager access balance sheet
     * @param meetingManager access meetings
     * @param storageManager save to storage
     */
    @Override
    public void execute(SignInDetails signInDetails, FinanceManager financeManager,
                        MeetingManager meetingManager, StorageManager storageManager) throws InvalidAccessException {
        UserRole userRole = signInDetails.getUserRole();
        if (userRole.equals(UserRole.ADMIN)) {
            Ui.printBalanceSheet(BalanceSheet.getBalanceSheet());
        } else {
            Ui.printEmployeeHelp();
            Ui.printGeneralHelp();
            throw new InvalidAccessException();
        }
    }
}


