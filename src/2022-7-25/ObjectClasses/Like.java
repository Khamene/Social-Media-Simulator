package ObjectClasses;

import Exceptions.PostAlreadyLikedException;
import Exceptions.PostDoesNotExistException;
import Exceptions.PostNotLikedException;

public class Like {
    private String likedUserID;
    private String likedPostID;

    public Like(String likedUserID, String likedPostID) {
        this.likedUserID = likedPostID;
        this.likedPostID = likedPostID;

        //ADD SQL CODE HERE LATER ON
        //--------------------------

        //--------------------------
    }

    public static void likePost(String likerUserID, String likedPostID) throws PostAlreadyLikedException {
        //ADD SQL CODE HERE LATER ON
        //--------------------------

        //--------------------------
    }

    public static void unlikePost(String likerUserID, String likedPostID) throws PostNotLikedException {
        //ADD SQL CODE HERE LATER ON
        //--------------------------

        //--------------------------
    }
}
