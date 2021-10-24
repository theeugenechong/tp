package cooper.command;

import cooper.exceptions.InvalidAccessException;
import cooper.exceptions.InvalidForumPostIdException;
import cooper.ui.Ui;
import cooper.forum.ForumManager;
import cooper.verification.SignInDetails;
import cooper.verification.UserRole;
import cooper.resources.ResourcesManager;

/**
 * The child class of Command that handles the 'post list' command specifically.
 */
public class PostListCommand extends Command {
    private final int postId;

    public PostListCommand(int postId) {
        super();
        this.postId = postId;
    }

    /**
     * The override function for executing the 'post list' command
     * It lists a specific forum post or all posts  available on the forum
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
            if (postId == -1) {
                forumManager.listPosts();
            } else {
                try {
                    forumManager.listPost(postId - 1);
                } catch (InvalidForumPostIdException e) {
                    Ui.printInvalidForumPostIndexError();
                }
            }
        } else {
            Ui.printEmployeeHelp();
            Ui.printGeneralHelp();
            Ui.printAdminHelp();
            throw new InvalidAccessException();
        }
    }
}

