package cooper.ui;

import cooper.finance.FinanceManager;

import java.util.ArrayList;

public class FinanceUi extends Ui {

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

    public static void printBalanceSheet(ArrayList<Integer> balanceSheet) {
        show(LINE);
        show(FinanceUi.balanceOpening);
        show(FinanceUi.headersUI[3]);
        int i;
        int balance = 0;
        for (i = 0; i < balanceSheet.size(); i++) {
            switch (i) {
            case FinanceManager.endOfAssets:
                show(FinanceUi.balanceSheetUI[i] + balanceSheet.get(i));
                show(FinanceUi.netAmountsUI[3] + " " + FinanceManager.netAssets);
                show(FinanceUi.headersUI[4]);
                break;
            case FinanceManager.endOfLiabilities:
                show(FinanceUi.balanceSheetUI[i] + balanceSheet.get(i));
                show(FinanceUi.netAmountsUI[4] + " " + FinanceManager.netLiabilities);
                show(FinanceUi.headersUI[5]);
                break;
            default:
                show(FinanceUi.balanceSheetUI[i] + balanceSheet.get(i));
                break;
            }
        }
        if (i == balanceSheet.size()) {
            show(FinanceUi.netAmountsUI[5] + " " + FinanceManager.netSE);
        }
        balance = FinanceManager.netAssets - FinanceManager.netLiabilities - FinanceManager.netSE;
        if (balance != 0) {
            show(FinanceUi.accountMistake);
        } else {
            show(FinanceUi.accountCorrect);
        }
        show(FinanceUi.netAmountsUI[6] + ": " + balance);
        show(LINE);

        LOGGER.info("The balance sheet is generated here");
    }

    public static void initiateCashFlowStatement() {
        show(LINE);
        show(FinanceUi.initiateCashFlow);
        show(FinanceUi.firstEntryCashFlow);
        show(LINE);
    }

    public static void initiateBalanceSheet() {
        show(LINE);
        show(FinanceUi.initiateBalanceSheet);
        show(FinanceUi.firstEntryBalanceSheet);
        show(LINE);
    }

    public static void printCashFlowStatement(ArrayList<Integer> cashFlowStatement) {
        show(LINE);
        show(FinanceUi.statementOpening);
        show(FinanceUi.headersUI[0]);
        int i;
        for (i = 0; i < cashFlowStatement.size(); i++) {
            switch (i) {
            case FinanceManager.endOfOA:
                show(FinanceUi.cashFlowUI[i] + cashFlowStatement.get(i));
                show(FinanceUi.netAmountsUI[0] + " " + FinanceManager.netOA);
                show(FinanceUi.headersUI[1]);
                break;
            case FinanceManager.endOfIA:
                show(FinanceUi.cashFlowUI[i] + cashFlowStatement.get(i));
                show(FinanceUi.netAmountsUI[1] + " " + FinanceManager.netIA);
                show(FinanceUi.headersUI[2]);
                break;
            default:
                show(FinanceUi.cashFlowUI[i] + cashFlowStatement.get(i));
                break;
            }
        }
        if (i == cashFlowStatement.size()) {
            show(FinanceUi.netAmountsUI[2] + " " + FinanceManager.netFA);
        }
        show(LINE);
    }

    public static void printCashFlowComplete() {
        show(FinanceUi.cashFlowComplete);
    }

    public static void printBalanceSheetComplete() {
        show(FinanceUi.balanceSheetComplete);
    }

    public static void showCannotAddToBalanceSheet() {
        show(LINE);
        show("The Balance Sheet has been completed! You can no longer add anything.");
        show(LINE);
    }

    public static void showCannotAddToCashFlow() {
        show(LINE);
        show("The Cash Flow Statement has been completed! You can no longer add anything.");
        show(LINE);
    }

    public static void printAddBalanceCommand(int amount, boolean isInflow, int balanceSheetStage) {
        show(LINE);
        show("Success!");
        show((isInflow ? "+" : "-") + amount + " has been added as " + FinanceUi.balanceSheetUI[balanceSheetStage]);
        switch (balanceSheetStage) {
        case FinanceManager.endOfAssets:
            show(FinanceUi.netAmountsUI[3] + " " + FinanceManager.netAssets);
            show("\n" + "next, please enter " + FinanceUi.balanceSheetUI[balanceSheetStage + 1]);
            break;
        case FinanceManager.endOfLiabilities:
            show(FinanceUi.netAmountsUI[4] + " " + FinanceManager.netLiabilities);
            show("\n" + "next, please enter " + FinanceUi.balanceSheetUI[balanceSheetStage + 1]);
            break;
        case FinanceManager.endOfSE:
            show(FinanceUi.netAmountsUI[5] + " " + FinanceManager.netSE);
            break;
        default:
            show("\n" + "next, please enter " + FinanceUi.balanceSheetUI[balanceSheetStage + 1]);
            break;
        }

        if (balanceSheetStage == 11) {
            printBalanceSheetComplete();
        }
        show(LINE);
    }

    public static void printAddCashFlowCommand(int amount, boolean isInflow, int cashFlowStage) {
        show(LINE);
        show("Success!");
        show((isInflow ? "+" : "-") + amount + " has been added as " + FinanceUi.cashFlowUI[cashFlowStage]);
        switch (cashFlowStage) {
        case FinanceManager.endOfOA:
            show(FinanceUi.netAmountsUI[0] + " " + FinanceManager.netOA);
            show("\n" + "next, please enter " + FinanceUi.cashFlowUI[cashFlowStage + 1]);
            break;
        case FinanceManager.endOfIA:
            show(FinanceUi.netAmountsUI[1] + " " + FinanceManager.netIA);
            show("\n" + "next, please enter " + FinanceUi.cashFlowUI[cashFlowStage + 1]);
            break;
        case FinanceManager.endOfFA:
            show(FinanceUi.netAmountsUI[2] + " " + FinanceManager.netFA);
            break;
        default:
            show("\n" + "next, please enter " + FinanceUi.cashFlowUI[cashFlowStage + 1]);
            break;
        }

        if (cashFlowStage == 8) {
            printCashFlowComplete();
        }
        show(LINE);
    }

    public static void showEmptyFinancialStatementException() {
        show(LINE);
        show("The financial statement is currently empty! Please add an entry.");
        show(LINE);
    }
}
