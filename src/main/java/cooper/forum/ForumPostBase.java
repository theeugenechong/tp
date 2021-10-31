package cooper.forum;

//@@author Rrraaaeee

public abstract class ForumPostBase {
    private final String content;
    private final String username;

    public ForumPostBase(String username, String content) {
        this.username = username;
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public String getContent() {
        return content;
    }

    public String toString() {
        return "@" + username + ": " + content;
    }
}
