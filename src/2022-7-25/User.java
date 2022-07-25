//import java.sql.Date;
import Exceptions.*;

import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.CompletionException;

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

    static String loggedInUserID = null;

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
        loggedInUserID = null;
    }

    public static void logout() throws NoUserLoggedInException{
        loggedInUserID = null;
    }

    public static void changeUsername(String username) throws NoUserLoggedInException, NullPointerException{

    }

    public static void changePassword(String oldPassword, String password) throws PasswordIncorrectException,
            NoUserLoggedInException, NullPointerException {

    }

    public static void changeName(String newName) throws NoUserLoggedInException, NullPointerException {

    }

    public static void changeBirthday(LocalDate birthDate) throws NoUserLoggedInException, NullPointerException{

    }

    public static void changeGender(boolean gender) throws NoUserLoggedInException {

    }

    public static void changeEmail(String email) throws NoUserLoggedInException, NullPointerException {

    }

    public static void changePrivacyMode(boolean isPrivate) throws NoUserLoggedInException {

    }

    public static void changeAccountType(boolean accountType) throws  NoUserLoggedInException {

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

    public static void sendFollowRequest(String userID) throws UserAlreadyFollowedException, UserDoesNotExistException, NoUserLoggedInException{

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

    //getters :::::::

//    public int getAge() throws UserDoesNotExistException{
//        return age;
//    }
//
//    public String getEmailAddress() throws UserDoesNotExistException {
//        return emailAddress;
//    }
//
//    public String getFirstName() throws UserDoesNotExistException {
//        return firstName;
//    }
//
//    public String getLastName() throws UserDoesNotExistException {
//        return lastName;
//    }
//
//    public char getGender() throws UserDoesNotExistException{
//        return gender;
//    }
//
//    public String getPassword() throws UserDoesNotExistException {
//        return password;
//    }
//
//    public String getUsername() throws UserDoesNotExistException {
//        return username;
//    }
//
//    public String getUserID() throws UserDoesNotExistException {
//        return userID;
//    }
//
//    public char getUserType() throws  UserDoesNotExistException{
//        return userType;
//    }
}


