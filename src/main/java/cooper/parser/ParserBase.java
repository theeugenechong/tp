package cooper.parser;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.dopsun.chatbot.cli.Parser;
import com.dopsun.chatbot.cli.ParserBuilder;
import com.dopsun.chatbot.cli.input.CommandSet;
import com.dopsun.chatbot.cli.input.FileCommandSet;
import com.dopsun.chatbot.cli.input.FileTrainingSet;
import com.dopsun.chatbot.cli.input.TrainingSet;
import cooper.exceptions.InvalidCommandFormatException;
import cooper.exceptions.InvalidUserRoleException;
import cooper.exceptions.UnrecognisedCommandException;
import cooper.ui.ParserUi;
import cooper.util.Util;

public abstract class ParserBase {

    protected Parser parser;

    /**
     * Constructor. Initialise internal parser.
     */
    public ParserBase() {
        try {
            InputStream commandSetInputStream = this.getClass().getResourceAsStream("/parser/command-data.properties");

            File commandSetTmpFile = Util.inputStreamToTmpFile(commandSetInputStream,
                    System.getProperty("user.dir") + "/tmp", "/tmp_file_command.txt");

            InputStream trainingPathInputStream = this.getClass().getResourceAsStream("/parser/training-data.yml");
            File trainingTmpFile = Util.inputStreamToTmpFile(trainingPathInputStream,
                    System.getProperty("user.dir") + "/tmp", "/tmp_file_training.txt");

            parser = prepareParser(commandSetTmpFile.getPath(), trainingTmpFile.getPath());

        } catch (IOException | URISyntaxException e) {
            ParserUi.showTmpFileCreationError();
        }
    }

    /**
     * API to parse a string.
     * @param input command to be parsed
     */
    public abstract Object parseInput(String input) throws UnrecognisedCommandException, InvalidCommandFormatException,
            InvalidUserRoleException;

    /**
     * A parser takes in a schema. Every child class needs to have its own schema.
     */
    protected Parser prepareParser(String commandSetPath, String trainingPath) throws URISyntaxException {
        // File.separator
        Path csPath = Paths.get(new File(commandSetPath).toURI());
        CommandSet commandSet = new FileCommandSet(csPath);
        Path tsPath = Paths.get(new File(trainingPath).toURI());
        TrainingSet trainingSet = new FileTrainingSet(tsPath);

        ParserBuilder parserBuilder = Parser.newBuilder();
        parserBuilder.addCommandSet(commandSet);
        parserBuilder.addTrainingSet(trainingSet);
        return parserBuilder.build();

    }



}
