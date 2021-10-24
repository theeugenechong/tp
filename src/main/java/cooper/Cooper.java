package cooper;

import java.util.NoSuchElementException;

import cooper.command.Command;
import cooper.exceptions.InvalidAccessException;
import cooper.exceptions.InvalidCommandFormatException;
import cooper.finance.FinanceManager;
import cooper.forum.ForumManager;
import cooper.log.CooperLogger;
import cooper.meetings.MeetingManager;
import cooper.storage.StorageManager;
import cooper.ui.Ui;
import cooper.exceptions.UnrecognisedCommandException;
import cooper.parser.CommandParser;
import cooper.verification.SignInDetails;
import cooper.verification.Verifier;
import cooper.resources.ResourcesManager;

public class Cooper {

    private final ResourcesManager cooperResourcesManager;

    public Cooper() {
        cooperResourcesManager = new ResourcesManager();
        CooperLogger.setupLogger();
    }

    /**
     * Main entry-point for the java.duke.Duke application.
     */
    public static void main(String[] args) {
        Cooper cooper = new Cooper();
        cooper.run();
    }

    public void run() {
        setUp();
        SignInDetails signInDetails = verifyUser();
        runLoopUntilExitCommand(signInDetails);
    }

    private void setUp() {
        Ui.showLogo();
        Ui.showIntroduction();
    }

    private SignInDetails verifyUser() {
        Verifier cooperVerifier = cooperResourcesManager.getVerifier();
        StorageManager cooperStorageManager = cooperResourcesManager.getStorageManager();
        assert cooperVerifier != null;
        assert cooperStorageManager != null;
        SignInDetails successfulSignInDetails = null;
        String input;
        while (!cooperVerifier.isSuccessfullySignedIn()) {
            input = Ui.getInput();
            successfulSignInDetails = cooperVerifier.verify(input);
        }
        assert successfulSignInDetails != null;
        cooperStorageManager.saveSignInDetails(cooperVerifier);
        return successfulSignInDetails;
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private void runLoopUntilExitCommand(SignInDetails signInDetails) {
        while (true) {
            try {
                String input = Ui.getInput();
                Command command = CommandParser.parse(input);
                assert command != null;
                command.execute(signInDetails, cooperResourcesManager);
            } catch (NoSuchElementException | InvalidCommandFormatException e) {
                Ui.showInvalidCommandFormatError();
            } catch (NumberFormatException e) {
                Ui.showInvalidNumberError();
            } catch (UnrecognisedCommandException e) {
                Ui.showUnrecognisedCommandError();
            } catch (InvalidAccessException e) {
                Ui.printNoAccessError();
            }
        }
    }
}
