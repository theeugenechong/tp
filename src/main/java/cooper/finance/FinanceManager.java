package cooper.finance;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Handles all actions and operations pertaining to financial assistance functions of the application.
 */
public class FinanceManager {
    public static BalanceSheet cooperBalanceSheet;
    public static CashFlow cooperCashFlowStatement;
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

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
        if (isInflow) {
            cooperBalanceSheet.balanceSheet.add(amount);
            assert amount >= 0 : "entry should be positive";
        } else {
            cooperBalanceSheet.balanceSheet.add(amount * -1);
            assert amount * -1 < 0 : "entry should be negative";
        }
        LOGGER.info("An entry to the balance sheet is created: " + amount);
    }

    public void addCashFlow(int amount, boolean isInflow) {
        if (isInflow) {
            cooperCashFlowStatement.cashFlowStatement.add(amount);
            assert amount >= 0 : "entry should be positive";
        } else {
            cooperCashFlowStatement.cashFlowStatement.add(amount * -1);
            assert amount * -1 < 0 : "entry should be negative";
        }
        LOGGER.info("An entry to the cash flow statement is created: " + amount);
    }
}
