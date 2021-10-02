package cooper.ui;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class Ui {

    static final String LOGO =
        "              ,----..       ,----..   ,-.----.                   \n"
        + "            /   /   \\     /   /   \\  \\    /  \\                   \n" 
        + "           /   .     :   /   .     : |   :    \\                  \n" 
        + "          .   /   ;.  \\ .   /   ;.  \\|   |  .\\ :          __  ,-.\n" 
        + "         .   ;   /  ` ;.   ;   /  ` ;.   :  |: |        ,' ,'/ /|\n" 
        + "   ,---. ;   |  ; \\ ; |;   |  ; \\ ; ||   |   \\ : ,---.  '  | |' |\n" 
        + "  /     \\|   :  | ; | '|   :  | ; | '|   : .   //     \\ |  |   ,'\n" 
        + " /    / '.   |  ' ' ' :.   |  ' ' ' :;   | |`-'/    /  |'  :  /  \n" 
        + " .    ' / '   ;  \\; /  |'   ;  \\; /  ||   | ;  .    ' / ||  | '   \n" 
        + " '   ; :__ \\   \\  ',  /  \\   \\  ',  / :   ' |  '   ;   /|;  : |   \n" 
        + " '   | '.'| ;   :    /    ;   :    /  :   : :  '   |  / ||  , ;   \n" 
        + " |   :    :  \\   \\ .'      \\   \\ .'   |   | :  |   :    | ---'    \n" 
        + "  \\   \\  /    `---`         `---`     `---'.|  \\  \\  /          \n" 
        + "  `----'                               `---`    `----'            \n";

    static final String GREETING =
        "____________________________________________________________\n"
        + "Hello! I'm Cooper\n"
        + "What can I do for you?\n";

    static final String SEPERATOR = "---------------";

    private PrintStream printStream;
    private Scanner scanner;

    /**
     * specify the input and output streams that ui interface with.
     */
    public Ui(InputStream inputStream, PrintStream printStream) {
        this.printStream = printStream;
        this.scanner = new Scanner(System.in);
    }


    public String getInput() {
        return scanner.nextLine();
    }

    public void showLogo() {
        show(LOGO);
    }

    public void showGreetingMessage() {
        show(GREETING);
    }

    public void showSeperator() {
        show(SEPERATOR);
    }

    public void showText(String text) {
        show(text);
    }

    /**
     * Exception message to show file path error.
     **/
    public void showInvalidFilePathError() {
        show("Parser received invalid input file path!");
        showSeperator();
    }

    /**
     * Exception message to show invalid command error.
     **/
    public void showInvalidCommandError() {
        show("I don't quite understand what do you mean, try type in [help] to see available commands.");
        showSeperator();
    }

    /**
     * Exception message to show invalid command argument error.
     **/
    public void showInvalidCommandArgumentError() {
        show("You have keyed in invalid command argument.");
        showSeperator();
    }

    /**
     * Exception message to show invalid number (such as task id) error.
     **/
    public void showInvalidNumberError() {
        show("Unrecognised number.");
        showSeperator();
    }

    public void showBye() {
        show("Bye bye :))");
    }

    public void showPrompt() {
        printStream.print(">> ");
    }

    /**
     * Close streams properly.
     */
    public void closeStreams() {
        scanner.close();
        printStream.close();
    }

    private void show(String printMessage) {
        printStream.println(printMessage);
    }

}
