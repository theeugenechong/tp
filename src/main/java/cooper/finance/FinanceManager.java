package cooper.finance;

import cooper.exceptions.InvalidProjectionException;
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
    public static int projectionIterator = 1;

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

    /**
     * Adds entries to the correct cash flow statement section in order.
     * @param amount the amount input
     * @param isInflow the inflow or outflow boolean
     * @param cashFlowStage the stage of the statement
     */
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

    @SuppressWarnings("UnnecessaryLocalVariable")
    /**
     * calculates this statements free cash flow based on the convention formula
     */
    public int calculateFreeCashFlow(ArrayList<Integer> cashFlowStatement) {
        int freeCashFlow = netOA - cashFlowStatement.get(capExIndex);
        return freeCashFlow;
    }

    /**
     * recursively finds the compounded free cash flow projection for each year up to the specified year.
     * @param principal the current principal
     * @param rate the fixed rate
     * @param years the number of years indicated
     * @return principal the final projection value
     * @throws InvalidProjectionException the exception
     */
    public double createProjection(double principal, double rate, int years) throws InvalidProjectionException {

        if (years <= 0) {
            throw new InvalidProjectionException();
        }

        if (projectionIterator <= years) {
            double growth = principal * (1 + (rate / 100));
            cooperProjection.getProjection().add(growth);
            projectionIterator++;
            return createProjection(growth, rate, years);
        }
        projectionIterator = 1;
        return principal;
    }

    /**
     * checks that the net amounts of the balance sheet are correctly summed.
     * @param balanceSheet the balance sheet
     */
    public static void runTotalAmountsCheck(ArrayList<Integer> balanceSheet) {
        netAssets = netLiabilities = netSE = 0;
        for (int i = 0; i < balanceSheet.size(); i++) {
            if (i <= endOfAssets) {
                netAssets += balanceSheet.get(i);
            } else if (i <= endOfLiabilities) {
                netLiabilities += balanceSheet.get(i);
            } else {
                netSE += balanceSheet.get(i);
            }
        }
    }

    /**
     * checks that the net amounts of the cash flow statement are correctly summed.
     * @param cashFlowStatement the cash flow statement
     */
    public static void runNetAmountsCheck(ArrayList<Integer> cashFlowStatement) {
        netOA = netIA = netFA = pastFCF = 0;
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

    //@@author theeugenechong
    /**
     * Creates a pdf version of the balance sheet using an online LaTeX compiler. A backup text file is created
     * in the event that there is no internet connection.
     */
    public void generateBalanceSheetAsPdf() {
        balanceSheetGenerator.addAssets(cooperBalanceSheet);
        balanceSheetGenerator.addLiabilities(cooperBalanceSheet);
        balanceSheetGenerator.addShareholderEquity(cooperBalanceSheet);
        balanceSheetGenerator.addCheckValue();

        balanceSheetGenerator.compilePdfAndSend();
    }

    /**
     * Creates a pdf version of the cash flow statement using an online LaTeX compiler. A backup text file is created
     * in the event that there is no internet connection.
     */
    public void generateCashFlowStatementAsPdf() {
        cashFlowStatementGenerator.addCfFromOperatingActivities(cooperCashFlowStatement);
        cashFlowStatementGenerator.addCfFromInvestingActivities(cooperCashFlowStatement);
        cashFlowStatementGenerator.addCfFromFinancingActivities(cooperCashFlowStatement);
        cashFlowStatementGenerator.addFreeCashFlow(cooperCashFlowStatement);

        cashFlowStatementGenerator.compilePdfAndSend();
    }
}
