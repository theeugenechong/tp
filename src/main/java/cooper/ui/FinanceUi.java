package cooper.ui;

import cooper.finance.FinanceManager;

import java.util.ArrayList;

@SuppressWarnings({"checkstyle:LineLength", "CheckStyle"})
public class FinanceUi extends Ui {

    private static final String INITIATE_BALANCE_SHEET = "You are now using the Balance Sheet function.";
    private static final String VIEW_CURRENT_BS = "You can enter 'list' to view the current Balance Sheet or ";
    private static final String FIRST_ENTRY_BALANCE_SHEET = "start off by entering Cash & Cash Equivalents:";
    private static final String BALANCE_OPENING = "This is the company's current Balance Sheet:";
    private static final String ACCOUNT_MISTAKE = "THERE IS AN ACCOUNTING MISTAKE! One of your entries is incorrect.";
    private static final String ACCOUNT_CORRECT = "Balance Sheet is perfectly balanced, as all things should be.";
    private static final String BALANCE_SHEET_COMPLETE = "The Balance Sheet is complete! enter 'list' to view.";

    private static final String INITIATE_CASH_FLOW = "You are now using the Cash Flow function.";
    private static final String VIEW_CURRENT_CF = "You can enter 'list' to view the current Cash Flow Statement or ";
    private static final String FIRST_ENTRY_CASH_FLOW = "start off by entering Net Income:";
    private static final String STATEMENT_OPENING = "This is the company's current Cash Flow Statement:";
    private static final String CASH_FLOW_COMPLETE = "The Cash Flow Statement is complete! Enter 'list' to view.";

    private static final String ADD_SUCCESS = "Success!";
    private static final String NEXT_PLEASE_ENTER = "Next, please enter ";
    protected static final String CANNOT_ADD_TO_BS = "The Balance Sheet is complete! You can no longer add anything.";
    protected static final String CANNOT_ADD_TO_CF = "The Cash Flow Statement is complete! You can no longer add anything.";
    protected static final String FINANCIAL_STATEMENT_EMPTY = "The financial statement is currently empty! Please add an entry.";
    protected static final String SPECIFY_STATEMENT_TO_VIEW = "Please specify the financial statement you wish to view/add to.";
    protected static final String ADDED_AS = " has been added as ";

    public static final String[] BALANCE_SHEET_UI = new String[] {
        "Cash and Cash Equivalents  ",
        "Accounts Receivable  ",
        "Prepaid Expenses  ",
        "Inventory  ",
        "Property and Equipment  ",
        "Goodwill  ",
        "Accounts Payable  ",
        "Accrued Expenses  ",
        "Unearned Revenue  ",
        "Long-term debt  ",
        "Equity Capital  ",
        "Retained Earnings  "
    };

    public static final String[] CASH_FLOW_UI = new String[] {
        "Net Income  ",
        "Depreciation and Amortisation  ",
        "Increase in Accounts Receivable  ",
        "Decrease in Accounts Payable  ",
        "Decrease in Inventory  ",
        "Capital Expenditures  ",
        "Proceeds from sale of equipment  ",
        "Proceeds from Issuing Debt  ",
        "Dividends Paid  "
    };

    public static final String[] HEADERS_UI = new String[] {
        "-----CASH FLOW FROM OPERATING ACTIVITIES-----",
        "-----CASH FLOW FROM INVESTING ACTIVITIES-----",
        "-----CASH FLOW FROM FINANCING ACTIVITIES-----",
        "-----ASSETS-----",
        "-----LIABILITIES-----",
        "-----SHAREHOLDER'S EQUITY-----"
    };

    public static final String[] NET_AMOUNTS_UI = new String[] {
        "Net Cash from Operating Activities: ",
        "Net Cash from Investing Activities: ",
        "Net Cash from Financing Activities: ",
        "Total Assets: ",
        "Total Liabilities: ",
        "Total Shareholder's Equity: ",
        "Check: "
    };

    public static void printBalanceSheet(ArrayList<Integer> balanceSheet) {
        show(LINE);
        show(BALANCE_OPENING);
        show(HEADERS_UI[3]);
        int i;
        for (i = 0; i < balanceSheet.size(); i++) {
            switch (i) {
            case FinanceManager.endOfAssets:
                show(BALANCE_SHEET_UI[i] + balanceSheet.get(i));
                show(NET_AMOUNTS_UI[3] + FinanceManager.netAssets);
                show(HEADERS_UI[4]);
                break;
            case FinanceManager.endOfLiabilities:
                show(BALANCE_SHEET_UI[i] + balanceSheet.get(i));
                show(NET_AMOUNTS_UI[4] + FinanceManager.netLiabilities);
                show(HEADERS_UI[5]);
                break;
            default:
                show(BALANCE_SHEET_UI[i] + balanceSheet.get(i));
                break;
            }
        }
        if (i == balanceSheet.size()) {
            show(NET_AMOUNTS_UI[5] + FinanceManager.netSE);
        }

        int balance = FinanceManager.netAssets - FinanceManager.netLiabilities - FinanceManager.netSE;

        if (balance != 0) {
            show(ACCOUNT_MISTAKE);
        } else {
            show(ACCOUNT_CORRECT);
        }

        show(NET_AMOUNTS_UI[6] + balance);
        show(LINE);

        LOGGER.info("The balance sheet is generated here");
    }

    public static void initiateCashFlowStatement() {
        show(LINE);
        show(INITIATE_CASH_FLOW);
        show(VIEW_CURRENT_CF);
        show(FIRST_ENTRY_CASH_FLOW);
        show(LINE);
    }

    public static void initiateBalanceSheet() {
        show(LINE);
        show(INITIATE_BALANCE_SHEET);
        show(VIEW_CURRENT_BS);
        show(FIRST_ENTRY_BALANCE_SHEET);
        show(LINE);
    }

    public static void printCashFlowStatement(ArrayList<Integer> cashFlowStatement) {
        show(LINE);
        show(FinanceUi.STATEMENT_OPENING);
        show(FinanceUi.HEADERS_UI[0]);
        int i;
        for (i = 0; i < cashFlowStatement.size(); i++) {
            switch (i) {
            case FinanceManager.endOfOA:
                show(FinanceUi.CASH_FLOW_UI[i] + cashFlowStatement.get(i));
                show(FinanceUi.NET_AMOUNTS_UI[0] + FinanceManager.netOA);
                show(FinanceUi.HEADERS_UI[1]);
                break;
            case FinanceManager.endOfIA:
                show(CASH_FLOW_UI[i] + cashFlowStatement.get(i));
                show(NET_AMOUNTS_UI[1] + FinanceManager.netIA);
                show(HEADERS_UI[2]);
                break;
            default:
                show(CASH_FLOW_UI[i] + cashFlowStatement.get(i));
                break;
            }
        }
        if (i == cashFlowStatement.size()) {
            show(NET_AMOUNTS_UI[2] + FinanceManager.netFA);
        }
        show(LINE);
    }

    public static void printCashFlowComplete() {
        show(CASH_FLOW_COMPLETE);
    }

    public static void printBalanceSheetComplete() {
        show(BALANCE_SHEET_COMPLETE);
    }

    public static void showCannotAddToBalanceSheet() {
        show(LINE);
        show(CANNOT_ADD_TO_BS);
        show(LINE);
    }

    public static void showCannotAddToCashFlow() {
        show(LINE);
        show(CANNOT_ADD_TO_CF);
        show(LINE);
    }

    public static void printAddBalanceCommand(int amount, boolean isInflow, int balanceSheetStage) {
        show(LINE);
        show(ADD_SUCCESS);
        show((isInflow ? "+" : "-") + amount + ADDED_AS + BALANCE_SHEET_UI[balanceSheetStage]);
        switch (balanceSheetStage) {
        case FinanceManager.endOfAssets:
            show(NET_AMOUNTS_UI[3] + FinanceManager.netAssets);
            show("\n" + NEXT_PLEASE_ENTER + FinanceUi.BALANCE_SHEET_UI[balanceSheetStage + 1]);
            break;
        case FinanceManager.endOfLiabilities:
            show(NET_AMOUNTS_UI[4] + FinanceManager.netLiabilities);
            show("\n" + NEXT_PLEASE_ENTER + FinanceUi.BALANCE_SHEET_UI[balanceSheetStage + 1]);
            break;
        case FinanceManager.endOfSE:
            show(NET_AMOUNTS_UI[5] + FinanceManager.netSE);
            break;
        default:
            show("\n" + NEXT_PLEASE_ENTER + FinanceUi.BALANCE_SHEET_UI[balanceSheetStage + 1]);
            break;
        }

        if (balanceSheetStage == FinanceManager.endOfSE) {
            printBalanceSheetComplete();
        }
        show(LINE);
    }

    public static void printAddCashFlowCommand(int amount, boolean isInflow, int cashFlowStage) {
        show(LINE);
        show(ADD_SUCCESS);
        show((isInflow ? "+" : "-") + amount + ADDED_AS + CASH_FLOW_UI[cashFlowStage]);
        switch (cashFlowStage) {
        case FinanceManager.endOfOA:
            show(NET_AMOUNTS_UI[0] + FinanceManager.netOA);
            show("\n" + NEXT_PLEASE_ENTER + FinanceUi.CASH_FLOW_UI[cashFlowStage + 1]);
            break;
        case FinanceManager.endOfIA:
            show(NET_AMOUNTS_UI[1] + FinanceManager.netIA);
            show("\n" + NEXT_PLEASE_ENTER + FinanceUi.CASH_FLOW_UI[cashFlowStage + 1]);
            break;
        case FinanceManager.endOfFA:
            show(NET_AMOUNTS_UI[2] + FinanceManager.netFA);
            break;
        default:
            show("\n" + NEXT_PLEASE_ENTER + FinanceUi.CASH_FLOW_UI[cashFlowStage + 1]);
            break;
        }

        if (cashFlowStage == FinanceManager.endOfFA) {
            printCashFlowComplete();
        }
        show(LINE);
    }

    public static void showEmptyFinancialStatementException() {
        show(LINE);
        show(FINANCIAL_STATEMENT_EMPTY);
        show(LINE);
    }

    public static void showPleaseSpecifyFinancialStatement() {
        show(LINE);
        show(SPECIFY_STATEMENT_TO_VIEW);
        show(LINE);
    }
}
