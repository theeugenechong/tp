package cooper.ui;

//@@author theeugenechong

public class ParserUi extends Ui {

    /**
     * Exception message to show invalid command error.
     **/
    public static void showUnrecognisedCommandError(boolean isSignIn) {
        show(LINE);
        show("I don't recognise the command you entered.");

        if (isSignIn) {
            show("To login, enter \"login  [yourUsername] pw [password] as [yourRole]\"");
            show("To register, enter \"register [yourUsername] pw [password] as [yourRole]\"");
        } else {
            show("Enter 'help' to view the format of each command.");
        }

        show(LINE);
    }

    public static void showTmpFileCreationError() {
        show(LINE);
        show("Error encountered when creating temp file: "
                + System.getProperty("user.dir") + "/tmp" + "/tmp_file_command.txt" + " or "
                + System.getProperty("user.dir") + "/tmp" + "/tmp_file_training.txt");
        show(LINE);
    }

    /**
     * Exception message to show invalid command argument error.
     **/
    public static void showInvalidCommandFormatError() {
        show(LINE);
        show("The command you entered is of the wrong format!");
        show("Enter 'help' to view the format of each command.");
        show(LINE);
    }

    /**
     * Exception message to show a non-integral value has been input where an integer value
     * is expected.
     **/
    public static void showInvalidNumberError() {
        show(LINE);
        show("Please enter a number for the amount.");
        show(LINE);
    }
}
