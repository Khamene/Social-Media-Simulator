package org.twitter.ObjectClasses;

import java.util.ArrayList;

public class NotLikedAd {
    String postID, userID, content;
    int point;

    public NotLikedAd(String postID, String userID, String content) {
        this.postID = postID;
        this.userID = userID;
        this.content = content;
        point = 0;
    }

    public int getPoint() { return point; }

    public String getPostID() { return postID; }

    public String getUserID() { return userID; }

    public String getContent() { return content; }

    public void incrementPoint() { point++; }

    public static boolean containsPost(ArrayList<AdvertisementPoint> posts, String postID) {
        for (AdvertisementPoint post : posts) {
            if (post.postID.equalsIgnoreCase(postID))
                return true;
        }

        return false;
    }
}
