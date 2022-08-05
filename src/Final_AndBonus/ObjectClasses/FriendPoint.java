package ObjectClasses;

import java.util.ArrayList;

public class FriendPoint {
    String userID, username;
    int point;

    public FriendPoint(String userID, String username, int point) {
        this.userID = userID;
        this.username = username;
        this.point = point;
    }

    public String getID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public int getPoint() {
        return point;
    }

    public static int getIndex(ArrayList<FriendPoint> friendPoints, String userID) {
        for (FriendPoint friendPoint : friendPoints) {
            if (friendPoint.userID.equals(userID))
                return friendPoints.indexOf(friendPoint);
        }

        return -1;
    }

    public void incrementPoint() {
        this.point++;
    }

    public boolean equals(Object another) {
        if (!(another.getClass() == this.getClass()))
            throw new ClassCastException("Object not a FriendPoint");

        if (((FriendPoint)another).getID().equals(this.userID) &&
                ((FriendPoint)another).getUsername().equals(this.username) &&
                ((FriendPoint)another).getPoint() == this.point)
            return true;
        else
            return false;
    }
}
