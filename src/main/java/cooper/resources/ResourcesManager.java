package cooper.resources;

import cooper.finance.FinanceManager;
import cooper.meetings.MeetingManager;
import cooper.storage.StorageManager;
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

    
    public FinanceManager getFinanceManager(UserRole userRole) {
        if (userRole.equals(UserRole.ADMIN)) {
            return cooperFinanceManager;
        } else {
            return null;
        }
    }

    public MeetingManager getMeetingManager(UserRole userRole) {
        if (userRole.equals(UserRole.ADMIN)) {
            return cooperMeetingManager;
        } else {
            return null;
        }
    }

    public ForumManager getForumManager(UserRole userRole) {
        return cooperForumManager;
    }

    /**
     * Storage class has "super privilege" to access private member in resources class.
     * Use this give-receive pattern to get private members from ResourcesManager (Similar to friend class)
     * Pattern adepted from:
     * https://stackoverflow.com/questions/14226228/implementation-of-friend-concept-in-javat
     **/
    public FinanceManager giveFinanceManager(StorageManager storageManager) {
        return storageManager.receiveFinanceManager(cooperFinanceManager);
    }

    public MeetingManager giveMeetingManager(StorageManager storageManager) {
        return storageManager.receiveMeetingManager(cooperMeetingManager);
    }

    public ForumManager giveForumManager(StorageManager storageManager) {
        return storageManager.receiveForumManager(cooperForumManager);
    }

}
