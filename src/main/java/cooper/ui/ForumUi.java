package cooper.ui;

import cooper.forum.ForumPost;

import java.util.ArrayList;

//@@author Rrraaaeee

public class ForumUi extends Ui {
    private static final String PROMPT_LIST = "Here is the list of forum posts:";
    private static final String PROMPT_POST = "Here is the forum post:";
    private static final String INVALID_DELETE = "You cannot delete a forum post that is not owned by you!.";
    private static final String INVALID_INDEX = "The forum index you just keyed in is outside the valid range.";
    private static final String RESPONSE_COMMENT = " has just commented on a post from the forum:";
    private static final String RESPONSE_DELETE = " has just deleted a post from the forum:";
    private static final String RESPONSE_POST = " has just posted to the forum:";

    public static void printForumPosts(ArrayList<ForumPost> forumPosts) {
        show(LINE);
        show(PROMPT_LIST);
        show(TABLE_LINE);
        int cntPost = 1;
        for (var post : forumPosts) {
            show("|  " + cntPost + ". " + post.toString());
            int cntComment = 1;
            for (var comment : post.getComments()) {
                show("|    -  " + cntComment + ". " + comment.toString());
                cntComment++;
            }
            cntPost++;
        }

        show(TABLE_LINE);
        show(LINE);
    }

    public static void printForumPost(ArrayList<ForumPost> forumPosts, int postId) {
        show(LINE);
        show(PROMPT_POST);
        show(TABLE_LINE);
        show("|  " + forumPosts.get(postId).toString());

        int cntComment = 1;
        for (var comment : forumPosts.get(postId).getComments()) {
            show("|    -  " + cntComment + "." + comment.toString());
            cntComment++;
        }

        show(TABLE_LINE);
        show(LINE);
    }

    public static void printNewPostCommand(String username, String content) {
        show(LINE);
        show(username + RESPONSE_POST);
        show(TABLE_LINE);
        show("|  " + content);
        show(TABLE_LINE);
        show(LINE);
    }

    public static void printDeletePostCommand(String username, String content) {
        show(LINE);
        show(username + RESPONSE_DELETE);
        show(TABLE_LINE);
        show("|  " + content);
        show(TABLE_LINE);
        show(LINE);
    }

    public static void printCommentPostCommand(String username, String content, String comment) {
        show(LINE);
        show(username + RESPONSE_COMMENT);
        show(TABLE_LINE);
        show("|  " + content);
        show("|    -  " + comment);
        show(TABLE_LINE);
        show(LINE);
    }

    public static void printInvalidForumPostIndexError() {
        show(LINE);
        show(INVALID_INDEX);
        show(LINE);
    }

    public static void printInvalidForumDeleteByNonOwnerError() {
        show(LINE);
        show(INVALID_DELETE);
        show(LINE);
    }
}
