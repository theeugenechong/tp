package cooper.command;

import cooper.exceptions.InvalidAccessException;
import cooper.exceptions.InvalidForumDeleteByNonOwnerException;
import cooper.exceptions.InvalidForumPostIdException;
import cooper.storage.StorageManager;
import cooper.ui.ForumUi;
import cooper.forum.ForumManager;
import cooper.verification.SignInDetails;
import cooper.verification.UserRole;
import cooper.resources.ResourcesManager;

//@@author Rrraaaeee
/**
 * The child class of Command that handles the 'post delete' command specifically.
 */
public class PostDeleteCommand extends Command {
    private final int postId;

    public PostDeleteCommand(int postId) {
        super();
        this.postId = postId;
    }

    /**
     * The override function for executing the 'add' command, calls for 'add' and subsequently
     * printing the status to the command line if and only if
     * the command is being accessed by an 'admin' level user.
     * @param signInDetails Sign in details of user to provide correct access
     * @param resourcesManager Provides access to manipulate data in the cOOPer's {@code FinanceManager},
     *                         {@code MeetingsManager} and {@code ForumManager}
     * @param storageManager Stores data which has just been added
     */
    @Override
    public void execute(SignInDetails signInDetails, ResourcesManager resourcesManager,
                        StorageManager storageManager) throws InvalidAccessException, NumberFormatException {
        UserRole userRole = signInDetails.getUserRole();
        ForumManager forumManager = resourcesManager.getForumManager(userRole);
        if (forumManager == null) {
            throw new InvalidAccessException();
        }

        try {
            String username = signInDetails.getUsername();
            String contentDeleted = forumManager.deletePost(username, postId - 1);
            storageManager.saveForum(forumManager);
            ForumUi.printDeletePostCommand(username, contentDeleted);
        } catch (InvalidForumPostIdException e) {
            ForumUi.printInvalidForumPostIndexError();
        } catch (InvalidForumDeleteByNonOwnerException e) {
            ForumUi.printInvalidForumDeleteByNonOwnerError();
        }
    }
}

