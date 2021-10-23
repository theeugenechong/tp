package cooper.storage;

import cooper.exceptions.InvalidFileDataException;
import cooper.meetings.Meeting;
import cooper.meetings.MeetingManager;
import cooper.ui.Ui;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class MeetingsStorage extends Storage {

    public MeetingsStorage(String filePath) {
        super(filePath);
    }

    public void loadMeetings(MeetingManager cooperMeetingManager) {
        ArrayList<Meeting> meetings = cooperMeetingManager.getMeetingsList();
        Scanner fileScanner = getScanner(filePath);
        readMeetings(fileScanner, meetings);
    }

    public void saveMeetings(MeetingManager cooperMeetingManager) {
        try {
            writeMeetings(filePath, cooperMeetingManager.getMeetingsList());
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
            System.exit(1);
        }
    }

    private static void readMeetings(Scanner fileScanner, ArrayList<Meeting> meetings) {
        if (fileScanner != null) {
            while (fileScanner.hasNext()) {
                String meetingsRow = fileScanner.nextLine();
                try {
                    decodeMeetings(meetingsRow, meetings);
                } catch (InvalidFileDataException e) {
                    Ui.showInvalidFileDataError();
                }
            }
        }
    }

    private static void decodeMeetings(String meetingAsString, ArrayList<Meeting> meetings)
            throws InvalidFileDataException {
        String[] attendees = meetingAsString.split("\\|");
        if (isInvalidFileData(attendees)) {
            throw new InvalidFileDataException();
        }
        assert !isInvalidFileData(attendees);

        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime meetingTime = LocalTime.parse(attendees[0].trim(), timeFormat);

        String meetingName = attendees[1];

        String[] attendeesAsArray = attendees[2].trim().split(",");
        ArrayList<String> attendeesArrayList = new ArrayList<>(Arrays.asList(attendeesAsArray));
        Meeting meeting = new Meeting(meetingName, meetingTime, attendeesArrayList);

        meetings.add(meeting);
    }

    private static boolean isInvalidFileData(String[] meeting) {
        if (meeting.length != 2) {
            return true;
        }

        try {
            DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime meetingTime = LocalTime.parse(meeting[0].trim(), timeFormat);
        } catch (DateTimeParseException e) {
            return true;
        }

        for (String s : meeting) {
            if (s.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private static void writeMeetings(Path filePath, ArrayList<Meeting> meetingsList)
            throws IOException {
        FileWriter fileWriter = new FileWriter(filePath.toString(), false);

        for (int i = 0; i < meetingsList.size(); i++) {
            String encodedMeeting = encodeMeeting(meetingsList.get(i));
            fileWriter.write(encodedMeeting + System.lineSeparator());
        }
        fileWriter.close();
    }

    private static String encodeMeeting(Meeting meeting) {
        StringBuilder encodedMeeting = new StringBuilder();

        String meetingTime = meeting.getTime().toString();
        encodedMeeting.append(meetingTime).append(" | ");

        String meetingName = meeting.getMeetingName();
        encodedMeeting.append(meetingName).append(" | ");

        String attendees = getAttendeesAsString(meeting.getListOfAttendees());
        encodedMeeting.append(attendees);

        return String.valueOf(encodedMeeting);
    }

    private static String getAttendeesAsString(ArrayList<String> attendees) {
        StringBuilder meetingAsString = new StringBuilder();
        for (String a : attendees) {
            /* don't need comma for last attendee */
            int indexOfLastAttendee = attendees.size() - 1;
            if (a.equals(attendees.get(indexOfLastAttendee))) {
                meetingAsString.append(a);
            } else {
                meetingAsString.append(a).append(",");
            }
        }
        return String.valueOf(meetingAsString);
    }
}
