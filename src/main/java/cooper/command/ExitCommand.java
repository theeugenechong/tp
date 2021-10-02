package cooper.command;

public class ExitCommand extends Command {

    public ExitCommand() {
        super();
    }

    public void execute() {
        System.out.println("I want to execute this [exit] command, but I do not know how!");
    }

}


