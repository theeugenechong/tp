package cooper.finance;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

//@@author ChrisLangton

public class FinanceManagerTest {

    static FinanceManager financeManager;

    @BeforeAll
    static void setUpFinanceManager() {
        financeManager = new FinanceManager();
    }

    @Test
    @Order(1)
    void test_properFirstAdditionToBalanceSheet() {
        int amount = 4000;
        int balanceSheetStage = 0;
        boolean isInflow = true;
        financeManager.addBalance(amount,isInflow, balanceSheetStage);

        Integer expected = 4000;
        // ArrayList<Integer> actualList = financeManager.getBalanceSheet();
        // Integer actualValue = actualList.get(0);
        // assertEquals(expected, actualValue);
    }

    @Test
    @Order(2)
    void test_properSecondAdditionToBalanceSheet() {
        int amount = 5000;
        int balanceSheetStage = 1;
        boolean isInflow = false;
        financeManager.addBalance(amount,isInflow, balanceSheetStage);

        Integer expected = -5000;
        // ArrayList<Integer> actualList = financeManager.getBalanceSheet();
        // Integer actualValue = actualList.get(1);
        // assertEquals(expected, actualValue);
    }

}
