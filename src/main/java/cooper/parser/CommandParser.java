package cooper.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.dopsun.chatbot.cli.Argument;
import com.dopsun.chatbot.cli.ParseResult;

import cooper.command.AddCommand;
import cooper.command.AvailabilityCommand;
import cooper.command.AvailableCommand;
import cooper.command.BsCommand;
import cooper.command.CfCommand;
import cooper.command.Command;
import cooper.command.ExitCommand;
import cooper.command.GenerateCommand;
import cooper.command.HelpCommand;
import cooper.command.ListCommand;
import cooper.command.LogoutCommand;
import cooper.command.MeetingsCommand;
import cooper.command.PostAddCommand;
import cooper.command.PostCommentCommand;
import cooper.command.PostDeleteCommand;
import cooper.command.PostListCommand;
import cooper.command.ProjectionCommand;
import cooper.command.ScheduleCommand;
import cooper.exceptions.InvalidCommandFormatException;
import cooper.exceptions.UnrecognisedCommandException;
import cooper.finance.FinanceCommand;

//@@author Rrraaaeee

@SuppressWarnings({"OptionalGetWithoutIsPresent", "SwitchStatementWithTooFewBranches"})
public class CommandParser extends ParserBase {

    private static CommandParser commandParserImpl = null;
    public static FinanceCommand financeFlag = FinanceCommand.IDLE;

    /**
     * Constructor. Initialise internal parser.
     */
    private CommandParser()  {
        super();
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
        case "availability":
        case "meetings":
        case "exit":
        case "bs":
        case "cf":
        case "logout":
            return parseSimpleInput(commandWord);
        case "add":
        case "available":
        case "schedule":
        case "post":
        case "generate":
        case "proj":
            return parseComplexInput(input);
        default:
            throw new UnrecognisedCommandException();
        }
    }

    private Command parseSimpleInput(String commandWord) throws UnrecognisedCommandException {
        assert commandWord != null;
        switch (commandWord) {
        case "list":
            return new ListCommand(financeFlag);
        case "help":
            return new HelpCommand();
        case "availability":
            return new AvailabilityCommand();
        case "meetings":
            return new MeetingsCommand();
        case "logout":
            return new LogoutCommand();
        case "exit":
            return new ExitCommand();
        case "cf":
            return new CfCommand();
        case "bs":
            financeFlag = FinanceCommand.BS;
            return new BsCommand();
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
            case "add":
                return parseAddArgs(commandArgs);
            case "available":
                return parseAvailableArgs(commandArgs);
            case "schedule":
                return parseScheduleArgs(commandArgs);
            case "postAdd":
                return parsePostAddArgs(commandArgs);
            case "postDelete":
                return parsePostDeleteArgs(commandArgs);
            case "postComment":
                return parsePostCommentArgs(commandArgs);
            case "postList":
                return parsePostListArgs(commandArgs);
            case "generate":
                return parseGenerateArgs(commandArgs);
            case "proj":
                financeFlag = FinanceCommand.PROJ;
                return parseProjectionArgs(commandArgs);
            default:
                throw new UnrecognisedCommandException();
            }
        } else {
            throw new InvalidCommandFormatException();
        }
    }

    //@@author ChrisLangton
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
        return new AddCommand(amount, isInflow, financeFlag);

    }

    //@@author fansxx
    private Command parseAvailableArgs(List<Argument> commandArgs) throws NoSuchElementException,
            InvalidCommandFormatException {
        String time = "";

        for (Argument a : commandArgs) {
            String argName = a.name();
            String argVal = a.value().get();
            switch (argName) {
            case "time-hint":
                time = argVal;
                break;
            default:
                throw new InvalidCommandFormatException();
            }
        }
        return new AvailableCommand(time);
    }

    private Command parseScheduleArgs(List<Argument> commandArgs) throws InvalidCommandFormatException,
            NoSuchElementException {
        String meetingName = null;
        ArrayList<String> usernames = new ArrayList<>();
        String time = null;
        for (Argument a : commandArgs) {
            String argName = a.name();
            String argVal = a.value().get();
            switch (argName) {
            case "meeting-hint":
                meetingName = argVal;
                break;
            case "usernames-hint":
                usernames = parseUsernamesInSchedule(argVal);
                time = parseTimeInSchedule(argVal);
                break;
            default:
                throw new InvalidCommandFormatException();
            }
        }
        return new ScheduleCommand(meetingName, usernames, time);
    }

    private ArrayList<String> parseUsernamesInSchedule(String args) throws InvalidCommandFormatException {
        if (!args.contains(",")) {
            throw new InvalidCommandFormatException();
        }

        String[] usernamesArray = args.split(",");
        ArrayList<String> usernamesArrayList = new ArrayList<>();
        for (String s : usernamesArray) {
            String trimmedUsername = s.trim();
            // if the command args contain the time, get only the last username and add it to the list
            getLastUsername(usernamesArrayList, trimmedUsername);
        }
        return usernamesArrayList;
    }

    private void getLastUsername(ArrayList<String> usernamesArrayList, String trimmedUsername) {
        if (trimmedUsername.contains("/at")) {
            String[] lastUsernameAndTime = trimmedUsername.split("/at");
            usernamesArrayList.add(lastUsernameAndTime[0].trim());
        } else {
            usernamesArrayList.add(trimmedUsername);
        }
    }

    private String parseTimeInSchedule(String args) {
        if (args.contains("/at")) {
            String[] argsArray = args.split("/at");
            return argsArray[1].trim();
        } else {
            return null;
        }
    }

    //@@author Rrraaaeee
    private Command parsePostAddArgs(List<Argument> commandArgs) throws NoSuchElementException,
            NumberFormatException, InvalidCommandFormatException {
        String content = "";

        for (Argument a : commandArgs) {
            String argName = a.name();
            String argVal = a.value().get();
            switch (argName) {
            case "content-hint":
                content = argVal;
                break;
            default:
                throw new InvalidCommandFormatException();
            }
        }
        return new PostAddCommand(content);
    }

    private Command parsePostDeleteArgs(List<Argument> commandArgs) throws NoSuchElementException,
            NumberFormatException, InvalidCommandFormatException {
        int postId = -1;

        for (Argument a : commandArgs) {
            String argName = a.name();
            String argVal = a.value().get();
            switch (argName) {
            case "index-hint":
                postId = Integer.parseInt(argVal);
                break;
            default:
                throw new InvalidCommandFormatException();
            }
        }
        return new PostDeleteCommand(postId);
    }

    private Command parsePostCommentArgs(List<Argument> commandArgs) throws NoSuchElementException,
            NumberFormatException, InvalidCommandFormatException {
        String content = "";
        int postId = -1;

        for (Argument a : commandArgs) {
            String argName = a.name();
            String argVal = a.value().get();
            switch (argName) {
            case "content-hint":
                content = argVal;
                break;
            case "index-hint":
                postId = Integer.parseInt(argVal);
                break;
            default:
                throw new InvalidCommandFormatException();
            }
        }
        return new PostCommentCommand(postId,content);
    }

    private Command parsePostListArgs(List<Argument> commandArgs) throws InvalidCommandFormatException,
            NumberFormatException {
        int postId = -1;
        for (Argument a : commandArgs) {
            String argName = a.name();
            String argVal = a.value().get();
            switch (argName) {
            case "list-hint":
                if (argVal.equals("all")) {
                    postId = -1; // list all
                } else {
                    postId = Integer.parseInt(argVal);
                }
                break;
            default:
                throw new InvalidCommandFormatException();
            }
        }
        return new PostListCommand(postId);
    }

    //@@author theeugenechong
    private Command parseGenerateArgs(List<Argument> commandArgs) throws NoSuchElementException,
            InvalidCommandFormatException {
        String documentToGenerate = null;
        for (Argument a : commandArgs) {
            String argName = a.name();
            String argVal = a.value().get();
            switch (argName) {
            case "document-hint":
                if (isValidDocToGenerate(argVal)) {
                    documentToGenerate = argVal.trim().toLowerCase();
                } else {
                    throw new InvalidCommandFormatException();
                }
                break;
            default:
                throw new InvalidCommandFormatException();
            }
        }
        return new GenerateCommand(documentToGenerate);
    }

    private boolean isValidDocToGenerate(String doc) {
        return doc.trim().equalsIgnoreCase("bs") || doc.trim().equalsIgnoreCase("cf");
    }

    //@@author ChrisLangton
    private Command parseProjectionArgs(List<Argument> commandArgs) throws InvalidCommandFormatException,
            NumberFormatException {
        int years = 0;
        for (Argument a : commandArgs) {
            String argName = a.name();
            String argVal = a.value().get();
            switch (argName) {
            case "years-hint":
                years = Integer.parseInt(argVal);
                break;
            default:
                throw new InvalidCommandFormatException();
            }
        }
        return new ProjectionCommand(years, financeFlag);
    }
}
