package cooper.forum;

import java.util.ArrayList;

//@@author Rrraaaeee

public class ForumPost extends ForumPostBase {
    private ArrayList<ForumComment> forumComments;

    public ForumPost(String username, String content) {
        super(username, content);
        forumComments = new ArrayList<ForumComment>();
    }

    public void addComment(String username, String content) {
        forumComments.add(new ForumComment(username, content));
    }

    public ArrayList<ForumComment> getComments() {
        return forumComments;
    }

}
