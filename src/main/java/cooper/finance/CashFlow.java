package cooper.finance;

import java.util.ArrayList;

public class CashFlow {
    public static ArrayList<Integer> cashFlowStatement;
    public static ArrayList<String> statementDescriptions;


    public CashFlow() {
        this.cashFlowStatement = new ArrayList<>();
        this.statementDescriptions = new ArrayList<>();
    }

    public static ArrayList<Integer> getCashFlowStatement() { return cashFlowStatement; }

    public static ArrayList<String> getStatementDescriptions() { return statementDescriptions; }
}