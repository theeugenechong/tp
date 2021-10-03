package cooper;

import java.net.URISyntaxException;

import cooper.command.Command;
import cooper.finance.FinanceManager;
import cooper.ui.Ui;
import cooper.parser.CommandParser;
import cooper.exceptions.FinishAppException;
import cooper.exceptions.InvalidCommandException;
import cooper.exceptions.InvalidArgumentException;


public class Cooper {
    private Ui ui;
    private CommandParser commandParser;
    private FinanceManager financeManager;

    public Cooper() {
        ui = new Ui(System.in, System.out);
        financeManager = new FinanceManager();
        try {
            commandParser = new CommandParser(ui);
        } catch (URISyntaxException e) {
            ui.showInvalidFilePathError();
            ui.showBye();
            ui.closeStreams();
            System.exit(0);
        }
    }

    public void run() {
        ui.showLogo();
        ui.showGreetingMessage();


        while (true) {
            try {
                ui.showPrompt();
                String input = ui.getInput();
                Command command = commandParser.parse(input);
                if (command != null) {
                    command.execute();
                }
            } catch (InvalidCommandException e) {
                ui.showInvalidCommandError();
            } catch (InvalidArgumentException e) {
                ui.showInvalidCommandArgumentError();
            } catch (NumberFormatException e) {
                ui.showInvalidNumberError();
            } catch (FinishAppException e) {
                ui.showBye();
                ui.closeStreams();
                System.exit(0);
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
