package cooper.command;

import cooper.exceptions.FinishAppException;

public abstract class Command {

    /**
     * Constructor.
     */
    public Command() {
    }

    /**
     * Child classes are required to implement how to execute on itself.
     */
    public abstract void execute() throws FinishAppException;
}
