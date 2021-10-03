package cooper.meetings;

import java.util.HashMap;

public class MeetingManager {
    private HashMap meetings = new HashMap(10);

    public void addAvailability(String time, String name) {
        meetings.put(time, name);
    }

    public String getAvailabilities(String time) {
        return (String) meetings.get(time);
    }

}
