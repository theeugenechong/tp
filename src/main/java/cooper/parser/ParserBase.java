package cooper.parser;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.dopsun.chatbot.cli.Parser;
import com.dopsun.chatbot.cli.ParserBuilder;
import com.dopsun.chatbot.cli.input.CommandSet;
import com.dopsun.chatbot.cli.input.FileCommandSet;
import com.dopsun.chatbot.cli.input.FileTrainingSet;
import com.dopsun.chatbot.cli.input.TrainingSet;
import cooper.exceptions.InvalidArgumentException;
import cooper.exceptions.UnrecognisedCommandException;

public abstract class ParserBase {

    /**
     * Constructor. Initialise internal parser.
     */
    public ParserBase() {
    }

    /**
     * API to parse a string.
     * @param input command to be parsed
     * @return
     */
    public abstract Object parse(String input) throws UnrecognisedCommandException, InvalidArgumentException;

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
        // parserBuilder.setTracer(System.out::println);
        return parserBuilder.build();

    }



}
