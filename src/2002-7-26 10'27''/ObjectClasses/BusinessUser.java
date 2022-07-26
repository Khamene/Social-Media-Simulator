package ObjectClasses;

import Exceptions.*;
import Functionality.SQLManager;

import java.time.LocalDate;

public class BusinessUser extends User{
    static int idCounter = 0;

    public BusinessUser(String userID, String firstName, String lastName, String emailAddress,
                        String phoneNumber , String username , String password, boolean gender, LocalDate date){
        super(userID,firstName,lastName,emailAddress,true,phoneNumber,username,password,gender,date, false);
    }

    public static void createBusinessAccount(String username, String password) {
        String userID = assignID();
        SQLManager.createBusinessUser(userID, username, password);
    }

    public static void viewAccountStats() throws NoUserLoggedInException, UserNotBusinessException {

    }

    public static void viewPostStats(String postID) throws NoUserLoggedInException, UserNotBusinessException, PostDoesNotExistException {

    }

    public static String assignID(){
        return String.format("#BU" + idCounter++);
    }
}
