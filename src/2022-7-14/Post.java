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

    public static void publishPost(String userID, String content, String filepath) throws PostContentNullException {
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

    public static void displayPostsIDs(String userID) {
        //ADD SQL CODE HERE LATER ON
        //--------------------------

        //--------------------------
    }

    public static void displayPost(String postID) {
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

    public static void likePost(String likerUserID, String likedUserID, String likedPostID) {
        try {
            Like.likePost(likerUserID, likedUserID, likedPostID);

            //ADD SQL CODE HERE LATER ON
            //--------------------------

            //--------------------------
        }
        catch (PostAlreadyLikedException exception) {
            System.out.println("You have already liked this post");
        }
    }
    public static void unlikePost(String likerUserID, String likedUserID, String likedPostID) {
        try {
            Like.unlikePost(likerUserID, likedUserID, likedPostID);

            //ADD SQL CODE HERE LATER ON
            //--------------------------

            //--------------------------
        }
        catch (PostNotLikedException exception) {
            System.out.println("You have not liked this post yet");
        }
    }

    private static String assignID() {
        return String.format("#P" + idCounter++);
    }
}
