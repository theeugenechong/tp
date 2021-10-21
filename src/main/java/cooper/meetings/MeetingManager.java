package cooper.meetings;

import cooper.exceptions.CannotScheduleMeetingException;
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
    private final TreeMap<LocalTime, ArrayList<String>> availability;
    private final ArrayList<Meeting> meetingsList = new ArrayList<>();

    public MeetingManager() {
        availability = new TreeMap<>();
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

    public TreeMap<LocalTime, ArrayList<String>> getAvailability() {
        return availability;
    }

    public void addAvailability(String time, String name) throws DuplicateUsernameException, InvalidTimeException {
        LocalTime localTime;
        if (isValidTimeFormat(time)) {
            localTime = LocalTime.parse(time, DateTimeFormatter.ofPattern(TIME_FORMAT));
        } else {
            throw new InvalidTimeException();
        }

        // if there is no time yet, create new timing
        if (!availability.containsKey(localTime)) {
            assert !availability.containsKey(localTime) : "there is no localTime object in meetings yet";
            availability.put(localTime, new ArrayList<>());
            LOGGER.info("A new time is created: " + time);
        }

        // check if the value is a duplicate
        if (!availability.get(localTime).contains(name)) {
            assert !availability.get(localTime).contains(name) : "there is no " + name + " in meetings yet";
            availability.get(localTime).add(name);
            LOGGER.info(name + " has been added to " + time);
        } else {
            throw new DuplicateUsernameException();
        }
    }

    public void autoScheduleMeeting(ArrayList<String> usernames) throws CannotScheduleMeetingException {
        for (LocalTime timing: availability.keySet()) {
            if (availability.get(timing).contains(usernames)) {
                // creates meeting and adds to list of meetings
                Meeting meeting = new Meeting(timing, usernames);
                meetingsList.add(meeting);
                return;
            }
        }
        throw new CannotScheduleMeetingException();
    }

    public void manualScheduleMeeting(ArrayList<String> usernames, String time) throws InvalidTimeException, CannotScheduleMeetingException {
        LocalTime localTime;
        if (isValidTimeFormat(time)) {
            localTime = LocalTime.parse(time, DateTimeFormatter.ofPattern(TIME_FORMAT));
        } else {
            throw new InvalidTimeException();
        }

        if (availability.get(localTime).contains(usernames)) {
            Meeting meeting = new Meeting(localTime, usernames);
            meetingsList.add(meeting);
        } else {
            throw new CannotScheduleMeetingException();
        }
    }
}
