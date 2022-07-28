package ObjectClasses;//import java.sql.Date;
import Exceptions.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import Functionality.SQLManager;
import org.w3c.dom.ls.LSOutput;

public abstract class User {
    private int age;
    private String userID;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String emailAddress;
    private String phoneNumber;
    private boolean gender;
    private boolean userType;
    private boolean isPrivate;
    private LocalDate createdDate;
    int followerCount;
    int followingCount;
    LocalDate birthday;

    private ArrayList<User> Following = new ArrayList<>();
    private ArrayList<User> Followers = new ArrayList<>();
    private ArrayList<Group> group = new ArrayList<>();
    private ArrayList<Post> tweets = new ArrayList<>();

    static String LoggedInUsername  = null;
    static boolean legiblePasswordChange = false;

    public static void login(String username, String password) throws UserDoesNotExistException, PasswordIncorrectException {
        int result = SQLManager.login(username, password);

        switch (result) {
            case 0:
                System.out.printf("User %s logged in successfully...%n", username);
                LoggedInUsername = username;
                break;
            case -1:
                throw new UserDoesNotExistException("No user exists with this username...");
            case -2:
                throw new PasswordIncorrectException("Incorrect password entered for this account...");
            default:
                break;
        }
    }

    public static String getLoggedInUsername() {
        return LoggedInUsername;
    }

    public static void logout() throws NoUserLoggedInException {
        if (LoggedInUsername != null) {
            LoggedInUsername = null;
            System.out.println("Logged out successfully...");
        }
        else
            throw new NoUserLoggedInException("No user logged in yet...");
    }

    public static String getUserID(String username) throws UserDoesNotExistException{
        String userID = SQLManager.getUserID(username);

        if (userID.equals(""))
            throw new UserDoesNotExistException("No user exists with this username...");
        else
            return userID;
    }

    public static void changeUsername(String username) throws NoUserLoggedInException{
        if (LoggedInUsername == null)
            throw new NoUserLoggedInException("No user logged in yet...");
        else {
            int result = SQLManager.changeUsername(LoggedInUsername, username);

            if (result == 0) {
                System.out.printf("Username changed to %s successfully...%n", username);
                LoggedInUsername = username;
            }
        }
    }

    public static void changePassword(String oldPassword, String password) throws PasswordIncorrectException,
            NoUserLoggedInException, UserDoesNotExistException {
        if (LoggedInUsername == null)
            throw new NoUserLoggedInException("No user logged in yet...");
        else {
            int result = SQLManager.changePassword(LoggedInUsername, oldPassword, password);

            switch (result) {
                case 0:
                    System.out.println("Password changed successfully...");
                    break;
                case -1:
                    throw new PasswordIncorrectException("Incorrect password entered for this account...");
                case -2:
                    throw new UserDoesNotExistException("No user exists with this username...");
                default:
                    break;
            }
        }
    }

    public static void changeBirthday(LocalDate birthDate) throws NoUserLoggedInException {
        if (LoggedInUsername == null) {
            throw new NoUserLoggedInException("No user logged in yet...");
        }
        else {
            int result = SQLManager.changeBirthday(LoggedInUsername, birthDate);

            if (result == 0) {
                System.out.printf("Birthday successfully changed to %s...%n", birthDate.toString());
            }
        }
    }

    public static void changeGender(boolean gender) throws NoUserLoggedInException {
        if (LoggedInUsername == null)
            throw new NoUserLoggedInException("No user logged in yet...");
        else {
            int result = SQLManager.changeGender(LoggedInUsername, gender);

            if (result == 0) {
                System.out.printf("Gender successfully changed%n");
            }
        }
    }

    public static void changeEmail(String email) throws NoUserLoggedInException {
        if (LoggedInUsername == null)
            throw new NoUserLoggedInException("No user logged in yet...");
        else {
            int result = SQLManager.changeEmail(LoggedInUsername, email);

            if (result == 0) {
                System.out.printf("Email successfully changed to %s...%n", email);
            }
        }
    }

    public static void changePrivacyMode(boolean isPrivate) throws NoUserLoggedInException {
        if (LoggedInUsername == null)
            throw new NoUserLoggedInException("No user logged in yet...");
        else {
            int result = SQLManager.changePrivacyMode(LoggedInUsername, isPrivate);

            if (result == 0) {
                System.out.printf("Privacy mode successfully changed...%n");
            }
        }
    }

    public static void deleteUser(String password) throws PasswordIncorrectException, NoUserLoggedInException, UserDoesNotExistException {
        if (LoggedInUsername == null)
            throw new NoUserLoggedInException("No user logged in yet...");
        else {
            int result = SQLManager.deleteUser(LoggedInUsername, password);

            switch (result) {
                case 0:
                    System.out.println("User successfully deleted");
                    LoggedInUsername = null;
                    break;
                case -1:
                    throw new PasswordIncorrectException("Incorrect password entered for this account...");
                case -2:
                    throw new UserDoesNotExistException("No user exists with this username...");
                default:
                    break;
            }
        }
    }

    public static void blockUser(String username) throws NoUserLoggedInException, UserDoesNotExistException, UserAlreadyBlockedException {
        if (LoggedInUsername == null)
            throw new NoUserLoggedInException("No user logged in yet...");

        String userID = User.getUserID(LoggedInUsername);
        String otherUserID = User.getUserID(username);

        int result = SQLManager.blockUser(userID, otherUserID);

        switch (result) {
            case 0:
                System.out.printf("User %s successfully blocked%n", username);
                break;
            case -1:
                throw new UserAlreadyBlockedException("User already blocked...");
            case -2:
                throw new UserDoesNotExistException("No user exists with this username...");
            default:
                break;
        }
    }

    public static void unblockUser(String username) throws NoUserLoggedInException, UserDoesNotExistException, UserNotBlockedException {
        if (LoggedInUsername == null)
            throw new NoUserLoggedInException("No user logged in yet...");

        String userID = User.getUserID(LoggedInUsername);
        String otherUserID = User.getUserID(username);

        int result = SQLManager.unblockUser(userID, otherUserID);

        switch (result) {
            case 0:
                System.out.printf("User %s successfully unblocked%n", username);
                break;
            case -1:
                throw new UserNotBlockedException("User is not blocked...");
            case -2:
                throw new UserDoesNotExistException("No user exists with this username...");
            default:
                break;
        }
    }

    public static String getSecurityQuestions(String username) throws UserDoesNotExistException{
        String securityQuestions = SQLManager.getSecurityQuestions(username);

        if (securityQuestions.equals("NaN"))
            throw new UserDoesNotExistException("No user exists with this username...");
        else if (!securityQuestions.equals(""))
            return securityQuestions;
        else
            return "";
    }

    public static void forgotPassword(String username, String securityAnswer1, String securityAnswer2) throws UserDoesNotExistException, WrongSecurityAnswerException {
        int result = SQLManager.forgotPassword(username, securityAnswer1, securityAnswer2);

        switch (result) {
            case 0:
                System.out.println("You may proceed to reset your password");
                legiblePasswordChange = true;
                break;
            case -1:
                throw new WrongSecurityAnswerException("Wrong answers for the security questions");
            case -2:
                throw new UserDoesNotExistException("No user exists with this username...");
            default:
                break;
        }
    }

    public static void changePasswordAfterForgotPassword(String username, String password) throws WrongSecurityAnswerException, UserDoesNotExistException{
        if (!legiblePasswordChange)
            throw new WrongSecurityAnswerException("You can not change passwords for this account");
        else {
            int result = SQLManager.changePassword(username, password);

            if (result == 0) {
                System.out.printf("Password successfully reset for account %s...%n", username);
            }
            else if (result == -1)
                throw new UserDoesNotExistException("No user exists with this username...");
        }

        legiblePasswordChange = false;
    }

    public static void visitPage(String otherUsername) throws UserDoesNotExistException,NoUserLoggedInException {

    }

    public static void tweet(String content) throws NoUserLoggedInException, PostContentNullException, UserDoesNotExistException {
        if (LoggedInUsername == null)
            throw new NoUserLoggedInException("No user logged in yet...");

        String userID = User.getUserID(LoggedInUsername);

        Post.publishPost(userID, content);
    }

    public static void tweet(String content, String filepath) throws NoUserLoggedInException{

    }

    public static void untweet(String postID) throws NoUserLoggedInException, UnauthorisedEditException, UserDoesNotExistException, PostDoesNotExistException {
        if (LoggedInUsername == null)
            throw new NoUserLoggedInException("No user logged in yet...");

        String userID = User.getUserID(LoggedInUsername);

        Post.checkMyPost(postID, userID);

        Post.deletePost(postID);
    }

    public static void sendDirectMessage(String receiverUsername, String content) throws NoUserLoggedInException,
            UserDoesNotExistException, UserWasBannedException, MessageContentNullException, UnauthorisedEditException {
        if (LoggedInUsername == null)
            throw new NoUserLoggedInException("No user logged in yet...");

        String userID = User.getUserID(LoggedInUsername);
        String receiverID = User.getUserID(receiverUsername);

        DirectMessage.SendDirectMessage(userID, receiverID, content);
    }

    public static void sendGroupMessage(String groupID, String content) throws NoUserLoggedInException,
            UserDoesNotExistException, MessageContentNullException, UserNotMemberException, UserWasBannedException, GroupDoesNotExistException, UnauthorisedEditException {
        if (LoggedInUsername == null)
            throw new NoUserLoggedInException("No user logged in yet...");

        String userID = User.getUserID(LoggedInUsername);

        GroupMessage.SendGroupMessage(userID, groupID, content);
    }

    public static void forwardDMessage(String messageID, String receiverID) throws NoUserLoggedInException, UserDoesNotExistException
            , MessageNotIntendedForUserException, MessageDoesNotExistException, MessageContentNullException, UserWasBannedException, UnauthorisedEditException {
        if (LoggedInUsername == null)
            throw new NoUserLoggedInException("No user logged in yet...");

        String userID = User.getUserID(LoggedInUsername);

        try {
            DirectMessage.validateIsMyMessage(messageID, userID);
        }
        catch (MessageNotIntendedForUserException e) {
            try {
                GroupMessage.validateIsMyMessage(messageID, userID);
            }
            catch (MessageNotIntendedForUserException exp) {
                throw exp;
            }
        }
        catch (MessageDoesNotExistException e) {
            throw e;
        }

        String content = Message.getContent(messageID);
        DirectMessage.SendDirectMessage(userID, receiverID, content);
        Message.setForward(messageID);
    }

    public static void forwardGMessage(String messageID, String groupID) throws NoUserLoggedInException, UserDoesNotExistException
            , MessageNotIntendedForUserException, MessageDoesNotExistException, MessageContentNullException, UserWasBannedException, UserNotMemberException, GroupDoesNotExistException, UnauthorisedEditException {
        if (LoggedInUsername == null)
            throw new NoUserLoggedInException("No user logged in yet...");

        String userID = User.getUserID(LoggedInUsername);

        try {
            DirectMessage.validateIsMyMessage(messageID, userID);
        }
        catch (MessageNotIntendedForUserException e) {
            try {
                GroupMessage.validateIsMyMessage(messageID, userID);
            }
            catch (MessageNotIntendedForUserException exp) {
                throw e;
            }
        }
        catch (MessageDoesNotExistException e) {
            throw e;
        }

        String content = Message.getContent(messageID);
        GroupMessage.SendGroupMessage(userID, groupID, content);
        Message.setForward(messageID);
    }

    public static void replyDMessage(String formerMessageID, String content) throws NoUserLoggedInException,
            UserDoesNotExistException, UserWasBannedException, MessageContentNullException, MessageDoesNotExistException, MessageNotIntendedForUserException, UnauthorisedEditException {
        if (LoggedInUsername == null)
            throw new NoUserLoggedInException("No user logged in yet...");

        String userID = User.getUserID(LoggedInUsername);

        try {
            DirectMessage.validateIsMyMessage(formerMessageID, userID);
        }
        catch (MessageNotIntendedForUserException | MessageDoesNotExistException e) {
            throw e;
        }

        String receiverID = Message.getSenderID(formerMessageID);

        DirectMessage.SendDirectMessage(userID, receiverID, content, formerMessageID);
    }

    public static void replyGMessage(String formerMessageID, String content) throws NoUserLoggedInException,
            UserDoesNotExistException, UserWasBannedException, MessageContentNullException, MessageDoesNotExistException, UserNotMemberException, MessageNotIntendedForUserException, GroupDoesNotExistException, UnauthorisedEditException {

        if (LoggedInUsername == null)
            throw new NoUserLoggedInException("No user logged in yet...");

        String userID = User.getUserID(LoggedInUsername);

        try {
            GroupMessage.validateIsMyMessage(userID, formerMessageID);
        }
        catch (MessageNotIntendedForUserException e) {
            throw e;
        }

        String groupID = Message.getGroupID(formerMessageID);

        GroupMessage.SendGroupMessage(userID, groupID, content, formerMessageID);
    }

    public static void editMessage(String messageID, String content) throws NoUserLoggedInException, UnauthorisedEditException, UserDoesNotExistException, MessageDoesNotExistException, MessageContentNullException {
        if (LoggedInUsername == null)
            throw new NoUserLoggedInException("No user logged in yet...");

        String userID = User.getUserID(LoggedInUsername);

        if (userID.equals(Message.getSenderID(messageID))) {
            Message.editMessage(messageID, userID, content);
        }
        else
            throw new UnauthorisedEditException("Message is not yours to edit");
    }

    public static void deleteMessage(String messageID) throws NoUserLoggedInException, UnauthorisedEditException, UserDoesNotExistException, MessageDoesNotExistException {
        if (LoggedInUsername == null)
            throw new NoUserLoggedInException("No user logged in yet...");

        String userID = User.getUserID(LoggedInUsername);

        if (userID.equals(Message.getSenderID(messageID))) {
            Message.deleteMessage(messageID);
        }
        else
            throw new UnauthorisedEditException("Message is not yours to delete");
    }

    public static void showChat(String otherUsername) throws UserDoesNotExistException, NoUserLoggedInException {
        if (LoggedInUsername == null)
            throw new NoUserLoggedInException("No user logged in yet...");

        String user1ID = User.getUserID(LoggedInUsername);
        String user2ID = User.getUserID(otherUsername);

        SQLManager.showChat(user1ID, user2ID);
    }

    public static void showMyGroups() throws NoUserLoggedInException, UserDoesNotExistException {
        if (LoggedInUsername == null)
            throw new NoUserLoggedInException("No user logged in yet...");

        String userID = User.getUserID(LoggedInUsername);

        SQLManager.showMyGroups(userID);
    }

    public static void showGroupMessages(String groupID) throws GroupDoesNotExistException, NoUserLoggedInException, UserDoesNotExistException, UserNotMemberException {
        if (LoggedInUsername == null)
            throw new NoUserLoggedInException("No user logged in yet...");

        String userID = User.getUserID(LoggedInUsername);

        Group.checkInGroup(userID, groupID);

        SQLManager.showChat(groupID);
    }

    public static void showGroupInfo(String groupID) throws GroupDoesNotExistException, NoUserLoggedInException {
        System.out.printf("Manifesting group info for groupID %s:%n", groupID);

        try {
            SQLManager.showGroupInfo(groupID);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void sendFollowRequest(String username) throws UserAlreadyFollowedException, UserDoesNotExistException, NoUserLoggedInException{
        if (LoggedInUsername == null)
            throw new NoUserLoggedInException("No user logged in yet...");

        String userID = User.getUserID(LoggedInUsername);
        String otherUserID = User.getUserID(username);

        int result = SQLManager.sendFollowRequest(userID, otherUserID);

        switch (result) {
            case 0:
                System.out.println("Follow request sent successfully...");
                break;
            case -1:
                throw new UserAlreadyFollowedException("Already sent follow request pending for this user...");
            case -2:
                throw new UserAlreadyFollowedException("User already in your followers list...");
            case -3:
                throw new UserDoesNotExistException("No user exists with this username...");
            default:
                break;
        }
    }

    public static void manageFollowRequest() throws NoUserLoggedInException{
        if (LoggedInUsername == null)
            throw new NoUserLoggedInException("No user logged in yet...");

        SQLManager.manageFollowRequests(LoggedInUsername);
    }

    public static void acceptFollowRequest(String followerUsername) throws NoUserLoggedInException, UserDoesNotExistException, FollowRequestDoesNotExistException {
        if (LoggedInUsername == null)
            throw new NoUserLoggedInException("No user logged in yet...");

        String userID = User.getUserID(LoggedInUsername);
        String otherUserID = User.getUserID(followerUsername);

        int result = SQLManager.acceptFollowRequest(userID, otherUserID);

        switch (result) {
            case 0:
                System.out.printf("%s is now a fellow follower of your fucking account!%n", followerUsername);
                return;
            case -1:
                throw new FollowRequestDoesNotExistException("No pending follow request from this user...");
            case -2:
                throw new FollowRequestDoesNotExistException("Selected user is already a follower of you...");
            case -3:
                throw new UserDoesNotExistException("No user exists with this username");
            default:
                break;
        }
    }

    public static void commentOnTweet(String postID, String content) throws NoUserLoggedInException {

    }

    public static void deleteComment(String postID) throws NoUserLoggedInException {

    }

    public static void likeTweet(String postID) throws NoUserLoggedInException{

    }

    public static void unlikeTweet(String postID) throws NoUserLoggedInException {

    }

    public static void createNewGroup(String groupName) throws NoUserLoggedInException, UserDoesNotExistException, GroupAlreadyExists {
        if (LoggedInUsername == null)
            throw new NoUserLoggedInException("No user logged in yet...");

        String userID = User.getUserID(LoggedInUsername);

        try {
            int result = SQLManager.createGroup(userID, groupName);

            if (result == 0)
                System.out.printf("Group %s created successfully...%n", groupName);
            else
                throw new GroupAlreadyExists("Duplicate group with this name already exists");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void showNewMessages() throws NoUserLoggedInException, UserDoesNotExistException {
        String userID = User.getUserID(LoggedInUsername);

        try {
            SQLManager.showInbox(userID);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //getters

    public static boolean getUserType(String username) throws  UserDoesNotExistException{
        String userType = SQLManager.getUserType(LoggedInUsername);

        if (userType.equals(""))
            throw new UserDoesNotExistException("No user exists with this username...");
        else if (userType.equals("B"))
            return true;
        else
            return false;
    }

    public static boolean getUserPrivacyMode(String username) throws  UserDoesNotExistException{
        String privacyMode = SQLManager.getPrivacyMode(LoggedInUsername);

        if (privacyMode.equals(""))
            throw new UserDoesNotExistException("No user exists with this username...");
        else if (privacyMode.equals("PUB"))
            return true;
        else
            return false;
    }
}


