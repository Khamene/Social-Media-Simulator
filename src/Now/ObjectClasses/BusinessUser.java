package ObjectClasses;

import Exceptions.*;
import Functionality.SQLManager;

import java.sql.SQLException;
import java.time.LocalDate;

public abstract class BusinessUser extends User{
    static int idCounter = 0;
    public static void createBusinessAccount(String username, String password, String firstName,
                                             String lastName, String email, String phoneNumber, boolean gender,
                                             boolean isPrivate, LocalDate birthday, int q1, String a1, int q2, String a2) {
        String userID = assignID();
        int age = LocalDate.now().getYear() - birthday.getYear();

        SQLManager.createBusinessUser(userID, username, password, firstName, lastName, email, phoneNumber, gender, isPrivate, birthday
                , q1, a1, q2, a2, age);
    }

    public static void viewAccountStats(String username) throws NoUserLoggedInException, UserNotBusinessException, UserDoesNotExistException{
        if (User.getUserType(username) == false)
            throw new UserNotBusinessException("Info can not be shared on a personal account...");

        try {
            SQLManager.showAccountStats(User.getUserID(username));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void viewPostStats(String postID) throws NoUserLoggedInException, UserNotBusinessException, PostDoesNotExistException, UnauthorisedEditException {
        Post.checkPostID(postID);

        checkAccount(postID);

        try {
            SQLManager.showPostStats(postID);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void checkAccount(String postID) throws UnauthorisedEditException {
        try {
            if (SQLManager.checkAccount(postID) == -1)
                throw new UnauthorisedEditException("Info can not be shared on a personal account...");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String assignID(){
        return String.format("#BU" + idCounter++);
    }
}
