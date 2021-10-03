package cooper.ui;

import cooper.finance.FinanceManager;

import java.io.PrintStream;
import java.util.Scanner;

public class Ui {

    private static final String LOGO = "            /$$$$$$   /$$$$$$  /$$$$$$$                    \n"
            +                  "           /$$__  $$ /$$__  $$| $$__  $$                   \n"
            +                  "  /$$$$$$$| $$  \\ $$| $$  \\ $$| $$  \\ $$ /$$$$$$   /$$$$$$ \n"
            +                  " /$$_____/| $$  | $$| $$  | $$| $$$$$$$//$$__  $$ /$$__  $$\n"
            +                  "| $$      | $$  | $$| $$  | $$| $$____/| $$$$$$$$| $$  \\__/\n"
            +                  "| $$      | $$  | $$| $$  | $$| $$     | $$_____/| $$      \n"
            +                  "|  $$$$$$$|  $$$$$$/|  $$$$$$/| $$     |  $$$$$$$| $$      \n"
            +                  " \\_______/ \\______/  \\______/ |__/      \\_______/|__/      ";

    private static final String LINE = "=========================================================================";

    private static final String GREETING = "Hello I'm cOOPer! Nice to meet you!";

    private static final Scanner scanner = new Scanner(System.in);
    private static final PrintStream printStream = System.out;


    public static String getInput() {
        showPrompt();
        return scanner.nextLine();
    }

    public static void showLogo() {
        show(LOGO);
    }

    public static void showIntroduction() {
        show(LINE);
        show(GREETING);
        show("Login or register to gain access to my features!");
        show("To login, enter [login /u yourUsername /as yourRole]");
        show("To register, enter [register /u yourUsername /as yourRole]");
        show(LINE);
    }

    public static void showPleaseRegisterMessage() {
        show(LINE);
        show("Your username does not exist, please register!");
        show(LINE);
    }

    public static void showPleaseLoginMessage() {
        show(LINE);
        show("Your username already exists, please login!");
        show(LINE);
    }

    public static void showInvalidUserRoleError() {
        show(LINE);
        show("Invalid role entered! Role can only be admin or employee.");
        show(LINE);
    }

    public static void showText(String text) {
        show(text);
    }

    /**
     * Exception message to show file path error.
     **/
    public static void showInvalidFilePathError() {
        show(LINE);
        show("Parser received invalid input file path!");
        show(LINE);
    }

    /**
     * Exception message to show invalid command error.
     **/
    public static void showUnrecognisedCommandError() {
        show(LINE);
        show("I don't recognise the command you entered. Enter [help] to view available commands.");
        show(LINE);
    }

    /**
     * Exception message to show invalid command argument error.
     **/
    public static void showInvalidCommandArgumentError() {
        show(LINE);
        show("You have keyed in invalid command arguments.");
        show(LINE);
    }

    /**
     * Exception message to show invalid number (such as task id) error.
     **/
    public static void showInvalidNumberError() {
        show(LINE);
        show("Unrecognised number.");
        show(LINE);
    }

    public static void showBye() {
        show(LINE);
        show("Bye, see you next time! :D");
        show(LINE);
    }

    public static void showPrompt() {
        printStream.print(">> ");
    }

    /**
     * Close streams properly.
     */
    public static void closeStreams() {
        scanner.close();
        printStream.close();
    }

    private static void show(String printMessage) {
        printStream.println(printMessage);
    }

    public static void printList() {
        show(LINE);
        show("This is the company's current Balance Sheet:");
        for (int i = 0; i < FinanceManager.balanceSheet.size(); i++) {
            if (FinanceManager.balanceSheet.size() != 0) {
                show(i + 1 + ". " + FinanceManager.balanceSheet.get(i));
            }
        }
        show (LINE);
    }

    public static void printAddCommand(String amount, boolean isInflow) {
        show(LINE);
        show("Success!");
        show("Amount: " + (isInflow ? "+" : "-") + amount + " has been added to the Balance Sheet.");
        show(LINE);
    }
}
