package cooper.forum;

//@@author Rrraaaeee

public class ForumComment extends ForumPostBase {

    /**
     * Constructor of ForumComment object.
     * @param username username of the user who input the comment
     * @param content content of the comment
     */
    public ForumComment(String username, String content) {
        super(username, content);
    }
}
