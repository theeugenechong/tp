package cooper.storage;

import cooper.exceptions.InvalidFileDataException;
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
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class MeetingsStorage extends Storage {

    public MeetingsStorage(String filePath) {
        super(filePath);
    }

    public void loadMeetings(MeetingManager cooperMeetingManager) {
        TreeMap<LocalTime, ArrayList<String>> meetings = cooperMeetingManager.getMeetings();
        Scanner fileScanner = getScanner(filePath);
        readMeetings(fileScanner, meetings);
    }

    public void saveMeetings(MeetingManager cooperMeetingManager) {
        try {
            writeMeetings(filePath, cooperMeetingManager.getMeetings());
        } catch (IOException e) {
            Ui.showFileWriteError(e);
            System.exit(1);
        }
    }

    private static void readMeetings(Scanner fileScanner, TreeMap<LocalTime, ArrayList<String>> meetings) {
        if (fileScanner != null) {
            while (fileScanner.hasNext()) {
                String meeting = fileScanner.nextLine();
                try {
                    decodeMeeting(meeting, meetings);
                } catch (InvalidFileDataException e) {
                    Ui.showInvalidFileDataError();
                }
            }
        }
    }

    private static void decodeMeeting(String meetingAsString, TreeMap<LocalTime, ArrayList<String>> meetings)
            throws InvalidFileDataException {
        String[] meeting = meetingAsString.split("\\|");
        if (isInvalidFileData(meeting)) {
            throw new InvalidFileDataException();
        }
        assert !isInvalidFileData(meeting);

        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime meetingTime = LocalTime.parse(meeting[0].trim(), timeFormat);

        String[] availabilitiesAsArray = meeting[1].trim().split(",");
        ArrayList<String> availabilities = new ArrayList<>(Arrays.asList(availabilitiesAsArray));

        meetings.put(meetingTime, availabilities);
    }

    private static boolean isInvalidFileData(String[] meeting) {
        if (meeting.length != 2) {
            return true;
        }

        try {
            DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime dummyMeetingTime = LocalTime.parse(meeting[0].trim(), timeFormat);
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

    private static void writeMeetings(Path filePath, TreeMap<LocalTime, ArrayList<String>> meetings)
            throws IOException {
        FileWriter fileWriter = new FileWriter(filePath.toString(), false);

        for (Map.Entry<LocalTime, ArrayList<String>> e : meetings.entrySet()) {
            String encodedMeeting = encodeMeeting(e);
            fileWriter.write(encodedMeeting + System.lineSeparator());
        }
        fileWriter.close();
    }

    private static String encodeMeeting(Map.Entry<LocalTime, ArrayList<String>> meeting) {
        StringBuilder encodedMeeting = new StringBuilder();

        String meetingTime = meeting.getKey().toString();
        encodedMeeting.append(meetingTime).append(" | ");

        String availabilities = getAvailabilitiesAsString(meeting.getValue());
        encodedMeeting.append(availabilities);

        return String.valueOf(encodedMeeting);
    }

    private static String getAvailabilitiesAsString(ArrayList<String> availabilities) {
        StringBuilder availabilitiesAsString = new StringBuilder();
        for (String a : availabilities) {
            /* don't need comma for last attendee */
            int indexOfLastAttendee = availabilities.size() - 1;
            if (a.equals(availabilities.get(indexOfLastAttendee))) {
                availabilitiesAsString.append(a);
            } else {
                availabilitiesAsString.append(a).append(",");
            }
        }
        return String.valueOf(availabilitiesAsString);
    }
}
