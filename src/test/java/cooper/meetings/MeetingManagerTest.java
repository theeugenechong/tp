package cooper.meetings;

import cooper.exceptions.InvalidTimeException;
import cooper.exceptions.InvalidDateTimeFormatException;
import cooper.exceptions.DuplicateUsernameException;
import cooper.exceptions.CannotScheduleMeetingException;
import cooper.exceptions.DuplicateMeetingException;
import cooper.exceptions.TimeNotInAvailabilityException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;

//@@author fansxx

public class MeetingManagerTest {
    static MeetingManager meetingManager;

    @BeforeAll
    static void setUpMeetingManager() {
        meetingManager = new MeetingManager();
    }

    @Test
    @Order(1)
    void addAvailability_invalidTimeFormat_expectException() {
        String inputTime = "12-02-2021 12.00";
        String inputName = "shixi";
        assertThrows(InvalidDateTimeFormatException.class, () -> meetingManager.addAvailability(inputTime, inputName));
    }

    @Test
    @Order(2)
    void addAvailability_duplicateName_expectException() throws InvalidDateTimeFormatException,
            InvalidTimeException, DuplicateUsernameException {
        String inputTime = "12-02-2021 16:00";
        String inputName = "shixi";
        meetingManager.addAvailability(inputTime, inputName);
        assertThrows(DuplicateUsernameException.class, () -> meetingManager.addAvailability(inputTime, inputName));
    }

    @Test
    @Order(3)
    void manualScheduleMeeting_duplicateMeeting_expectException() throws DuplicateUsernameException,
            InvalidDateTimeFormatException, InvalidTimeException, CannotScheduleMeetingException,
            DuplicateMeetingException, TimeNotInAvailabilityException {
        meetingManager.addAvailability("12-02-2021 12:00", "shixi");
        meetingManager.addAvailability("12-02-2021 12:00", "fan");

        String meetingName = "Project Meeting";
        ArrayList<String> listOfAttendees = new ArrayList<>();
        listOfAttendees.add("shixi");
        listOfAttendees.add("fan");
        String time = "12-02-2021 12:00";
        meetingManager.manualScheduleMeeting(meetingName, listOfAttendees, time);
        assertThrows(DuplicateMeetingException.class, () ->
                meetingManager.manualScheduleMeeting(meetingName, listOfAttendees, time));
    }

    @Test
    @Order(4)
    void manualScheduleMeeting_InvalidTimeFormat_expectException() {
        String meetingName = "Project Meeting";
        ArrayList<String> listOfAttendees = new ArrayList<>();
        listOfAttendees.add("shixi");
        listOfAttendees.add("fan");
        String time = "12-02-2021 1200";
        assertThrows(InvalidDateTimeFormatException.class, () ->
                meetingManager.manualScheduleMeeting(meetingName, listOfAttendees, time));
    }

    @Test
    @Order(5)
    void manualScheduleMeeting_InvalidTime_expectException() {
        String meetingName = "Project Meeting";
        ArrayList<String> listOfAttendees = new ArrayList<>();
        listOfAttendees.add("shixi");
        listOfAttendees.add("fan");
        String time = "12-02-2021 12:34";
        assertThrows(InvalidTimeException.class, () ->
                meetingManager.manualScheduleMeeting(meetingName, listOfAttendees, time));
    }

    @Test
    @Order(6)
    void autoScheduleMeeting_noAvailability_expectException() {
        String meetingName = "Project Meeting";
        ArrayList<String> listOfAttendees = new ArrayList<>();
        listOfAttendees.add("shixi");
        listOfAttendees.add("fan");
        assertThrows(CannotScheduleMeetingException.class, () ->
                meetingManager.autoScheduleMeeting(meetingName, listOfAttendees));
    }

    @Test
    @Order(7)
    void manualScheduleMeeting_timeNotInAvailability_expectException() {
        String meetingName = "Project Meeting";
        ArrayList<String> listOfAttendees = new ArrayList<>();
        listOfAttendees.add("shixi");
        listOfAttendees.add("fan");
        String time = "10-02-2021 12:00";
        assertThrows(TimeNotInAvailabilityException.class, () ->
                meetingManager.manualScheduleMeeting(meetingName, listOfAttendees, time));
    }

    @Test
    @Order(8)
    void addAvailability_notStartOfHour_expectException() {
        String inputTime = "12-02-2021 16:24";
        String inputName = "shixi";
        assertThrows(InvalidTimeException.class, () -> meetingManager.addAvailability(inputTime, inputName));
    }
}
