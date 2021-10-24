package cooper.command;

import cooper.exceptions.InvalidAccessException;
import cooper.ui.Ui;
import cooper.forum.ForumManager;
import cooper.verification.SignInDetails;
import cooper.verification.UserRole;
import cooper.resources.ResourcesManager;

/**
 * The child class of Command that handles the 'post add' command specifically.
 */
public class PostAddCommand extends Command {
    private String content;

    public PostAddCommand(String content) {
        super();
        this.content = content;
    }

    /**
     * The override function for executing the 'post add' command
     * It adds a new post thread to the forum
     * the command is being accessed by 'employee' and 'admin' level users.
     * @param signInDetails access role
     * @param resourcesManager handles all manager classes and their access rights
     */
    @Override
    public void execute(SignInDetails signInDetails, 
            ResourcesManager resourcesManager) throws InvalidAccessException {
        UserRole userRole = signInDetails.getUserRole();
        ForumManager forumManager = resourcesManager.getForumManager(userRole);
        if (forumManager != null) {
            String username = signInDetails.getUsername();
            forumManager.addPost(username, content);
            Ui.printNewPostCommand(username, content);
        } else {
            Ui.printEmployeeHelp();
            Ui.printGeneralHelp();
            Ui.printAdminHelp();
            throw new InvalidAccessException();
        }
    }
}

