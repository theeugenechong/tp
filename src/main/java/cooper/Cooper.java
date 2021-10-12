package cooper;

import java.util.NoSuchElementException;

import cooper.command.Command;
import cooper.exceptions.InvalidAccessException;
import cooper.finance.FinanceManager;
import cooper.log.CooperLogger;
import cooper.meetings.MeetingManager;
import cooper.ui.Ui;
import cooper.exceptions.UnrecognisedCommandException;
import cooper.parser.CommandParser;
import cooper.exceptions.InvalidArgumentException;
import cooper.verification.SignInDetails;
import cooper.verification.Verifier;
import cooper.storage.Storage;

public class Cooper {

    private final FinanceManager cooperFinanceManager;
    private final MeetingManager cooperMeetingManager;
    private final Verifier cooperVerifier;
    private final Storage cooperStorage;

    public Cooper() {
        cooperVerifier = new Verifier();
        cooperStorage = new Storage();
        cooperFinanceManager = new FinanceManager();
        cooperMeetingManager = new MeetingManager();
        CooperLogger.setupLogger();
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        Ui.showLogo();
        Ui.showIntroduction();

        cooperStorage.loadLoginDetails(cooperVerifier);
        SignInDetails signInDetails = verifyUser();
        cooperStorage.loadResources(signInDetails, cooperFinanceManager, cooperMeetingManager);

        while (true) {
            try {
                String input = Ui.getInput();
                Command command = CommandParser.parse(input);
                assert command != null;
                command.execute(signInDetails, cooperFinanceManager, cooperMeetingManager);
                cooperStorage.saveCommand(input);
                cooperStorage.saveStorage();
            } catch (InvalidArgumentException | NoSuchElementException e) {
                Ui.showInvalidCommandArgumentError();
            } catch (NumberFormatException e) {
                Ui.showInvalidNumberError();
            } catch (UnrecognisedCommandException e) {
                Ui.showUnrecognisedCommandError();
            } catch (InvalidAccessException e) {
                Ui.printNoAccessError();
            }
        }
    }

    private SignInDetails verifyUser() {
        SignInDetails successfulSignInDetails = null;
        String input;

        while (!cooperVerifier.isSuccessfullySignedIn()) {
            input = Ui.getInput();
            successfulSignInDetails = cooperVerifier.verify(input);
        }

        assert successfulSignInDetails != null;
        cooperStorage.saveCommand("register " + successfulSignInDetails.getUsername()
                                   + " as " + successfulSignInDetails.getUserRole().toString().toLowerCase());
        cooperStorage.saveStorage();
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
