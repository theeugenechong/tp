package cooper.meetings;

import cooper.exceptions.DuplicateUsernameException;
import cooper.exceptions.InvalidTimeException;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;

public class MeetingManager {
    public static final String TIME_FORMAT = "HH:mm";
    private final HashMap<LocalTime, ArrayList<String>> meetings;

    public MeetingManager() {
        meetings = new HashMap<>(10);
    }

    private boolean isValidTimeFormat(String value, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        try {
            LocalTime lt = LocalTime.parse(value, formatter);
            String result = lt.format(formatter);
            return result.equals(value);
        } catch (DateTimeParseException e) {

        }
        return false;
    }

    public HashMap<LocalTime, ArrayList<String>> getMeetings() {
        return meetings;
    }

    public void addAvailability(String time, String name) throws DuplicateUsernameException, InvalidTimeException {
        LocalTime localTime;
        if (isValidTimeFormat(time, TIME_FORMAT)) {
            localTime = LocalTime.parse(time, DateTimeFormatter.ofPattern(TIME_FORMAT));
        } else {
            throw new InvalidTimeException();
        }

        // if there is no time yet, create new timing
        if (!meetings.containsKey(localTime)) {
            meetings.put(localTime, new ArrayList<>());
        }

        // check if the value is a duplicate
        if (!meetings.get(localTime).contains(name)) {
            meetings.get(localTime).add(name);
        } else {
            throw new DuplicateUsernameException();
        }
    }
}
