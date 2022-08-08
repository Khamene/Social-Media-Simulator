package org.twitter.ObjectClasses;

import java.util.ArrayList;

public class AdvertisementPoint {
    String postID;
    boolean liked;
    ArrayList<String> sameSidedUserIDs;

    public AdvertisementPoint(String postID, boolean liked) {
        this.postID = postID;
        this.liked = liked;
        sameSidedUserIDs = new ArrayList<>();
    }

    public String getPostID() { return postID; }

    public boolean getLiked() { return liked; }

    public ArrayList<String> getSameSidedUserIDs() { return sameSidedUserIDs; }

    public void addSameSidedUser(String userID) {
        sameSidedUserIDs.add(userID);
    }
}
