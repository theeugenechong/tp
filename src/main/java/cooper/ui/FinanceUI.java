package cooper.ui;

import java.util.Locale;

public class FinanceUI {

    public static final String initiateBalanceSheet = "You are now using the Balance Sheet function.";
    public static final String firstEntryBalanceSheet = "Please enter Cash & Cash Equivalents:";
    public static final String balanceOpening = "This is the company's current Balance Sheet:";
    public static final String accountMistake = "THERE IS AN ACCOUNTING MISTAKE! One of your entries is incorrect.";
    public static final String accountCorrect = "Balance Sheet is perfectly balanced, as all things should be.";
    public static final String balanceSheetComplete = "The Balance Sheet has been completed! enter 'list' to view.";
    public static final String initiateCashFlow = "You are now using the Cash Flow function.";
    public static final String firstEntryCashFlow = "Please enter Net Income:";
    public static final String statementOpening = "This is the company's current Cash Flow Statement:";
    public static final String cashFlowComplete = "The Cash Flow Statement has been completed! Enter 'list' to view.";
    public static final String[] cashFlowUI = new String[] {
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

    public static final String[] balanceSheetUI = new String[] {
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

    public static final String[] headersUI = new String[] {
        "-----CASH FLOW FROM OPERATING ACTIVITIES-----",
        "-----CASH FLOW FROM INVESTING ACTIVITIES-----",
        "-----CASH FLOW FROM FINANCING ACTIVITIES-----",
        "-----ASSETS-----",
        "-----LIABILITIES-----",
        "-----SHAREHOLDER'S EQUITY-----"
    };

    public static final String[] netAmountsUI = new String[] {
        "Net Cash from Operating Activities",
        "Net Cash from Investing Activities",
        "Net Cash from Financing Activities",
        "Total Assets",
        "Total Liabilities",
        "Total Shareholder's Equity",
        "Check"
    };
}
