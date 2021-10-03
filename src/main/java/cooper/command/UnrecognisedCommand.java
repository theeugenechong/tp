package cooper.command;

import cooper.ui.Ui;

public class UnrecognisedCommand extends Command {

    private final String unrecognisedCommand;

    public UnrecognisedCommand(String input) {
        super();
        this.unrecognisedCommand = input;
    }

    @Override
    public void execute() {
        Ui.showUnrecognisedCommandError(unrecognisedCommand);
    }
}
