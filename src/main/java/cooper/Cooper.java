package cooper;

import java.util.NoSuchElementException;

import cooper.command.Command;
import cooper.exceptions.AmountOutOfRangeException;
import cooper.exceptions.EmptyFinancialStatementException;
import cooper.exceptions.InvalidAccessException;
import cooper.exceptions.InvalidAddFormatException;
import cooper.exceptions.InvalidAssetException;
import cooper.exceptions.InvalidCommandFormatException;
import cooper.exceptions.InvalidDocumentException;
import cooper.exceptions.InvalidLiabilityException;
import cooper.exceptions.InvalidProjectionException;
import cooper.exceptions.InvalidScheduleFormatException;
import cooper.exceptions.NoTimeEnteredException;
import cooper.exceptions.NoUsernameAfterCommaException;
import cooper.exceptions.UnrecognisedCommandException;
import cooper.log.CooperLogger;
import cooper.storage.StorageManager;
import cooper.ui.MeetingsUi;
import cooper.ui.Ui;
import cooper.ui.ParserUi;
import cooper.ui.FinanceUi;
import cooper.ui.VerificationUi;
import cooper.parser.CommandParser;
import cooper.verification.SignInDetails;
import cooper.verification.Verifier;
import cooper.resources.ResourcesManager;

//@@author Rrraaaeee
/**
 * Entry point of the cOOPer application.
 */
public class Cooper {

    /* cOOPer's components */
    private final Verifier cooperVerifier;
    private final ResourcesManager cooperResourcesManager;
    private final StorageManager cooperStorageManager;

    /**
     * Initializes cOOPer's components.
     */
    public Cooper() {
        cooperVerifier = new Verifier();
        cooperStorageManager = new StorageManager();
        cooperResourcesManager = new ResourcesManager();
        CooperLogger.setupLogger();
    }

    /**
     * Main entry-point of the cOOPer application.
     */
    public static void main(String[] args) {
        Cooper cooper = new Cooper();
        cooper.run();
    }

    //@@author fansxx
    /**
     * Runs cOOPer.
     */
    public void run() {
        setUp();
        runLoopUntilExitCommand();
    }

    /**
     * Shows the greeting message and loads data from the storage upon launching cOOPer.
     */
    private void setUp() {
        Ui.showLogo();
        Ui.showIntroduction();

        // Load data from storage
        cooperStorageManager.loadAllData(cooperVerifier, cooperResourcesManager);
    }

    /**
     * Runs cOOPer until the {@code exit} command is entered.
     */
    @SuppressWarnings("InfiniteLoopStatement")
    private void runLoopUntilExitCommand() {
        while (true) {
            SignInDetails signInDetails = verifyUser();
            runLoopUntilLogoutCommand(signInDetails);
        }
    }

    //@@author theeugenechong
    /**
     * Gets user credentials and verifies them. Will continue looping until the credentials enter are valid.
     * Saves the user as a registered user only after the user signs in successfully.
     * @return The sign in details of the user who successfully signed in which is to be used in the execution of
     *         commands.
     */
    private SignInDetails verifyUser() {
        SignInDetails successfulSignInDetails = null;
        while (!cooperVerifier.isSuccessfullySignedIn()) {
            String input = Ui.getInput();
            successfulSignInDetails = cooperVerifier.verify(input);
            cooperStorageManager.saveSignInDetails(cooperVerifier);
        }

        CommandParser.setCooperState(CooperState.LOGIN);
        return successfulSignInDetails;
    }

    //@@author ChrisLangton
    /**
     * Allows the user to continuously enter commands to use cOOPer's features until the {@code logout} command is
     * entered.
     * @param signInDetails The sign in details of the user who successfully signed in which is to be used
     *                      in the execution of commands.
     */
    private void runLoopUntilLogoutCommand(SignInDetails signInDetails) {
        while (true) {
            if (CommandParser.isLogout()) {
                cooperVerifier.setSuccessfullySignedIn(false);
                VerificationUi.showLogoutMessage();
                break;
            }

            try {
                String input = Ui.getInput();
                Command command = CommandParser.parse(input);
                assert command != null;
                command.execute(signInDetails, cooperResourcesManager, cooperStorageManager);
            } catch (NoSuchElementException | InvalidCommandFormatException e) {
                ParserUi.showInvalidCommandFormatError();
            } catch (InvalidScheduleFormatException e) {
                MeetingsUi.showInvalidScheduleFormatException();
            } catch (NoTimeEnteredException e) {
                MeetingsUi.showNoTimeEnteredException();
            } catch (NoUsernameAfterCommaException e) {
                MeetingsUi.showNoUsernameAfterCommaException();
            } catch (NumberFormatException e) {
                ParserUi.showInvalidNumberError();
            } catch (UnrecognisedCommandException e) {
                ParserUi.showUnrecognisedCommandError();
            } catch (InvalidAccessException e) {
                VerificationUi.showNoAccessError();
            } catch (InvalidProjectionException e) {
                FinanceUi.showPleaseInputValidProjection();
            } catch (AmountOutOfRangeException e) {
                FinanceUi.showPleaseInputValidRange();
            } catch (EmptyFinancialStatementException e) {
                FinanceUi.showEmptyFinancialStatementException();
            } catch (InvalidDocumentException e) {
                FinanceUi.showInvalidDocumentError();
            } catch (InvalidAddFormatException e) {
                FinanceUi.showPleaseInputValidAdd();
            } catch (InvalidAssetException e) {
                FinanceUi.showPleaseInputValidAsset();
            } catch (InvalidLiabilityException e) {
                FinanceUi.showPleaseInputValidLiability();
            }
        }
    }
}

