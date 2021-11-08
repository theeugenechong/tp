package cooper.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.dopsun.chatbot.cli.Argument;
import com.dopsun.chatbot.cli.ParseResult;

import cooper.CooperState;
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
import cooper.exceptions.InvalidAddFormatException;
import cooper.exceptions.InvalidCommandFormatException;
import cooper.exceptions.InvalidDocumentException;
import cooper.exceptions.InvalidScheduleFormatException;
import cooper.exceptions.NoTimeEnteredException;
import cooper.exceptions.NoUsernameAfterCommaException;
import cooper.exceptions.UnrecognisedCommandException;
import cooper.finance.FinanceCommand;
import cooper.ui.Ui;

//@@author Rrraaaeee

@SuppressWarnings({"OptionalGetWithoutIsPresent", "SwitchStatementWithTooFewBranches"})
public class CommandParser extends ParserBase {

    private static CommandParser commandParserImpl = null;

    // when command parser is called, user is already logged in
    private static CooperState cooperState = CooperState.LOGIN;
    private static final String BS = "bs";
    private static final String CF = "cf";
    private static final String DOCUMENT_HINT = "document-hint";
    private static final String PARSER_SCHEMA = "command-data.properties"; 
    private static final String LIST = "list";
    private static final String HELP = "help";
    private static final String AVAILABILITY = "availability";
    private static final String MEETINGS = "meetings";
    private static final String EXIT = "exit";
    private static final String LOGOUT = "logout";
    private static final String ADD = "add";
    private static final String AVAILABLE = "available";
    private static final String SCHEDULE = "schedule";
    private static final String POST = "post";
    private static final String GENERATE = "generate";
    private static final String PROJ = "proj";
    private static final String POSTADD = "postAdd";
    private static final String POSTDELETE = "postDelete";
    private static final String POSTCOMMENT = "postComment";
    private static final String POSTLIST = "postList";


    private CommandParser()  {
        super(PARSER_SCHEMA);
    }

    public static boolean isLogout() {
        return cooperState == CooperState.LOGOUT;
    }

    public static void setCooperState(CooperState state) {
        cooperState = state;
        Ui.updatePromptState(state);
    }

    /**
     * API to parse a string input into a command object.
     * @param input user input
     * @return the command object
     */
    public static Command parse(String input) throws UnrecognisedCommandException, NoSuchElementException,
            InvalidCommandFormatException, InvalidScheduleFormatException, NoTimeEnteredException,
            NoUsernameAfterCommaException, InvalidDocumentException, InvalidAddFormatException {
        if (commandParserImpl == null) {
            commandParserImpl = new CommandParser();
        }
        Command command = commandParserImpl.parseInput(input);
        Ui.updatePromptState(cooperState);
        return command;
    }

    /**
     * Impl for parse() method.
     * @param input command to be parsed
     * @return the command object
     */
    @Override
    public Command parseInput(String input) throws UnrecognisedCommandException, NoSuchElementException,
            InvalidCommandFormatException, InvalidScheduleFormatException, NoTimeEnteredException,
            NoUsernameAfterCommaException, InvalidDocumentException, InvalidAddFormatException {
        assert input != null;
        String commandWord = input.split(WHITESPACE_SEQUENCE)[0].toLowerCase();

        switch (commandWord) {
        case LIST:
        case HELP:
        case AVAILABILITY:
        case MEETINGS:
        case EXIT:
        case BS:
        case CF:
        case LOGOUT:
            return parseSimpleInput(commandWord);
        case ADD:
        case AVAILABLE:
        case SCHEDULE:
        case POST:
        case GENERATE:
        case PROJ:
            return parseComplexInput(input);
        default:
            throw new UnrecognisedCommandException();
        }
    }

    /**
     * Method to parse single-word input.
     * @param commandWord single-word input string
     * @return a command object
     */
    private Command parseSimpleInput(String commandWord) throws UnrecognisedCommandException {
        assert commandWord != null;
        switch (commandWord) {
        case LIST:
            return new ListCommand(FinanceCommand.getCommandFromState(cooperState));
        case HELP:
            return new HelpCommand();
        case AVAILABILITY:
            cooperState = CooperState.LOGIN;
            return new AvailabilityCommand();
        case MEETINGS:
            cooperState = CooperState.LOGIN;
            return new MeetingsCommand();
        case LOGOUT:
            return new LogoutCommand();
        case EXIT:
            return new ExitCommand();
        case CF:
            return new CfCommand();
        case BS:
            return new BsCommand();
        default:
            throw new UnrecognisedCommandException();
        }
    }

    /**
     * Method to parse a multi-word input. Using the Dopsun cli library
     * @param input multi-word input
     * @return a command object
     */
    private Command parseComplexInput(String input) throws UnrecognisedCommandException, NoSuchElementException,
            InvalidCommandFormatException, InvalidScheduleFormatException, NoTimeEnteredException,
            NoUsernameAfterCommaException, InvalidDocumentException, InvalidAddFormatException {
        Optional<ParseResult> optResult = parser.tryParse(input);
        if (optResult.isPresent()) {
            var result = optResult.get();
            String command = result.allCommands().get(0).name();
            List<Argument> commandArgs = result.allCommands().get(0).arguments();
            switch (command) {
            case ADD:
                return parseAddArgs(commandArgs);
            case AVAILABLE:
                cooperState = CooperState.LOGIN;
                return parseAvailableArgs(commandArgs);
            case SCHEDULE:
                cooperState = CooperState.LOGIN;
                return parseScheduleArgs(commandArgs);
            case POSTADD:
                cooperState = CooperState.LOGIN;
                return parsePostAddArgs(commandArgs);
            case POSTDELETE:
                cooperState = CooperState.LOGIN;
                return parsePostDeleteArgs(commandArgs);
            case POSTCOMMENT:
                cooperState = CooperState.LOGIN;
                return parsePostCommentArgs(commandArgs);
            case POSTLIST:
                cooperState = CooperState.LOGIN;
                return parsePostListArgs(commandArgs);
            case GENERATE:
                return parseGenerateArgs(commandArgs);
            case PROJ:
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
            NumberFormatException, InvalidAddFormatException {
        String amountAsString;
        int amount = 0;
        boolean isInflow = true;

        for (Argument a : commandArgs) {
            String argName = a.name();
            String argVal = a.value().get();
            if (argName.equals("amount-hint") && !argVal.contains("-")) {
                if (argVal.charAt(0) == '(' && argVal.charAt(argVal.length() - 1) == ')') {
                    isInflow = false;
                    amountAsString = argVal.substring(1, argVal.length() - 1);
                } else {
                    isInflow = true;
                    amountAsString = argVal;
                }
                amount = Integer.parseInt(amountAsString);
            } else {
                throw new InvalidAddFormatException();
            }
        }
        return new AddCommand(amount, isInflow, FinanceCommand.getCommandFromState(cooperState));

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
            NoSuchElementException, InvalidScheduleFormatException, NoTimeEnteredException,
            NoUsernameAfterCommaException {

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

    /**
     * Gets the usernames in the schedule command.
     *
     * @param args the arguments after the /with
     * @return an ArrayList of usernames detected in the command argument
     * @throws InvalidScheduleFormatException if there are no arguments after /with and before /at
     * @throws NoUsernameAfterCommaException if there are no usernames after the last comma
     */
    private ArrayList<String> parseUsernamesInSchedule(String args) throws InvalidScheduleFormatException,
            NoUsernameAfterCommaException {
        if (args.length() < 1) {
            throw new InvalidScheduleFormatException();
        }

        String[] usernamesArray;
        if (args.contains(",")) {
            if (args.endsWith(",")) {
                throw new NoUsernameAfterCommaException();
            }
            usernamesArray = args.split(",");
            if (usernamesArray.length < 1) {
                throw new InvalidScheduleFormatException();
            }
        } else {
            usernamesArray = new String[1];
            usernamesArray[0] = args;
        }

        ArrayList<String> usernamesArrayList = new ArrayList<>();
        for (String s : usernamesArray) {
            String trimmedUsername = s.trim();
            // if the command args contain the time, get only the last username and add it to the list
            getLastUsername(usernamesArrayList, trimmedUsername);
        }
        return usernamesArrayList;
    }

    /**
     * Gets the last username in the schedule command.
     *
     * @param usernamesArrayList the list of usernames to add this last username to
     * @param trimmedUsername the last argument of the schedule command (after the last comma)
     * @throws NoUsernameAfterCommaException if there are no usernames after the last comma
     */
    private void getLastUsername(ArrayList<String> usernamesArrayList, String trimmedUsername) throws
            NoUsernameAfterCommaException {
        if (trimmedUsername.contains("/at")) {
            String[] lastUsernameAndTime = trimmedUsername.split("/at");
            if (lastUsernameAndTime[0].length() < 1) {
                throw new NoUsernameAfterCommaException();
            } else {
                usernamesArrayList.add(lastUsernameAndTime[0].trim());
            }
        } else {
            usernamesArrayList.add(trimmedUsername);
        }
    }

    /**
     * Gets the time parameter in the schedule command.
     *
     * @param args thea argument after the /with
     * @return a String that corresponds to the time. A null string is returned if there is no time
     * @throws NoTimeEnteredException if there is no time entered after /at
     */
    private String parseTimeInSchedule(String args) throws NoTimeEnteredException {
        if (args.contains("/at")) {
            String[] argsArray = args.split("/at");
            if (argsArray.length < 2) {
                throw new NoTimeEnteredException();
            } else {
                return argsArray[1].trim();
            }
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

        Integer postId = null;
        for (Argument a : commandArgs) {
            String argName = a.name();
            String argVal = a.value().get();
            switch (argName) {
            case "list-hint":
                if (argVal.trim().equals("all")) {
                    postId = null; // list all
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
    /**
     * Parses the {@code generate} command to identify the document to generate as PDF.
     * @return a {@code GenerateCommand} containing the document the user wants to generate
     * @throws NoSuchElementException if the command is missing arguments
     * @throws InvalidCommandFormatException if the command is of the wrong format
     */
    private Command parseGenerateArgs(List<Argument> commandArgs) throws NoSuchElementException,
            InvalidCommandFormatException, InvalidDocumentException {
        String documentToGenerate = null;
        for (Argument a : commandArgs) {
            String argName = a.name();
            String argVal = a.value().get();
            switch (argName) {
            case DOCUMENT_HINT:
                documentToGenerate = checkDocToGenerate(argVal);
                break;
            default:
                throw new InvalidCommandFormatException();
            }
        }
        return new GenerateCommand(documentToGenerate);
    }

    /**
     * Helper method to determine is the user argument is one of bs or cf.
     */
    private String checkDocToGenerate(String doc) throws InvalidDocumentException {
        if (doc.trim().equalsIgnoreCase(BS)) {
            return BS;
        } else if (doc.trim().equalsIgnoreCase(CF)) {
            return CF;
        } else {
            throw new InvalidDocumentException();
        }
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
        return new ProjectionCommand(years);
    }
}
