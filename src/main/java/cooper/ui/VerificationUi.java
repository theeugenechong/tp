package cooper.ui;

import cooper.verification.UserRole;

//@@author theeugenechong
/**
 * Contains the constants and methods for the Ui involving verification process.
 */
public class VerificationUi extends Ui {

    private static final String PLEASE_REGISTER = "Your username does not exist, please register!";
    private static final String PLEASE_LOGIN = "Your username already exists, please login!";
    private static final String REGISTERED_SUCCESSFULLY = " is now successfully registered as an ";
    private static final String EXCLAMATION = "!";
    private static final String LOGGED_IN_SUCCESSFULLY = "You are now logged in successfully as ";
    private static final String INCORRECT_ROLE = "You are logging in with an incorrect role! Please try again.";
    private static final String INVALID_ROLE = "Invalid role entered! Role can only be admin or employee.";
    private static final String INCORRECT_PASSWORD = "Incorrect password entered! Please try again.";
    private static final String NO_ACCESS = "You do not have access to this command.";
    private static final String LOGGED_OUT = "You are now logged out!";
    protected static final String SIGN_IN_DETAILS_WRONG_FORMAT = "Your sign in details are of the wrong format!";
    protected static final String CAN_ONLY_LOGIN_REGISTER_EXIT = "You can only log in, register, or exit here!";


    public static void showSignInDetailsIncorrectFormatError() {
        show(LINE);
        show(SIGN_IN_DETAILS_WRONG_FORMAT);
        show(LOGIN);
        show(REGISTER);
        show(LINE);
    }

    public static void showUnrecognisedCommandAtSignInError() {
        show(LINE);
        show(CAN_ONLY_LOGIN_REGISTER_EXIT);
        show(LOGIN);
        show(REGISTER);
        show(System.lineSeparator() + EXIT);
        show(LINE);
    }

    /**
     * Asks user to register as the username does not exist in the list of cOOPer's registered users.
     */
    public static void showPleaseRegisterMessage() {
        show(LINE);
        show(PLEASE_REGISTER);
        show(LINE);
    }

    /**
     * Asks user to login as the username already exists in the list of cOOPer's registered users.
     */
    public static void showPleaseLoginMessage() {
        show(LINE);
        show(PLEASE_LOGIN);
        show(LINE);
    }

    /**
     * Informs the user of their successful registration.
     * @param username User's name
     * @param userRole User's role
     */
    public static void showRegisteredSuccessfullyMessage(String username, UserRole userRole) {
        String userRoleAsString = userRole.toString();
        show(LINE);
        show(username + REGISTERED_SUCCESSFULLY + userRoleAsString + EXCLAMATION);
        show(LINE);
    }

    /**
     * Informs the user of their successful login to cOOPer.
     * @param username User's name
     */
    public static void showLoggedInSuccessfullyMessage(String username) {
        show(LINE);
        show(LOGGED_IN_SUCCESSFULLY + username + EXCLAMATION);
        show(LINE);
    }

    /**
     * Informs the user that they are not logging in with the role they registered with.
     */
    public static void showIncorrectRoleMessage() {
        show(LINE);
        show(INCORRECT_ROLE);
        show(LINE);
    }

    /**
     * Informs the user that the role they are signing in with is neither admin nor employee.
     */
    public static void showInvalidUserRoleError() {
        show(LINE);
        show(INVALID_ROLE);
        show(LINE);
    }

    /**
     * Informs user that the password entered is not the password the user registered with.
     */
    public static void showIncorrectPasswordError() {
        show(LINE);
        show(INCORRECT_PASSWORD);
        show(LINE);
    }

    /**
     * Informs the user that the user is taking on a role which does not have access to a certain feature.
     */
    public static void showNoAccessError() {
        show(LINE);
        show(NO_ACCESS);
        show(LINE);
    }

    /**
     * Informs the user that they are logged out of cOOPer.
     */
    public static void showLogoutMessage() {
        show(LINE);
        show(LOGGED_OUT);
        show(LOGIN);
        show(REGISTER);
        show(System.lineSeparator() + EXIT);
        show(LINE);
    }
}
