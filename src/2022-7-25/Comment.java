import Exceptions.PostAlreadyCommentedException;
import Exceptions.PostNotCommentedException;

public class Comment {
    private String commenterID;
    private String postID;
    private String content;

    public static void commentOnPost(String commenterID, String postID) throws PostAlreadyCommentedException {

    }

    public static void deleteComment(String commenterID, String postID) throws PostNotCommentedException {

    }
}
