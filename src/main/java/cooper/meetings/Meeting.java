package cooper.meetings;

import java.time.LocalTime;
import java.util.ArrayList;

public class Meeting {
    private LocalTime time;
    private ArrayList<String> listOfAttendees;

    public Meeting(LocalTime time, ArrayList<String> listOfAttendees) {
        this.time = time;
        this.listOfAttendees = listOfAttendees;
    }
}
