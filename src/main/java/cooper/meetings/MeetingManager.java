package cooper.meetings;

import cooper.ui.Ui;

import java.util.HashMap;

public class MeetingManager {
    private static HashMap<String, String> meetings = new HashMap(10);

    public static void addAvailability(String time, String name) {
        meetings.put(time, name);
    }

    public static void printAvailabilities() {
        Ui.printMeetingTableHeader();
        for (String key: meetings.keySet()) {
            Ui.showText(key + " | " + meetings.get(key));
            //System.out.println(key + " \\| " + meetings.get(key));
        }
    }
}
