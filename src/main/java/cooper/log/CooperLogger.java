package cooper.log;

import java.util.logging.FileHandler;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.Level;

//@@author fansxx

public class CooperLogger {
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void setupLogger() {
        LogManager.getLogManager().reset();

        // set up file handler
        try {
            FileHandler fileHandler = new FileHandler("cooperLogs.log");
            fileHandler.setFormatter(new SimpleFormatter());
            fileHandler.setLevel(Level.INFO);
            logger.addHandler(fileHandler);
        } catch (java.io.IOException e) {
            logger.log(Level.SEVERE, "File logger not working.");
        }
    }
}
