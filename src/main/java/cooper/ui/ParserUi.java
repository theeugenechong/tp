package cooper.ui;

//@@author theeugenechong
/**
 * Contains the constants and methods for the Ui involving cOOPer's parsing.
 */
public class ParserUi extends Ui {

    private static final String UNRECOGNISED_COMMAND = "I don't recognise the command you entered.";
    private static final String ENTER_HELP = "Enter \"help\" to view the format of each command.";
    private static final String TMP_FILE_CREATION_ERROR = "Error encountered when creating temp file: "
            + System.getProperty("user.dir") + "/tmp" + "/tmp_file_command.txt" + " or "
            + System.getProperty("user.dir") + "/tmp" + "/tmp_file_training.txt";
    private static final String INVALID_COMMAND_FORMAT = "The command you entered is of the wrong format!";
    private static final String PLEASE_ENTER_NUMBER = "Please enter a positive integer "
            + "(up to 300,000,000) for the argument.";

    /**
     * Informs user that an unrecognised command ha been entered.
     **/
    public static void showUnrecognisedCommandError() {
        show(LINE);
        show(UNRECOGNISED_COMMAND);
        show(ENTER_HELP);
        show(LINE);
    }

    /**
     * Informs user that there is an error creating the tmp file for cOOPer's Parser.
     */
    public static void showTmpFileCreationError() {
        show(LINE);
        show(TMP_FILE_CREATION_ERROR);
        show(LINE);
    }

    /**
     * Exception message to show invalid command argument error.
     **/
    public static void showInvalidCommandFormatError() {
        show(LINE);
        show(INVALID_COMMAND_FORMAT);
        show(ENTER_HELP);
        show(LINE);
    }

    /**
     * Informs user that a non-integral value has been input where an integer value
     * is expected.
     **/
    public static void showInvalidNumberError() {
        show(LINE);
        show(PLEASE_ENTER_NUMBER);
        show(LINE);
    }
}
