package Functionality;

import Exceptions.*;
import ObjectClasses.User;

public class Main {
    public static void main(String[] args) throws PasswordIncorrectException, UserDoesNotExistException, NoUserLoggedInException, FollowRequestDoesNotExistException, UserAlreadyFollowedException {
        SQLManager.initialize();


        User.login("M522T", "Mehrshad");

        User.sendFollowRequest("M522T");

        User.manageFollowRequest();

        User.acceptFollowRequest("m522t");

        User.logout();

        SQLManager.finalizeForOnce();
    }
}
