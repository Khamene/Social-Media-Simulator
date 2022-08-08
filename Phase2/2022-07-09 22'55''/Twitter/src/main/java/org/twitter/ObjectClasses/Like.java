package org.twitter.ObjectClasses;

import javafx.scene.control.Slider;
import org.twitter.Exceptions.PostAlreadyLikedException;
import org.twitter.Exceptions.PostNotLikedException;
import org.twitter.Exceptions.UserDoesNotExistException;
import org.twitter.Functionality.SQLManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Like {
    String posterID, likerID, postID;
    LocalDate dateLiked;
    int likeNum;

    public Like(String posterID, String likerID, String postID, LocalDate dateLiked) {
        this.posterID = posterID;
        this.likerID = likerID;
        this.postID = postID;
        this.dateLiked = dateLiked;
        likeNum = 1;
    }

    public String getPostID() {
        return postID;
    }

    public String getPosterID() {
        return posterID;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public String getLikerID() {
        return likerID;
    }

    public LocalDate getDateLiked() {
        return dateLiked;
    }

    public static ArrayList<Like> getLikes(String username) throws UserDoesNotExistException, SQLException {
        String posterID = User.getUserID(username);

        ArrayList<Like> primeLikes = SQLManager.getLikes(posterID);
        ArrayList<Like> likes = new ArrayList<>();

        for (int i = 0; i < primeLikes.size(); i++) {
            if (i == 0) {
                likes.add(primeLikes.get(i));
            }
            else {
                if (primeLikes.get(i).dateLiked.equals(primeLikes.get(i-1).dateLiked)) {
                    likes.get(likes.size() - 1).likeNum++;
                }
                else {
                    likes.add(primeLikes.get(i));
                }
            }
        }

        return likes;
    }

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
