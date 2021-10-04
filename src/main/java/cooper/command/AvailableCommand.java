package cooper.command;

import cooper.meetings.MeetingManager;
import cooper.ui.Ui;

public class AvailableCommand extends Command {
    private String time;
    private String username;

    public AvailableCommand(String time, String username) {
        super();
        this.time = time;
        this.username = username;
    }

    public void execute() {
        MeetingManager.addAvailability(time, username);
        Ui.printAvailableCommand(time, username);
    }

}



