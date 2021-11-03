package cooper.storage;

import cooper.exceptions.InvalidFileDataException;
import cooper.meetings.Meeting;
import cooper.meetings.MeetingManager;
import cooper.ui.FileIoUi;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

//@@author fansxx

public class MeetingsStorage extends Storage {

    protected static final String DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm";
    protected static final String COMMA = ",";
    protected static final String MEETINGS_TXT = "meetings.txt";
    public static final String DATE_FORMAT = "dd-MM-yyyy";
    public static final String TIME_FORMAT = "HH:mm";

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
            FileIoUi.showFileWriteError(e);
            System.exit(1);
        }
    }

    private void readMeetings(Scanner fileScanner, ArrayList<Meeting> meetings) {
        if (fileScanner == null) {
            return;
        }

        while (fileScanner.hasNext()) {
            String meetingsRow = fileScanner.nextLine();
            try {
                decodeMeetings(meetingsRow, meetings);
            } catch (InvalidFileDataException e) {
                FileIoUi.showInvalidFileDataError(e);
            }
        }
    }

    private void decodeMeetings(String meetingAsString, ArrayList<Meeting> meetings)
            throws InvalidFileDataException {
        String[] attendees = meetingAsString.split(SEPARATOR_REGEX);
        if (isInvalidFileData(attendees)) {
            throw new InvalidFileDataException(MEETINGS_TXT);
        }
        assert !isInvalidFileData(attendees);

        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        String dateTime = attendees[0].trim() + " " + attendees[1].trim();
        LocalDateTime meetingTime = LocalDateTime.parse(dateTime, dateTimeFormat);

        String meetingName = attendees[2].trim();

        String[] attendeesAsArray = attendees[3].trim().split(COMMA);
        ArrayList<String> attendeesArrayList = new ArrayList<>(Arrays.asList(attendeesAsArray));
        Meeting meeting = new Meeting(meetingName, meetingTime, attendeesArrayList);

        meetings.add(meeting);
    }

    private boolean isInvalidFileData(String[] meeting) {
        if (meeting.length != 4) {
            return true;
        }

        try {
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(DATE_FORMAT);
            LocalDate dummyMeetingDate = LocalDate.parse(meeting[0].trim(), dateFormat);
        } catch (DateTimeParseException e) {
            return true;
        }

        try {
            DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern(TIME_FORMAT);
            LocalTime dummyMeetingTime = LocalTime.parse(meeting[1].trim(), timeFormat);
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

    private void writeMeetings(String filePath, ArrayList<Meeting> meetingsList)
            throws IOException {
        FileWriter fileWriter = new FileWriter(filePath, false);

        for (Meeting meeting : meetingsList) {
            String encodedMeeting = encodeMeeting(meeting);
            fileWriter.write(encodedMeeting + System.lineSeparator());
        }
        fileWriter.close();
    }

    private String encodeMeeting(Meeting meeting) {
        StringBuilder encodedMeeting = new StringBuilder();

        String meetingDate = meeting.getDateTime().toLocalDate().format(DateTimeFormatter.ofPattern(DATE_FORMAT));
        encodedMeeting.append(meetingDate).append(SEPARATOR);

        String meetingTime = meeting.getDateTime().toLocalTime().format(DateTimeFormatter.ofPattern(TIME_FORMAT));
        encodedMeeting.append(meetingTime).append(SEPARATOR);

        String meetingName = meeting.getMeetingName();
        encodedMeeting.append(meetingName).append(SEPARATOR);

        String attendees = getAttendeesAsString(meeting.getListOfAttendees());
        encodedMeeting.append(attendees);

        return String.valueOf(encodedMeeting);
    }

    private String getAttendeesAsString(ArrayList<String> attendees) {
        StringBuilder meetingAsString = new StringBuilder();
        for (String a : attendees) {
            /* don't need comma for last attendee */
            int indexOfLastAttendee = attendees.size() - 1;
            if (a.equals(attendees.get(indexOfLastAttendee))) {
                meetingAsString.append(a);
            } else {
                meetingAsString.append(a).append(COMMA);
            }
        }
        return String.valueOf(meetingAsString);
    }
}
