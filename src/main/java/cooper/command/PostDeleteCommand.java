package cooper.command;

import cooper.exceptions.InvalidAccessException;
import cooper.exceptions.InvalidForumDeleteByNonOwnerException;
import cooper.exceptions.InvalidForumPostIdException;
import cooper.ui.Ui;
import cooper.forum.ForumManager;
import cooper.verification.SignInDetails;
import cooper.verification.UserRole;
import cooper.resources.ResourcesManager;

/**
 * The child class of Command that handles the 'post delete' command specifically.
 */
public class PostDeleteCommand extends Command {
    private int postId;

    public PostDeleteCommand(int postId) {
        super();
        this.postId = postId;
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
            try {
                String username = signInDetails.getUsername();
                String contentDeleted = forumManager.deletePost(username, postId - 1);
                Ui.printDeletePostCommand(username, contentDeleted);
            } catch (InvalidForumPostIdException e) {
                Ui.printInvalidForumPostIndexError();
            } catch (InvalidForumDeleteByNonOwnerException e) {
                Ui.printInvalidForumDeleteByNonOwnerError();
            }
        } else {
            Ui.printEmployeeHelp();
            Ui.printGeneralHelp();
            Ui.printAdminHelp();
            throw new InvalidAccessException();
        }
    }
}

