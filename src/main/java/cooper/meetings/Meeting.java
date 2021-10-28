package cooper.meetings;

import java.time.LocalTime;
import java.util.ArrayList;

//@@author fansxx

public class Meeting {
    private final String meetingName;
    private final LocalTime time;
    private final ArrayList<String> listOfAttendees;

    public Meeting(String meetingName, LocalTime time, ArrayList<String> listOfAttendees) {
        this.meetingName = meetingName;
        this.time = time;
        this.listOfAttendees = listOfAttendees;
    }

    public String getMeetingName() {
        return meetingName;
    }

    public LocalTime getTime() {
        return time;
    }

    public ArrayList<String> getListOfAttendees() {
        return listOfAttendees;
    }
}
