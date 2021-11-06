package cooper.meetings;

import java.time.LocalDateTime;
import java.util.ArrayList;

//@@author fansxx

public class Meeting {
    private final String meetingName;
    private final LocalDateTime dateTime;
    private final ArrayList<String> listOfAttendees;

    public Meeting(String meetingName, LocalDateTime dateTime, ArrayList<String> listOfAttendees) {
        this.meetingName = meetingName;
        this.dateTime = dateTime;
        this.listOfAttendees = listOfAttendees;
    }

    public String getMeetingName() {
        return meetingName;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public ArrayList<String> getListOfAttendees() {
        return listOfAttendees;
    }
}
