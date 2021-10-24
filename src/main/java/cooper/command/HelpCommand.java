package cooper.command;

import cooper.ui.Ui;
import cooper.verification.SignInDetails;
import cooper.verification.UserRole;
import cooper.resources.ResourcesManager;

public class HelpCommand extends Command {

    @Override
    public void execute(SignInDetails signInDetails, 
            ResourcesManager resourcesManager) {
        UserRole userRole = signInDetails.getUserRole();
        if (userRole.equals(UserRole.ADMIN)) {
            Ui.printAdminHelp();
        } else if (userRole.equals(UserRole.EMPLOYEE)) {
            Ui.printEmployeeHelp();
        }
        Ui.printGeneralHelp();
    }
}


