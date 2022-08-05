package org.twitter.ObjectClasses;

import org.twitter.Exceptions.PostAlreadyLikedException;
import org.twitter.Exceptions.PostNotLikedException;
import org.twitter.Functionality.SQLManager;
import java.sql.SQLException;

public abstract class Like {
    public static void alreadyLike(String postID, String userID) throws PostAlreadyLikedException {
        try {
            if (SQLManager.determineLike(userID, postID) == 0)
                throw new PostAlreadyLikedException("You have already liked this post...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void notLiked(String postID, String userID) throws PostNotLikedException {
        try {
            if (SQLManager.determineLike(userID, postID) == -1)
                throw new PostNotLikedException("You have not liked this post yet...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
