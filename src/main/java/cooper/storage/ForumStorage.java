package cooper.storage;

import cooper.exceptions.InvalidForumPostIdException;
import cooper.forum.ForumComment;
import cooper.forum.ForumManager;
import cooper.forum.ForumPost;
import cooper.ui.FileIoUi;
import cooper.ui.ForumUi;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

//@@author Rrraaaeee

public class ForumStorage extends Storage {

    protected static final String POST = "P";
    protected static final String COMMENT = "C";

    public ForumStorage(String filePath) {
        super(filePath);
    }

    public void loadForum(ForumManager cooperForumManager) {
        Scanner fileScanner = getScanner(filePath);
        if (fileScanner == null) {
            return;
        }
        int currentPostId = 0;
        while (fileScanner.hasNext()) {
            String[] post = fileScanner.nextLine().split(SEPARATOR_REGEX);
            switch (post[0]) {
            case POST:
                currentPostId++;
                cooperForumManager.addPost(post[1],post[2]);
                break;
            case COMMENT:
                tryAddPostComment(cooperForumManager, post, currentPostId - 1);
                break;
            default:
                break;
            }
        }
    }

    private void tryAddPostComment(ForumManager forumManager, String[] post, int postId) {
        try {
            forumManager.addComment(post[1],post[2],postId);
        } catch (InvalidForumPostIdException e) {
            ForumUi.printInvalidForumPostIndexError();
            System.exit(1);
        }
    }

    public void saveForum(ForumManager cooperForumManager) {
        try {
            FileWriter fileWriter = new FileWriter(filePath, false);
            ArrayList<ForumPost> forumPosts = cooperForumManager.getForumPosts();
            for (ForumPost post : forumPosts) {
                saveForumPost(fileWriter, post);
            }
            fileWriter.close();
        } catch (IOException e) {
            FileIoUi.showFileWriteError(e);
            System.exit(1);
        }
    }

    private void saveForumPost(FileWriter fileWriter, ForumPost post) throws IOException {
        fileWriter.write("P|" + post.getUsername() + "|" + post.getContent() + "\n");
        for (ForumComment comment : post.getComments()) {
            fileWriter.write("C|" + comment.getUsername() + "|" + comment.getContent() + "\n");
        }
    }
}
