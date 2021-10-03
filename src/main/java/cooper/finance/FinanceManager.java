package cooper.finance;
import java.util.ArrayList;

public class FinanceManager {
    public static ArrayList<Integer> balanceSheet = new ArrayList<>();


    public FinanceManager() {

    }

    public static void addBalance(int amount) {
        balanceSheet.add(Integer.valueOf(amount));
    }

}
