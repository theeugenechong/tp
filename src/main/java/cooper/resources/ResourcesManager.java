package cooper.resources;

import cooper.finance.FinanceManager;
import cooper.meetings.MeetingManager;
import cooper.forum.ForumManager;
import cooper.storage.StorageManager;
import cooper.verification.UserRole;
import cooper.verification.Verifier;

public class ResourcesManager {

    private final Verifier cooperVerifier;
    private final FinanceManager cooperFinanceManager;
    private final MeetingManager cooperMeetingManager;
    private final ForumManager cooperForumManager;
    private final StorageManager cooperStorageManager;

    public ResourcesManager() {
        cooperStorageManager = new StorageManager();
        cooperFinanceManager = new FinanceManager();
        cooperMeetingManager = new MeetingManager();
        cooperForumManager = new ForumManager();
        cooperVerifier = new Verifier();
        // load storage 
        cooperStorageManager.loadAllData(
                cooperVerifier,
                cooperFinanceManager,
                cooperMeetingManager);


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

    public StorageManager getStorageManager() {
        return cooperStorageManager;

    }

    public Verifier getVerifier() {
        return cooperVerifier;
    }


}
