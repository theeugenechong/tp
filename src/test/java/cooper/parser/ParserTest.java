package cooper.parser;

import cooper.exceptions.InvalidCommandFormatException;
import cooper.exceptions.InvalidUserRoleException;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

import cooper.exceptions.UnrecognisedCommandException;

//@@author Rrraaaeee

public class ParserTest {

    @Test
    @Order(1)
    void parse_unnaturalInput_throwsUnrecognisedCommandException() {
        assertThrows(UnrecognisedCommandException.class, () ->
                CommandParser.parse("foo 123"));

        assertThrows(UnrecognisedCommandException.class, () ->
                CommandParser.parse("$%^&&"));
    }

    @Test
    void parse_unnaturalInput_exceptionThrown() {
        assertThrows(NumberFormatException.class, () ->
                CommandParser.parse("add $%^&"));

        assertThrows(InvalidCommandFormatException.class, () ->
                CommandParser.parse("proj"));
    }

    @Test
    void parseSignInDetails_emptyArguments_exceptionThrown() {
        assertThrows(InvalidCommandFormatException.class, () ->
                SignInDetailsParser.parse("login as admin"));

        assertThrows(InvalidCommandFormatException.class, () ->
                SignInDetailsParser.parse("login Topias as"));

        assertThrows(InvalidCommandFormatException.class, () ->
                CommandParser.parse("available"));
    }

    @Test
    void parseSignInDetails_invalidRole_throwsInvalidUserRoleException() {
        assertThrows(InvalidUserRoleException.class, () ->
                SignInDetailsParser.parse("login Topias /pw 1111 /as abc"));

        assertThrows(InvalidUserRoleException.class, () ->
                SignInDetailsParser.parse("register Martin /pw 1111 /as boss"));
    }
}
