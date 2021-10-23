package cooper.resources;

import cooper.finance.FinanceManager;
import cooper.meetings.MeetingManager;
import cooper.forum.ForumManager;
import cooper.verification.UserRole;

public class ResourcesManager {

    private final FinanceManager cooperFinanceManager;
    private final MeetingManager cooperMeetingManager;
    private final ForumManager cooperForumManager;

    public ResourcesManager(FinanceManager financeManager, MeetingManager meetingManager,
            ForumManager forumManager) {
        cooperFinanceManager = financeManager;
        cooperMeetingManager = meetingManager;
        cooperForumManager = forumManager;
    }

    public FinanceManager getFinanceManager(UserRole userRole) {
        if (userRole.equals(UserRole.ADMIN)) {
            return cooperFinanceManager;
        } else {
            return null;
        }
    }

    public MeetingManager getMeetingManager(UserRole userRole) {
        return cooperMeetingManager;
    }

    public ForumManager getForumManager(UserRole userRole) {
        return cooperForumManager;
    }
}
