package cooper.command;

import cooper.meetings.MeetingManager;

public class MeetingsCommand extends Command {

    public MeetingsCommand() {
        super();
    }

    public void execute() {
        MeetingManager.printAvailabilities();
    }

}


