package cooper.parser;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import com.dopsun.chatbot.cli.Argument;
import com.dopsun.chatbot.cli.ParseResult;
import com.dopsun.chatbot.cli.Parser;

import cooper.command.AddCommand;
import cooper.command.AvailableCommand;
import cooper.command.Command;
import cooper.command.ExitCommand;
import cooper.command.ListCommand;
import cooper.command.LoginCommand;
import cooper.command.MeetingsCommand;
import cooper.command.HelpCommand;
import cooper.command.UnrecognisedCommand;
import cooper.exceptions.InvalidArgumentException;
import cooper.ui.Ui;
import cooper.util.Util;


public class CommandParser extends ParserBase {

    private Parser parser;

    /**
     * Constructor. Initialise internal parser.
     */
    public CommandParser() throws URISyntaxException {
        super();

        try {
            InputStream commandSetInputStream = this.getClass().getResourceAsStream("/parser/command-data.properties");

            File commandSetTmpFile = Util.inputStreamToTmpFile(commandSetInputStream,
                    System.getProperty("user.dir") + "/tmp", "/tmp_file_command.txt");

            InputStream trainingPathInputStream = this.getClass().getResourceAsStream("/parser/training-data.yml");
            File trainingTmpFile = Util.inputStreamToTmpFile(trainingPathInputStream,
                    System.getProperty("user.dir") + "/tmp", "/tmp_file_training.txt");

            parser = prepareParser(commandSetTmpFile.getPath(), trainingTmpFile.getPath());

        } catch (IOException e) {
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
    public Command parse(String input) throws InvalidArgumentException {
        if (input.split(" ").length < 2) {
            return parseSimpleInput(input);
        } else {
            return parseComplexInput(input);
        }
    }

    private Command parseSimpleInput(String input) {
        switch (input) {
        case "add":
            return new AddCommand();
        case "list":
            return new ListCommand();
        case "help":
            return new HelpCommand();
        case "meetings":
            return new MeetingsCommand();
        case "exit":
            return new ExitCommand();
        default:
            return new UnrecognisedCommand(input);
        }
    }

    private Command parseComplexInput(String input) throws InvalidArgumentException {
        Optional<ParseResult> optResult = parser.tryParse(input);
        if (optResult.isPresent()) {
            var result = optResult.get();
            String command = result.allCommands().get(0).name();
            // String template = res.allCommands().get(0).template();
            List<Argument> commandArgs = result.allCommands().get(0).arguments();
            switch (command) {
            case "available":
                return parseAvailableArgs(commandArgs);
            default:
                return new UnrecognisedCommand(input);
            }
        } else {
            return new UnrecognisedCommand(input);
        }
    }

    private Command parseAvailableArgs(List<Argument> commandArgs) throws InvalidArgumentException {
        Command command = new AvailableCommand();
        /*
        command.setTaskType(TaskType.TODO);
        for (Argument a : commandArgs) {
            String argName = a.name();
            String argVal = a.value().get();
            switch (argName) {
            case "task-hint":
                command.setTaskDescription(argVal);
                break;
            /
            case "date-hint":
                command.setTimeInfo(argVal);
                break;
            /
            default:
                ui.showText("Unrecognised argument for todo: " + argName);
                throw new InvalidArgumentException();
            }
        }
        */
        return command;
    }

}
