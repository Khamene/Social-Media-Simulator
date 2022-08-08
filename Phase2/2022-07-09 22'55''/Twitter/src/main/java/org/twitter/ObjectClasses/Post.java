package org.twitter.ObjectClasses;

import org.twitter.Exceptions.*;
import org.twitter.Functionality.SQLManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Post {
    static int idCounter = 0;

    String postID, content, posterID, filePath;
    int likeNum, commentNum;
    LocalDateTime modifiedTime;

    public Post(String postID, String content, String posterID, String filePath, int likeNum, int commentNum, LocalDateTime modifiedTime) {
        this.postID = postID;
        this.posterID = posterID;
        this.content = content;
        this.filePath = filePath;
        this.likeNum = likeNum;
        this.commentNum = commentNum;
        this.modifiedTime = modifiedTime;
    }

    public Post(String postID, String content, String posterID, int likeNum, int commentNum, LocalDateTime modifiedTime) {
        this.postID = postID;
        this.posterID = posterID;
        this.content = content;
        this.filePath = null;
        this.likeNum = likeNum;
        this.commentNum = commentNum;
        this.modifiedTime = modifiedTime;
    }

    public String getPosterID() {
        return posterID;
    }

    public String getPostID() {
        return postID;
    }

    public String getContent() {
        return content;
    }

    public String getFilePath() {
        return filePath;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public LocalDateTime getModifiedTime() {
        return modifiedTime;
    }

    public static ArrayList<Post> getUserPosts(String posterUsername) throws UserDoesNotExistException {
        String posterID = User.getUserID(posterUsername);
        String userID = User.getUserID(User.getLoggedInUsername());

        try {
            ArrayList<Post> posts = SQLManager.getuserPosts(posterID, userID);

            Collections.sort(posts, new Comparator<Post>() {
                @Override
                public int compare(Post o1, Post o2) {
                    if (o1.modifiedTime.compareTo(o2.modifiedTime) > 0)
                        return 1;
                    else if (o1.modifiedTime.compareTo(o2.modifiedTime) == 0)
                        return 0;
                    else
                        return -1;
                }
            });

            return posts;
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Failed");
        }
    }

    public static void checkPostID(String postID) throws PostDoesNotExistException {
        try {
            if (SQLManager.checkPostID(postID) == -1)
                throw new PostDoesNotExistException("No such post exists...");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void showComments(String postID) {
        try {
            SQLManager.showComments(postID);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean getLiked(String postID) throws UserDoesNotExistException, SQLException {
        String userID = User.getUserID(User.getLoggedInUsername());

        return SQLManager.getLiked(userID, postID);
    }

    public static void publishPost(String userID, String content) throws PostContentNullException{
        if (content.equals(""))
            throw new PostContentNullException("Can not post null content");

        String postID = assignID();

        try {
            SQLManager.publishPost(userID, postID, content);

            System.out.println("Post created successfully...");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void publishPost(String userID, String content, String filePath) throws PostContentNullException, NoSuchFileException {
        if (content.equals(""))
            throw new PostContentNullException("Can not post null content");

        String postID = assignID();

        try {
            SQLManager.publishPost(userID, postID, content, filePath);

            System.out.println("Post created successfully...");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void displayPostsIDs(String userID) {
        //ADD SQL CODE HERE LATER ON
        //--------------------------

        //--------------------------
    }

    public static void displayPost(String postID) throws PostDoesNotExistException {
        //ADD SQL CODE HERE LATER ON
        //--------------------------

        //--------------------------

        //POSSIBLE REL WITH JAVAFX
    }

    public static void deletePost(String postID) throws PostDoesNotExistException {
        try {
            SQLManager.deletePost(postID);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void checkMyPost(String postID, String userID) throws UnauthorisedEditException{
        try {
            int result = SQLManager.checkMyPost(userID, postID);

            if (result == -1)
                throw new UnauthorisedEditException("Post is not yours...");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void checkFollowedPost(String postID, String userID) throws UnauthorisedEditException {
        try {
            Post.checkMyPost(postID, userID);
        }
        catch (UnauthorisedEditException e) {
            try {
                if (SQLManager.checkFollowingPost(userID, postID) == -1)
                    throw new UnauthorisedEditException("You can not view info for this post");
            }
            catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }

    public static String assignID() {
        return String.format("#P" + idCounter++);
    }
}
