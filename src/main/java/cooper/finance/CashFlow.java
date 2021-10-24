package cooper.finance;

import java.util.ArrayList;

public class CashFlow {
    public static ArrayList<Integer> cashFlowStatement;
    public static int netOperatingActivities = 0;
    public static int netInvestingActivities = 0;
    public static int netFinancingActivities = 0;
    public static int cashFlowStage = 0;

    public CashFlow() {
        this.cashFlowStatement = new ArrayList<>();
    }

    public static ArrayList<Integer> getCashFlowStatement() { return cashFlowStatement; }

}