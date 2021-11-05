package cooper.parser;

import com.dopsun.chatbot.cli.Argument;
import com.dopsun.chatbot.cli.ParseResult;
import cooper.exceptions.InvalidCommandFormatException;
import cooper.exceptions.InvalidUserRoleException;
import cooper.exceptions.UnrecognisedCommandException;
import cooper.ui.Ui;
import cooper.verification.Login;
import cooper.verification.PasswordHasher;
import cooper.verification.Registration;
import cooper.verification.SignInDetails;
import cooper.verification.SignInProtocol;
import cooper.verification.UserRole;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

//@@author theeugenechong

/**
 * Parser to parse sign in details for verification.
 */
@SuppressWarnings("OptionalGetWithoutIsPresent")
public class SignInDetailsParser extends  ParserBase {


    private static SignInDetailsParser signInDetailsParserImpl = null;

    protected static final String ADMIN = "admin";
    protected static final String EMPLOYEE = "employee";
    protected static final String LOGIN = "login";
    protected static final String REGISTER = "register";
    protected static final String EXIT = "exit";

    /* Constants to help dopsun parser */
    private static final String SIGN_IN_DATA_PROPERTIES = "sign-in-data.properties";
    private static final String USERNAME_HINT = "username-hint";
    private static final String PASSWORD_HINT = "password-hint";
    private static final String ROLE_HINT = "role-hint";


    /**
     * Constructor. Initialise internal parser.
     */
    private SignInDetailsParser()  {
        super(SIGN_IN_DATA_PROPERTIES);
    }

    /**
     * API to parse a command in string.
     * @param input command to be parsed
     * @return a SignInProtocol object, to be passed to verifier
     */
    public static SignInProtocol parse(String input) throws UnrecognisedCommandException, NoSuchElementException,
            InvalidCommandFormatException, InvalidUserRoleException {
        if (signInDetailsParserImpl == null) {
            signInDetailsParserImpl = new SignInDetailsParser();
        }
        assert signInDetailsParserImpl != null;
        return signInDetailsParserImpl.parseInput(input);
    }

    /**
     * Gets the raw password of the user as it is not good to store the raw password in the object itself.
     *
     * @param input user input
     * @return The user's unencrypted password
     */
    public static String parseRawPassword(String input) throws InvalidCommandFormatException {
        if (signInDetailsParserImpl == null) {
            signInDetailsParserImpl = new SignInDetailsParser();
        }
        return signInDetailsParserImpl.parsePassword(input);
    }

    /**
     * Parses the sign in details entered by the user.
     *
     * @param input sign in details to be parsed
     * @return a {@code SignInProtocol} object containing the sign in details of the user signing in
     * @throws InvalidUserRoleException if the user is not signing in with admin or employee
     * @throws UnrecognisedCommandException if the user is entering none of login, register or exit
     * @throws InvalidCommandFormatException if the sign in details are missing some arguments
     */
    @Override
    public SignInProtocol parseInput(String input) throws InvalidUserRoleException, UnrecognisedCommandException,
            InvalidCommandFormatException {
        assert input != null;
        String signInProtocol = input.split(WHITESPACE_SEQUENCE)[0].toLowerCase();

        switch (signInProtocol) {
        case LOGIN:
        case REGISTER:
            return parseSignInDetails(input);
        case EXIT:
            exitProgram();
            return null;
        default:
            throw new UnrecognisedCommandException();
        }
    }

    /**
     * Shows a bye message and exits the program.
     */
    private void exitProgram() {
        Ui.showBye();
        Ui.closeStreams();
        System.exit(0);
    }

    /**
     * Parses the {@code input} and constructs a {@code SignInProtocol} object which represents the sign-in process
     * of the user.
     *
     * @param input user input
     * @return {@code SignInProtocol} containing the users sign in details
     * @throws InvalidUserRoleException if the user is not signing in with admin or employee
     * @throws NoSuchElementException if the arguments are missing from the input
     * @throws InvalidCommandFormatException if the sign in details are of the wrong format
     */
    private SignInProtocol parseSignInDetails(String input) throws UnrecognisedCommandException,
            InvalidUserRoleException, NoSuchElementException, InvalidCommandFormatException {
        Optional<ParseResult> optResult = parser.tryParse(input);
        if (optResult.isPresent()) {
            var result = optResult.get();
            String command = result.allCommands().get(0).name();
            List<Argument> commandArgs = result.allCommands().get(0).arguments();
            switch (command) {
            case LOGIN:
                SignInDetails signInDetails = parseSignInArgs(commandArgs);
                return new Login(signInDetails);
            case REGISTER:
                signInDetails = parseSignInArgs(commandArgs);
                return new Registration(signInDetails);
            default:
                throw new UnrecognisedCommandException();
            }
        } else {
            throw new InvalidCommandFormatException();
        }
    }

    /**
     * Similar to {@code parseSignInDetails} but is a helper method.
     */
    private SignInDetails parseSignInArgs(List<Argument> commandArgs) throws InvalidUserRoleException,
            NoSuchElementException, InvalidCommandFormatException {
        String username = null;
        String userEncryptedPassword = null;
        String userSalt = null;
        UserRole userRole = null;

        for (Argument a : commandArgs) {
            String argName = a.name();
            String argVal = a.value().get();
            switch (argName) {
            case USERNAME_HINT:
                username = argVal;
                break;
            case PASSWORD_HINT:
                userSalt = PasswordHasher.getNewSalt();
                userEncryptedPassword = PasswordHasher.generatePasswordHash(argVal, userSalt);
                break;
            case ROLE_HINT:
                userRole = checkUserRole(argVal);
                break;
            default:
                throw new InvalidCommandFormatException();
            }
        }
        return new SignInDetails(username, userEncryptedPassword, userSalt, userRole);
    }

    /**
     * Helper method to check if the role input by the user is one of admin or employee.
     * @param role role input by the user
     * @return the role the user included in their sign in details
     * @throws InvalidUserRoleException if the role is neither admin nor employee
     */
    private UserRole checkUserRole(String role) throws InvalidUserRoleException {
        UserRole userRole;
        if (role.trim().equalsIgnoreCase(ADMIN)) {
            userRole = UserRole.ADMIN;
        } else if (role.trim().equalsIgnoreCase(EMPLOYEE)) {
            userRole = UserRole.EMPLOYEE;
        } else {
            throw new InvalidUserRoleException();
        }
        return userRole;
    }

    /**
     * Parses the user input to get the raw password of the user.
     * @return a string representing the user's raw password
     */
    private String parsePassword(String input) throws InvalidCommandFormatException, NoSuchElementException {
        Optional<ParseResult> optResult = parser.tryParse(input);
        if (optResult.isPresent()) {
            var result = optResult.get();
            List<Argument> commandArgs = result.allCommands().get(0).arguments();
            return getRawPassword(commandArgs);
        } else {
            throw new InvalidCommandFormatException();
        }
    }

    /**
     * Helper method for {@code parsePassword}.
     */
    private String getRawPassword(List<Argument> commandArgs) throws InvalidCommandFormatException,
            NoSuchElementException {
        String userRawPassword = null;
        for (Argument a : commandArgs) {
            String argName = a.name();
            String argVal = a.value().get();
            switch (argName) {
            case USERNAME_HINT:
            case ROLE_HINT:
                break;
            case PASSWORD_HINT:
                userRawPassword = argVal;
                break;
            default:
                throw new InvalidCommandFormatException();
            }
        }
        return userRawPassword;
    }
}
