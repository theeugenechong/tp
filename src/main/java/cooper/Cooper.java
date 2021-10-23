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

    private final FinanceManager cooperFinanceManager;
    private final ForumManager cooperForumManager;
    private final MeetingManager cooperMeetingManager;
    private final Verifier cooperVerifier;
    private final StorageManager cooperStorageManager;
    private final ResourcesManager cooperResourcesManager;

    public Cooper() {
        cooperVerifier = new Verifier();
        cooperStorageManager = new StorageManager();
        cooperFinanceManager = new FinanceManager();
        cooperMeetingManager = new MeetingManager();
        cooperForumManager = new ForumManager();
        cooperResourcesManager = new ResourcesManager(
                cooperFinanceManager,
                cooperMeetingManager,
                cooperForumManager);
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
        cooperStorageManager.loadAllData(cooperVerifier,
                cooperFinanceManager,
                cooperMeetingManager);
    }

    private SignInDetails verifyUser() {
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
                command.execute(signInDetails, cooperResourcesManager, cooperStorageManager);
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
