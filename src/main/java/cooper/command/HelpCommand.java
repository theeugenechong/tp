package cooper.command;

import cooper.finance.FinanceManager;
import cooper.meetings.MeetingManager;
import cooper.ui.Ui;
import cooper.verification.SignInDetails;
import cooper.verification.UserRole;

public class HelpCommand extends Command {

    public HelpCommand() {
        super();
    }

    @Override
    public void execute(SignInDetails signInDetails, FinanceManager financeManager, MeetingManager meetingManager) {
        UserRole userRole = signInDetails.getUserRole();
        if (userRole.equals(UserRole.ADMIN)) {
            Ui.printAdminHelp();
        } else if (userRole.equals(UserRole.EMPLOYEE)) {
            Ui.printEmployeeHelp();
        }
        Ui.printGeneralHelp();
    }
}


