package cooper.parser;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.dopsun.chatbot.cli.Argument;
import com.dopsun.chatbot.cli.ParseResult;
import com.dopsun.chatbot.cli.Parser;

import cooper.command.AddCommand;
import cooper.command.AvailableCommand;
import cooper.command.Command;
import cooper.command.ExitCommand;
import cooper.command.ListCommand;
import cooper.command.MeetingsCommand;
import cooper.command.HelpCommand;
import cooper.exceptions.InvalidCommandFormatException;
import cooper.exceptions.InvalidUserRoleException;
import cooper.exceptions.UnrecognisedCommandException;
import cooper.ui.Ui;
import cooper.util.Util;
import cooper.verification.PasswordHasher;
import cooper.verification.SignInProtocol;
import cooper.verification.Login;
import cooper.verification.Registration;
import cooper.verification.SignInDetails;
import cooper.verification.UserRole;


@SuppressWarnings("OptionalGetWithoutIsPresent")
public class CommandParser extends ParserBase {

    private static CommandParser commandParserImpl = null;
    private Parser parser;

    /**
     * Constructor. Initialise internal parser.
     */
    private CommandParser()  {
        super();

        try {
            InputStream commandSetInputStream = this.getClass().getResourceAsStream("/parser/command-data.properties");

            File commandSetTmpFile = Util.inputStreamToTmpFile(commandSetInputStream,
                    System.getProperty("user.dir") + "/tmp", "/tmp_file_command.txt");

            InputStream trainingPathInputStream = this.getClass().getResourceAsStream("/parser/training-data.yml");
            File trainingTmpFile = Util.inputStreamToTmpFile(trainingPathInputStream,
                    System.getProperty("user.dir") + "/tmp", "/tmp_file_training.txt");

            parser = prepareParser(commandSetTmpFile.getPath(), trainingTmpFile.getPath());

        } catch (IOException | URISyntaxException e) {
            Ui.showText("Error encountered when creating temp file: "
                    + System.getProperty("user.dir") + "/tmp" + "/tmp_file_command.txt" + " or "
                    + System.getProperty("user.dir") + "/tmp" + "/tmp_file_training.txt");
        }
    }

    /**
     * API to parse a command in string.
     * @param input command to be parsed
     * @return a command object, to be passed into command handler
     */
    public static Command parse(String input) throws UnrecognisedCommandException, NoSuchElementException,
            InvalidCommandFormatException {
        if (commandParserImpl == null) {
            commandParserImpl = new CommandParser();
        }
        return commandParserImpl.parseInput(input);
    }

    public Command parseInput(String input) throws UnrecognisedCommandException, NoSuchElementException,
            InvalidCommandFormatException {
        assert input != null;
        String commandWord = input.split("\\s+")[0].toLowerCase();

        switch (commandWord) {
        case "list":
        case "help":
        case "meetings":
        case "exit":
            return parseSimpleInput(commandWord);
        case "add":
        case "available":
            return parseComplexInput(input);
        default:
            throw new UnrecognisedCommandException();
        }
    }

    private Command parseSimpleInput(String input) throws UnrecognisedCommandException {
        assert input != null;
        switch (input) {
        case "list":
            return new ListCommand();
        case "help":
            return new HelpCommand();
        case "meetings":
            return new MeetingsCommand();
        case "exit":
            return new ExitCommand();
        default:
            throw new UnrecognisedCommandException();
        }
    }

    private Command parseComplexInput(String input) throws UnrecognisedCommandException, NoSuchElementException,
            InvalidCommandFormatException {
        Optional<ParseResult> optResult = parser.tryParse(input);
        if (optResult.isPresent()) {
            var result = optResult.get();
            String command = result.allCommands().get(0).name();
            // String template = res.allCommands().get(0).template();
            List<Argument> commandArgs = result.allCommands().get(0).arguments();
            switch (command) {
            case "available":
                return parseAvailableArgs(commandArgs);
            case "add":
                return parseAddArgs(commandArgs);
            default:
                throw new UnrecognisedCommandException();
            }
        } else {
            throw new InvalidCommandFormatException();
        }
    }

    private Command parseAddArgs(List<Argument> commandArgs) throws NoSuchElementException,
            NumberFormatException, InvalidCommandFormatException {
        String amountAsString;
        int amount = 0;
        boolean isInflow = true;

        for (Argument a : commandArgs) {
            String argName = a.name();
            String argVal = a.value().get();
            if (argName.equals("amount-hint")) {
                if (argVal.charAt(0) == '(' && argVal.charAt(argVal.length() - 1) == ')') {
                    isInflow = false;
                    amountAsString = argVal.substring(1, argVal.length() - 1);
                } else {
                    isInflow = true;
                    amountAsString = argVal;
                }
                amount = Integer.parseInt(amountAsString);
            } else {
                throw new InvalidCommandFormatException();
            }
        }
        return new AddCommand(amount, isInflow);
    }

    private Command parseAvailableArgs(List<Argument> commandArgs) throws NoSuchElementException,
            InvalidCommandFormatException {
        String time = "";
        String username = "";

        for (Argument a : commandArgs) {
            String argName = a.name();
            String argVal = a.value().get();
            switch (argName) {
            case "username-hint":
                username = argVal;
                break;
            case "time-hint":
                time = argVal;
                break;
            default:
                throw new InvalidCommandFormatException();
            }
        }
        return new AvailableCommand(time, username);
    }
}
