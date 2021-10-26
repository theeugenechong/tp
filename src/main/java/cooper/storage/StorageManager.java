package cooper.storage;

import cooper.finance.BalanceSheet;
import cooper.finance.CashFlow;
import cooper.finance.FinanceManager;
import cooper.meetings.MeetingManager;
import cooper.forum.ForumManager;
import cooper.verification.Verifier;

public class StorageManager {

    private static final String BASE_DIRECTORY = "cooperData";
    private final SignInDetailsStorage signInDetailsStorage;
    private final BalanceSheetStorage balanceSheetStorage;
    private final CashFlowStorage cashFlowStorage;
    private final ForumStorage forumStorage;
    private final AvailabilityStorage availabilityStorage;
    private final MeetingsStorage meetingsStorage;

    public StorageManager() {
        this.signInDetailsStorage = new SignInDetailsStorage(BASE_DIRECTORY + "/signInDetails.txt");
        this.balanceSheetStorage = new BalanceSheetStorage(BASE_DIRECTORY + "/balanceSheet.txt");
        this.availabilityStorage = new AvailabilityStorage(BASE_DIRECTORY + "/availability.txt");
        this.meetingsStorage = new MeetingsStorage(BASE_DIRECTORY + "/meetings.txt");
        this.cashFlowStorage = new CashFlowStorage(BASE_DIRECTORY + "/cashFlowStatement.txt");
        this.forumStorage = new ForumStorage(BASE_DIRECTORY + "/forum.txt");
    }

    public void loadAllData(Verifier cooperVerifier, FinanceManager cooperFinanceManager,
                            MeetingManager cooperMeetingManager, ForumManager cooperForumManager) {
        signInDetailsStorage.loadSignInDetails(cooperVerifier);
        cashFlowStorage.loadCashFlowStatement(cooperFinanceManager.cooperCashFlowStatement);
        balanceSheetStorage.loadBalanceSheet(cooperFinanceManager.cooperBalanceSheet);
        availabilityStorage.loadAvailability(cooperMeetingManager);
        meetingsStorage.loadMeetings(cooperMeetingManager);
        forumStorage.loadForum(cooperForumManager);
    }

    public void saveSignInDetails(Verifier cooperVerifier) {
        signInDetailsStorage.saveSignInDetails(cooperVerifier);
    }

    public void saveBalanceSheet(BalanceSheet cooperBalanceSheet) {
        balanceSheetStorage.saveBalanceSheet(cooperBalanceSheet);
    }

    public void saveCashFlowStatement(CashFlow cooperCashFlowStatement) {
        cashFlowStorage.saveCashFlowStatement(cooperCashFlowStatement);
    }

    public void saveAvailability(MeetingManager cooperMeetingManager) {
        availabilityStorage.saveAvailability(cooperMeetingManager);
    }

    public void saveMeetings(MeetingManager cooperMeetingManager) {
        meetingsStorage.saveMeetings(cooperMeetingManager);
    }

    public void saveForum(ForumManager cooperForumManager) {
        forumStorage.saveForum(cooperForumManager);
    }
}
