package cooper.finance;

import java.util.ArrayList;

public class BalanceSheet {
    public ArrayList<Integer> balanceSheet;
    public static int balanceSheetStage = 0;

    public BalanceSheet() {
        this.balanceSheet = new ArrayList<>();
    }

    public ArrayList<Integer> getBalanceSheet() {
        return balanceSheet;
    }
}
