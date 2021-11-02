package cooper.ui;

import cooper.forum.ForumPost;

import java.util.ArrayList;

//@@author Rrraaaeee

public class ForumUi extends Ui {

    public static void printForumPosts(ArrayList<ForumPost> forumPosts) {
        show(LINE);
        show("Here is the list of forum posts:");
        show(TABLE_LINE);
        int cntPost = 1;
        for (var post : forumPosts) {
            show("|  " + cntPost + ". " + post.toString());
            int cntComment = 1;
            for (var comment : post.getComments()) {
                show("|    ∟  " + cntComment + ". " + comment.toString());
                cntComment++;
            }
            cntPost++;
        }

        show(TABLE_LINE);
        show(LINE);
    }

    public static void printForumPost(ArrayList<ForumPost> forumPosts, int postId) {
        show(LINE);
        show("Here is the forum post:");
        show(TABLE_LINE);
        show("|  " + forumPosts.get(postId).toString());

        int cntComment = 1;
        for (var comment : forumPosts.get(postId).getComments()) {
            show("|    ∟  " + cntComment + "." + comment.toString());
            cntComment++;
        }

        show(TABLE_LINE);
        show(LINE);
    }

    public static void printNewPostCommand(String username, String content) {
        show(LINE);
        show(username + " has just posted to forum:");
        show(TABLE_LINE);
        show("|  " + content);
        show(TABLE_LINE);
        show(LINE);
    }

    public static void printDeletePostCommand(String username, String content) {
        show(LINE);
        show(username + " has just deleted a post from forum:");
        show(TABLE_LINE);
        show("|  " + content);
        show(TABLE_LINE);
        show(LINE);
    }

    public static void printCommentPostCommand(String username, String content, String comment) {
        show(LINE);
        show(username + " has just commented on a  post from forum:");
        show(TABLE_LINE);
        show("|  " + content);
        show("|    ∟  " + comment);
        show(TABLE_LINE);
        show(LINE);
    }

    public static void printInvalidForumPostIndexError() {
        show(LINE);
        show("The forum index you just keyed in is outside the valid range.");
        show(LINE);
    }

    public static void printInvalidForumDeleteByNonOwnerError() {
        show(LINE);
        show("You cannot delete a forum post that is not owned by you!.");
        show(LINE);
    }
}
