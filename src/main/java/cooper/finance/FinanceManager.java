package cooper.finance;

import java.util.ArrayList;

/**
 * Handles all actions and operations pertaining to financial assistance functions of the application
 */
public class FinanceManager {
    private final ArrayList<Integer> balanceSheet;

    public FinanceManager() {
        balanceSheet = new ArrayList<>();
    }

    /**
     * Getter for the Balance Sheet
     * @return balanceSheet ArrayList
     */
    public ArrayList<Integer> getBalanceSheet() {
        return balanceSheet;
    }

    /**
     * Adds specified amount input by user to the balanceSheet, with specified inflow or outflow
     * @param amount
     * @param isInflow
     */
    public void addBalance(int amount, boolean isInflow) {
        if (isInflow) {
            balanceSheet.add(amount);
        }
        else {
            balanceSheet.add(amount * -1);
        }
    }

}
