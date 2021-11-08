package cooper.meetings;

import cooper.exceptions.InvalidTimeException;
import cooper.exceptions.InvalidDateTimeFormatException;
import cooper.exceptions.DuplicateUsernameException;
import cooper.exceptions.DuplicateMeetingException;
import cooper.exceptions.CannotScheduleMeetingException;
import cooper.exceptions.TimeNotInAvailabilityException;
import cooper.ui.MeetingsUi;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.logging.Logger;

//@@author fansxx

public class MeetingManager {
    private static final String DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm";
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private final TreeMap<LocalDateTime, ArrayList<String>> availability;
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
    private boolean isValidDateTimeFormat(String value) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        try {
            LocalDateTime ldt = LocalDateTime.parse(value, formatter);
            String result = ldt.format(formatter);
            return result.equals(value);
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private boolean isStartOfHour(String time) {
        String[] hoursAndMinutes = time.split(":");
        return hoursAndMinutes[1].equals("00");
    }

    private String getTime(String dateTime) {
        String[] dateTimeArray = dateTime.split(" ");
        return dateTimeArray[1];
    }

    public TreeMap<LocalDateTime, ArrayList<String>> getAvailability() {
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
     * Adds the user to the specified dateTime he is available at.
     *
     * @param dateTime the date and the start of the hour the user is available at
     * @param name the username of the user that inputted his availability
     * @throws DuplicateUsernameException if the username has already been declared available at that dateTime
     * @throws InvalidDateTimeFormatException if the format of the dateTime is not the specified format
     * @throws InvalidTimeException if the dateTime is not the start of the hour
     */
    public void addAvailability(String dateTime, String name) throws DuplicateUsernameException,
            InvalidDateTimeFormatException, InvalidTimeException {
        LocalDateTime localDateTime;
        if (isValidDateTimeFormat(dateTime)) {
            localDateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
        } else {
            throw new InvalidDateTimeFormatException();
        }

        if (!isStartOfHour(getTime(dateTime))) {
            throw new InvalidTimeException();
        }

        if (!availability.containsKey(localDateTime)) {
            assert !availability.containsKey(localDateTime) : "there is no localDateTime object in availability yet";
            availability.put(localDateTime, new ArrayList<>());
            LOGGER.info("A new dateTime is created: " + dateTime);
        }

        if (!availability.get(localDateTime).contains(name)) {
            assert !availability.get(localDateTime).contains(name) : "there is no " + name + " in availability yet";
            availability.get(localDateTime).add(name);
            LOGGER.info(name + " has been added to " + dateTime);
        } else {
            throw new DuplicateUsernameException();
        }
    }

    /**
     * Adds new meeting to meeting list.
     *
     * @param meetingName the name of the meeting
     * @param usernames the usernames of the users in the meeting
     * @param timing the time of the meeting
     */
    private void addMeeting(String meetingName, ArrayList<String> usernames, LocalDateTime timing) {
        Meeting meeting = new Meeting(meetingName, timing, usernames);
        meetingsList.add(meeting);
        MeetingsUi.printSuccessfulScheduleCommand(meetingName, timing, usernames);
    }

    /**
     * Checks if the time the user is trying to schedule a meeting at already has a meeting the user is part of.
     *
     * @param username the username of the user to be checked if they already have a meeting at this time
     * @param timing the time that the user is trying to schedule a meeting at
     * @return true if the user already has a meeting scheduled at the time, false otherwise
     */
    private boolean isMeetingTimeFull(String username, LocalDateTime timing) {
        for (Meeting meeting : meetingsList) {
            if (meeting.getDateTime().equals(timing) && meeting.getListOfAttendees().contains(username)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if any of the users already has a meeting scheduled at a particular time.
     *
     * @param usernames the usernames to be checked
     * @param timing the time that the user wants to schedule a meeting at
     * @return true if all the users already have another meeting at the timing, false otherwise
     */
    private boolean isMeetingTimeFullForAll(ArrayList<String> usernames, LocalDateTime timing) {
        for (Meeting meeting : meetingsList) {
            if (meeting.getDateTime().equals(timing) && isOneUserNotAvailable(usernames, meeting)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the particular meeting has at least one user in the usernames list.
     *
     * @param usernames the usernames to be checked if they have a meeting at a particular time
     * @param meeting the meeting to be checked to see if any of the specified users are in
     * @return true if there is at least one user in the usernames list in the meeting, false otherwise
     */
    private boolean isOneUserNotAvailable(ArrayList<String> usernames, Meeting meeting) {
        for (String username : usernames) {
            if (meeting.getListOfAttendees().contains(username)) {
                return true;
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
        for (LocalDateTime dateTime: availability.keySet()) {
            if (availability.get(dateTime).containsAll(usernames) && !isMeetingTimeFullForAll(usernames, dateTime)) {
                addMeeting(meetingName, usernames, dateTime);
                return;
            }
        }
        throw new CannotScheduleMeetingException();
    }

    /**
     * Try to schedule a meeting at the timing entered by the user.
     *
     * @param meetingName the name of the meeting
     * @param usernames the usernames of the users to be in the meeting
     * @param dateTime the date and time the user is trying to schedule a meeting at
     * @throws InvalidDateTimeFormatException if the format of the date and time is not the specified format
     * @throws InvalidTimeException if the time is not the start of the hour
     * @throws TimeNotInAvailabilityException if the time does not have any available users
     * @throws CannotScheduleMeetingException if no meeting can be scheduled because one or more of the users entered
     *      is unavailable
     * @throws DuplicateMeetingException if one or more user already has a meeting at the date and time
     */
    public void manualScheduleMeeting(String meetingName, ArrayList<String> usernames, String dateTime)
            throws InvalidDateTimeFormatException, InvalidTimeException,
            CannotScheduleMeetingException, DuplicateMeetingException, TimeNotInAvailabilityException {
        LocalDateTime localDateTime;
        if (isValidDateTimeFormat(dateTime)) {
            localDateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
        } else {
            throw new InvalidDateTimeFormatException();
        }

        if (!isStartOfHour(getTime(dateTime))) {
            throw new InvalidTimeException();
        }

        boolean dateTimeExist = false;
        for (LocalDateTime ldt: availability.keySet()) {
            if (localDateTime.equals(ldt)) {
                dateTimeExist = true;
                break;
            }
        }
        if (!dateTimeExist) {
            throw new TimeNotInAvailabilityException();
        }

        if (availability.get(localDateTime).containsAll(usernames)) {
            for (String username : usernames) {
                if (isMeetingTimeFull(username, localDateTime)) {
                    MeetingsUi.showMeetingTimeFull(username, dateTime);
                    throw new DuplicateMeetingException();
                }
            }
            addMeeting(meetingName, usernames, localDateTime);
        } else {
            throw new CannotScheduleMeetingException();
        }
    }
}
