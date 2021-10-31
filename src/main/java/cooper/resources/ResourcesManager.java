package cooper.resources;

import cooper.finance.FinanceManager;
import cooper.meetings.MeetingManager;
import cooper.forum.ForumManager;
import cooper.verification.UserRole;

//@@author Rrraaaeee

public class ResourcesManager {

    private final FinanceManager cooperFinanceManager;
    private final MeetingManager cooperMeetingManager;
    private final ForumManager cooperForumManager;

    public ResourcesManager() {
        cooperFinanceManager = new FinanceManager();
        cooperMeetingManager = new MeetingManager();
        cooperForumManager = new ForumManager();
    }

    public FinanceManager getFinanceManager() {
        return cooperFinanceManager;
    }
    
    public FinanceManager getFinanceManager(UserRole userRole) {
        if (userRole.equals(UserRole.ADMIN)) {
            return cooperFinanceManager;
        } else {
            return null;
        }
    }

    public MeetingManager getMeetingManager() {
        return cooperMeetingManager;
    }

    public MeetingManager getMeetingManager(UserRole userRole) {
        return cooperMeetingManager;
    }

    // FIXME: These three APIs should not be accessed without user roles,
    // access right management is done in V2.1
    public ForumManager getForumManager() {
        return cooperForumManager;
    }

    public ForumManager getForumManager(UserRole userRole) {
        return cooperForumManager;
    }
}
