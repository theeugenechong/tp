package cooper.command;

public abstract class Command {

    public Command() {
    }

    /**
     * Child classes are required to implement how to execute on itself.
     */
    public abstract void execute();
}
