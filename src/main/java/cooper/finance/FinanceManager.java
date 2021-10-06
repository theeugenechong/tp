package cooper.finance;

import java.util.ArrayList;

public class FinanceManager {
    private final ArrayList<Integer> balanceSheet;

    public FinanceManager() {
        balanceSheet = new ArrayList<>();
    }

    public ArrayList<Integer> getBalanceSheet() {
        return balanceSheet;
    }

    public void addBalance(int amount) {
        balanceSheet.add(amount);
    }

}
