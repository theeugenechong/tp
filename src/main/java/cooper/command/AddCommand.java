package cooper.command;

import cooper.ui.Ui;
import cooper.finance.FinanceManager;

public class AddCommand extends Command {

    private boolean isInflow;
    private String amount;



    public AddCommand(String amount, boolean isInflow) {
        super();
        this.amount = amount;
        this.isInflow = isInflow;
    }

    public void execute() {

        FinanceManager.addBalance(Integer.parseInt(amount));

        Ui.showSeperator();
        System.out.println("Success!");
        System.out.println("Amount: " + (isInflow ? "+" : "-") + amount + " has been added to the Balance Sheet.");
        Ui.showSeperator();
    }

}


