package cooper.command;

import cooper.ui.Ui;

public class ExitCommand extends Command {

    public ExitCommand() {
        super();
    }

    public void execute() {
        Ui.showBye();
        Ui.closeStreams();
        System.exit(0);
    }

}


