package cooper.finance;

import java.util.ArrayList;

//@@author ChrisLangton

/**
 * The class in which the cash flow statement is kept.
 */
public class CashFlow {
    private static final int CF_SIZE = 10;
    public ArrayList<Integer> cashFlowStatement;
    public static int cashFlowStage = 0;

    public CashFlow() {
        this.cashFlowStatement = new ArrayList<>(CF_SIZE);

        for (int i = 0; i < CF_SIZE; i++) {
            this.cashFlowStatement.add(0);
        }
    }

    public ArrayList<Integer> getCashFlowStatement() {
        return cashFlowStatement;
    }
}