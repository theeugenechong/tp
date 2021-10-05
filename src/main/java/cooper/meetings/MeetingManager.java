package cooper.meetings;

import cooper.exceptions.DuplicateUsernameError;

import java.util.ArrayList;
import java.util.HashMap;

public class MeetingManager {
    private final HashMap<String, ArrayList<String>> meetings;

    public MeetingManager() {
        meetings = new HashMap<>(10);
    }

    public HashMap<String, ArrayList<String>> getMeetings() {
        return meetings;
    }

    public void addAvailability(String time, String name) throws DuplicateUsernameError {
        if (!meetings.containsKey(time)) {
            meetings.put(time, new ArrayList<>());
        }

        // check if the value is a duplicate
        if (!meetings.get(time).contains(name)) {
            meetings.get(time).add(name);
        } else {
            throw new DuplicateUsernameError();
        }
    }
}
