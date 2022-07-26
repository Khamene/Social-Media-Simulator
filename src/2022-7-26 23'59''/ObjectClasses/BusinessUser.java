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

    public static void createBusinessAccount(String username, String password, String firstName,
                                             String lastName, String email, String phoneNumber, boolean gender,
                                             boolean isPrivate, LocalDate birthday, int q1, String a1, int q2, String a2) {
        String userID = assignID();
        int age = LocalDate.now().getYear() - birthday.getYear();

        SQLManager.createBusinessUser(userID, username, password, firstName, lastName, email, phoneNumber, gender, isPrivate, birthday
                , q1, a1, q2, a2, age);
    }

    public static void viewAccountStats() throws NoUserLoggedInException, UserNotBusinessException {

    }

    public static void viewPostStats(String postID) throws NoUserLoggedInException, UserNotBusinessException, PostDoesNotExistException {

    }

    public static String assignID(){
        return String.format("#BU" + idCounter++);
    }
}
