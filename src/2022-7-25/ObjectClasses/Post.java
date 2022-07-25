package ObjectClasses;

import Exceptions.*;
import Exceptions.PostNotLikedException;

public class Post {
    private String postID;
    private String userID;
    private int likeNum;
    private int commentNum;
    private String content;
    private String filepath;

    static int idCounter = 0;

    public Post(String content, String userID, String filepath, String postID) {
        this.postID = postID;
        this.userID = userID;
        likeNum = 0;
        commentNum = 0;

        this.content = content;
        this.filepath = filepath;
    }

    public Post(String content, String userID, String postID) {
        this.postID = postID;
        this.userID = userID;
        likeNum = 0;
        commentNum = 0;

        this.content = content;
        this.filepath = null;
    }

    public static void publishPost(String userID, String content, String filepath) throws PostContentNullException, NoSuchFileException {
        //EXCEPTION HANDLING

        String postID = assignID();
        Post newPost = new Post(content, userID, filepath,postID);

        //ADD SQL CODE HERE LATER ON
        //--------------------------

        //--------------------------
    }

    public static void publishPost(String userID, String content) throws PostContentNullException{
        //EXCEPTION HANDLING

        String postID = assignID();
        Post newPost = new Post(content, userID,postID);

        //ADD SQL CODE HERE LATER ON
        //--------------------------

        //--------------------------
    }

    public static void setFile(String filePath) throws NoSuchFileException {

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
        //ADD SQL CODE HERE LATER ON
        //--------------------------

        //--------------------------
    }

    public static void likePost(String likerUserID, String likedPostID) throws PostDoesNotExistException{
        try {
            Like.likePost(likerUserID, likedPostID);

            //ADD SQL CODE HERE LATER ON
            //--------------------------

            //--------------------------
        }
        catch (PostAlreadyLikedException exception) {
            System.out.println(exception.getMessage());
        }
    }
    public static void unlikePost(String likerUserID, String likedPostID) throws PostDoesNotExistException{
        try {
            Like.unlikePost(likerUserID, likedPostID);

            //ADD SQL CODE HERE LATER ON
            //--------------------------

            //--------------------------
        }
        catch (PostNotLikedException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public static void commentPost(String commenterID, String postID) throws PostDoesNotExistException {
        try {
            Comment.commentOnPost(commenterID, postID);

            //--------------
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteComment(String commenterID, String postID) throws PostDoesNotExistException {
        try {
            Comment.deleteComment(commenterID, postID);

            //------------------
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static String assignID() {
        return String.format("#P" + idCounter++);
    }
}
