package org.twitter.ObjectClasses;

import org.twitter.Exceptions.*;
import org.twitter.Functionality.SQLManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class User {
    static String LoggedInUsername  = null;
    static boolean legiblePasswordChange = false;

    String username, photoPath;

    public User(String username, String photoPath) {
        this.username = username;
        this.photoPath = photoPath;
    }

    public User(String username) {
        this.username = username;
        this.photoPath = null;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public String getUsername() {
        return username;
    }

    public static void login(String username, String password) throws UserDoesNotExistException, PasswordIncorrectException {
        int result = SQLManager.login(username, password);

        switch (result) {
            case 0:
                System.out.printf("User %s logged in successfully...%n", username);
                LoggedInUsername = username;

                try {
                    SQLManager.congratulateBirthday(LoggedInUsername);
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
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

    public static boolean checkPassword(String password) throws NoUserLoggedInException, SQLException {
        if (LoggedInUsername == null)
            throw new NoUserLoggedInException("No user logged in yet...");

        return SQLManager.checkPassword(LoggedInUsername, password);
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

    public static void changeProfile(String photoPath) throws NoUserLoggedInException {
        if (LoggedInUsername == null)
            throw new NoUserLoggedInException("No user logged in yet...");

        try {
            SQLManager.changeProfilePhoto(LoggedInUsername, photoPath);
        }
        catch (SQLException e) {
            e.printStackTrace();
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

    public static void deleteUser() {
        try {
            SQLManager.deleteUser(LoggedInUsername);
        } catch (SQLException e) {
            e.printStackTrace();
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

    public static boolean getBlocked(String username) throws NoUserLoggedInException, UserDoesNotExistException {
        String userID = User.getUserID(LoggedInUsername);
        String otherID = User.getUserID(username);

        try {
            return SQLManager.getBlocked(userID, otherID);
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Failed to do the task...");
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

    //COMPLETE THIS ONE
    public static void visitPage(String otherUsername) throws UserDoesNotExistException, NoUserLoggedInException, UnauthorisedEditException {
        if (LoggedInUsername == null)
            throw new NoUserLoggedInException("No user logged in yet...");

        String userID = User.getUserID(LoggedInUsername);

        if (otherUsername.equalsIgnoreCase(LoggedInUsername)) {
            try {
                SQLManager.visitPage(userID, userID);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else {
            String otherUserID = User.getUserID(otherUsername);
            checkFollowedAccount(userID, otherUserID);

            try {
                SQLManager.visitPage(otherUserID, userID);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public static void tweet(String content) throws NoUserLoggedInException, PostContentNullException, UserDoesNotExistException {
        if (LoggedInUsername == null)
            throw new NoUserLoggedInException("No user logged in yet...");

        String userID = User.getUserID(LoggedInUsername);

        Post.publishPost(userID, content);
    }

//    public static void tweet(String content, String filepath) throws NoUserLoggedInException{
//
//    }

    public static void mainFeed() throws NoUserLoggedInException, UserDoesNotExistException {
        if (LoggedInUsername == null)
            throw new NoUserLoggedInException("No user logged in yet...");

        String userID = User.getUserID(LoggedInUsername);

        try {
            SQLManager.showFeed(userID);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void suggestAds() throws NoUserLoggedInException, UserDoesNotExistException {
        if (LoggedInUsername == null)
            throw new NoUserLoggedInException("No user logged in yet...");

        String userID = User.getUserID(LoggedInUsername);

        try {
            SQLManager.suggestAds(userID);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void untweet(String postID) throws NoUserLoggedInException, UnauthorisedEditException, UserDoesNotExistException, PostDoesNotExistException {
        if (LoggedInUsername == null)
            throw new NoUserLoggedInException("No user logged in yet...");

        String userID = User.getUserID(LoggedInUsername);

        Post.checkMyPost(postID, userID);

        Post.deletePost(postID);
    }

    public static ArrayList<User> searchUser(String content) throws NoUserLoggedInException, UserDoesNotExistException {
        if (LoggedInUsername == null)
            throw new NoUserLoggedInException("No user logged in yet...");

        String userID = User.getUserID(LoggedInUsername);

        try {
            ArrayList<User> users = SQLManager.searchUser(userID, content);

            return users;
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Failed");
        }
    }

    public static ArrayList<User> searchUserAll(String content) throws NoUserLoggedInException {
        if (LoggedInUsername == null)
            throw new NoUserLoggedInException("No user logged in yet...");

        try {
            ArrayList<User> users = SQLManager.searchUserAll(content);

            return users;
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Failed");
        }
    }

    public static void searchMessage(String content) throws UserDoesNotExistException, NoUserLoggedInException {
        if (LoggedInUsername == null)
            throw new NoUserLoggedInException("No user logged in yet...");

        String userID = User.getUserID(LoggedInUsername);

        try {
            SQLManager.searchMessage(userID, content);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
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

    public static void suggestFollowings() throws NoUserLoggedInException, UserDoesNotExistException {
        if (LoggedInUsername == null)
            throw new NoUserLoggedInException("No user logged in yet...");

        try {
            SQLManager.suggestUsers(User.getUserID(LoggedInUsername));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void sendFollowRequest(String username) throws UserAlreadyFollowedException, UserDoesNotExistException, NoUserLoggedInException, UnauthorisedEditException {
        if (LoggedInUsername == null)
            throw new NoUserLoggedInException("No user logged in yet...");

        String userID = User.getUserID(LoggedInUsername);
        String otherUserID = User.getUserID(username);

        int result = 0;
        try {
            result = SQLManager.sendFollowRequest(userID, otherUserID);
        } catch (UnauthorisedEditException e) {
            throw e;
        }

        switch (result) {
            case 0:
                System.out.println("Follow request sent successfully...");
                break;
            case -1:
                throw new UnauthorisedEditException("Already sent follow request pending for this user...");
            case -2:
                throw new UserAlreadyFollowedException("User already in your followers list...");
            case -3:
                throw new UserDoesNotExistException("No user exists with this username...");
            default:
                break;
        }
    }

    public static ArrayList<User> getFollowRequests() throws UserDoesNotExistException {
        String userID = User.getUserID(LoggedInUsername);

        try {
            return SQLManager.getFollowRequests(userID);
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Failed");
        }
    }

    public static void manageFollowRequest() throws NoUserLoggedInException, UserDoesNotExistException {
        if (LoggedInUsername == null)
            throw new NoUserLoggedInException("No user logged in yet...");

        SQLManager.manageFollowRequests(User.getUserID(LoggedInUsername));
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

    public static int getFollowerCount(String username) throws UserDoesNotExistException, SQLException {
        String userID = User.getUserID(username);

        return SQLManager.getFollowerCount(userID);
    }

    public static int getFollowingCount(String username) throws UserDoesNotExistException, SQLException {
        String userID = User.getUserID(username);

        return SQLManager.getFollowingCount(userID);
    }

    public static void unfollow(String followedUsername) throws UserDoesNotExistException {
        String userID = User.getUserID(LoggedInUsername);
        String followedID = User.getUserID(followedUsername);

        try {
            SQLManager.unfollow(userID, followedID);
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Operation failed");
        }
    }

    public static void commentOnTweet(String postID, String content) throws NoUserLoggedInException, UserDoesNotExistException, PostDoesNotExistException, UnauthorisedEditException, PostAlreadyCommentedException {
        if (LoggedInUsername == null)
            throw new NoUserLoggedInException("No user logged in yet...");

        String userID = User.getUserID(LoggedInUsername);

        Comment.alreadyComment(postID, userID);

        try {
            int result = SQLManager.commentPost(userID, postID, content);

            if (result == 0)
                System.out.println("Post successfully commented...");
            else if (result == -1)
                throw new PostDoesNotExistException("No such post exists...");
            else
                throw new UnauthorisedEditException("You can not interact with a post of someone you do not follow...");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void showComments(String postID) throws NoUserLoggedInException, UserDoesNotExistException, UnauthorisedEditException, PostDoesNotExistException {
        if (LoggedInUsername == null)
            throw new NoUserLoggedInException("No user logged in yet...");

        String userID = User.getUserID(LoggedInUsername);

        Post.checkPostID(postID);

        Post.checkFollowedPost(postID, userID);

        Post.showComments(postID);
    }

    public static void deleteComment(String postID) throws NoUserLoggedInException, UserDoesNotExistException, PostNotCommentedException, PostDoesNotExistException, UnauthorisedEditException {
        if (LoggedInUsername == null)
            throw new NoUserLoggedInException("No user logged in yet...");

        String userID = User.getUserID(LoggedInUsername);

        Comment.notCommented(postID, userID);

        try {
            int result = SQLManager.unCommentPost(userID, postID);

            if (result == 0)
                System.out.println("Comment deleted successfully...");
            else if (result == -1)
                throw new PostDoesNotExistException("No such post exists...");
            else
                throw new UnauthorisedEditException("You can not interact with a post of someone you do not follow...");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void likeTweet(String postID) throws NoUserLoggedInException, UserDoesNotExistException, PostDoesNotExistException, UnauthorisedEditException, PostAlreadyLikedException {
        if (LoggedInUsername == null)
            throw new NoUserLoggedInException("No user logged in yet...");

        String userID = User.getUserID(LoggedInUsername);

        Like.alreadyLike(postID, userID);

        try {
           int result = SQLManager.likePost(userID, postID);

           if (result == 0)
               System.out.println("Post successfully liked...");
           else if (result == -1)
               throw new PostDoesNotExistException("No such post exists...");
           else
               throw new UnauthorisedEditException("You can not interact with a post of someone you do not follow...");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void unlikeTweet(String postID) throws NoUserLoggedInException, UserDoesNotExistException, PostNotLikedException, UnauthorisedEditException, PostDoesNotExistException {
        if (LoggedInUsername == null)
            throw new NoUserLoggedInException("No user logged in yet...");

        String userID = User.getUserID(LoggedInUsername);

        Like.notLiked(postID, userID);

        try {
            int result = SQLManager.unlikePost(userID, postID);

            if (result == 0)
                System.out.println("Post unliked successfully...");
            else if (result == -1)
                throw new PostDoesNotExistException("No such post exists...");
            else
                throw new UnauthorisedEditException("You can not interact with a post of someone you do not follow...");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

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

    public static void checkFollowedAccount(String followerID, String followedID) throws UnauthorisedEditException {
        try {
            if (SQLManager.checkFollowedAccount(followerID, followedID) == -1)
                throw new UnauthorisedEditException("User is not followed by you");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //getters

    public static boolean getUserType(String username) throws  UserDoesNotExistException{
        String userType = SQLManager.getUserType(username);

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

    public static void setLoggedInUsername(String username) {
        LoggedInUsername = username;
    }

    public static void abortMidway() {
        LoggedInUsername = null;
    }

    public static void updateBirthday(LocalDate birthday) {
        SQLManager.changeBirthday(LoggedInUsername, birthday);
    }

    public static void updateSecurityQuestions(int q1, int q2, String ans1, String ans2) {
        try {
            SQLManager.changeSecQuestions(LoggedInUsername, q1, q2, ans1, ans2);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getUserProfile(String username) throws UserDoesNotExistException {
        try {
            String profilePath = SQLManager.getProfilePath(username);

            if (profilePath != null)
                return profilePath;
            else
                return "";
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Operation failed");
        }
    }
}


