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

    /**
     * get a finance manager if the user role has access right.
     * @param userRole user's role for checking access rights
     * @return financeManager or null
     */
    public FinanceManager getFinanceManager(UserRole userRole) {
        if (checkFinanceAccessibility(userRole)) {
            return cooperFinanceManager;
        } else {
            return null;
        }
    }

    /**
     * get a meeting manager if the user role has access right.
     * @param userRole user's role for checking access rights
     * @return meetingManager or null
     */
    public MeetingManager getMeetingManager(UserRole userRole) {
        if (checkMeetingAccessibility(userRole)) {
            return cooperMeetingManager;
        } else {
            return null;
        }
    }

    public MeetingManager getMeetingManager() {
        return cooperMeetingManager;
    }

    /**
     * get a forum manager if the user role has access right.
     * @param userRole user's role for checking access rights
     * @return forumManager or null
     */
    public ForumManager getForumManager(UserRole userRole) {
        if (checkForumAccessibility(userRole)) {
            return cooperForumManager;
        } else {
            return null;
        }
    }

    private boolean checkFinanceAccessibility(UserRole userRole) {
        return (userRole.equals(UserRole.ADMIN));
    }

    private boolean checkMeetingAccessibility(UserRole userRole) {
        return (userRole.equals(UserRole.ADMIN));
    }

    private boolean checkForumAccessibility(UserRole userRole) {
        return (userRole.equals(UserRole.ADMIN) || userRole.equals(UserRole.EMPLOYEE));
    }


    /**
     * Storage class has "super privilege" to access private member in resources class.
     * Use this give-receive pattern to get private members from ResourcesManager (Similar to friend class)
     * Pattern adapted from:
     * https://stackoverflow.com/questions/14226228/implementation-of-friend-concept-in-javat
     * @param storageManager storage manager object to pass private members to
     * @return  the private member object queried
     */
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
