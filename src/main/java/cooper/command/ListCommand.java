package cooper.command;

import cooper.ui.Ui;
import cooper.finance.FinanceManager;

public class ListCommand extends Command {

    public ListCommand() {
        super();
    }

    public void execute() {
        Ui.showSeperator();
        System.out.println("This is the company's current Balance Sheet: ");
        FinanceManager.printBalanceSheet();
        Ui.showSeperator();
    }

}


