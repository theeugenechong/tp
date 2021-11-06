package cooper.ui;

import java.io.PrintStream;
import java.util.Scanner;
import java.util.logging.Logger;

import cooper.CooperState;

//@@author Rrraaaeee

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

    protected static final String TABLE_LINE = "+--------------------------------------------------------------------+";
    protected static final String AVAILABILITY_TABLE_TOP =
            "+------------+-------+-----------------------------------------------+";
    protected static final String MEETINGS_TABLE_TOP =
            "+------------+------------+-------+----------------------------------+";

    private static final String GREETING = "Hello I'm cOOPer! Nice to meet you!";

    private static final Scanner IN = new Scanner(System.in);
    private static final PrintStream OUT = System.out;

    protected static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private static final String EMPTY_STRING = "";
    protected static final String NL = System.lineSeparator();

    protected static final String LOGIN_REGISTER_FOR_ACCESS = "Log in or register to gain access to my features!";
    protected static final String LOGIN = "To log in, enter \"login [yourUsername] /pw [password] /as [yourRole]\".";
    protected static final String REGISTER = "To register, enter \"register [yourUsername] /pw [password] /as "
            + "[yourRole]\".";
    protected static final String EXIT = "To exit, enter \"exit\".";

    private static final String BYE_MESSAGE = "Bye, see you next time!";
    private static final String PROMPT = ">> ";

    /* Constants used for admin help command */
    private static final String HELP_TABLE_TOP =
            "+---------------+------------------------------------------------------+";
    private static final String ADMIN_COMMANDS = "Here are the commands available to an admin along with their "
            + "formats:";
    private static final String BS_FORMAT       = "| bs            | bs";
    private static final String CF_FORMAT       = "| cf            | cf";
    private static final String PROJ_FORMAT     = "| proj          | proj [years]";
    private static final String ADD_FORMAT      = "| add           | add [amount]";
    private static final String LIST_FORMAT     = "| list          | list";
    private static final String GENERATE_FORMAT = "| generate      | generate [financialStatement]";
    private static final String SCHEDULE_FORMAT = "| schedule      | schedule [meetingName] /with [username1], "
            + "[username2] or " + NL
            + "|               | schedule [meetingName] /with [username1], [username2] /at [meetingTime]";

    /* Constants used for employee help command */
    private static final String EMPLOYEE_COMMANDS = "Here are the commands available to an employee along with their "
            + "formats:";
    private static final String POST_ADD_FORMAT     = "| post add      | post add [postContent]";
    private static final String POST_DELETE_FORMAT  = "| post delete   | post delete [postId]";
    private static final String POST_COMMENT_FORMAT = "| post comment  | post comment [commentContent] /on [postId]";
    private static final String POST_LIST_FORMAT    = "| post list     | post list all or post list [postId]";
    private static final String AVAILABLE_FORMAT    = "| available     | available [date] [time]";
    private static final String AVAILABILITY_FORMAT = "| availability  | availability";
    private static final String MEETINGS_FORMAT     = "| meetings      | meetings";
    private static final String LOGOUT_FORMAT       = "| logout        | logout";
    private static final String EXIT_FORMAT         = "| exit          | exit";
    private static final String REFER_TO_UG = "Refer to my User Guide for more information: ";
    private static final String UG_LINK = "https://ay2122s1-cs2113t-w13-4.github.io/tp/UserGuide.html";

    private static CooperState cooperState = CooperState.LOGOUT;
    private static final String STATE_LOGOUT = "[Logged out] ";
    private static final String STATE_CF = "[Cash Flow] ";
    private static final String STATE_BS = "[Balance Sheet] ";

    /**
     * Reads input from the user. Behaves like a real command line in the sense that an empty string entered is ignored.
     * @return user input
     */
    public static String getInput() {
        String input = EMPTY_STRING;
        while (input.trim().length() == 0) {
            showPrompt();
            input = IN.nextLine();
        }
        return input;
    }

    /**
     * Shows cOOPer's logo.
     */
    public static void showLogo() {
        show(LOGO);
    }

    /**
     * Shows the greeting message along with the message asking user to login / register upon entry to the program.
     */
    public static void showIntroduction() {
        showGreetingMessage();
        showLoginRegisterIntroMessage();
    }

    /**
     * Show cOOPer's greetings upon entry to the program.
     */
    private static void showGreetingMessage() {
        show(LINE);
        show(GREETING);
        show(LINE);
    }

    /**
     * Shows a message asking user to login / register along with the format.
     */
    private static void showLoginRegisterIntroMessage() {
        show(LOGIN_REGISTER_FOR_ACCESS);
        show(LOGIN);
        show(REGISTER);
        show(NL + EXIT);
        show(LINE);
    }

    /**
     * Shows bye message upon exiting the program.
     */
    public static void showBye() {
        show(LINE);
        show(BYE_MESSAGE);
        show(LINE);
    }

    /**
     * Shows cOOPer's command prompt when reading input.
     */
    private static void showPrompt() {
        show(PROMPT, false); // false: do not print newline
        switch (cooperState) {
        case LOGOUT:
            show(STATE_LOGOUT, false);
            break;
        case CF:
            show(STATE_CF, false);
            break;
        case BS:
            show(STATE_BS, false);
            break;
        default:
            // no mode, do not need to show anything
            break;
        }
    }

    public static void updatePromptState(CooperState state) {
        cooperState = state;
    }

    /**
     * Close streams properly.
     */
    public static void closeStreams() {
        IN.close();
        OUT.close();
    }

    protected static void show(String printMessage) {
        OUT.println(printMessage);
    }

    protected static void show(String printMessage, boolean newline) {
        OUT.print(printMessage);

        if (newline) {
            OUT.println();
        }
    }

    //@@author ChrisLangton
    /**
     * Prints commands available to an admin only along with their formats.
     */
    public static void printAdminHelp() {
        show(LINE);
        show(ADMIN_COMMANDS);
        show(HELP_TABLE_TOP);
        show(BS_FORMAT);
        show(CF_FORMAT);
        show(PROJ_FORMAT);
        show(ADD_FORMAT);
        show(LIST_FORMAT);
        show(GENERATE_FORMAT);
        show(SCHEDULE_FORMAT);
    }

    /**
     * Prints commands available to an employee only along with their formats.
     */
    public static void printEmployeeHelp() {
        show(LINE);
        show(EMPLOYEE_COMMANDS);
        show(HELP_TABLE_TOP);
    }

    //@@author fansxx
    /**
     * Prints commands available to all users along with their formats.
     */
    public static void printGeneralHelp() {
        show(POST_ADD_FORMAT);
        show(POST_DELETE_FORMAT);
        show(POST_COMMENT_FORMAT);
        show(POST_LIST_FORMAT);
        show(AVAILABLE_FORMAT);
        show(AVAILABILITY_FORMAT);
        show(MEETINGS_FORMAT);
        show(LOGOUT_FORMAT);
        show(EXIT_FORMAT);
        show(HELP_TABLE_TOP);
        show(REFER_TO_UG);
        show(UG_LINK);
        show(LINE);
    }
}
