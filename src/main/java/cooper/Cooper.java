package cooper;

import java.net.URISyntaxException;

import cooper.command.Command;
import cooper.parser.CommandParser;
import cooper.exceptions.InvalidArgumentException;
import cooper.ui.Ui;


public class Cooper {

    private CommandParser commandParser;

    public Cooper() {
        try {
            commandParser = new CommandParser();
        } catch (URISyntaxException e) {
            Ui.showInvalidFilePathError();
            Ui.showBye();
            Ui.closeStreams();
            System.exit(0);
        }
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        Ui.showLogo();
        Ui.showIntroduction();
        while (true) {
            try {
                String input = Ui.getInput();
                Command command = commandParser.parse(input);
                command.execute();
            } catch (InvalidArgumentException e) {
                Ui.showInvalidCommandArgumentError();
            } catch (NumberFormatException e) {
                Ui.showInvalidNumberError();
            }
        }
    }


    /**
     * Main entry-point for the java.duke.Duke application.
     */
    public static void main(String[] args) {
        Cooper cooper = new Cooper();
        cooper.run();
    }

}
