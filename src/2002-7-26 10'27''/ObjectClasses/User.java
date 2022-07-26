package ObjectClasses;//import java.sql.Date;
import Exceptions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import Functionality.SQLManager;

public abstract class User {
    private int age;
    private String userID;
    private final String firstName;
    private final String lastName;
    private String username;
    private String password;
    private String emailAddress;
    private String phoneNumber;
    private final boolean gender;
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

    public User(String userID ,String firstName, String lastName, String emailAddress, boolean userType,
                     String phoneNumber , String username ,String password, boolean gender,LocalDate date, boolean isPrivate){
        this.password=password;
        this.username=username;
        this.firstName=firstName;
        this.lastName=lastName;
        this.emailAddress = emailAddress;
        this.gender=gender;
        this.phoneNumber=phoneNumber;
        this.userType=userType;
        LocalDate localDate = LocalDate.now();
        this.createdDate=date;
        this.age = date.getYear() - localDate.getYear() + 1;
        this.userID = userID;
        followerCount = 0;
        followingCount = 0;
        this.isPrivate = isPrivate;
    }

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

    public static void changeName(String newName) throws NoUserLoggedInException {
        if (LoggedInUsername == null)
            throw new NoUserLoggedInException("No user logged in yet...");
        else {
            int result = SQLManager.changeName(LoggedInUsername, newName);

            if (result == 0) {
                System.out.printf("Name changed successfully to %s%n", newName);
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
                System.out.printf("Birthday successfully changed to %s", birthDate.toString());
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
                System.out.printf("Email successfully changed to %s%n", email);
            }
        }
    }

    public static void changePrivacyMode(boolean isPrivate) throws NoUserLoggedInException {
        if (LoggedInUsername == null)
            throw new NoUserLoggedInException("No user logged in yet...");
        else {
            int result = SQLManager.changePrivacyMode(LoggedInUsername, isPrivate);

            if (result == 0) {
                System.out.printf("Privacy mode successfully changed%n");
            }
        }
    }

    public static void changeAccountType(boolean accountType) throws  NoUserLoggedInException {
        if (LoggedInUsername == null)
            throw new NoUserLoggedInException("No user logged in yet...");
        else {
            int result = SQLManager.changeAccountType(LoggedInUsername, accountType);

            if (result == 0) {
                System.out.printf("Account type successfully changed%n");
            }
        }
    }

    public static void deleteUser(String password) throws PasswordIncorrectException,NoUserLoggedInException{

    }

    public static void visitPage(String otherUsername) throws UserDoesNotExistException,NoUserLoggedInException {

    }

    public static void tweet(String content) throws NoUserLoggedInException {

    }

    public static void tweet(String content, String filepath) throws NoUserLoggedInException{

    }

    public static void sendDirectMessage(String recieverID, String content) throws NoUserLoggedInException{

    }

    public static void forwardMessage(String messageID, String recieverOrGroupID) throws NoUserLoggedInException, UserDoesNotExistException
    , MessageNotIntendedForUserException {

    }

    public static void editMessage(String messageID, String content) throws NoUserLoggedInException, UnauthorisedEditException {

    }

    public static void deleteMessage(String messageID) throws NoUserLoggedInException, UnauthorisedEditException {

    }

    public static void sendGroupMessage(String groupID, String content) throws NoUserLoggedInException{

    }

    public static void sendFollowRequest(String username) throws UserAlreadyFollowedException, UserDoesNotExistException, NoUserLoggedInException{

    }

    public static void manageFollowRequest() throws NoUserLoggedInException{

    }

    public static void acceptFollowRequest(String followerUsername) throws NoUserLoggedInException, UserDoesNotExistException, FollowRequestDoesNotExistException {

    }

    public static void commentOnTweet(String postID, String content) throws NoUserLoggedInException {

    }

    public static void deleteComment(String postID) throws NoUserLoggedInException {

    }

    public static void likeTweet(String postID) throws NoUserLoggedInException{

    }

    public static void unlikeTweet(String postID) throws NoUserLoggedInException {

    }

    public static void createNewGroup(String groupName) throws NoUserLoggedInException {

    }

    public static void showNewMessages() throws NoUserLoggedInException {

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


