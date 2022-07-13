public class Like {
    private String likerUserID;
    private String likedUserID;
    private String likedPostID;

    public Like(String likerUserID, String likedUserID, String likedPostID) {
        this.likerUserID = likerUserID;
        this.likedUserID = likedPostID;
        this.likedPostID = likedPostID;

        //ADD SQL CODE HERE LATER ON
        //--------------------------

        //--------------------------
    }

    public String getLikedUserID(String likedPostID) throws PostDoesNotExistException{
        //ADD SQL CODE HERE LATER ON
        //--------------------------

        //--------------------------
        return "";
    }

    public String getLikerUserID(String likedPostID) throws PostDoesNotExistException{
        //ADD SQL CODE HERE LATER ON
        //--------------------------

        //--------------------------
        return "";
    }

    public static void likePost(String likerUserID, String likedUserID, String likedPostID) throws PostAlreadyLikedException{
        //ADD SQL CODE HERE LATER ON
        //--------------------------

        //--------------------------
    }

    public static void unlikePost(String likerUserID, String likedUserID, String likedPostID) throws PostNotLikedException{
        //ADD SQL CODE HERE LATER ON
        //--------------------------

        //--------------------------
    }
}
