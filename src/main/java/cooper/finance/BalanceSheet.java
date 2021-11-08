package cooper.finance;

import java.util.ArrayList;

//@@author ChrisLangton

/**
 * The class in which the balance sheet is kept.
 */
public class BalanceSheet {
    private static final int BS_SIZE = 12;
    public ArrayList<Integer> balanceSheet;
    public static int balanceSheetStage = 0;

    public BalanceSheet() {
        this.balanceSheet = new ArrayList<>(BS_SIZE);

        for (int i = 0; i < BS_SIZE; i++) {
            this.balanceSheet.add(0);
        }
    }

    public ArrayList<Integer> getBalanceSheet() {
        return balanceSheet;
    }
}
