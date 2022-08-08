package org.twitter.ObjectClasses;

import org.twitter.Exceptions.*;
import org.twitter.Functionality.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Comment {
    String posterID, commentorID, postID, content;
    LocalDate dateCommented;
    int commentNum;

    public Comment(String postID, String posterID, String commentorID, String content, LocalDate dateCommented) {
        this.postID = postID;
        this.posterID = posterID;
        this.commentorID = commentorID;
        this.content = content;
        this.dateCommented = dateCommented;
        commentNum = 1;
    }

    public String getPosterID() {
        return posterID;
    }

    public String getPostID() {
        return postID;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public String getContent() {
        return content;
    }

    public String getCommentorID() {
        return commentorID;
    }

    public LocalDate getDateCommented() {
        return dateCommented;
    }

    public static ArrayList<Comment> getComments(String username) throws UserDoesNotExistException, SQLException {
        String userID = User.getUserID(username);

        ArrayList<Comment> primeComments = SQLManager.getComments(userID);
        ArrayList<Comment> comments = new ArrayList<>();

        for (int i = 0; i < primeComments.size(); i++) {
            if (i == 0) {
                comments.add(primeComments.get(i));
            }
            else {
                if (primeComments.get(i).dateCommented.equals(primeComments.get(i-1).dateCommented)) {
                    comments.get(i).commentNum++;
                }
                else {
                    comments.add(primeComments.get(i));
                }
            }
        }

        return comments;
    }

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
