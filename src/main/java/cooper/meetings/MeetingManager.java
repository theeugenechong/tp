package cooper.meetings;

import cooper.ui.Ui;

import java.util.ArrayList;
import java.util.HashMap;

public class MeetingManager {
    private static HashMap<String, ArrayList<String>> meetings = new HashMap<>(10);

    public static void addAvailability(String time, String name) {
        if (!meetings.containsKey(time)) {
            meetings.put(time, new ArrayList<>());
        }
        meetings.get(time).add(name);
    }

    public static void printAvailabilities() {
        Ui.printMeetingTableHeader();
        for (String key: meetings.keySet()) {
            Ui.showText(key + " | " + meetings.get(key));
        }
    }
}
