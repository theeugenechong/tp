package cooper.ui;

import cooper.meetings.Meeting;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.TreeMap;

//@@author fansxx

public class MeetingsUi extends Ui {

    public static final String AVAILABILITY_TABLE_MESSAGE = "Here are the availabilities:";
    public static final String MEETINGS_TABLE_MESSAGE = "Here are your meetings for today:";
    public static final String NO_MEETINGS_TABLE_MESSAGE = "| You have no meetings yet!";
    public static final String AVAILABILITY_TABLE_HEADINGS = "| time  | names";
    public static final String AVAILABILITY_TABLE_LINE =
            "+-------+-------------------------------------------------------------";
    public static final String MEETINGS_TABLE_HEADINGS = "| meeting    | time  | attendees";
    public static final String MEETINGS_TABLE_LINE =
            "+---------------------------------------------------------------------";
    public static final String SUCCESS_MESSAGE = "Success!";
    public static final String SUCCESSFUL_AVAILABLE_MESSAGE = "'s availability has been added to ";

    public static final String INVALID_TIME_FORMAT_MESSAGE =
            "The time format you entered is not accepted! Please enter again.";
    public static final String INVALID_TIME_MESSAGE =
            "The time you entered is not the start of the hour! Please enter again.";
    public static final String DUPLICATE_USERNAME_ERROR_MESSAGE =
            "The username has already been entered under that timeslot.";
    public static final String CANNOT_SCHEDULE_MEETING_ERROR_MESSAGE = "Oops, no meeting can be scheduled!";
    public static final String DUPLICATE_MEETING_ERROR_MESSAGE = "Please schedule a meeting at another time!";
    public static final String INVALID_SCHEDULE_FORMAT_ERROR_MESSAGE =
            "Please enter the users you would like to schedule a meeting with.";
    public static final String NO_TIME_ENTERED_ERROR_MESSAGE = "Please enter the time of the meeting after /at";
    public static final String NO_USERNAME_AFTER_COMMA_ERROR_MESSAGE =
            "You must enter a username after a comma! Please enter again.";

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

    public static void showInvalidTimeFormatException() {
        show(LINE);
        show(INVALID_TIME_FORMAT_MESSAGE);
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

    public static void printAvailableCommand(String time, String username) {
        show(LINE);
        show(SUCCESS_MESSAGE);
        show(username + SUCCESSFUL_AVAILABLE_MESSAGE + time);
        show(LINE);
    }

    public static void printSuccessfulScheduleCommand(String meetingName, String time, ArrayList<String> usernames) {
        show(LINE);
        show(SUCCESS_MESSAGE);
        show("You have scheduled a <<" + meetingName + ">> meeting at " + time + " with attendees: "
                + listOfAvailabilities(usernames));
        show(LINE);
    }

    public static void printAvailabilities(TreeMap<LocalTime, ArrayList<String>> availability) {
        printAvailabilityTableHeader();
        show(AVAILABILITY_TABLE_HEADINGS);
        show(AVAILABILITY_TABLE_LINE);
        for (LocalTime timing: availability.keySet()) {
            show("| " + timing + " | " + listOfAvailabilities(availability.get(timing)));
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
                show("| <<" + meeting.getMeetingName() + ">> | " + meeting.getTime() + " | "
                        + listOfAvailabilities(meeting.getListOfAttendees()));
            }
        }
        show(MEETINGS_TABLE_TOP);
        show(LINE);
    }
}
