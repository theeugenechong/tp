package cooper.meetings;

import cooper.exceptions.CannotScheduleMeetingException;
import cooper.exceptions.DuplicateMeetingException;
import cooper.exceptions.DuplicateUsernameException;
import cooper.exceptions.InvalidTimeException;
import cooper.ui.MeetingsUi;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.logging.Logger;

//@@author fansxx

public class MeetingManager {
    private static final String TIME_FORMAT = "HH:mm";
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private final TreeMap<LocalTime, ArrayList<String>> availability;
    private final ArrayList<Meeting> meetingsList;

    public MeetingManager() {
        availability = new TreeMap<>();
        meetingsList = new ArrayList<>();
    }

    /**
     * Checks if the time value inputted is in the correct format.
     *
     * @param value time value to be checked if it is in the correct format
     * @return true if the format is the same as TIME_FORMAT, false otherwise
     */
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

    public ArrayList<Meeting> getMeetingsList() {
        return meetingsList;
    }

    /**
     * Gets an arraylist of meetings that is unique to each user.
     *
     * @param username the username of the user that we want to get the meetings of
     * @return ArrayList of meetings the user is in
     */
    public ArrayList<Meeting> getUserSpecificMeetings(String username) {
        ArrayList<Meeting> allMeetings = getMeetingsList();
        ArrayList<Meeting> userSpecificMeetings = new ArrayList<>();
        for (Meeting meeting : allMeetings) {
            if (meeting.getListOfAttendees().contains(username)) {
                userSpecificMeetings.add(meeting);
            }
        }
        return userSpecificMeetings;
    }

    /**
     * Adds the user to the specified time he is available at.
     *
     * @param time the start of the hour the user is available at
     * @param name the username of the user that inputted his availability
     * @throws DuplicateUsernameException if the username has already been declared available at that time
     * @throws InvalidTimeException if the format of the time is not the specified format
     */
    public void addAvailability(String time, String name) throws DuplicateUsernameException, InvalidTimeException {
        LocalTime localTime;
        if (isValidTimeFormat(time)) {
            localTime = LocalTime.parse(time, DateTimeFormatter.ofPattern(TIME_FORMAT));
        } else {
            throw new InvalidTimeException();
        }

        /* if there is no time yet, create new timing */
        if (!availability.containsKey(localTime)) {
            assert !availability.containsKey(localTime) : "there is no localTime object in availability yet";
            availability.put(localTime, new ArrayList<>());
            LOGGER.info("A new time is created: " + time);
        }

        /* check if the value is a duplicate */
        if (!availability.get(localTime).contains(name)) {
            assert !availability.get(localTime).contains(name) : "there is no " + name + " in availability yet";
            availability.get(localTime).add(name);
            LOGGER.info(name + " has been added to " + time);
        } else {
            throw new DuplicateUsernameException();
        }
    }

    private void addMeeting(String meetingName, ArrayList<String> usernames, LocalTime timing) {
        Meeting meeting = new Meeting(meetingName, timing, usernames);
        meetingsList.add(meeting);
        MeetingsUi.printSuccessfulScheduleCommand(meetingName, timing.toString(), usernames);
    }

    /**
     * Checks if the time the user is trying to schedule a meeting at already has a meeting the user is part of.
     *
     * @param username the username of the user to be checked if they already have a meeting at this time
     * @param timing the time that the user is trying to schedule a meeting at
     * @return true if the user already has a meeting scheduled at the time, false otherwise
     */
    private boolean isMeetingTimeFull(String username, LocalTime timing) {
        for (Meeting meeting : meetingsList) {
            if (meeting.getTime().equals(timing)) {
                if (meeting.getListOfAttendees().contains(username)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isMeetingTimeFullForAll(ArrayList<String> usernames, LocalTime timing) {
        for (Meeting meeting : meetingsList) {
            if (meeting.getTime().equals(timing)) {
                for (String username : usernames) {
                    if (meeting.getListOfAttendees().contains(username)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Try to find a timing when all the usernames specified is available to schedule a meeting.
     * If no timing exists, no meeting is scheduled.
     *
     * @param meetingName the name of the meeting
     * @param usernames the usernames that the user is trying to schedule a meeting with
     * @throws CannotScheduleMeetingException if no timing is found when all users are available
     */
    public void autoScheduleMeeting(String meetingName, ArrayList<String> usernames)
            throws CannotScheduleMeetingException {
        for (LocalTime timing: availability.keySet()) {
            if (availability.get(timing).containsAll(usernames)) {
                // check if all the users specified don't have an existing meeting at that time
                if (!isMeetingTimeFullForAll(usernames, timing)) {
                    addMeeting(meetingName, usernames, timing);
                    return;
                }
            }
        }
        throw new CannotScheduleMeetingException();
    }

    /**
     * Try to schedule a meeting at the timing entered by the user.
     *
     * @param meetingName the name of the meeting
     * @param usernames the usernames of the users to be in the meeting
     * @param time the time the user is trying to schedule a meeting at
     * @throws InvalidTimeException if the format of the time is not the specified format
     * @throws CannotScheduleMeetingException if no meeting can be scheduled because one or more of the users entered
     *      is unavailable
     * @throws DuplicateMeetingException if one or more user already has a meeting at the time
     */
    public void manualScheduleMeeting(String meetingName, ArrayList<String> usernames, String time)
            throws InvalidTimeException,
            CannotScheduleMeetingException, DuplicateMeetingException {
        LocalTime localTime;
        if (isValidTimeFormat(time)) {
            localTime = LocalTime.parse(time, DateTimeFormatter.ofPattern(TIME_FORMAT));
        } else {
            throw new InvalidTimeException();
        }

        if (availability.get(localTime).containsAll(usernames)) {
            for (String username : usernames) {
                if (isMeetingTimeFull(username, localTime)) {
                    MeetingsUi.showMeetingTimeFull(username, time);
                    throw new DuplicateMeetingException();
                }
            }
            addMeeting(meetingName, usernames, localTime);
        } else {
            throw new CannotScheduleMeetingException();
        }
    }
}
