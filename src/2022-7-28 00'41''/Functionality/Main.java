package Functionality;

import Exceptions.*;
import ObjectClasses.PersonalUser;
import ObjectClasses.User;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws PasswordIncorrectException, UserDoesNotExistException,
            NoUserLoggedInException, FollowRequestDoesNotExistException, UserAlreadyFollowedException,
            MessageContentNullException, UserWasBannedException, MessageNotIntendedForUserException, MessageDoesNotExistException, UnauthorisedEditException {
        SQLManager.initialize();


//        SQLManager.nothing();
        PersonalUser.createNewPersonalAccount("Aminmin9", "AminAminAMin", "Amin","Kahm"
                ,"AminAminAmin","11111111111",false, true, LocalDate.now(), 3, "HHHH", 5,
                "JJJJJ");

        User.login("M522T", "Mehrshad");
//
//        User.sendFollowRequest("M522T");
//
//        User.manageFollowRequest();
//
//        User.acceptFollowRequest("m522t");
//
//        User.sendDirectMessage("M522T", "Fuck you once more");

        User.replyDMessage("#DM0", "Fuck you too once more");
        User.showChat("M522T");

        User.logout();

        SQLManager.finalizeForOnce();
    }
}
