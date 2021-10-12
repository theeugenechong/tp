package cooper.parser;

import cooper.exceptions.InvalidUserRoleException;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

import cooper.exceptions.InvalidArgumentException;
import cooper.exceptions.UnrecognisedCommandException;

import java.util.NoSuchElementException;

public class ParserTest {

    @Test
    @Order(1)
    void parse_unnaturalInput_throwsUnrecognisedCommandException() {
        assertThrows(UnrecognisedCommandException.class, () ->
                CommandParser.parse("foo 123"));

        assertThrows(UnrecognisedCommandException.class, () ->
                CommandParser.parse("$%^&&"));

        assertThrows(UnrecognisedCommandException.class, () ->
                CommandParser.parse("available Eugene at"));

        assertThrows(UnrecognisedCommandException.class, () ->
                CommandParser.parse("meetings $%^&"));
    }

    @Test
    void parse_unnaturalInput_exceptionThrown() {
        assertThrows(NumberFormatException.class, () ->
                CommandParser.parse("add $%^&"));

        assertThrows(NoSuchElementException.class, () ->
                CommandParser.parse("available at 22:53"));
    }

    @Test
    void parseSignInDetails_emptyArguments_exceptionThrown() {
        assertThrows(NoSuchElementException.class, () ->
                CommandParser.parseSignInDetails("login as admin"));

        assertThrows(InvalidArgumentException.class, () ->
                CommandParser.parseSignInDetails("login Topias as"));
    }

    @Test
    void parseSignInDetails_invalidRole_throwsInvalidUserRoleException() {
        assertThrows(InvalidUserRoleException.class, () ->
                CommandParser.parseSignInDetails("login Topias as abc"));

        assertThrows(InvalidUserRoleException.class, () ->
                CommandParser.parseSignInDetails("register Martin as boss"));
    }
}
