package cooper.finance;

import java.util.ArrayList;

public class CashFlow {
    public ArrayList<Integer> cashFlowStatement;
    public static int cashFlowStage = 0;

    public CashFlow() {
        this.cashFlowStatement = new ArrayList<>();
    }

    public ArrayList<Integer> getCashFlowStatement() {
        return cashFlowStatement;
    }

}