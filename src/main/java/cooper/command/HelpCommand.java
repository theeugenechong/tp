package cooper.command;

public class HelpCommand extends Command {

    public HelpCommand() {
        super();
    }

    public void execute() {
        System.out.println("I want to execute this [help] command, but I do not know how!");
    }

}


