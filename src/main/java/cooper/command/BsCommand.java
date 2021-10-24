package cooper.command;

import cooper.exceptions.InvalidAccessException;
import cooper.finance.FinanceCommand;
import cooper.finance.FinanceManager;
import cooper.meetings.MeetingManager;
import cooper.parser.CommandParser;
import cooper.resources.ResourcesManager;
import cooper.storage.StorageManager;
import cooper.ui.Ui;
import cooper.verification.SignInDetails;

public class BsCommand extends Command {

    public BsCommand() {
        super();
    }

    @Override
    public void execute(SignInDetails signInDetails, ResourcesManager resourcesManager) throws InvalidAccessException {
        CommandParser.financeFlag = FinanceCommand.BS;
        Ui.initiateBalanceSheet();
    }
}
