package cooper.finance;

import cooper.finance.pdfgenerator.BalanceSheetGenerator;
import cooper.finance.pdfgenerator.CashFlowStatementGenerator;
import java.util.ArrayList;
import java.util.logging.Logger;

//@@author ChrisLangton
/**
 * Handles all actions and operations pertaining to financial assistance functions of the application.
 */
public class FinanceManager {
    public BalanceSheet cooperBalanceSheet;
    public CashFlow cooperCashFlowStatement;
    public Projection cooperProjection;
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public static final int endOfOA = 4; //INDEX
    public static final int endOfIA = 6; //INDEX
    public static final int endOfFA = 8; //INDEX
    public static final int freeCashFlow = 9; //INDEX
    public static int netOA = 0;
    public static int netIA = 0;
    public static int netFA = 0;
    public static final int endOfAssets = 5;
    public static final int endOfLiabilities = 9;
    public static final int endOfSE = 11;
    public static int netAssets = 0;
    public static int netLiabilities = 0;
    public static int netSE = 0;
    public static int pastFCF = 0;
    public static int capExIndex = 5;

    private final BalanceSheetGenerator balanceSheetGenerator;
    private final CashFlowStatementGenerator cashFlowStatementGenerator;

    public FinanceManager() {
        this.cooperBalanceSheet = new BalanceSheet();
        this.cooperCashFlowStatement = new CashFlow();
        this.balanceSheetGenerator = new BalanceSheetGenerator();
        this.cashFlowStatementGenerator = new CashFlowStatementGenerator();
        this.cooperProjection = new Projection();
    }

    /**
     * Adds specified amount input by user to the balanceSheet, with specified inflow or outflow.
     * @param amount amount inout by user
     * @param isInflow boolean which specifies if {@code amount} is inflow or outflow
     */
    public void addBalance(int amount, boolean isInflow, int balanceSheetStage) {
        int signedAmount = amount;
        if (isInflow) {
            assert amount >= 0 : "entry should be positive";
        } else {
            signedAmount *= -1;
            assert amount * -1 < 0 : "entry should be negative";
        }

        cooperBalanceSheet.getBalanceSheet().set(balanceSheetStage, signedAmount);
        if (balanceSheetStage <= endOfAssets) {
            netAssets += signedAmount;
        } else if (balanceSheetStage <= endOfLiabilities) {
            netLiabilities += signedAmount;
        } else if (balanceSheetStage <= endOfSE) {
            netSE += signedAmount;
        }

        LOGGER.info("An entry to the balance sheet is created: " + amount);
    }

    public void addCashFlow(int amount, boolean isInflow, int cashFlowStage) {
        int signedAmount = amount;
        if (isInflow) {
            assert amount >= 0 : "entry should be positive";
        } else {
            signedAmount *= -1;
            assert amount * -1 < 0 : "entry should be negative";
        }
        cooperCashFlowStatement.getCashFlowStatement().set(cashFlowStage, signedAmount);
        if (cashFlowStage <= endOfOA) {
            netOA += signedAmount;
        } else if (cashFlowStage <= endOfIA) {
            netIA += signedAmount;
        } else if (cashFlowStage <= endOfFA) {
            netFA += signedAmount;
        } else {
            pastFCF += signedAmount;
        }
        LOGGER.info("An entry to the cash flow statement is created: " + amount);
    }

    //@@author theeugenechong
    public void generateBalanceSheetAsPdf() {
        balanceSheetGenerator.addAssets(cooperBalanceSheet);
        balanceSheetGenerator.addLiabilities(cooperBalanceSheet);
        balanceSheetGenerator.addShareholderEquity(cooperBalanceSheet);
        balanceSheetGenerator.addBalance();

        balanceSheetGenerator.compilePdfAndSend();
    }

    public void generateCashFlowStatementAsPdf() {
        cashFlowStatementGenerator.addCfFromOperatingActivities(cooperCashFlowStatement);
        cashFlowStatementGenerator.addCfFromInvestingActivities(cooperCashFlowStatement);
        cashFlowStatementGenerator.addCfFromFinancingActivities(cooperCashFlowStatement);

        cashFlowStatementGenerator.compilePdfAndSend();
    }

    //@@author ChrisLangton
    public int calculateFreeCashFlow(ArrayList<Integer> cashFlowStatement) {
        int freeCashFlow = netOA - cashFlowStatement.get(capExIndex);
        return freeCashFlow;
    }

    public double createProjection(double principal, double rate, int years) {
        if (years > 0) {
            double growth = (principal * Math.pow(1 + (rate / 100), years));
            cooperProjection.getProjection().add(growth);
            return createProjection(growth, rate, years - 1);
        }
        return principal;
    }

    public static void runNetAmountsCheck(ArrayList<Integer> cashFlowStatement) {
        if (netOA == 0) {
            for (int i = 0; i < cashFlowStatement.size(); i++) {
                if (i <= endOfOA) {
                    netOA += cashFlowStatement.get(i);
                } else if (i <= endOfIA) {
                    netIA += cashFlowStatement.get(i);
                } else if (i <= endOfFA) {
                    netFA += cashFlowStatement.get(i);
                } else {
                    pastFCF += cashFlowStatement.get(i);
                }
            }
        }
    }
}
