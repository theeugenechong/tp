package cooper.ui;

import cooper.verification.UserRole;

//@@author theeugenechong

public class VerificationUi extends Ui {

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

    public static void showRegisteredSuccessfullyMessage(String username, UserRole userRole) {
        String userRoleAsString = userRole.toString();
        show(LINE);
        show(username + " is now successfully registered as an " + userRoleAsString + "!");
        show(LINE);
    }

    public static void showLoggedInSuccessfullyMessage(String username) {
        show(LINE);
        show("You are now logged in successfully as " + username + "!");
        show(LINE);
    }

    public static void showIncorrectRoleMessage() {
        show(LINE);
        show("You are logging in with an incorrect role! Please try again.");
        show(LINE);
    }

    public static void showInvalidUserRoleError() {
        show(LINE);
        show("Invalid role entered! Role can only be admin or employee.");
        show(LINE);
    }

    public static void showIncorrectPasswordError() {
        show(LINE);
        show("Incorrect password entered! Please try again.");
        show(LINE);
    }

    public static void printNoAccessError() {
        show(LINE);
        show("You do not have access to this command.");
        show(LINE);
    }
}
