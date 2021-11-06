package cooper.storage;

import cooper.finance.BalanceSheet;
import cooper.finance.CashFlow;
import cooper.finance.FinanceManager;
import cooper.meetings.MeetingManager;
import cooper.forum.ForumManager;
import cooper.verification.Verifier;
import cooper.resources.ResourcesManager;

//@@author theeugenechong

public class StorageManager {

    private static final String BASE_DIRECTORY = "cooperData";
    protected static final String SIGN_IN_DETAILS_FILE = "/signInDetails.txt";
    protected static final String BALANCE_SHEET_FILE = "/balanceSheet.txt";
    protected static final String AVAILABILITY_FILE = "/availability.txt";
    protected static final String MEETINGS_FILE = "/meetings.txt";
    protected static final String CASH_FLOW_STATEMENT_FILE = "/cashFlowStatement.txt";
    protected static final String FORUM_FILE = "/forum.txt";

    private final SignInDetailsStorage signInDetailsStorage;
    private final BalanceSheetStorage balanceSheetStorage;
    private final CashFlowStorage cashFlowStorage;
    private final ForumStorage forumStorage;
    private final AvailabilityStorage availabilityStorage;
    private final MeetingsStorage meetingsStorage;

    public StorageManager() {
        this.signInDetailsStorage = new SignInDetailsStorage(BASE_DIRECTORY + SIGN_IN_DETAILS_FILE);
        this.balanceSheetStorage = new BalanceSheetStorage(BASE_DIRECTORY + BALANCE_SHEET_FILE);
        this.availabilityStorage = new AvailabilityStorage(BASE_DIRECTORY + AVAILABILITY_FILE);
        this.meetingsStorage = new MeetingsStorage(BASE_DIRECTORY + MEETINGS_FILE);
        this.cashFlowStorage = new CashFlowStorage(BASE_DIRECTORY + CASH_FLOW_STATEMENT_FILE);
        this.forumStorage = new ForumStorage(BASE_DIRECTORY + FORUM_FILE);
    }

    public void loadAllData(Verifier cooperVerifier, ResourcesManager resourcesManager) {
        signInDetailsStorage.loadSignInDetails(cooperVerifier);

        FinanceManager cooperFinanceManager = resourcesManager.giveFinanceManager(this);
        cashFlowStorage.loadCashFlowStatement(cooperFinanceManager.cooperCashFlowStatement);
        balanceSheetStorage.loadBalanceSheet(cooperFinanceManager.cooperBalanceSheet);

        MeetingManager cooperMeetingManager = resourcesManager.giveMeetingManager(this);
        availabilityStorage.loadAvailability(cooperMeetingManager);
        meetingsStorage.loadMeetings(cooperMeetingManager);

        ForumManager cooperForumManager = resourcesManager.giveForumManager(this);
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


    /**
     * Storage class has "super privilege" to access private member in resources class.
     * Use this give-receive pattern to get private members from ResourcesManager (Similar to friend class)
     * Pattern adepted from:
     * https://stackoverflow.com/questions/14226228/implementation-of-friend-concept-in-javat
     **/
    public FinanceManager receiveFinanceManager(FinanceManager financeManager) {
        return financeManager;
    }

    public MeetingManager receiveMeetingManager(MeetingManager meetingManager) {
        return meetingManager;
    }

    public ForumManager receiveForumManager(ForumManager forumManager) {
        return forumManager;
    }
}
