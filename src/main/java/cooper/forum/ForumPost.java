package cooper.forum;

import java.util.ArrayList;

//@@author Rrraaaeee

public class ForumPost extends ForumPostBase {
    private final ArrayList<ForumComment> forumComments;

    public ForumPost(String username, String content) {
        super(username, content);
        forumComments = new ArrayList<>();
    }

    public void addComment(String username, String content) {
        forumComments.add(new ForumComment(username, content));
    }

    public ArrayList<ForumComment> getComments() {
        return forumComments;
    }
}
