package cooper.meetings;

import cooper.exceptions.DuplicateUsernameError;
import cooper.ui.Ui;

import java.util.ArrayList;
import java.util.HashMap;

public class MeetingManager {
    private static HashMap<String, ArrayList<String>> meetings = new HashMap<>(10);

    public static void addAvailability(String time, String name) throws DuplicateUsernameError {
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

    public static void printAvailabilities() {
        Ui.printMeetingTableHeader();
        for (String key: meetings.keySet()) {
            Ui.showText(key + " | " + meetings.get(key));
        }
    }
}
