package cooper;

import java.net.URISyntaxException;
import java.util.NoSuchElementException;

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
    private final FinanceManager cooperFinanceManager;
    private final MeetingManager cooperMeetingManager;
    private final Verifier cooperVerifier;

    public Cooper() {
        try {
            commandParser = new CommandParser();
        } catch (URISyntaxException e) {
            Ui.showInvalidFilePathError();
            Ui.showBye();
            Ui.closeStreams();
            System.exit(0);
        }
        cooperVerifier = new Verifier(commandParser);
        cooperFinanceManager = new FinanceManager();
        cooperMeetingManager = new MeetingManager();
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        Ui.showLogo();
        Ui.showIntroduction();

        SignInDetails signInDetails = verifyUser();

        while (true) {
            try {
                String input = Ui.getInput();
                Command command = commandParser.parse(input);
                command.execute(signInDetails, cooperFinanceManager, cooperMeetingManager);
            } catch (InvalidArgumentException | NoSuchElementException e) {
                Ui.showInvalidCommandArgumentError();
            } catch (NumberFormatException e) {
                Ui.showInvalidNumberError();
            } catch (UnrecognisedCommandException e) {
                Ui.showUnrecognisedCommandError();
            }
        }
    }

    private SignInDetails verifyUser() {
        SignInDetails successfulSignInDetails = null;
        while (!cooperVerifier.isSuccessfullySignedIn()) {
            String input = Ui.getInput();
            successfulSignInDetails = cooperVerifier.verify(input);
        }
        return successfulSignInDetails;
    }

    /**
     * Main entry-point for the java.duke.Duke application.
     */
    public static void main(String[] args) {
        Cooper cooper = new Cooper();
        cooper.run();
    }
}
