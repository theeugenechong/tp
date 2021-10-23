package cooper.forum;

import java.util.ArrayList;

import cooper.exceptions.InvalidForumPostIdException;


public class ForumManager {
    private ArrayList<ForumPost> forumPosts;

    public ForumManager() {
        forumPosts = new ArrayList<ForumPost>();
    }

    public void addPost(String username, String content) {
        forumPosts.add(new ForumPost(username, content));
    }

    public void addComment(String username, String content, int postId)
            throws InvalidForumPostIdException {
        if (postId >= forumPosts.size() || postId < 0) {
            throw new InvalidForumPostIdException();
        } else {
            forumPosts.get(postId).addComment(username, content);
        }
    }

    public void deletePost(String username, int postId) throws InvalidForumPostIdException{
        if (postId >= forumPosts.size() || postId < 0) {
            throw new InvalidForumPostIdException();
        } else {
            ForumPost post = forumPosts.get(postId);
            if (post.getUsername() == username) {
                // can only delete one's own post
                forumPosts.remove(postId);
            }
        }
    }
}
