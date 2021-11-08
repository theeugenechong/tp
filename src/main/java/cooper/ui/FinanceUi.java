package cooper.ui;

import cooper.finance.FinanceManager;

import java.util.ArrayList;

//@@author ChrisLangton

public class FinanceUi extends Ui {

    private static final String INITIATE_BALANCE_SHEET = "You are now using the Balance Sheet function.";
    private static final String VIEW_CURRENT_BS = "You can enter 'list' to view the current Balance Sheet or ";
    private static final String FIRST_ENTRY_BALANCE_SHEET = "start off by entering Cash & Cash Equivalents:";
    private static final String BALANCE_OPENING = "This is the company's current Balance Sheet (in SGD):";
    private static final String ACCOUNT_MISTAKE = "THERE IS AN ACCOUNTING MISTAKE! "
            + "One or more of your entries are incorrect.";
    private static final String ACCOUNT_CORRECT = "Balance Sheet is perfectly balanced, as all things should be.";
    private static final String BALANCE_SHEET_COMPLETE = "The Balance Sheet is complete! enter 'list' to view.";

    private static final String INITIATE_CASH_FLOW = "You are now using the Cash Flow function.";
    private static final String VIEW_CURRENT_CF = "You can enter 'list' to view the current Cash Flow Statement or ";
    private static final String FIRST_ENTRY_CASH_FLOW = "start off by entering Net Income:";
    private static final String STATEMENT_OPENING = "This is the company's current Cash Flow Statement (in SGD):";
    private static final String CASH_FLOW_COMPLETE = "The Cash Flow Statement is complete! Enter 'list' to view.";

    private static final String ADD_SUCCESS = "Success!";
    private static final String NEXT_PLEASE_ENTER = "Next, please enter ";

    private static final String CANT_ADD_TO_BS = "The Balance Sheet is complete! You can no longer add anything.";
    private static final String CANT_ADD_TO_CF = "The Cash Flow Statement is complete! You can no longer add anything.";

    private static final String STATEMENT_EMPTY = "The financial statement is currently empty! Please add an entry.";
    private static final String STATEMENT_TO_ADD = "Please specify the financial statement you wish to add to.";
    private static final String STATEMENT_TO_VIEW = "Please specify the financial statement you wish to view.";

    private static final String INPUT_VALID_RANGE = "Please enter a positive integer "
            + "(up to 300,000,000) for the argument.";
    private static final String INPUT_VALID_ASSET = "Please enter the asset as a positive number.";
    private static final String INPUT_VALID_LIABILITY = "Please enter the liability as a negative number.";
    private static final String INPUT_VALID_ADD = "Please use the format \"add [amount]\" with [amount] in parentheses"
            + " in the case of a negative number.";

    private static final String INPUT_VALID_PROJECTION = "Please key in a valid number of years (1 or more)";
    private static final String AT_CURRENT_PROFITABILITY = "At your current rate of profitability growth ";
    private static final String IN_FREE_CASH_FLOW = "in Free Cash Flow, these are\nfuture year's projections:";
    private static final String YEARS_CAN_EXPECT = " years you can expect Free Cash Flow of ";

    private static final String MORE_THAN_ONE_BILLION = "1 Billion SGD or more ";
    private static final String LESS_THAN_ONE_BILLION = "-1 Billion SGD or more ";
    private static final String ADDED_AS = " has been added as ";
    private static final String AFTER = "After ";
    private static final String YEAR = " Year: ";

    private static final int AMOUNT_UPPER_LIMIT = 1_000_000_000;
    private static final int AMOUNT_LOWER_LIMIT = -1_000_000_000;
    private static final int PROJECTION_UPPER_LIMIT = 1_000_000_000;
    private static final int PROJECTION_LOWER_LIMIT = -1_000_000_000;

    private static final String CAN_ONLY_GENERATE_BS_OR_CF =
            "The financial statement you want to generate can only be 'bs' or 'cf'!";

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
        "Proceeds from Sale of Equipment  ",
        "Proceeds from Issuing Debt  ",
        "Dividends Paid  ",
        "Last year's Free Cash Flow  "
    };

    public static final String[] HEADERS_UI = new String[] {
        "-----CASH FLOW FROM OPERATING ACTIVITIES-----",
        "-----CASH FLOW FROM INVESTING ACTIVITIES-----",
        "-----CASH FLOW FROM FINANCING ACTIVITIES-----",
        "-----ASSETS-----",
        "-----LIABILITIES-----",
        "-----SHAREHOLDER'S EQUITY-----",
        "-----FREE CASH FLOW-----"
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

    /**
     * Prints the balance sheet to the command line.
     * @param balanceSheet the balance sheet
     */
    public static void printBalanceSheet(ArrayList<Integer> balanceSheet) {
        show(LINE);
        show(BALANCE_OPENING);
        show(HEADERS_UI[3]);
        int i;
        for (i = 0; i < balanceSheet.size(); i++) {
            switch (i) {
            case FinanceManager.endOfAssets:
                show(BALANCE_SHEET_UI[i] + balanceSheet.get(i));
                if (FinanceManager.netAssets > AMOUNT_LOWER_LIMIT && FinanceManager.netAssets < AMOUNT_UPPER_LIMIT) {
                    show(NET_AMOUNTS_UI[3] + FinanceManager.netAssets);
                } else if (FinanceManager.netAssets >= AMOUNT_UPPER_LIMIT) {
                    show(NET_AMOUNTS_UI[3] + MORE_THAN_ONE_BILLION);
                } else {
                    show(NET_AMOUNTS_UI[3] + " " + LESS_THAN_ONE_BILLION);
                }
                show(HEADERS_UI[4]);
                break;
            case FinanceManager.endOfLiabilities:
                show(BALANCE_SHEET_UI[i] + balanceSheet.get(i));
                if (FinanceManager.netLiabilities > AMOUNT_LOWER_LIMIT
                        && FinanceManager.netLiabilities < AMOUNT_UPPER_LIMIT) {
                    show(NET_AMOUNTS_UI[4] + FinanceManager.netLiabilities);
                } else if (FinanceManager.netLiabilities >= AMOUNT_UPPER_LIMIT) {
                    show(NET_AMOUNTS_UI[4] + MORE_THAN_ONE_BILLION);
                } else {
                    show(NET_AMOUNTS_UI[4] + " " + LESS_THAN_ONE_BILLION);
                }
                show(HEADERS_UI[5]);
                break;
            default:
                show(BALANCE_SHEET_UI[i] + balanceSheet.get(i));
                break;
            }
        }

        if (i == balanceSheet.size()) {
            if (FinanceManager.netSE > AMOUNT_LOWER_LIMIT && FinanceManager.netSE < AMOUNT_UPPER_LIMIT) {
                show(NET_AMOUNTS_UI[5] + FinanceManager.netSE);
            } else if (FinanceManager.netSE >= AMOUNT_UPPER_LIMIT) {
                show(NET_AMOUNTS_UI[5] + MORE_THAN_ONE_BILLION);
            } else {
                show(NET_AMOUNTS_UI[5] + " " + LESS_THAN_ONE_BILLION);
            }
        }

        int balance = FinanceManager.netAssets + FinanceManager.netLiabilities - FinanceManager.netSE;

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
                show(CASH_FLOW_UI[i] + cashFlowStatement.get(i));
                if (FinanceManager.netOA > AMOUNT_LOWER_LIMIT && FinanceManager.netOA < AMOUNT_UPPER_LIMIT) {
                    show(NET_AMOUNTS_UI[0] + " " + FinanceManager.netOA);
                } else if (FinanceManager.netOA >= AMOUNT_UPPER_LIMIT) {
                    show(NET_AMOUNTS_UI[0] + " " + MORE_THAN_ONE_BILLION);
                } else {
                    show(NET_AMOUNTS_UI[0] + " " + LESS_THAN_ONE_BILLION);
                }
                show(HEADERS_UI[1]);
                break;
            case FinanceManager.endOfIA:
                show(CASH_FLOW_UI[i] + cashFlowStatement.get(i));
                if (FinanceManager.netIA > AMOUNT_LOWER_LIMIT && FinanceManager.netIA < AMOUNT_UPPER_LIMIT) {
                    show(NET_AMOUNTS_UI[1] + " " + FinanceManager.netIA);
                } else if (FinanceManager.netIA >= AMOUNT_UPPER_LIMIT) {
                    show(NET_AMOUNTS_UI[1] + " " + MORE_THAN_ONE_BILLION);
                } else {
                    show(NET_AMOUNTS_UI[1] + " " + LESS_THAN_ONE_BILLION);
                }
                show(HEADERS_UI[2]);
                break;
            case FinanceManager.freeCashFlow:
                break;
            default:
                show(CASH_FLOW_UI[i] + cashFlowStatement.get(i));
                break;
            }
        }
        if (i == cashFlowStatement.size()) {
            if (FinanceManager.netFA > AMOUNT_LOWER_LIMIT && FinanceManager.netFA < AMOUNT_UPPER_LIMIT) {
                show(NET_AMOUNTS_UI[2] + " " + FinanceManager.netFA);
            } else if (FinanceManager.netFA >= AMOUNT_UPPER_LIMIT) {
                show(NET_AMOUNTS_UI[2] + " " + MORE_THAN_ONE_BILLION);
            } else {
                show(NET_AMOUNTS_UI[2] + " " + LESS_THAN_ONE_BILLION);
            }
            show(HEADERS_UI[6]);
            if (cashFlowStatement.get(FinanceManager.freeCashFlow) > AMOUNT_LOWER_LIMIT
                    && cashFlowStatement.get(FinanceManager.freeCashFlow) < AMOUNT_UPPER_LIMIT) {
                show(CASH_FLOW_UI[FinanceManager.freeCashFlow] + " "
                        + cashFlowStatement.get(FinanceManager.freeCashFlow));
            } else if (cashFlowStatement.get(FinanceManager.freeCashFlow) >= AMOUNT_UPPER_LIMIT) {
                show(CASH_FLOW_UI[FinanceManager.freeCashFlow] + " " + MORE_THAN_ONE_BILLION);
            } else {
                show(CASH_FLOW_UI[FinanceManager.freeCashFlow] + " " + LESS_THAN_ONE_BILLION);

            }
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
        show(CANT_ADD_TO_BS);
        show(LINE);
    }

    public static void showCannotAddToCashFlow() {
        show(LINE);
        show(CANT_ADD_TO_CF);
        show(LINE);
    }

    public static void printAddBalanceCommand(int amount, boolean isInflow, int balanceSheetStage) {
        show(LINE);
        show(ADD_SUCCESS);
        show((isInflow ? "+" : "-") + amount + ADDED_AS + BALANCE_SHEET_UI[balanceSheetStage]);
        switch (balanceSheetStage) {
        case FinanceManager.endOfAssets:
            if (FinanceManager.netAssets < AMOUNT_UPPER_LIMIT) {
                show(NET_AMOUNTS_UI[3] + FinanceManager.netAssets);
            } else {
                show(NET_AMOUNTS_UI[3] + MORE_THAN_ONE_BILLION);
            }
            show(NL + NEXT_PLEASE_ENTER + FinanceUi.BALANCE_SHEET_UI[balanceSheetStage + 1]);
            break;
        case FinanceManager.endOfLiabilities:
            if (FinanceManager.netLiabilities < AMOUNT_UPPER_LIMIT) {
                show(NET_AMOUNTS_UI[4] + FinanceManager.netLiabilities);
            } else {
                show(NET_AMOUNTS_UI[4] + MORE_THAN_ONE_BILLION);
            }
            show(NL + NEXT_PLEASE_ENTER + FinanceUi.BALANCE_SHEET_UI[balanceSheetStage + 1]);
            break;
        case FinanceManager.endOfSE:
            if (FinanceManager.netSE < AMOUNT_UPPER_LIMIT) {
                show(NET_AMOUNTS_UI[5] + FinanceManager.netSE);
            } else {
                show(NET_AMOUNTS_UI[5] + MORE_THAN_ONE_BILLION);
            }
            break;
        default:
            show(NL + NEXT_PLEASE_ENTER + FinanceUi.BALANCE_SHEET_UI[balanceSheetStage + 1]);
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
            if (FinanceManager.netOA < AMOUNT_UPPER_LIMIT && FinanceManager.netOA > AMOUNT_LOWER_LIMIT) {
                show(NET_AMOUNTS_UI[0] + FinanceManager.netOA);
            } else if (FinanceManager.netOA >= AMOUNT_UPPER_LIMIT) {
                show(NET_AMOUNTS_UI[0] + MORE_THAN_ONE_BILLION);
            } else {
                show(NET_AMOUNTS_UI[0] + LESS_THAN_ONE_BILLION);
            }
            show(NL + NEXT_PLEASE_ENTER + FinanceUi.CASH_FLOW_UI[cashFlowStage + 1]);
            break;
        case FinanceManager.endOfIA:
            if (FinanceManager.netIA < AMOUNT_UPPER_LIMIT && FinanceManager.netIA > AMOUNT_LOWER_LIMIT) {
                show(NET_AMOUNTS_UI[1] + FinanceManager.netIA);
            } else if (FinanceManager.netIA >= AMOUNT_UPPER_LIMIT) {
                show(NET_AMOUNTS_UI[1] + MORE_THAN_ONE_BILLION);
            } else {
                show(NET_AMOUNTS_UI[1] + LESS_THAN_ONE_BILLION);
            }
            show(NL + NEXT_PLEASE_ENTER + FinanceUi.CASH_FLOW_UI[cashFlowStage + 1]);
            break;
        case FinanceManager.endOfFA:
            if (FinanceManager.netFA < AMOUNT_UPPER_LIMIT && FinanceManager.netFA > AMOUNT_LOWER_LIMIT) {
                show(NET_AMOUNTS_UI[2] + FinanceManager.netFA);
            } else if (FinanceManager.netFA >= AMOUNT_UPPER_LIMIT) {
                show(NET_AMOUNTS_UI[2] + MORE_THAN_ONE_BILLION);
            } else {
                show(NET_AMOUNTS_UI[2] + LESS_THAN_ONE_BILLION);
            }
            show(NL + NEXT_PLEASE_ENTER + FinanceUi.CASH_FLOW_UI[cashFlowStage + 1]);
            break;
        case FinanceManager.freeCashFlow:
            show("");
            break;
        default:
            show(NL + NEXT_PLEASE_ENTER + FinanceUi.CASH_FLOW_UI[cashFlowStage + 1]);
            break;
        }

        if (cashFlowStage == FinanceManager.freeCashFlow) {
            printCashFlowComplete();
        }
        show(LINE);
    }

    public static void printProjections(double finalGrowthValue, ArrayList<Double> cooperProjections) {
        show(LINE);
        show(AT_CURRENT_PROFITABILITY + IN_FREE_CASH_FLOW);
        int yearCount = 1;
        for (Double cooperProjection : cooperProjections) {
            if (cooperProjection.intValue() < PROJECTION_UPPER_LIMIT
                    && cooperProjection.intValue() > PROJECTION_LOWER_LIMIT) {
                show(yearCount + YEAR + cooperProjection.intValue());
            } else if (cooperProjection.intValue() <= PROJECTION_LOWER_LIMIT) {
                show(yearCount + YEAR + LESS_THAN_ONE_BILLION);
            } else {
                show(yearCount + YEAR + MORE_THAN_ONE_BILLION);
            }
            yearCount++;
        }
        if ((int)finalGrowthValue < PROJECTION_UPPER_LIMIT && (int)finalGrowthValue > PROJECTION_LOWER_LIMIT) {
            show(AFTER + (yearCount - 1) + YEARS_CAN_EXPECT + (int) finalGrowthValue);
        } else if ((int)finalGrowthValue <= PROJECTION_LOWER_LIMIT) {
            show(AFTER + (yearCount - 1) + YEARS_CAN_EXPECT + LESS_THAN_ONE_BILLION);
        } else {
            show(AFTER + (yearCount - 1) + YEARS_CAN_EXPECT + MORE_THAN_ONE_BILLION);
        }
        show(LINE);
    }

    public static void showEmptyFinancialStatementException() {
        show(LINE);
        show(STATEMENT_EMPTY);
        show(LINE);
    }

    public static void showPleaseSpecifyFinancialStatementToAdd() {
        show(LINE);
        show(STATEMENT_TO_ADD);
        show(LINE);
    }

    public static void showPleaseSpecifyFinancialStatementToView() {
        show(LINE);
        show(STATEMENT_TO_VIEW);
        show(LINE);
    }

    public static void showPleaseInputValidProjection() {
        show(LINE);
        show(INPUT_VALID_PROJECTION);
        show(LINE);
    }

    public static void showPleaseInputValidRange() {
        show(LINE);
        show(INPUT_VALID_RANGE);
        show(LINE);
    }


    public static void showInvalidDocumentError() {
        show(LINE);
        show(CAN_ONLY_GENERATE_BS_OR_CF);
        show(LINE);
    }

    public static void showPleaseInputValidAdd() {
        show(LINE);
        show(INPUT_VALID_ADD);
        show(LINE);
    }

    public static void showPleaseInputValidAsset() {
        show(LINE);
        show(INPUT_VALID_ASSET);
        show(LINE);
    }

    public static void showPleaseInputValidLiability() {
        show(LINE);
        show(INPUT_VALID_LIABILITY);
        show(LINE);
    }
}
