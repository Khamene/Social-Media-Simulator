package Functionality;

import Exceptions.NoUserLoggedInException;
import Exceptions.PasswordIncorrectException;
import Exceptions.UserDoesNotExistException;
import ObjectClasses.User;

public class Main {
    public static void main(String[] args) throws PasswordIncorrectException, UserDoesNotExistException, NoUserLoggedInException {
        SQLManager.initialize();


        User.login("MMMM", "MehrshadTaji2571381");

        User.changeUsername("MMMM");

//        User.changeUsername("Mehrshad");
//        User.changeUsername("Mehrshad");

        User.changePassword("MehrshadTaji2571381", "MehrshadTaji2571381");

        User.changeName("My Name Mehrshad");

        User.logout();
//        Functionality.SQLManager.createPersonalUser("400", "Mehrshad", "MEHRSHAD");

        SQLManager.finalizeForOnce();
    }
}
