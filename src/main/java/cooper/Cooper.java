package cooper;

import java.util.NoSuchElementException;

import cooper.command.Command;
import cooper.exceptions.InvalidAccessException;
import cooper.exceptions.InvalidCommandFormatException;
import cooper.exceptions.LogoutException;
import cooper.log.CooperLogger;
import cooper.storage.StorageManager;
import cooper.ui.Ui;
import cooper.exceptions.UnrecognisedCommandException;
import cooper.parser.CommandParser;
import cooper.verification.SignInDetails;
import cooper.verification.Verifier;
import cooper.resources.ResourcesManager;

public class Cooper {

    private final Verifier cooperVerifier;
    private final ResourcesManager cooperResourcesManager;
    private final StorageManager cooperStorageManager;

    public Cooper() {
        cooperVerifier = new Verifier();
        cooperResourcesManager = new ResourcesManager();
        cooperStorageManager = new StorageManager();
        CooperLogger.setupLogger();
    }

    /**
     * Main entry-point for the java.duke.Duke application.
     */
    public static void main(String[] args) {
        Cooper cooper = new Cooper();
        cooper.run();
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        setUp();
        while (true) {
            SignInDetails signInDetails = verifyUser();
            runLoopUntilExitCommand(signInDetails);
        }
    }

    private void setUp() {
        Ui.showLogo();
        Ui.showIntroduction();

        // Load data from storage
        cooperStorageManager.loadAllData(cooperVerifier, cooperResourcesManager.getFinanceManager(),
                cooperResourcesManager.getMeetingManager());
    }

    private SignInDetails verifyUser() {
        SignInDetails successfulSignInDetails = null;
        while (!cooperVerifier.isSuccessfullySignedIn()) {
            String input = Ui.getInput();
            successfulSignInDetails = cooperVerifier.verify(input);
        }
        assert successfulSignInDetails != null;
        cooperStorageManager.saveSignInDetails(cooperVerifier);
        return successfulSignInDetails;
    }

    private void runLoopUntilExitCommand(SignInDetails signInDetails) {
        while (true) {
            try {
                String input = Ui.getInput();
                Command command = CommandParser.parse(input);
                assert command != null;
                command.execute(signInDetails, cooperResourcesManager, cooperStorageManager);
            } catch (NoSuchElementException | InvalidCommandFormatException e) {
                Ui.showInvalidCommandFormatError();
            } catch (NumberFormatException e) {
                Ui.showInvalidNumberError();
            } catch (UnrecognisedCommandException e) {
                Ui.showUnrecognisedCommandError(false);
            } catch (InvalidAccessException e) {
                Ui.printNoAccessError();
            } catch (LogoutException e) {
                cooperVerifier.setSuccessfullySignedIn(false);
                Ui.showLoginRegisterMessage(false);
                break;
            }
        }
    }
}
