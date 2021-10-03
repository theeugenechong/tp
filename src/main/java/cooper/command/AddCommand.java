package cooper.command;

public class AddCommand extends Command {

    private boolean isInflow;
    private String amount;

    public AddCommand() {
        super();
        isInflow = true;
        amount = "";
    }

    public AddCommand(String amount, boolean isInflow) {
        super();
        this.amount = amount;
        this.isInflow = isInflow;
    }

    public void execute() {
        System.out.println("I want to execute this [add] command, but I do not know how!");
        System.out.println("Amount: " + amount);
        System.out.println("isInflow: " + isInflow);
    }

}


