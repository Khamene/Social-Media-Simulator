package ObjectClasses;

import Exceptions.*;
import Exceptions.PostNotLikedException;
import Functionality.SQLManager;

import java.sql.SQLException;

public abstract class Post {
    private String postID;
    private String userID;
    private int likeNum;
    private int commentNum;
    private String content;
    private String filepath;

    static int idCounter = 0;

/*  public static void publishPost(String userID, String content, String filepath) throws PostContentNullException, NoSuchFileException {
        if (content.equals(""))
            throw new PostContentNullException("Can not post null content");

        String postID = assignID();
    }*/

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

/*  public static void setFile(String filePath) throws NoSuchFileException {

    }*/

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
