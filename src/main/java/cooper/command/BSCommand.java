package cooper.command;

import cooper.exceptions.InvalidAccessException;
import cooper.finance.FinanceManager;
import cooper.meetings.MeetingManager;
import cooper.storage.StorageManager;
import cooper.verification.SignInDetails;

public class BSCommand extends Command {

    public BSCommand() {
        super();
    }

    @Override
    public void execute(SignInDetails signInDetails, FinanceManager financeManager, MeetingManager meetingManager, StorageManager storageManager) throws InvalidAccessException {

    }
}
