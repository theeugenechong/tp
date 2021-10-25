package cooper.ui;

import cooper.meetings.Meeting;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.TreeMap;

public class MeetingsUi extends Ui {
    private static final String LINE = "=========================================================================";
    private static final String TABLE_TOP = "┌────────────────────────────────────────────────────────────────────┐";
    private static final String TABLE_BOT = "└────────────────────────────────────────────────────────────────────┘";

    public static void showInvalidTimeException() {
        show(LINE);
        show("The time format you entered is not accepted! Please enter again.");
        show(LINE);
    }

    public static void showDuplicateUsernameException() {
        show(LINE);
        show("The username has already been entered under that timeslot.");
        show(LINE);
    }

    public static void showCannotScheduleMeetingException() {
        show(LINE);
        show("Oops, no meeting can be scheduled!");
        show(LINE);
    }

    public static void showDuplicateMeetingException() {
        show(LINE);
        show("You have already scheduled a meeting at that time!");
        show(LINE);
    }

    public static void printAvailableCommand(String time, String username) {
        show(LINE);
        show("Success!");
        show(username + "'s availability has been added to " + time);
        show(LINE);
    }

    public static void printSuccessfulScheduleCommand(String meetingName, String time, ArrayList<String> usernames) {
        show(LINE);
        show("Success!");
        show("You have scheduled a <<" + meetingName + ">> meeting at " + time + " with "
                + listOfAvailabilities(usernames));
        show(LINE);
    }

    public static void printAvailabilities(TreeMap<LocalTime, ArrayList<String>> availability) {
        printTableHeader("Here are the availabilities:");
        show("│ time  │ names");
        show("├────────────────────────────────────────────────────────────────────");
        for (LocalTime timing: availability.keySet()) {
            show("│ " + timing + " │ " + listOfAvailabilities(availability.get(timing)));
        }
        show(TABLE_BOT);
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

    private static void printTableHeader(String headerCaption) {
        show(LINE);
        show(headerCaption);
        show(TABLE_TOP);
    }

    public static void printMeetings(ArrayList<Meeting> meetingsList) {
        printTableHeader("Here are your meetings for today:");
        if (meetingsList.size() == 0) {
            show("│ You have no meetings yet!");
        } else {
            show("│ meeting    │ time  │ attendees");
            show("├────────────────────────────────────────────────────────────────────");
            for (Meeting meeting : meetingsList) {
                show("│ <<" + meeting.getMeetingName() + ">> │ " + meeting.getTime() + " │ "
                        + listOfAvailabilities(meeting.getListOfAttendees()));
            }
        }
        show(TABLE_BOT);
        show(LINE);
    }
}
