package org.twitter.ObjectClasses;

import org.twitter.Functionality.SQLManager;

import java.time.LocalDate;


public class PersonalUser extends User{
    static int idCounter = 0;

    public PersonalUser(String username, String photoPath) {
        super(username, photoPath);
    }

    public static void createNewPersonalAccount(String username, String password, String firstName,
                                                String lastName, String email, String phoneNumber, boolean gender,
                                                boolean isPrivate, LocalDate birthday, int q1, String a1, int q2, String a2) {
        String userID = assignID();

        int age = LocalDate.now().getYear() - birthday.getYear();

        SQLManager.createPersonalUser(userID, username, password, firstName, lastName, email, phoneNumber, gender, isPrivate, birthday
        , q1, a1, q2, a2, age);
    }

    public static void createNewPersonalAccount(String username, String password, String firstName,
                                               String lastName, String email, String phoneNumber, boolean gender,
                                               boolean isPrivate) {
        String userID = assignID();

        SQLManager.createPersonalUser(userID, username, password, firstName, lastName, email, phoneNumber, gender, isPrivate);
    }

    public static String assignID() {
        return String.format("#PU" + idCounter++);
    }
}
