package cooper.finance;

import java.util.logging.Logger;

/**
 * Handles all actions and operations pertaining to financial assistance functions of the application.
 */
public class FinanceManager {
    public BalanceSheet cooperBalanceSheet;
    public CashFlow cooperCashFlowStatement;
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public static final int endOfOA = 4; //INDEX
    public static final int endOfIA = 6; //INDEX
    public static final int endOfFA = 8; //INDEX
    public static int netOA = 0;
    public static int netIA = 0;
    public static int netFA = 0;

    public FinanceManager() {
        this.cooperBalanceSheet = new BalanceSheet();
        this.cooperCashFlowStatement = new CashFlow();
    }

    /**
     * Adds specified amount input by user to the balanceSheet, with specified inflow or outflow.
     * @param amount amount inout by user
     * @param isInflow boolean which specifies if {@code amount} is inflow or outflow
     */
    public void addBalance(int amount, boolean isInflow) {
        int signedAmount = amount;
        if (isInflow) {
            cooperBalanceSheet.balanceSheet.add(signedAmount);
            assert amount >= 0 : "entry should be positive";
        } else {
            signedAmount *= -1;
            cooperBalanceSheet.balanceSheet.add(signedAmount);
            assert amount * -1 < 0 : "entry should be negative";
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
        cooperCashFlowStatement.getCashFlowStatement().add(signedAmount);
        if (cashFlowStage <= endOfOA) {
            netOA += signedAmount;
        } else if (cashFlowStage <= endOfIA) {
            netIA += signedAmount;
        } else if (cashFlowStage <= endOfFA) {
            netFA += signedAmount;
        }
        LOGGER.info("An entry to the cash flow statement is created: " + amount);
    }
}
