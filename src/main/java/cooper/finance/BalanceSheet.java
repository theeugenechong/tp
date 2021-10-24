package cooper.finance;

import java.util.ArrayList;

public class BalanceSheet {
    public static ArrayList<Integer> balanceSheet;
    public static ArrayList<String> sheetDescriptions;

    public BalanceSheet() {
        this.balanceSheet = new ArrayList<>();
        this. sheetDescriptions = new ArrayList<>();
    }

    public static ArrayList<Integer> getBalanceSheet() {
        return balanceSheet;
    }
}
