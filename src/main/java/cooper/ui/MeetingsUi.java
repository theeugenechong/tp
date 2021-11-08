package cooper.ui;

import cooper.meetings.Meeting;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.TreeMap;

//@@author fansxx

public class MeetingsUi extends Ui {

    public static final String AVAILABILITY_TABLE_MESSAGE = "Here are the availabilities:";
    public static final String MEETINGS_TABLE_MESSAGE = "Here are your meetings for today:";
    public static final String NO_MEETINGS_TABLE_MESSAGE = "| You have no meetings yet!";
    public static final String AVAILABILITY_TABLE_HEADINGS = "| date       | time  | names";
    public static final String AVAILABILITY_TABLE_LINE =
            "+------------+-------+-----------------------------------------------+";
    public static final String MEETINGS_TABLE_HEADINGS = "| meeting    | date       | time  | attendees";
    public static final String MEETINGS_TABLE_LINE =
            "+--------------------------------------------------------------------+";
    public static final String SUCCESS_MESSAGE = "Success!";
    public static final String SUCCESSFUL_AVAILABLE_MESSAGE = "'s availability has been added to ";

    /* Error messages */
    public static final String INVALID_DATE_TIME_FORMAT_MESSAGE =
            "The date and time you entered is not valid! Please enter again.";
    public static final String CORRECT_DATE_TIME_FORMAT_MESSAGE = "The correct format is: [dd-MM-yyyy HH:mm]";
    public static final String INVALID_TIME_MESSAGE =
            "The time you entered is not the start of the hour! Please enter again.";
    public static final String DUPLICATE_USERNAME_ERROR_MESSAGE =
            "The username has already been entered under that timeslot.";
    public static final String CANNOT_SCHEDULE_MEETING_ERROR_MESSAGE = "Oops, no meeting can be scheduled! "
            + "Not all users are available at a common timing.";
    public static final String DUPLICATE_MEETING_ERROR_MESSAGE = "Please schedule a meeting at another time!";
    public static final String INVALID_SCHEDULE_FORMAT_ERROR_MESSAGE =
            "Please enter the users you would like to schedule a meeting with.";
    public static final String NO_TIME_ENTERED_ERROR_MESSAGE = "Please enter the time of the meeting after /at";
    public static final String NO_USERNAME_AFTER_COMMA_ERROR_MESSAGE =
            "You must enter a username after a comma! Please enter again.";
    public static final String NO_AVAILABILITY_TIMING_ERROR_MESSAGE =
            "Oops, no users are available at the time you entered!";

    /* formatting */
    public static final String DATE_FORMAT = "dd-MM-yyyy";
    public static final String TIME_FORMAT = "HH:mm";

    /* print error messages */
    public static void showInvalidScheduleFormatException() {
        show(LINE);
        show(INVALID_SCHEDULE_FORMAT_ERROR_MESSAGE);
        show(LINE);
    }

    public static void showNoTimeEnteredException() {
        show(LINE);
        show(NO_TIME_ENTERED_ERROR_MESSAGE);
        show(LINE);
    }

    public static void showNoUsernameAfterCommaException() {
        show(LINE);
        show(NO_USERNAME_AFTER_COMMA_ERROR_MESSAGE);
        show(LINE);
    }

    public static void showInvalidDateTimeFormatException() {
        show(LINE);
        show(INVALID_DATE_TIME_FORMAT_MESSAGE);
        show(CORRECT_DATE_TIME_FORMAT_MESSAGE);
        show(LINE);
    }

    public static void showInvalidTimeException() {
        show(LINE);
        show(INVALID_TIME_MESSAGE);
        show(LINE);
    }

    public static void showDuplicateUsernameException() {
        show(LINE);
        show(DUPLICATE_USERNAME_ERROR_MESSAGE);
        show(LINE);
    }

    public static void showCannotScheduleMeetingException() {
        show(LINE);
        show(CANNOT_SCHEDULE_MEETING_ERROR_MESSAGE);
        show(LINE);
    }

    public static void showDuplicateMeetingException() {
        show(DUPLICATE_MEETING_ERROR_MESSAGE);
        show(LINE);
    }

    public static void showMeetingTimeFull(String username, String time) {
        show(LINE);
        show("Oops, no meeting can be scheduled because " + username + " already has a meeting at " + time);
    }

    public static void showTimeNotInAvailabilityException() {
        show(LINE);
        show(NO_AVAILABILITY_TIMING_ERROR_MESSAGE);
        show(LINE);
    }

    /* print successful commands */
    public static void printAvailableCommand(String dateTime, String username) {
        show(LINE);
        show(SUCCESS_MESSAGE);
        show(username + SUCCESSFUL_AVAILABLE_MESSAGE + dateTime);
        show(LINE);
    }

    public static void printSuccessfulScheduleCommand(String meetingName, LocalDateTime dateTime,
                                                      ArrayList<String> usernames) {
        show(LINE);
        show(SUCCESS_MESSAGE);
        show("You have scheduled a <<" + meetingName + ">> meeting at "
                + dateTime.toLocalDate().format(DateTimeFormatter.ofPattern(DATE_FORMAT)) + " "
                + dateTime.toLocalTime().format(DateTimeFormatter.ofPattern(TIME_FORMAT)));
        show("with attendees: " + listOfAvailabilities(usernames));
        show(LINE);
    }

    public static void printAvailabilities(TreeMap<LocalDateTime, ArrayList<String>> availability) {
        printAvailabilityTableHeader();
        show(AVAILABILITY_TABLE_HEADINGS);
        show(AVAILABILITY_TABLE_LINE);
        for (LocalDateTime dateTime: availability.keySet()) {
            show("| " + dateTime.toLocalDate().format(DateTimeFormatter.ofPattern(DATE_FORMAT)) + " | "
                    + dateTime.toLocalTime().format(DateTimeFormatter.ofPattern(TIME_FORMAT)) + " | "
                    + listOfAvailabilities(availability.get(dateTime)));
        }
        show(AVAILABILITY_TABLE_TOP);
        show(LINE);
    }

    private static String listOfAvailabilities(ArrayList<String> availabilities) {
        StringBuilder listOfAvailabilities = new StringBuilder();
        for (String a : availabilities) {
            /* don't need comma for last attendee */
            int indexOfLastAttendee = availabilities.size() - 1;
            if (a.equals(availabilities.get(indexOfLastAttendee))) {
                listOfAvailabilities.append(a);
            } else {
                listOfAvailabilities.append(a).append(", ");
            }
        }
        return String.valueOf(listOfAvailabilities);
    }

    private static void printAvailabilityTableHeader() {
        show(LINE);
        show(AVAILABILITY_TABLE_MESSAGE);
        show(AVAILABILITY_TABLE_TOP);
    }

    private static void printMeetingsTableHeader() {
        show(LINE);
        show(MEETINGS_TABLE_MESSAGE);
        show(MEETINGS_TABLE_TOP);
        show(MEETINGS_TABLE_HEADINGS);
        show(MEETINGS_TABLE_LINE);
    }

    public static void printMeetings(ArrayList<Meeting> meetingsList) {
        printMeetingsTableHeader();
        if (meetingsList.size() == 0) {
            show(NO_MEETINGS_TABLE_MESSAGE);
        } else {
            for (Meeting meeting : meetingsList) {
                show("| <<" + meeting.getMeetingName() + ">> | "
                        + meeting.getDateTime().toLocalDate().format(DateTimeFormatter.ofPattern(DATE_FORMAT)) + " | "
                        + meeting.getDateTime().toLocalTime().format(DateTimeFormatter.ofPattern(TIME_FORMAT)) + " | "
                        + listOfAvailabilities(meeting.getListOfAttendees()));
            }
        }
        show(MEETINGS_TABLE_TOP);
        show(LINE);
    }
}
