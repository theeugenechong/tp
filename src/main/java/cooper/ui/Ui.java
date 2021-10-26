package cooper.ui;

import java.io.PrintStream;
import java.util.Scanner;
import java.util.logging.Logger;

public class Ui {

    private static final String LOGO = "            /$$$$$$   /$$$$$$  /$$$$$$$\n"
            +                          "           /$$__  $$ /$$__  $$| $$__  $$\n"
            +                          "  /$$$$$$$| $$  \\ $$| $$  \\ $$| $$  \\ $$ /$$$$$$   /$$$$$$\n"
            +                          " /$$_____/| $$  | $$| $$  | $$| $$$$$$$//$$__  $$ /$$__  $$\n"
            +                          "| $$      | $$  | $$| $$  | $$| $$____/| $$$$$$$$| $$  \\__/\n"
            +                          "| $$      | $$  | $$| $$  | $$| $$     | $$_____/| $$\n"
            +                          "|  $$$$$$$|  $$$$$$/|  $$$$$$/| $$     |  $$$$$$$| $$\n"
            +                          " \\_______/ \\______/  \\______/ |__/      \\_______/|__/";

    protected static final String LINE = "=========================================================================";

    protected static final String TABLE_TOP = "┌────────────────────────────────────────────────────────────────────┐";
    protected static final String TABLE_BOT = "└────────────────────────────────────────────────────────────────────┘";

    private static final String GREETING = "Hello I'm cOOPer! Nice to meet you!";

    private static final Scanner scanner = new Scanner(System.in);
    private static final PrintStream printStream = System.out;

    protected static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static String getInput() {
        showPrompt();
        return scanner.nextLine();
    }

    public static void showLogo() {
        show(LOGO);
    }

    public static void showIntroduction() {
        showGreetingMessage();
        showLoginRegisterMessage(true);
    }

    private static void showGreetingMessage() {
        show(LINE);
        show(GREETING);
        show(LINE);
    }

    public static void showLoginRegisterMessage(boolean isIntro) {
        if (isIntro) {
            show("Login or register to gain access to my features!");
        } else {
            show(LINE);
        }
        show("To login, enter \"login  [yourUsername] pw [password] as [yourRole]\"");
        show("To register, enter \"register [yourUsername] pw [password] as [yourRole]\"");
        show(LINE);
    }

    public static void showBye() {
        show(LINE);
        show("Bye, see you next time! :D");
        show(LINE);
    }

    private static void showPrompt() {
        show(">> ", false); // false: do not print newline
    }

    /**
     * Close streams properly.
     */
    public static void closeStreams() {
        scanner.close();
        printStream.close();
    }

    protected static void show(String printMessage) {
        printStream.println(printMessage);
    }

    protected static void show(String printMessage, boolean newline) {
        printStream.print(printMessage);

        if (newline) {
            printStream.println();
        }
    }

    public static void printAdminHelp() {
        show(LINE);
        show("Here are the commands available to an admin along with their formats:");
        show("bs            | bs");
        show("cf            | cf");
        show("add           | add [amount]");
        show("list          | list");
        show("generate      | generate [financialStatement]");
        show("schedule      | schedule [meetingName] with [username1], [username2] /at [meetingTime]");
    }

    public static void printEmployeeHelp() {
        show(LINE);
        show("Here are the commands available to an employee along with their formats:");
    }

    public static void printGeneralHelp() {
        show("post add      | post add [postContent]");
        show("post delete   | post delete [postId]");
        show("post comment  | post comment [commentContent] on [postId]");
        show("post list all | post list all / post list [postId]");
        show("available     | available [availableTime]");
        show("availability  | availability");
        show("meetings      | meetings");
        show("logout        | logout");
        show("exit          | exit");
        show(LINE);
    }
}
