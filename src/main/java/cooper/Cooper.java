package cooper;

import java.net.URISyntaxException;
import java.util.HashMap;

import cooper.command.Command;
import cooper.finance.FinanceManager;
import cooper.ui.Ui;
import cooper.exceptions.UnrecognisedCommandException;
import cooper.parser.CommandParser;
import cooper.exceptions.InvalidArgumentException;
import cooper.verification.Verifier;

public class Cooper {

    private CommandParser commandParser;
    private FinanceManager financeManager;
    private Verifier verifier;

    public Cooper() {

        financeManager = new FinanceManager();
        verifier = new Verifier(new HashMap<>(), commandParser);

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

        verifier.verify();


        while (true) {
            try {
                String input = Ui.getInput();
                Command command = commandParser.parse(input);
                command.execute();
            } catch (InvalidArgumentException e) {
                Ui.showInvalidCommandArgumentError();
            } catch (NumberFormatException e) {
                Ui.showInvalidNumberError();
            } catch (UnrecognisedCommandException e) {
                Ui.showUnrecognisedCommandError();
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
