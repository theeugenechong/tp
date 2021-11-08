package cooper.forum;

import java.util.ArrayList;

import cooper.exceptions.InvalidForumPostIdException;
import cooper.exceptions.InvalidForumDeleteByNonOwnerException;
import cooper.ui.ForumUi;

//@@author Rrraaaeee

public class ForumManager {
    private final ArrayList<ForumPost> forumPosts;
    private static final Integer ID_UPPER_LIMIT = 300_000_000;

    public ForumManager() {
        forumPosts = new ArrayList<>();
    }

    /**
     * This method creates and adds a post to the forumPost arraylist data structure.
     * @param username username of the user who added the post
     * @param content post content
     */
    public void addPost(String username, String content) {
        forumPosts.add(new ForumPost(username, content));
    }

    /**
     * This method adds a comment to the specified post id. If the post id is invalid,
     * Throw an invalidForumPostIdException.
     * @param username username of the user who added the comment
     * @param content comment content
     * @param postId post id of where comment goes to
     * @throws InvalidForumPostIdException post id is invalid or outside range
     * @throws NumberFormatException post id is not a number
     */
    public void addComment(String username, String content, int postId)
            throws InvalidForumPostIdException, NumberFormatException {
        checkValidPostId(postId);
        forumPosts.get(postId).addComment(username, content);
    }

    /**
     * This method deletes a post to the specified post id. If the post id is invalid,
     * Throw an invalidForumPostIdException.
     * @param username username of the user who added the comment
     * @return content of the post deleted
     * @throws InvalidForumPostIdException post id is invalid or outside range
     * @throws InvalidForumDeleteByNonOwnerException deletion is not done by the owner
     * @throws NumberFormatException post id is not a number
     */
    public String deletePost(String username, int postId) 
            throws InvalidForumPostIdException, InvalidForumDeleteByNonOwnerException, NumberFormatException {
        checkValidPostId(postId);
        ForumPost post = forumPosts.get(postId);
        if (post.getUsername().equals(username)) {
            // can only delete one's own post
            String content = forumPosts.get(postId).getContent();
            forumPosts.remove(postId);
            return content;
        } else {
            throw new InvalidForumDeleteByNonOwnerException();
        }
    }

    /**
     * This method adds a comment object to the arraylist stored under post object.
     * @param username username of the user who added the comment
     * @param content comment content
     * @param postId post id of where comment goes to
     * @return content of the comment for printing onto UI
     * @throws InvalidForumPostIdException post id is invalid or outside range
     * @throws NumberFormatException post id is not a number
     */
    public String commentPost(String username, String content, int postId)  
            throws InvalidForumPostIdException, NumberFormatException {
        checkValidPostId(postId);
        forumPosts.get(postId).addComment(username, content);
        return forumPosts.get(postId).getContent(); // return the original post for Ui
    }

    public void listPosts() {
        ForumUi.printForumPosts(forumPosts);
    }

    public void listPost(int postId) throws InvalidForumPostIdException, NumberFormatException {
        checkValidPostId(postId);
        ForumUi.printForumPost(forumPosts, postId);
    }

    /**
     * This method does sanity check to make sure post Id keyed in is a valid id.
     * Throws exceptions otherwise
     * @param postId post id of where comment goes to
     * @throws InvalidForumPostIdException post id is invalid or outside range
     * @throws NumberFormatException post id is not a number
     */
    private void checkValidPostId(int postId) throws InvalidForumPostIdException, NumberFormatException {
        if (postId >= forumPosts.size() || postId < 0) {
            if (postId > ID_UPPER_LIMIT) {
                throw new NumberFormatException();
            }
            throw new InvalidForumPostIdException();
        }
    }

    public ArrayList<ForumPost> getForumPosts() {
        return forumPosts;
    }
}
