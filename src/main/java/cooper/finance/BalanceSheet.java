package cooper.finance;

import java.util.ArrayList;

public class BalanceSheet {
    public ArrayList<Integer> balanceSheet;
    public ArrayList<String> sheetDescriptions;

    public BalanceSheet() {
        this.balanceSheet = new ArrayList<>();
        this. sheetDescriptions = new ArrayList<>();
    }

    public ArrayList<Integer> getBalanceSheet() {
        return balanceSheet;
    }
}
