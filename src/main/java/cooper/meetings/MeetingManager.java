package cooper.meetings;

import cooper.exceptions.DuplicateUsernameException;
import cooper.exceptions.InvalidTimeException;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.logging.Logger;

public class MeetingManager {
    private static final String TIME_FORMAT = "HH:mm";
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private final TreeMap<LocalTime, ArrayList<String>> meetings;

    public MeetingManager() {
        meetings = new TreeMap<>();
    }

    private boolean isValidTimeFormat(String value) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_FORMAT);
        try {
            LocalTime lt = LocalTime.parse(value, formatter);
            String result = lt.format(formatter);
            return result.equals(value);
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public TreeMap<LocalTime, ArrayList<String>> getMeetings() {
        return meetings;
    }

    public void addAvailability(String time, String name) throws DuplicateUsernameException, InvalidTimeException {
        LocalTime localTime;
        if (isValidTimeFormat(time)) {
            localTime = LocalTime.parse(time, DateTimeFormatter.ofPattern(TIME_FORMAT));
        } else {
            throw new InvalidTimeException();
        }

        // if there is no time yet, create new timing
        if (!meetings.containsKey(localTime)) {
            assert !meetings.containsKey(localTime) : "there is no localTime object in meetings yet";
            meetings.put(localTime, new ArrayList<>());
            LOGGER.info("A new time is created: " + time);
        }

        // check if the value is a duplicate
        if (!meetings.get(localTime).contains(name)) {
            assert !meetings.get(localTime).contains(name) : "there is no " + name + " in meetings yet";
            meetings.get(localTime).add(name);
            LOGGER.info(name + " has been added to " + time);
        } else {
            throw new DuplicateUsernameException();
        }
    }
}
