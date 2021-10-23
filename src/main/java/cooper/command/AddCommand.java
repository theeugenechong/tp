package cooper.command;

import cooper.exceptions.InvalidAccessException;
import cooper.storage.StorageManager;
import cooper.ui.Ui;
import cooper.finance.FinanceManager;
import cooper.verification.SignInDetails;
import cooper.verification.UserRole;
import cooper.resources.ResourcesManager;

/**
 * The child class of Command that handles the 'add' command specifically.
 */
public class AddCommand extends Command {

    public boolean isInflow;
    public int amount;

    public AddCommand(int amount, boolean isInflow) {
        super();
        this.amount = amount;
        this.isInflow = isInflow;
    }

    /**
     * The override function for executing the 'add' command, calls for 'add' and subsequently
     * printing the status to the command line if and only if
     * the command is being accessed by an 'admin' level user.
     * @param signInDetails access role
     * @param resourcesManager handles all manager classes and their access rights
     * @param storageManager save to storage
     */
    @Override
    public void execute(SignInDetails signInDetails, 
            ResourcesManager resourcesManager, StorageManager storageManager) throws InvalidAccessException {
        UserRole userRole = signInDetails.getUserRole();
        FinanceManager financeManager = resourcesManager.getFinanceManager(userRole);
        if (financeManager != null) {
            financeManager.addBalance(amount, isInflow);
            storageManager.saveBalanceSheet(financeManager);
            Ui.printAddCommand(amount, isInflow);
        } else {
            Ui.printAdminHelp();
            Ui.printGeneralHelp();
            throw new InvalidAccessException();
        }
    }
}


