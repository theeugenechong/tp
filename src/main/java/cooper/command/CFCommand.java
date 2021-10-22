package cooper.command;

import cooper.exceptions.InvalidAccessException;
import cooper.finance.FinanceCommand;
import cooper.finance.FinanceManager;
import cooper.meetings.MeetingManager;
import cooper.parser.CommandParser;
import cooper.storage.StorageManager;
import cooper.verification.SignInDetails;

public class CFCommand extends Command {

    public CFCommand() {
        super();
    }

    @Override
    public void execute(SignInDetails signInDetails, FinanceManager financeManager, MeetingManager meetingManager,
                        StorageManager storageManager) throws InvalidAccessException {
        CommandParser.financeFlag = FinanceCommand.CF;
    }
}
