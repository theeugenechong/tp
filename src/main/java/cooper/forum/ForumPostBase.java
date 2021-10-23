package cooper.forum;

public abstract class ForumPostBase {
    private final String content;
    private final String username;

    public ForumPostBase(String username, String content) {
        this.username = username;
        this.content = content;
    }

    public String toString() {
        return "@" + username + ": " + content;
    }
}
