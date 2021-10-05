package cooper;

import java.net.URISyntaxException;
import java.util.HashMap;

import cooper.command.Command;
import cooper.finance.FinanceManager;
import cooper.meetings.MeetingManager;
import cooper.ui.Ui;
import cooper.exceptions.UnrecognisedCommandException;
import cooper.parser.CommandParser;
import cooper.exceptions.InvalidArgumentException;
import cooper.verification.SignInDetails;
import cooper.verification.Verifier;

public class Cooper {

    private CommandParser commandParser;
    private final FinanceManager financeManager;
    private final MeetingManager meetingManager;
    private final Verifier verifier;

    public Cooper() {
        try {
            commandParser = new CommandParser();
        } catch (URISyntaxException e) {
            Ui.showInvalidFilePathError();
            Ui.showBye();
            Ui.closeStreams();
            System.exit(0);
        }
        verifier = new Verifier(new HashMap<>(), commandParser);
        financeManager = new FinanceManager();
        meetingManager = new MeetingManager();
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        Ui.showLogo();
        Ui.showIntroduction();

        UserRole userRole = verifyUser();

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

    private UserRole verifyUser() {
        UserRole successfulSignInRole = null;
        while (!verifier.isSuccessfullySignedIn()) {
            String input = Ui.getInput();
            successfulSignInRole = verifier.verify(input);
        }
        return successfulSignInRole;
    }


    /**
     * Main entry-point for the java.duke.Duke application.
     */
    public static void main(String[] args) {
        Cooper cooper = new Cooper();
        cooper.run();
    }

}
