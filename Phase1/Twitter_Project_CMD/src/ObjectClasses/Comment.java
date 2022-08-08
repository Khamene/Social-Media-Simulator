package ObjectClasses;

import Exceptions.PostAlreadyCommentedException;
import Exceptions.PostAlreadyLikedException;
import Exceptions.PostNotCommentedException;
import Exceptions.PostNotLikedException;
import Functionality.SQLManager;

import java.sql.SQLException;

public abstract class Comment {
    public static void alreadyComment(String postID, String userID) throws PostAlreadyCommentedException {
        try {
            if (SQLManager.determineComment(userID, postID) == 0)
                throw new PostAlreadyCommentedException("You have already commented on this post...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void notCommented(String postID, String userID) throws PostNotCommentedException {
        try {
            if (SQLManager.determineComment(userID, postID) == -1)
                throw new PostNotCommentedException("You have not commented on this post yet...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
