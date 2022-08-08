package org.twitter.Functionality;

import org.twitter.Exceptions.*;
import org.twitter.ObjectClasses.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class SQLManager {
    static String url = "jdbc:mysql://localhost:3306/twitter";
    static String username = "root";
    static String password = "MehrshadTaji2571381";

    static Connection con = null;
    static Statement statement = null;

    public static void initialize() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e) {
            System.out.println("No suitable driver found to connect with SQL Database...");
            System.exit(-1);
        }

        try {
            con = DriverManager.getConnection(url, username, password);
            statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        }
        catch (SQLException e) {
            System.out.println("Failed to connect with SQL Database...");
            System.exit(-1);
        }


        try {
            String query = String.format("SELECT ID FROM USERS WHERE USERTYPE = \"P\" ORDER BY DATECREATED DESC LIMIT 1");

            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                int size = Integer.parseInt(resultSet.getString(1).substring(3)) + 1;

                for (int s = 0; s < size; s++) {
                    PersonalUser.assignID();
                }
            }

            query = String.format("SELECT ID FROM USERS WHERE USERTYPE = \"B\" ORDER BY DATECREATED DESC LIMIT 1");

            resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                int size = Integer.parseInt(resultSet.getString(1).substring(3)) + 1;

                for (int s = 0; s < size; s++) {
                    BusinessUser.assignID();
                }
            }

            query = String.format("SELECT MESSAGEID FROM MESSAGES WHERE MESSAGETYPE = \"D\" ORDER BY TIMESENT DESC LIMIT 1");

            resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                int size = Integer.parseInt(resultSet.getString(1).substring(3)) + 1;

                for (int s = 0; s < size; s++) {
                    DirectMessage.assignID();
                }
            }

            query = String.format("SELECT MESSAGEID FROM MESSAGES WHERE MESSAGETYPE = \"G\" ORDER BY TIMESENT DESC LIMIT 1");

            resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                int size = Integer.parseInt(resultSet.getString(1).substring(3)) + 1;

                for (int s = 0; s < size; s++) {
                    GroupMessage.assignID();
                }
            }

            query = String.format("SELECT POSTID FROM POSTS ORDER BY DATEMODIFIED DESC LIMIT 1");

            resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                int size = Integer.parseInt(resultSet.getString(1).substring(2)) + 1;

                for (int s = 0; s < size; s++) {
                    Post.assignID();
                }
            }

            query = String.format("SELECT GROUPID FROM DIRECTGROUPS");

            resultSet = statement.executeQuery(query);

            resultSet.last();

            int size = resultSet.getRow();

            for (int s = 0; s < size; s++) {
                    Group.assignGroupID();
                }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Interact directly with User class!!
    //-----------------------------------------
    public static void createPersonalUser(String userID, String username, String password, String firstName,
                                          String lastName, String email, String phoneNumber, boolean gender,
                                          boolean isPrivate, LocalDate birthday, int q1, String a1, int q2, String a2, int age) {
        String query = String.format("INSERT INTO USERS (ID, USERNAME, PASSWORD, FIRSTNAME, LASTNAME, EMAIL, PHONENUMBER," +
                " PRIVACYMODE, BIRTHDAY, USERTYPE, DATECREATED, SECURITYQUESTION1," +
                " SECURITYANSWER1, SECURITYQUESTION2, SECURITYANSWER2, AGE) VALUES" +
                " (\"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"PUB\", \"%s\", \"P\", \"%s\", \"%d\"," +
                " \"%s\", \"%d\", \"%s\", \"%s\")", userID, username, password, firstName, lastName, email,
                 phoneNumber, birthday.toString(),LocalDateTime.now().toString(), q1, a1, q2, a2, age);

        try {
            statement.execute(query);

            if (gender)
                query = String.format("UPDATE USERS SET GENDER = \"F\" WHERE USERNAME = \"%s\"", username);
            else
                query = String.format("UPDATE USERS SET GENDER = \"M\" WHERE USERNAME = \"%s\"", username);

            statement.execute(query);

            if (isPrivate)
                query = String.format("UPDATE USERS SET PRIVACYMODE = \"PRIV\" WHERE USERNAME = \"%s\"", username);
            else
                query = String.format("UPDATE USERS SET PRIVACYMODE = \"PUB\" WHERE USERNAME = \"%s\"", username);

            statement.execute(query);

            System.out.printf("User %s created successfully...%n", username);
        }
        catch (SQLException e) {
//            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void createPersonalUser(String userID, String username, String password, String firstName,
                                          String lastName, String email, String phoneNumber, boolean gender,
                                          boolean isPrivate) {
        String query = String.format("INSERT INTO USERS (ID, USERNAME, PASSWORD, FIRSTNAME, LASTNAME, EMAIL, PHONENUMBER," +
                        " PRIVACYMODE, BIRTHDAY, USERTYPE, DATECREATED, SECURITYQUESTION1," +
                        " SECURITYANSWER1, SECURITYQUESTION2, SECURITYANSWER2, AGE) VALUES" +
                        " (\"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"PUB\", NULL, \"P\", \"%s\", NULL," +
                        " NULL, NULL, NULL, NULL)", userID, username, password, firstName, lastName, email,
                phoneNumber,LocalDateTime.now().toString());

        try {
            statement.execute(query);

            if (gender)
                query = String.format("UPDATE USERS SET GENDER = \"F\" WHERE USERNAME = \"%s\"", username);
            else
                query = String.format("UPDATE USERS SET GENDER = \"M\" WHERE USERNAME = \"%s\"", username);

            statement.execute(query);

            if (isPrivate)
                query = String.format("UPDATE USERS SET PRIVACYMODE = \"PRIV\" WHERE USERNAME = \"%s\"", username);
            else
                query = String.format("UPDATE USERS SET PRIVACYMODE = \"PUB\" WHERE USERNAME = \"%s\"", username);

            statement.execute(query);

            System.out.printf("User %s created successfully...%n", username);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createBusinessUser(String userID, String username, String password, String firstName,
                                          String lastName, String email, String phoneNumber, boolean gender,
                                          boolean isPrivate, LocalDate birthday, int q1, String a1, int q2, String a2, int age) {
        String query = String.format("INSERT INTO USERS (ID, USERNAME, PASSWORD, FIRSTNAME, LASTNAME, EMAIL, PHONENUMBER," +
                        " PRIVACYMODE, BIRTHDAY, USERTYPE, DATECREATED, SECURITYQUESTION1," +
                        " SECURITYANSWER1, SECURITYQUESTION2, SECURITYANSWER2, AGE  ) VALUES" +
                        " (\"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"PUB\", \"%s\", \"B\", \"%s\", \"%d\"," +
                        " \"%s\", \"%d\", \"%s\", \"%s\")", userID, username, password, firstName, lastName, email,
                phoneNumber, birthday.toString(),LocalDateTime.now().toString(), q1, a1, q2, a2, age);

        try {
            statement.execute(query);

            if (gender)
                query = String.format("UPDATE USERS SET GENDER = \"F\" WHERE USERNAME = \"%s\"", username);
            else
                query = String.format("UPDATE USERS SET GENDER = \"M\" WHERE USERNAME = \"%s\"", username);

            statement.execute(query);

            if (isPrivate)
                query = String.format("UPDATE USERS SET PRIVACYMODE = \"PRIV\" WHERE USERNAME = \"%s\"", username);
            else
                query = String.format("UPDATE USERS SET PRIVACYMODE = \"PUB\" WHERE USERNAME = \"%s\"", username);

            statement.execute(query);

            System.out.printf("User %s created successfully...%n", username);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createBusinessUser(String userID, String username, String password, String firstName,
                                          String lastName, String email, String phoneNumber, boolean gender,
                                          boolean isPrivate) {
        String query = String.format("INSERT INTO USERS (ID, USERNAME, PASSWORD, FIRSTNAME, LASTNAME, EMAIL, PHONENUMBER," +
                        " PRIVACYMODE, BIRTHDAY, USERTYPE, DATECREATED, SECURITYQUESTION1," +
                        " SECURITYANSWER1, SECURITYQUESTION2, SECURITYANSWER2, AGE) VALUES" +
                        " (\"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\", \"PUB\", NULL, \"B\", \"%s\", NULL," +
                        " NULL, NULL, NULL, NULL)", userID, username, password, firstName, lastName, email,
                phoneNumber,LocalDateTime.now().toString());

        try {
            statement.execute(query);

            if (gender)
                query = String.format("UPDATE USERS SET GENDER = \"F\" WHERE USERNAME = \"%s\"", username);
            else
                query = String.format("UPDATE USERS SET GENDER = \"M\" WHERE USERNAME = \"%s\"", username);

            statement.execute(query);

            if (isPrivate)
                query = String.format("UPDATE USERS SET PRIVACYMODE = \"PRIV\" WHERE USERNAME = \"%s\"", username);
            else
                query = String.format("UPDATE USERS SET PRIVACYMODE = \"PUB\" WHERE USERNAME = \"%s\"", username);

            statement.execute(query);

            System.out.printf("User %s created successfully...%n", username);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int login(String username, String password) {
        String query = String.format("SELECT USERNAME, PASSWORD FROM USERS WHERE USERNAME = \"%s\"", username);
        try {
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                query = String.format("SELECT PASSWORD FROM USERS WHERE USERNAME = \"%s\"", username);
                resultSet = statement.executeQuery(query);

                resultSet.next();

                if (resultSet.getString(1).equals(password)) {
                    return 0;
                }
                else {
                    return -2;
                }
            }
            else {
                return -1;
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return -3;
        }
    }

    public static String getUserID(String username) {
        String query = String.format("SELECT ID FROM USERS WHERE USERNAME = \"%s\"", username);

        try {
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                return resultSet.getString(1);
            }
            else
                return "";
        }
        catch (SQLException e) {
            return "";
        }
    }

    public static String getUserType(String username) {
        String query = String.format("SELECT USERTYPE FROM USERS WHERE USERNAME = \"%s\"", username);

        try {
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                return resultSet.getString(1);
            }
            else
                return "";
        }
        catch (SQLException e) {
            return "";
        }
    }

    public static String getPrivacyMode(String username) {
        String query = String.format("SELECT PRIVACYMODE FROM USERS WHERE USERNAME = \"%s\"", username);

        try {
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                return resultSet.getString(1);
            }
            else
                return "";
        }
        catch (SQLException e) {
            return "";
        }
    }

    public static int changeUsername(String lastUsername, String currentUsername) {
        String query = String.format("UPDATE USERS SET USERNAME = \"%s\" WHERE USERNAME = \"%s\"", currentUsername, lastUsername);

        try {
            statement.execute(query);
            return 0;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    public static int changePassword(String username, String oldPassword, String newPassword) {
        String query = String.format("SELECT PASSWORD FROM USERS WHERE USERNAME = \"%s\"", username);

        try {
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                if (resultSet.getString(1).equals(oldPassword)) {
                    query = String.format("UPDATE USERS SET PASSWORD = \"%s\" WHERE USERNAME = \"%s\"", newPassword, username);
                    statement.execute(query);
                    return 0;
                }
                else
                    return -1;
            }
            else
                return -2;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return -3;
        }
    }

    public static int changeBirthday(String username,LocalDate birthday)  {
        String query = String.format("UPDATE USERS SET BIRTHDAY = \"%s\" WHERE USERNAME = \"%s\"", birthday.toString(), username);

        try {
            statement.execute(query);
            return 0;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    public static int changeGender(String username, boolean gender) {
        String query;

        if (gender == false) {
            query = String.format("UPDATE USERS SET GENDER = \"M\" WHERE USERNAME = \"%s\"", username);
        }
        else {
            query = String.format("UPDATE USERS SET GENDER = \"F\" WHERE USERNAME = \"%s\"", username);
        }

        try {
            statement.execute(query);
            return 0;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    public static int changeEmail(String username, String email) {
        String query = String.format("UPDATE USERS SET EMAIL = \"%s\" WHERE USERNAME = \"%s\"", email, username);

        try {
            statement.execute(query);
            return 0;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    public static int changePrivacyMode(String username,boolean isPrivate) {
        String query;

        if (isPrivate)
            query = String.format("UPDATE USERS SET PRIVACYMODE = \"PRIV\" WHERE USERNAME = \"%s\"", username);
        else
            query = String.format("UPDATE USERS SET PRIVACYMODE = \"PUB\" WHERE USERNAME = \"%s\"", username);

        try {
            statement.execute(query);
            return 0;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    public static void changeProfilePhoto(String username, String path) throws SQLException {
        String query = String.format("UPDATE USERS SET PROFILEPHOTO = \"%s\" WHERE USERNAME = \"%s\"", path, username);

        statement.execute(query);
    }

    public static void changeSecQuestions(String username, int q1, int q2, String ans1, String ans2) throws SQLException {
        String query = String.format("UPDATE USERS SET SECURITYQUESTION1 = \"%d\", SECURITYQUESTION2 = \"%d\", " +
                "SECURITYANSWER1 = \"%s\", SECURITYANSWER2 = \"%s\" WHERE USERNAME = \"%s\"", q1, q2, ans1, ans2, username);

        statement.execute(query);
    }

    public static void congratulateBirthday(String username) throws SQLException{
        String query = String.format("SELECT BIRTHDAY FROM USERS WHERE USERNAME = \"%s\"", username);

        LocalDate today = LocalDate.now();

        ResultSet resultSet = statement.executeQuery(query);

        resultSet.next();

        LocalDate birthday = resultSet.getDate(1).toLocalDate();

        if (today.getMonth().equals(birthday.getMonth()) && today.getDayOfMonth() == birthday.getDayOfMonth())
            System.out.printf("Happy birthday %s!%n", username);
    }

    public static int deleteUser(String username, String password) {
        String query = String.format("SELECT USERNAME, PASSWORD FROM USERS WHERE USERNAME = \"%s\"", username);

        try {
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                if (resultSet.getString(2).equals(password)) {
                    query = String.format("DELETE FROM USERS WHERE USERNAME = \"%s\"", username);

                    statement.execute(query);

                    return 0;
                }
                else
                    return -1;
            }
            else
                return -2;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return -3;
        }
    }

    public static void deleteUser(String username) throws SQLException {
        String query = String.format("DELETE FROM USERS WHERE USERNAME = \"%s\"", username);

        statement.execute(query);
    }

    public static int blockUser(String blockerID, String blockedID) {
        String query = String.format("SELECT USERNAME FROM USERS WHERE ID = \"%s\"", blockedID);

        try {
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                query = String.format("SELECT * FROM BLOCKS WHERE BLOCKERID = \"%s\" && BLOCKEDID = \"%s\"", blockerID, blockedID);

                resultSet = statement.executeQuery(query);

                if (resultSet.next())
                    return -1;
                else {
                    query = String.format("INSERT INTO BLOCKS VALUES (\"%s\", \"%s\")", blockerID, blockedID);

                    statement.execute(query);

                    return 0;
                }
            }
            else
                return -2;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return -3;
        }
    }

    public static boolean getBlocked(String blockerID, String blockedID) throws SQLException {
        String query = String.format("SELECT * FROM BLOCKS WHERE BLOCKERID = \"%s\" AND BLOCKEDID = \"%s\"", blockerID, blockedID);

        ResultSet resultSet = statement.executeQuery(query);

        if (resultSet.next())
            return true;
        else
            return false;
    }

    public static int unblockUser(String blockerID, String blockedID) {
        String query = String.format("SELECT USERNAME FROM USERS WHERE ID = \"%s\"", blockedID);

        try {
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                query = String.format("SELECT * FROM BLOCKS WHERE BLOCKERID = \"%s\" && BLOCKEDID = \"%s\"", blockerID, blockedID);

                resultSet = statement.executeQuery(query);

                if (resultSet.next()) {
                    query = String.format("DELETE FROM BLOCKS WHERE BLOCKERID = \"%s\" && BLOCKEDID = \"%s\"", blockerID, blockedID);

                    statement.execute(query);

                    return 0;
                }
                else {
                    return -1;
                }
            }
            else
                return -2;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return -3;
        }
    }
    
    public static String getSecurityQuestions(String username) {
        String query = String.format("SELECT SECURITYQUESTION1, SECURITYQUESTION2 FROM USERS WHERE USERNAME = \"%s\"", username);
        
        try {
            ResultSet resultSet = statement.executeQuery(query);
            
            if (resultSet.next()) {
                return String.format(resultSet.getString(1) + resultSet.getString(2));
            }
            else 
                return "NaN";
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return "";
        }
    }

    public static String getProfilePath(String username) throws SQLException {
        String query = String.format("SELECT PROFILEPHOTO FROM USERS WHERE USERNAME = \"%s\"", username);

        ResultSet resultSet = statement.executeQuery(query);

        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        else {
            return null;
        }
    }
    
    public static int forgotPassword(String username ,String answer1, String answer2) {
        String query = String.format("SELECT SECURITYANSWER1, SECURITYANSWER2 FROM USERS WHERE USERNAME = \"%s\"", username);

        try {
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                if (resultSet.getString(1).equalsIgnoreCase(answer1) && resultSet.getString(2).equalsIgnoreCase(answer2)) {
                    return 0;
                }
                else
                    return -1;
            }
            else
                return -2;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return -3;
        }
    }

    public static int changePassword(String username, String newPassword) {
        String query = String.format("SELECT PASSWORD FROM USERS WHERE USERNAME = \"%s\"", username);

        try {
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                query = String.format("UPDATE USERS SET PASSWORD = \"%s\" WHERE USERNAME = \"%s\"", newPassword, username);
                statement.execute(query);
                return 0;
            }
            else
                return -1;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return -2;
        }
    }

    public static int sendFollowRequest(String userID, String otherUserID) throws UnauthorisedEditException {
        if (userID.equalsIgnoreCase(otherUserID))
            throw new UnauthorisedEditException("You can not follow yourself!");

        String query = String.format("SELECT USERNAME FROM USERS WHERE ID = \"%s\"", otherUserID);

        try {
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                query = String.format("SELECT * FROM FOLLOWREQUESTS WHERE FROMID = \"%s\" && TOID = \"%s\"", userID , otherUserID);

                resultSet = statement.executeQuery(query);

                if (resultSet.next())
                    return -1;
                else {
                    query = String.format("SELECT * FROM RELATIONS WHERE FOLLOWERID = \"%s\" && FOLLOWEDID = \"%s\"", userID, otherUserID);

                    resultSet = statement.executeQuery(query);

                    if (resultSet.next())
                        return -2;
                    else {
                         query = String.format("INSERT INTO FOLLOWREQUESTS VALUES (\"%s\", \"%s\")", userID , otherUserID);

                         statement.execute(query);

                         return 0;
                    }
                }
            }
            else
                return -3;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return -4;
        }
    }

    public static void manageFollowRequests(String userID) {
        String query = String.format("SELECT FOLLOWREQUESTS.FROMID, USERS.USERNAME FROM FOLLOWREQUESTS INNER JOIN " +
                "USERS ON FROMID = ID WHERE FOLLOWREQUESTS.TOID = \"%s\"", userID);

        try {
            ResultSet resultSet = statement.executeQuery(query);

            System.out.println("Requests to follow your account from other users pending your approval: ");
            System.out.println("UserID\tUsername");
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1) + "\t" + resultSet.getString(2));
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static int acceptFollowRequest(String userID, String otherUserID) {
        String query = String.format("SELECT USERNAME FROM USERS WHERE ID = \"%s\"", otherUserID);

        try {
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                query = String.format("SELECT FOLLOWEDID FROM RELATIONS WHERE FOLLOWEDID = \"%s\" && FOLLOWERID = \"%s\"", userID, otherUserID);

                resultSet = statement.executeQuery(query);

                if (resultSet.next())
                    return -2;
                else {
                    query = String.format("SELECT FROMID FROM FOLLOWREQUESTS WHERE FROMID = \"%s\" && TOID = \"%s\"", otherUserID, userID);

                    resultSet = statement.executeQuery(query);

                    if (resultSet.next()) {
                        query = String.format("DELETE FROM FOLLOWREQUESTS WHERE FROMID = \"%s\" && TOID = \"%s\"", otherUserID, userID);
                        statement.execute(query);
                        query = String.format("INSERT INTO RELATIONS VALUES (\"%s\", \"%s\")", otherUserID, userID);
                        statement.execute(query);

                        return 0;
                    }
                    else
                        return -1;
                }
            }
            else
                return -3;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return -4;
        }
    }

    public static void unfollow(String userID, String followedID) throws SQLException{
        String query = String.format("DELETE FROM RELATIONS WHERE FOLLOWERID = \"%s\" AND FOLLOWEDID = \"%s\"", userID, followedID);

        statement.execute(query);

        query = String.format("DELETE FROM FOLLOWREQUESTS WHERE FROMID = \"%s\" AND TOID = \"%s\"", userID, followedID);

        statement.execute(query);
    }

    public static int getFollowerCount(String userID) throws SQLException {
        String query = String.format("SELECT FOLLOWERID FROM RELATIONS WHERE FOLLOWEDID = \"%s\"", userID);

        ResultSet resultSet = statement.executeQuery(query);

        resultSet.last();

        return resultSet.getRow();
    }

    public static int getFollowingCount(String userID) throws SQLException {
        String query = String.format("SELECT FOLLOWEDID FROM RELATIONS WHERE FOLLOWERID = \"%s\"", userID);

        ResultSet resultSet = statement.executeQuery(query);

        resultSet.last();

        return resultSet.getRow();
    }

    public static void showMyGroups(String userID) {
        String query = String.format("SELECT GROUPSPARTICIPANTS.GROUPID, DIRECTGROUPS.GROUPNAME, DIRECTGROUPS.OWNERID FROM GROUPSPARTICIPANTS" +
                " INNER JOIN DIRECTGROUPS USING (GROUPID) WHERE GROUPSPARTICIPANTS.USERID = \"%s\"", userID);

        try {
            ResultSet resultSet = statement.executeQuery(query);

            System.out.println("Groups in which you participate: ");
            System.out.println("ID Name Owner");

            while (resultSet.next()) {
                System.out.println(resultSet.getString(1) + "\t" + resultSet.getString(2) + "\t" + resultSet.getString(3));
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<DirectMessage> getChat(String userID, String otherID) throws SQLException {
        String query = String.format("SELECT MESSAGEID, CONTENT, USER1, TIMESENT FROM MESSAGES" +
                " WHERE (USER1 = \"%s\" AND USER2 = \"%s\") OR (USER2 = \"%s\" AND USER1 = \"%s\")", userID, otherID, userID, otherID);

        ResultSet resultSet = statement.executeQuery(query);

        ArrayList<DirectMessage> messages = new ArrayList<>();

        while (resultSet.next()) {
            String messageID = resultSet.getString(1);
            String content = resultSet.getString(2);
            LocalDateTime timeSent = resultSet.getTimestamp(4).toLocalDateTime();

            boolean mine;
            if (resultSet.getString(3).equalsIgnoreCase(userID))
                mine = true;
            else
                mine = false;

            messages.add(new DirectMessage(otherID, content, mine, messageID, timeSent));
        }

        for (DirectMessage message : messages) {
            if (!message.getMine()) {
                query = String.format("UPDATE INBOX SET SEEN = 1 WHERE MESSAGEID = \"%s\"", message.getMessageID());
                statement.execute(query);
            }
        }

        return messages;
    }

    public static ArrayList<GroupMessage> getChat(String groupName) throws SQLException, UserDoesNotExistException {
        String loggedInUserID = User.getUserID(User.getLoggedInUsername());

        String query = String.format("SELECT MESSAGES.CONTENT, MESSAGES.USER1, MESSAGES.MESSAGEID, USERS.PROFILEPHOTO, MESSAGES.TIMESENT FROM MESSAGES" +
                " INNER JOIN DIRECTGROUPS USING (GROUPID) INNER JOIN USERS ON MESSAGES.USER1 = USERS.ID" +
                " WHERE DIRECTGROUPS.GROUPNAME = \"%s\"", groupName);

        ResultSet resultSet = statement.executeQuery(query);

        ArrayList<GroupMessage> messages = new ArrayList<>();

        while (resultSet.next()) {
            String messageID = resultSet.getString(3);
            String senderID = resultSet.getString(2);
            String content = resultSet.getString(1);
            LocalDateTime timeSent = resultSet.getTimestamp(5).toLocalDateTime();
            String photoPath = null;
            if (resultSet.getString(4) != null)
                photoPath = resultSet.getString(4);

            boolean mine;
            if (senderID.equalsIgnoreCase(loggedInUserID))
                mine = true;
            else
                mine = false;

            if (photoPath != null)
                messages.add(new GroupMessage(senderID, messageID, content, photoPath, mine, groupName, timeSent));
            else
                messages.add(new GroupMessage(senderID, messageID, content, mine, groupName, timeSent));
        }

        for (GroupMessage message : messages) {
            query = String.format("UPDATE INBOX SET SEEN = 1 WHERE MESSAGEID = \"%s\" AND USERID = \"%s\"",
                    message.getMessageID(), User.getUserID(User.getLoggedInUsername()));

            statement.execute(query);
        }

        return messages;
    }

    public static void showChat(String user1, String user2) {
            String query = String.format("SELECT MESSAGES.MESSAGEID, USER1, MESSAGES.CONTENT, REPLYMESSAGES.CONTENT FROM MESSAGES LEFT JOIN" +
                    " REPLYMESSAGES ON MESSAGES.REPLIEDMESSAGEID = REPLYMESSAGES.MESSAGEID WHERE (USER1 = \"%s\" AND USER2 = \"%s\")" +
                    " OR (USER2 = \"%s\" AND USER1 = \"%s\") ORDER BY MESSAGES.MESSAGEID", user1, user2, user1, user2);
        
        try {
            ResultSet resultSet = statement.executeQuery(query);

            System.out.println("Showing chat between you two:");
            System.out.println("MessageID from USERID : (Briefed replied message if exists) | Content");
            
            while (resultSet.next()) {
                if (resultSet.getString(4) == null) {
                    System.out.println(resultSet.getString(1) + " from " + resultSet.getString(2) + " : " + resultSet.getString(3));
                }
                else {
                    System.out.println(resultSet.getString(1) + " from " + resultSet.getString(2) + " : " +
                            resultSet.getString(4) + " | " + resultSet.getString(3));
                }
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void showChat(String groupID) {
        String query = String.format("SELECT MESSAGES.MESSAGEID, USER1, MESSAGES.CONTENT, REPLYMESSAGES.CONTENT FROM MESSAGES LEFT JOIN" +
                " REPLYMESSAGES ON MESSAGES.REPLIEDMESSAGEID = REPLYMESSAGES.MESSAGEID WHERE GROUPID = \"%s\" ORDER BY MESSAGES.MESSAGEID", groupID);

        try {
            ResultSet resultSet = statement.executeQuery(query);

            System.out.println("Showing chat in group: ");
            System.out.println("MessageID from USERID : (Briefed replied message if exists) | Content");

            while (resultSet.next()) {
                if (resultSet.getString(4) == null) {
                    System.out.println(resultSet.getString(1) + " from " + resultSet.getString(2) + " : " + resultSet.getString(3));
                }
                else {
                    System.out.println(resultSet.getString(1) + " from " + resultSet.getString(2) + " : " +
                            resultSet.getString(4) + " | " + resultSet.getString(3));
                }
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<User> searchUser(String userID, String content) throws SQLException {
        String query = String.format("SELECT USERS.USERNAME, USERS.PROFILEPHOTO FROM RELATIONS INNER JOIN USERS" +
                " ON USERS.ID = RELATIONS.FOLLOWERID WHERE RELATIONS.FOLLOWEDID = \"%s\"" +
                " AND USERS.USERNAME LIKE \"%%%s%%\"", userID, content);

        ResultSet resultSet = statement.executeQuery(query);

        ArrayList<User> users = new ArrayList<>();

        while (resultSet.next()) {
            String username = resultSet.getString(1);

            if (resultSet.getString(2) != null)
                users.add(new User(username, resultSet.getString(2)));
            else {
                users.add(new User(username));
            }
        }

        return users;
    }

    public static ArrayList<User> searchUserAll(String content) throws SQLException {
        String query = String.format("SELECT USERNAME, PROFILEPHOTO FROM USERS WHERE" +
                " USERNAME LIKE \"%%%s%%\"", content);

        ResultSet resultSet = statement.executeQuery(query);

        ArrayList<User> users = new ArrayList<>();

        while (resultSet.next()) {
            String username = resultSet.getString(1);

            if (resultSet.getString(2) != null)
                users.add(new User(username, resultSet.getString(2)));
            else {
                users.add(new User(username));
            }
        }

        return users;
    }

    public static ArrayList<User> getFollowRequests(String userID) throws SQLException {
        String query = String.format("SELECT USERS.USERNAME, USERS.PROFILEPHOTO FROM FOLLOWREQUESTS" +
                " INNER JOIN USERS ON FOLLOWREQUESTS.FROMID = USERS.ID" +
                " WHERE FOLLOWREQUESTS.TOID = \"%s\"", userID);

        ResultSet resultSet = statement.executeQuery(query);

        ArrayList<User> users = new ArrayList<>();

        while (resultSet.next()) {
            String userName = resultSet.getString(1);

            if (resultSet.getString(2) != null) {
                String photoPath = resultSet.getString(2);
                users.add(new User(userName, photoPath));
            }
            else {
                users.add(new User(userName));
            }
        }

        return users;
    }

    public static void searchMessage(String userID, String content) throws SQLException {
        String query = String.format("SELECT Messages.MESSAGEID, Messages.USER1, Messages.CONTENT, INBOX.USERID FROM MESSAGES " +
                "INNER JOIN INBOX USING (MESSAGEID) WHERE INBOX.USERID = \"%s\" AND MESSAGES.CONTENT" +
                " LIKE \"%%%s%%\"", userID, content);

        ResultSet resultSet = statement.executeQuery(query);

        System.out.println("Attempting to collect matching messages: ");

        while (resultSet.next()) {
            System.out.println(resultSet.getString(1) + " from " + resultSet.getString(2) +
                    " : " + resultSet.getString(3).substring(0,6));
        }
    }

    public static void showInbox(String userID) throws SQLException {
        String query = String.format("SELECT INBOX.MESSAGEID, MESSAGES.USER1, MESSAGES.CONTENT FROM INBOX INNER JOIN " +
                "MESSAGES USING (MESSAGEID) WHERE USERID = \"%s\" AND SEEN = 0", userID);

        ResultSet resultSet = statement.executeQuery(query);

        System.out.println("Your new Messages: ");

        while (resultSet.next()) {
            System.out.println(resultSet.getString(1) + " from " + resultSet.getString(2) + " : " + resultSet.getString(3));
        }

        query = String.format("UPDATE INBOX SET SEEN = 1 WHERE USERID = \"%s\"", userID);

        statement.execute(query);
    }

    public static int createGroup(String userID, String groupName) throws SQLException {
        String query = String.format("SELECT GROUPID FROM DIRECTGROUPS WHERE GROUPNAME = \"%s\"", groupName);

        ResultSet resultSet = statement.executeQuery(query);

        if (resultSet.next())
            return -1;

        String groupID = Group.assignGroupID();

        query = String.format("INSERT INTO DIRECTGROUPS VALUES (\"%s\", \"%s\", \"%s\", NULL, 0)", groupID, userID, groupName);

        statement.execute(query);

        query = String.format("INSERT INTO GROUPSPARTICIPANTS VALUES (\"%s\",0,\"%s\")", userID, groupID);

        statement.execute(query);

        query = String.format("INSERT INTO GROUPSADMINS VALUES (\"%s\",\"%s\")", groupID, userID);

        statement.execute(query);

        return 0;
    }

    public static Group getGroup(String groupName) throws SQLException {
        String query = String.format("SELECT GROUPID, MEMBERNUM, OWNERID FROM DIRECTGROUPS WHERE GROUPNAME = \"%s\"", groupName);

        ResultSet resultSet = statement.executeQuery(query);

        if (resultSet.next()) {
            String groupID = resultSet.getString(1);
            int memberNum = resultSet.getInt(2);
            String ownerID = resultSet.getString(3);

            query = String.format("SELECT USERS.USERNAME, USERS.PROFILEPHOTO, GROUPSPARTICIPANTS.BANNED FROM GROUPSPARTICIPANTS" +
                    " INNER JOIN USERS ON USERS.ID = GROUPSPARTICIPANTS.USERID" +
                    " WHERE GROUPSPARTICIPANTS.GROUPID = \"%s\"", groupID);

            resultSet = statement.executeQuery(query);

            ArrayList<String> usersID = new ArrayList<>();
            ArrayList<Boolean> banned = new ArrayList<>();
            ArrayList<String> photos = new ArrayList<>();

            while (resultSet.next()) {
                usersID.add(resultSet.getString(1));

                if (resultSet.getString(2) != null)
                    photos.add(resultSet.getString(2));
                else
                    photos.add("");

                if (resultSet.getBoolean(3))
                    banned.add(true);
                else
                    banned.add(false);
            }

            query = String.format("SELECT USERS.USERNAME FROM GROUPSADMINS INNER JOIN USERS" +
                    " ON USERS.ID = GROUPSADMINS.USERID WHERE GROUPSADMINS.GROUPID = \"%s\"", groupID);

            resultSet = statement.executeQuery(query);

            ArrayList<String> adminsID = new ArrayList<>();

            while (resultSet.next()) {
                adminsID.add(resultSet.getString(1));
            }

            Group myGroup = new Group(groupName, ownerID, memberNum, usersID, adminsID, photos, banned);
            return myGroup;
        }
        else
            return null;
    }

    public static void showGroupInfo(String groupID) throws SQLException {
        String query = String.format("SELECT GROUPID, GROUPNAME, OWNERID FROM DIRECTGROUPS WHERE GROUPID = \"%s\"", groupID);

        ResultSet resultSet = statement.executeQuery(query);

        if (resultSet.next()) {
            System.out.print(resultSet.getString(1) + " | " + resultSet.getString(2) + " | OwnerID : " + resultSet.getString(3));
        }

        query = String.format("SELECT USERID FROM GROUPSPARTICIPANTS WHERE GROUPID = \"%s\"", groupID);

        resultSet = statement.executeQuery(query);

        resultSet.last();

        int size = resultSet.getRow();

        System.out.println(" | Number of participants: " + size);

        query = String.format("SELECT USERS.ID, USERS.USERNAME FROM GROUPSADMINS INNER JOIN USERS ON USERS.ID = GROUPSADMINS.USERID WHERE GROUPID = \"%s\"", groupID);

        resultSet = statement.executeQuery(query);

        System.out.println("Group Admins: ");

        while (resultSet.next()) {
            System.out.println(resultSet.getString(1) + "\t" + resultSet.getString(2));
        }
    }

    public static void showFeed(String userID) throws SQLException {
        String query = String.format("SELECT DISTINCTROW POSTS.POSTID, POSTS.USERID, POSTS.CONTENT," +
                " POSTS.DATEMODIFIED, USERS.USERTYPE, POSTS.LIKENUM, POSTS.COMMENTNUM FROM POSTS" +
                " INNER JOIN RELATIONS ON RELATIONS.FOLLOWEDID = POSTS.USERID" +
                " INNER JOIN USERS ON POSTS.USERID = USERS.ID" +
                " WHERE RELATIONS.FOLLOWERID = \"%s\"" +
                " ORDER BY POSTS.DATEMODIFIED DESC", userID);

        System.out.println("Showing you user feed: ");

        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            if (resultSet.getString(5).equalsIgnoreCase("B")) {
                System.out.println("-----------------------------------");
                System.out.println("Ad " + resultSet.getString(1) + " posted by: " + resultSet.getString(2));
                System.out.println(resultSet.getString(3));
                System.out.println("Date posted: " + resultSet.getTimestamp(4).toString());
                System.out.println("Likes count: " + resultSet.getInt(6) + "\tComments count: " + resultSet.getInt(7));
                System.out.println("-----------------------------------");
            }
            else {
                System.out.println("-----------------------------------");
                System.out.println(resultSet.getString(1) + " posted by: " + resultSet.getString(2));
                System.out.println(resultSet.getString(3));
                System.out.println("Date posted: " + resultSet.getTimestamp(4).toString());
                System.out.println("Likes count: " + resultSet.getInt(6) + "\tComments count: " + resultSet.getInt(7));
                System.out.println("-----------------------------------");
            }
        }
    }

    public static ArrayList<Like> getLikes(String posterID) throws SQLException {
        String query = String.format("SELECT POSTS.POSTID, LIKES.USERID, LIKES.DATELIKED FROM LIKES" +
                " INNER JOIN POSTS USING (POSTID) WHERE POSTS.USERID = \"%s\" ORDER BY LIKES.DATELIKED DESC", posterID);

        ResultSet resultSet = statement.executeQuery(query);

        ArrayList<Like> likes = new ArrayList<>();

        while (resultSet.next()) {
            String postID = resultSet.getString(1);
            String likerID = resultSet.getString(2);
            LocalDate dateLiked = resultSet.getDate(3).toLocalDate();

            likes.add(new Like(posterID, likerID, postID, dateLiked));
        }

        return likes;
    }

    public static ArrayList<Comment> getComments(String posterID) throws SQLException {
        String query = String.format("SELECT POSTS.POSTID, COMMENTS.USERID, COMMENTS.DATECOMMENTED, COMMENTS.COMMENT" +
                " FROM COMMENTS INNER JOIN POSTS USING (POSTID) WHERE POSTS.USERID = \"%s\" ORDER BY COMMENTS.DATECOMMENTED DESC", posterID);

        ResultSet resultSet = statement.executeQuery(query);

        ArrayList<Comment> comments = new ArrayList<>();

        while (resultSet.next()) {
            String postID = resultSet.getString(1);
            String commenterID = resultSet.getString(2);
            String content = resultSet.getString(4);
            LocalDate dateCommented = resultSet.getDate(3).toLocalDate();

            comments.add(new Comment(postID, posterID, commenterID, content, dateCommented));
        }

        return comments;
    }

    public static int likePost(String userID, String postID) throws SQLException {
        String query = String.format("SELECT USERID FROM POSTS WHERE POSTID = \"%s\"", postID);

        ResultSet resultSet = statement.executeQuery(query);

        if (resultSet.next()) {
            boolean found = false;

            String posterID = resultSet.getString(1);

            if (resultSet.getString(1).equalsIgnoreCase(userID))
                found = true;
            else {
                query = String.format("SELECT FOLLOWERID FROM RELATIONS WHERE FOLLOWEDID = \"%s\"", posterID);

                resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    if (resultSet.getString(1).equalsIgnoreCase(userID)) {
                        found = true;
                        break;
                    }
                }
            }

            if (found) {
                query = String.format("INSERT INTO LIKES VALUES (\"%s\",\"%s\",\"%s\")", postID, userID, LocalDate.now().toString());

                statement.execute(query);

                query = String.format("SELECT USERTYPE FROM USERS WHERE ID = \"%s\"", posterID);
                resultSet = statement.executeQuery(query);
                resultSet.next();
                if (resultSet.getString(1).equalsIgnoreCase("B")) {
                    query = String.format("SELECT LIKED FROM ADVERTISEMENTS WHERE POSTID = \"%s\" AND VISITORID = \"%s\"", postID, userID);
                    resultSet = statement.executeQuery(query);

                    if (resultSet.next()) {
                        query = String.format("UPDATE ADVERTISEMENTS SET LIKED = 1 WHERE POSTID = \"%s\"", postID);
                        statement.execute(query);
                    }
                    else {
                        if (!posterID.equalsIgnoreCase(query)) {
                            query = String.format("INSERT INTO ADVERTISEMENTS VALUES (\"%s\", \"%s\", \"%s\", 1)", postID, posterID, userID);
                            statement.execute(query);
                        }
                    }
                }

                return 0;
            }
            else
                return -2;
        }
        else
            return -1;
    }

    public static int unlikePost(String userID, String postID) throws SQLException {
        String query = String.format("SELECT USERID FROM POSTS WHERE POSTID = \"%s\"", postID);

        ResultSet resultSet = statement.executeQuery(query);

        if (resultSet.next()) {
            String posterID = resultSet.getString(1);

            query = String.format("SELECT FOLLOWERID FROM RELATIONS WHERE FOLLOWEDID = \"%s\"", resultSet.getString(1));

            resultSet = statement.executeQuery(query);

            boolean found = false;
            while (resultSet.next()) {
                if (resultSet.getString(1).equalsIgnoreCase(userID)) {
                    found = true;
                    break;
                }
            }

            if (found) {
                query = String.format("DELETE FROM LIKES WHERE USERID = \"%s\" AND POSTID = \"%s\"", userID, postID);

                statement.execute(query);

                query = String.format("SELECT USERTYPE FROM USERS WHERE ID = \"%s\"", posterID);
                resultSet = statement.executeQuery(query);
                resultSet.next();
                if (resultSet.getString(1).equalsIgnoreCase("B")) {
                    query = String.format("UPDATE ADVERTISEMENTS SET LIKED = 0 WEHRE POSTID = \"%s\"", postID);
                    statement.execute(query);
                }

                return 0;
            }
            else
                return -2;
        }
        else
            return -1;
    }

    public static int commentPost(String userID, String postID, String comment) throws SQLException {
        String query = String.format("SELECT USERID FROM POSTS WHERE POSTID = \"%s\"", postID);

        ResultSet resultSet = statement.executeQuery(query);

        if (resultSet.next()) {
            boolean found = false;

            if (resultSet.getString(1).equalsIgnoreCase(userID)) {
                found = true;
            }
            else {
                query = String.format("SELECT FOLLOWERID FROM RELATIONS WHERE FOLLOWEDID = \"%s\"", resultSet.getString(1));

                resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    if (resultSet.getString(1).equalsIgnoreCase(userID)) {
                        found = true;
                        break;
                    }
                }
            }

            if (found) {
                query = String.format("INSERT INTO COMMENTS VALUES (\"%s\",\"%s\",\"%s\", \"%s\")", postID, userID, comment, LocalDate.now().toString());

                statement.execute(query);

                return 0;
            }
            else
                return -2;
        }
        else
            return -1;
    }

    public static int unCommentPost(String userID, String postID) throws SQLException {
        String query = String.format("SELECT USERID FROM POSTS WHERE POSTID = \"%s\"", postID);

        ResultSet resultSet = statement.executeQuery(query);

        if (resultSet.next()) {
            query = String.format("SELECT FOLLOWERID FROM RELATIONS WHERE FOLLOWEDID = \"%s\"", resultSet.getString(1));

            resultSet = statement.executeQuery(query);

            boolean found = false;
            while (resultSet.next()) {
                if (resultSet.getString(1).equalsIgnoreCase(userID)) {
                    found = true;
                    break;
                }
            }

            if (found) {
                query = String.format("DELETE FROM COMMENTS WHERE USERID = \"%s\" AND POSTID = \"%s\"", userID, postID);

                statement.execute(query);

                return 0;
            }
            else
                return -2;
        }
        else
            return -1;
    }

    public static int checkAccount(String postID) throws SQLException {
        String query = String.format("SELECT POSTS.USERID, USERS.USERTYPE FROM POSTS INNER JOIN USERS" +
                " ON POSTS.USERID = USERS.ID WHERE POSTID = \"%s\"", postID);

        ResultSet resultSet = statement.executeQuery(query);

        resultSet.next();
        if (resultSet.getString(2).equalsIgnoreCase("B"))
            return 0;
        else
            return -1;
     }

    public static int checkFollowedAccount(String followerAccount, String followedAccount) throws SQLException {
        String query = String.format("SELECT FOLLOWERID FROM RELATIONS WHERE FOLLOWERID = \"%s\" AND FOLLOWEDID = \"%s\"", followerAccount, followedAccount);

        ResultSet resultSet = statement.executeQuery(query);

        if (resultSet.next())
            return 0;
        else
            return -1;
    }

    public static void showAccountStats(String userID) throws SQLException {
        System.out.println("Showing stats for account " + userID);

        String query = String.format("SELECT FOLLOWERID FROM RELATIONS WHERE FOLLOWEDID = \"%s\"", userID);
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.last();
        int followerCount = resultSet.getRow();
        query = String.format("SELECT FOLLOWEDID FROM RELATIONS WHERE FOLLOWERID = \"%s\"", userID);
        resultSet = statement.executeQuery(query);
        resultSet.last();
        int followingCount = resultSet.getRow();

        System.out.print("Followers Count: " + followerCount + "\tFollowings Count: " + followingCount);

        query = String.format("SELECT POSTID FROM POSTS WHERE USERID = \"%s\"", userID);
        resultSet = statement.executeQuery(query);
        resultSet.last();
        int postCount = resultSet.getRow();

        System.out.println("\tPosts Count: " + postCount);

        query = String.format("SELECT LIKES.DATELIKED FROM LIKES INNER JOIN POSTS USING (POSTID) WHERE POSTS.USERID = \"%s\"", userID);
        resultSet = statement.executeQuery(query);

        if (resultSet.next()) {
            System.out.println("Showing like stats: ");

            int likeNum = 1;
            Date previousLike = resultSet.getDate(1);

            while (resultSet.next()) {
                if (resultSet.getDate(1).equals(previousLike)) {
                    likeNum++;
                }
                else {
                    System.out.println("Likes at date " + previousLike.toString() + " : " + likeNum);
                    likeNum = 1;
                    previousLike = resultSet.getDate(1);
                }
            }

            System.out.println("Likes at date " + previousLike.toString() + " : " + likeNum);
        }

        query = String.format("SELECT COMMENTS.DATECOMMENTED FROM COMMENTS INNER JOIN POSTS USING (POSTID) WHERE POSTS.USERID = \"%s\"", userID);
        resultSet = statement.executeQuery(query);

        if (resultSet.next()) {
            System.out.println("Showing comment stats: ");

            int commentNum = 1;
            Date previousComment = resultSet.getDate(1);

            while (resultSet.next()) {
                if (resultSet.getDate(1).equals(previousComment)) {
                    commentNum++;
                }
                else {
                    System.out.println("Comments at date " + previousComment.toString() + " : " + commentNum);
                    commentNum = 1;
                    previousComment = resultSet.getDate(1);
                }
            }

            System.out.println("Comments at date " + previousComment.toString() + " : " + commentNum);
        }

        query = String.format("SELECT DATEVISITED FROM VISITEDDATES WHERE VISITEDID = \"%s\"", userID);
        resultSet = statement.executeQuery(query);

        if (resultSet.next()) {
            System.out.println("Showing visit stats: ");

            int commentNum = 1;
            Date previousComment = resultSet.getDate(1);

            while (resultSet.next()) {
                if (resultSet.getDate(1).equals(previousComment)) {
                    commentNum++;
                }
                else {
                    System.out.println("Visits at date " + previousComment.toString() + " : " + commentNum);
                    commentNum = 1;
                    previousComment = resultSet.getDate(1);
                }
            }

            System.out.println("Visits at date " + previousComment.toString() + " : " + commentNum);
        }
    }

    public static void visitPage(String userID, String visitorID) throws SQLException {
        String query = String.format("SELECT USERNAME, USERTYPE, DATECREATED FROM USERS WHERE ID = \"%s\"", userID);
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();

        System.out.println("Visiting the page with ID: " + userID);

        boolean accountType = (resultSet.getString(2).equalsIgnoreCase("B"));

        if (accountType)
            System.out.println(resultSet.getString(1) + "\tUser type: Business\tActive since: " + resultSet.getDate(3).toString());
        else
            System.out.println(resultSet.getString(1) + "\tUser type: Personal\tActive since: " + resultSet.getDate(3).toString());

        query = String.format("SELECT FOLLOWERID FROM RELATIONS WHERE FOLLOWEDID = \"%s\"", userID);
        resultSet = statement.executeQuery(query);
        resultSet.last();
        int followerCount = resultSet.getRow();
        query = String.format("SELECT FOLLOWEDID FROM RELATIONS WHERE FOLLOWERID = \"%s\"", userID);
        resultSet = statement.executeQuery(query);
        resultSet.last();
        int followingCount = resultSet.getRow();

        System.out.print("Followers Count: " + followerCount + "\tFollowings Count: " + followingCount);

        query = String.format("SELECT POSTID, CONTENT, LIKENUM, COMMENTNUM, DATEMODIFIED FROM POSTS WHERE USERID = \"%s\"", userID);
        resultSet = statement.executeQuery(query);
        resultSet.last();
        int postCount = resultSet.getRow();

        System.out.println("\tPosts Count: " + postCount);

        resultSet.beforeFirst();


        ArrayList<String> posts = new ArrayList<>();
        while (resultSet.next()) {
            posts.add(resultSet.getString(1));
            System.out.println("-----------------------------------");
            if (accountType) {
                System.out.println("Ad " + resultSet.getString(1) + ": ");
                System.out.println(resultSet.getString(2));
                System.out.println("Date posted: " + resultSet.getTimestamp(5).toString());
                System.out.println("Likes count: " + resultSet.getInt(3) + "\tComments count: " + resultSet.getInt(4));
            }
            else {
                System.out.println(resultSet.getString(1) + ": ");
                System.out.println(resultSet.getString(2));
                System.out.println("Date posted: " + resultSet.getTimestamp(5).toString());
                System.out.println("Likes count: " + resultSet.getInt(3) + "\tComments count: " + resultSet.getInt(4));
            }
            System.out.println("-----------------------------------");
        }

        if (accountType) {
            //Should change in phase 2
            //--------------------------------------------------
            if (!visitorID.equalsIgnoreCase(userID)) {
                for (String post : posts) {
                    query = String.format("INSERT INTO ADVERTISEMENTS VALUES (\"%s\", \"%s\", \"%s\", 0)", post, userID, visitorID);
                    statement.execute(query);
                }
            }
            //--------------------------------------------------
        }

        if (!visitorID.equalsIgnoreCase(userID)) {
            query = String.format("INSERT INTO VISITEDDATES VALUES (\"%s\", \"%s\", \"%s\")", userID, visitorID, LocalDate.now().toString());
            statement.execute(query);
        }
    }

    public static void showPostStats(String postID) throws SQLException {
        String query = String.format("SELECT POSTID, DATEMODIFIED, LIKENUM, COMMENTNUM FROM POSTS WHERE POSTID = \"%s\"", postID);

        ResultSet resultSet = statement.executeQuery(query);

        resultSet.next();

         System.out.println("Showing stats for Post: ");
         System.out.println(resultSet.getString(1) + "\t" + resultSet.getDate(2).toString() + "\t:\tLikes: " + resultSet.getString(3) + "\tComments: " + resultSet.getString(4));

         query = String.format("SELECT DATELIKED FROM LIKES WHERE POSTID = \"%s\" ORDER BY DATELIKED DESC", postID);

         resultSet = statement.executeQuery(query);

         if (resultSet.next()) {
             System.out.println("Showing like stats: ");

             int likeNum = 1;
             Date previousLike = resultSet.getDate(1);

            while (resultSet.next()) {
                if (resultSet.getDate(1).equals(previousLike)) {
                    likeNum++;
                }
                else {
                    System.out.println("Likes at date " + previousLike.toString() + " : " + likeNum);
                    likeNum = 1;
                    previousLike = resultSet.getDate(1);
                }
            }

            System.out.println("Likes at date " + previousLike.toString() + " : " + likeNum);
         }

         query = String.format("SELECT DATECOMMENTED FROM COMMENTS WHERE POSTID = \"%s\" ORDER BY DATECOMMENTED DESC", postID);

         resultSet = statement.executeQuery(query);

         if (resultSet.next()) {
             System.out.println("Showing comment stats: ");

             int commentNum = 1;
             Date previousLike = resultSet.getDate(1);

             while (resultSet.next()) {
                 if (resultSet.getDate(1).equals(previousLike)) {
                     commentNum++;
                 }
                 else {
                     System.out.println("Comments at date " + previousLike.toString() + " : " + commentNum);
                     commentNum = 1;
                     previousLike = resultSet.getDate(1);
                 }
             }

             System.out.println("Comments at date " + previousLike.toString() + " : " + commentNum);
         }
    }

    public static void suggestUsers(String userID) throws SQLException {
        String query = String.format("SELECT USERS.ID FROM USERS" +
                " INNER JOIN RELATIONS ON USERS.ID = RELATIONS.FOLLOWEDID" +
                " WHERE RELATIONS.FOLLOWERID = \"%s\"", userID);
        ResultSet resultSet = statement.executeQuery(query);

        ArrayList<String> userFriends = new ArrayList<>();
        while (resultSet.next()) {
            userFriends.add(resultSet.getString(1));
        }

        ArrayList<FriendPoint> userNotFriends = new ArrayList<>();

        for (String userFriend : userFriends) {
            query = String.format("SELECT USERS.ID, USERS.USERNAME FROM USERS" +
                    " INNER JOIN RELATIONS ON USERS.ID = RELATIONS.FOLLOWEDID" +
                    " WHERE RELATIONS.FOLLOWERID = \"%s\"", userFriend);

            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String tempID = resultSet.getString(1);
                String tempUsername = resultSet.getString(2);

                if (!userFriend.contains(tempID)) {
                    int index = FriendPoint.getIndex(userNotFriends, tempID);
                    if (index == -1 ) {
                        if (!tempID.equalsIgnoreCase(userID))
                            userNotFriends.add(new FriendPoint(tempID, tempUsername, 1));
                    }
                    else {
                        userNotFriends.get(index).incrementPoint();
                    }
                }
            }
        }

        Collections.sort(userNotFriends, new Comparator<FriendPoint>() {
            @Override
            public int compare(FriendPoint o1, FriendPoint o2) {
                if (o1.getPoint() > o2.getPoint())
                    return -1;
                else if (o1.getPoint() == o2.getPoint())
                    return 0;
                else
                    return 1;
            }
        });

        System.out.println("Suggesting users you might want to follow: ");
        for (FriendPoint userNotFriend : userNotFriends) {
            System.out.println(userNotFriend.getID() + "\t" + userNotFriend.getUsername() + "\tCommon friends: " + userNotFriend.getPoint());
        }
    }

    public static void suggestAds(String userID) throws SQLException {
        String query = String.format("SELECT POSTID, LIKED FROM ADVERTISEMENTS WHERE VISITORID = \"%s\"", userID);
        ResultSet resultSet = statement.executeQuery(query);

        ArrayList<AdvertisementPoint> visitedAds = new ArrayList<>();
        while (resultSet.next()) {
            visitedAds.add(new AdvertisementPoint(resultSet.getString(1), resultSet.getBoolean(2)));
        }

        for (AdvertisementPoint visitedAd : visitedAds) {
            if (visitedAd.getLiked())
                query = String.format("SELECT ADVERTISEMENTS.VISITORID FROM ADVERTISEMENTS INNER JOIN RELATIONS" +
                        " ON ADVERTISEMENTS.VISITORID = RELATIONS.FOLLOWEDID AND RELATIONS.FOLLOWERID = \"%s\"" +
                        " WHERE POSTID = \"%s\" AND LIKED = 1", userID ,visitedAd.getPostID());
            else
                query = String.format("SELECT ADVERTISEMENTS.VISITORID FROM ADVERTISEMENTS INNER JOIN RELATIONS" +
                        " ON ADVERTISEMENTS.VISITORID = RELATIONS.FOLLOWEDID AND RELATIONS.FOLLOWERID = \"%s\"" +
                        " WHERE POSTID = \"%s\" AND LIKED = 0", userID ,visitedAd.getPostID());

            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                visitedAd.addSameSidedUser(resultSet.getString(1));
            }
        }

        query = String.format("SELECT POSTS.POSTID, POSTS.USERID, POSTS.CONTENT FROM POSTS INNER JOIN USERS ON USERS.ID = POSTS.USERID WHERE USERS.USERTYPE = \"B\"");
        resultSet = statement.executeQuery(query);
        ArrayList<NotLikedAd> notVisitedAds = new ArrayList<>();
        while (resultSet.next()) {
            if (!NotLikedAd.containsPost(visitedAds, resultSet.getString(1))) {
                notVisitedAds.add(new NotLikedAd(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3)));
            }
        }

        for (NotLikedAd notVisitedAd : notVisitedAds) {
            for (AdvertisementPoint visitedAd : visitedAds) {
                for (String sameSidedUserID : visitedAd.getSameSidedUserIDs()) {
                    if (visitedAd.getLiked())
                        query = String.format("SELECT POSTID FROM ADVERTISEMENTS WHERE POSTID = \"%s\" AND VISITORID = \"%s\" AND LIKED = 1", notVisitedAd.getPostID(), sameSidedUserID);
                    else
                        query = String.format("SELECT POSTID FROM ADVERTISEMENTS WHERE POSTID = \"%s\" AND VISITORID = \"%s\" AND LIKED = 0", notVisitedAd.getPostID(), sameSidedUserID);

                    resultSet = statement.executeQuery(query);

                    if (resultSet.next()) {
                        notVisitedAd.incrementPoint();
                    }
                }
            }
        }

        Collections.sort(notVisitedAds, new Comparator<NotLikedAd>() {
            @Override
            public int compare(NotLikedAd o1, NotLikedAd o2) {
                if (o1.getPoint() > o2.getPoint())
                    return -1;
                else if (o1.getPoint() == o2.getPoint())
                    return 0;
                else
                    return 1;
            }
        });

        System.out.println("Suggesting ads you might want to take a look upon: ");
        for (NotLikedAd notVisitedAd : notVisitedAds) {
            System.out.println("-------------------------------------------");
            System.out.println("Ad " + notVisitedAd.getPostID() + "\tBy " + notVisitedAd.getUserID() + "\tSimilar reaction from " + notVisitedAd.getPoint() + " friends");
            System.out.println(notVisitedAd.getContent());
            System.out.println("-------------------------------------------");
        }
    }

    public static boolean checkPassword(String username, String password) throws SQLException {
        String query = String.format("SELECT PASSWORD FROM USERS WHERE USERNAME = \"%s\"", username);

        ResultSet resultSet = statement.executeQuery(query);

        resultSet.next();

        if (resultSet.getString(1).equals(password))
            return true;
        else
            return false;
    }
    //-----------------------------------------

    //Interact directly with DirectMessage class!!
    //-----------------------------------------
    public static int sendDM(String messageID,String senderID, String receiverID, String content) {
        String query = String.format("SELECT * FROM BLOCKS WHERE BLOCKERID = \"%s\" && BLOCKEDID = \"%s\"", receiverID, senderID);

        try {
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next())
                return -1;
            else {
                query = String.format("INSERT INTO MESSAGES VALUES (\"%s\", \"%s\", \"%s\", NULL, \"%s\", 0, 0, NULL, \"D\", \"%s\")"
                        , messageID, senderID, receiverID, content, LocalDateTime.now().toString());

                statement.execute(query);
                return 0;
            }
        }
        catch (SQLException e) {
//            System.out.println(e.getMessage());
            e.printStackTrace();
            return -2;
        }
    }

    public static int validatePossession(String userID, String messageID) {
        String query = String.format("SELECT USER2 FROM MESSAGES WHERE MESSAGEID = \"%s\"", messageID);

        try {
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                if (resultSet.getString(1).equalsIgnoreCase(userID))
                    return 0;
                else
                    return -2;
            }
            else
                return -1;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return -3;
        }
    }
    //-----------------------------------------

    //Interact directly with Group class!!
    // -----------------------------------------
    public static String getGroupID(String groupName) throws SQLException {
        String query = String.format("SELECT GROUPID FROM DIRECTGROUPS WHERE GROUPNAME = \"%s\"", groupName);
        
        ResultSet resultSet = statement.executeQuery(query);

        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        else
            return "";
    }

    public static String getGroupIDFromMessage(String messageID) {
        String query = String.format("SELECT GROUPID FROM MESSAGES WHERE MESSAGEID = \"%s\"", messageID);

        try {
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next())
                return resultSet.getString(1);
            else
                return "";
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return "NaN";
        }
    }

    public static int checkInGroup(String userID, String groupID) {
        String query = String.format("SELECT USERID FROM GROUPSPARTICIPANTS WHERE GROUPID = \"%s\" AND USERID = \"%s\"", groupID, userID);

        try {
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next())
                return 0;
            else
                return -1;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return -2;
        }
    }

    public static ArrayList<String> getParticipants(String groupID) throws SQLException {
        String query = String.format("SELECT USERID FROM GROUPSPARTICIPANTS WHERE GROUPID = \"%s\"", groupID);

        ResultSet resultSet = statement.executeQuery(query);

        ArrayList<String> result = new ArrayList<>();

        while (resultSet.next()) {
            result.add(resultSet.getString(1));
        }

        return result;
    }
    
    public static int checkAdmin(String groupID, String userID) throws SQLException {
        String query = String.format("SELECT USERID FROM GROUPSADMINS WHERE GROUPID = \"%s\"", groupID);
        
        ResultSet resultSet = statement.executeQuery(query);

        boolean found = false;
        while (resultSet.next()) {
            if (resultSet.getString(1).equalsIgnoreCase(userID)) {
                found = true;
                break;
            }
        }

        if (found)
            return 0;
        else
            return -1;
    }

    public static int addMember(String groupID, String userID) throws SQLException {
        String query = String.format("SELECT USERNAME FROM USERS WHERE ID = \"%s\"", userID);

        ResultSet resultSet = statement.executeQuery(query);

        if (resultSet.next()) {
            query = String.format("INSERT INTO GROUPSPARTICIPANTS VALUES (\"%s\",0,\"%s\")", userID, groupID);

            statement.execute(query);

            query = String.format("SELECT MESSAGEID FROM MESSAGES WHERE GROUPID = \"%s\"", groupID);

            resultSet = statement.executeQuery(query);

            ArrayList<String> messages = new ArrayList<>();

            while (resultSet.next()) {
                messages.add(resultSet.getString(1));
            }

            for (String message : messages) {
                query = String.format("INSERT INTO INBOX VALUES (\"%s\", 0, \"%s\")", message, userID);

                statement.execute(query);
            }

            return 0;
        }
        else
            return -1;
    }

    public static int removeMember(String groupID, String userID) throws SQLException {
        String query = String.format("SELECT USERNAME FROM USERS WHERE ID = \"%s\"", userID);

        ResultSet resultSet = statement.executeQuery(query);

        if (resultSet.next()) {
            query = String.format("DELETE FROM GROUPSPARTICIPANTS WHERE USERID = \"%s\" AND GROUPID = \"%s\"", userID, groupID);

            statement.execute(query);

            query = String.format("DELETE FROM GROUPSADMINS WHERE USERID = \"%s\" AND GROUPID = \"%s\"", userID, groupID);

            statement.execute(query);

            query = String.format("SELECT MESSAGEID FROM MESSAGES WHERE GROUPID = \"%s\"", groupID);

            resultSet = statement.executeQuery(query);

            ArrayList<String> messages = new ArrayList<>();

            while (resultSet.next()) {
                messages.add(resultSet.getString(1));
            }

            for (String message : messages) {
                query = String.format("DELETE FROM INBOX WHERE MESSAGEID = \"%s\" AND USERID = \"%s\"", message, userID);

                statement.execute(query);
            }

            return 0;
        }
        else
            return -1;
    }

    public static int banMember(String groupID, String userID) throws SQLException {
        String query = String.format("SELECT USERNAME FROM USERS WHERE ID = \"%s\"",userID);

        ResultSet resultSet = statement.executeQuery(query);

        if (resultSet.next()) {
            query = String.format("UPDATE GROUPSPARTICIPANTS SET BANNED = 1 WHERE USERID = \"%s\" AND GROUPID = \"%s\"", userID, groupID);

            statement.execute(query);

            return 0;
        }
        else
            return -1;
    }

    public static int unbanMember(String groupID, String userID) throws SQLException {
        String query = String.format("SELECT USERNAME FROM USERS WHERE ID = \"%s\"",userID);

        ResultSet resultSet = statement.executeQuery(query);

        if (resultSet.next()) {
            query = String.format("UPDATE GROUPSPARTICIPANTS SET BANNED = 0 WHERE USERID = \"%s\" AND GROUPID = \"%s\"", userID, groupID);

            statement.execute(query);

            return 0;
        }
        else
            return -1;
    }

    public static void changeGroupName(String lastName, String newName) throws SQLException {
        String query = String.format("UPDATE DIRECTGROUPS SET GROUPNAME = \"%s\" WHERE GROUPNAME = \"%s\"", newName, lastName);

        statement.execute(query);
    }

    public static int addAdmin(String groupID, String userID) throws SQLException {
        String query = String.format("SELECT USERNAME FROM USERS WHERE ID = \"%s\"",userID);

        ResultSet resultSet = statement.executeQuery(query);

        if (resultSet.next()) {
            query = String.format("INSERT INTO GROUPSADMINS VALUES (\"%s\",\"%s\")", groupID, userID);

            statement.execute(query);

            return 0;
        }
        else
            return -1;
    }

    public static int removeAdmin(String groupID, String userID) throws SQLException {
        String query = String.format("SELECT USERNAME FROM USERS WHERE ID = \"%s\"",userID);

        ResultSet resultSet = statement.executeQuery(query);

        if (resultSet.next()) {
            query = String.format("DELETE FROM GROUPSADMINS WHERE GROUPID = \"%s\" AND USERID = \"%s\"", groupID, userID);

            statement.execute(query);

            return 0;
        }
        else
            return -1;
    }

    public static int checkGroupOwner(String groupID, String userID) throws SQLException {
        String query = String.format("SELECT OWNERID FROM DIRECTGROUPS WHERE GROUPID = \"%s\"", groupID);

        ResultSet resultSet = statement.executeQuery(query);

        if (resultSet.next()) {
            if (resultSet.getString(1).equalsIgnoreCase(userID))
                return 0;
            else
                return -2;
        }
        else
            return -1;
    }
    // -----------------------------------------

    //Interact directly with Post class!!
    // -----------------------------------------
    public static int checkPostID(String postID) throws SQLException {
        String query = String.format("SELECT POSTID FROM POSTS WHERE POSTID = \"%s\"", postID);

        ResultSet resultSet = statement.executeQuery(query);

        if (resultSet.next())
            return 0;
        else
            return -1;
    }

    public static void publishPost(String userID, String postID, String content) throws SQLException{
        String query = String.format("INSERT INTO POSTS VALUES (\"%s\", \"%s\", 0, 0, \"%s\", NULL, \"%s\")"
                , postID, userID, content, LocalDateTime.now().toString());

        statement.execute(query);
    }

    public static int checkMyPost(String userID, String postID) throws SQLException {
        String query = String.format("SELECT CONTENT FROM POSTS WHERE USERID = \"%s\" AND POSTID = \"%s\"", userID, postID);

        ResultSet resultSet = statement.executeQuery(query);

        if (resultSet.next()) {
            return 0;
        }
        else
            return -1;
    }

    public static int checkFollowingPost(String userID, String postID) throws SQLException {
        String query = String.format("SELECT POSTS.POSTID FROM POSTS" +
                " INNER JOIN RELATIONS ON RELATIONS.FOLLOWEDID = POSTS.USERID" +
                " WHERE RELATIONS.FOLLOWERID = \"%s\" AND POSTS.POSTID = \"%s\"", userID, postID);

        ResultSet resultSet = statement.executeQuery(query);

        if (resultSet.next())
            return 0;
        else
            return -1;
    }

    public static void deletePost(String postID) throws SQLException {
        String query = String.format("DELETE FROM POSTS WHERE POSTID = \"%s\"", postID);

        statement.execute(query);
    }

    public static void showComments(String postID) throws SQLException {
        String query = String.format("SELECT COMMENTS.USERID, USERS.USERNAME, COMMENTS.COMMENT FROM COMMENTS" +
                " INNER JOIN USERS ON COMMENTS.USERID = USERS.ID WHERE POSTID = \"%s\"", postID);

        ResultSet resultSet = statement.executeQuery(query);

        System.out.println("Showing comments for posts: ");

        while (resultSet.next()) {
            System.out.println(resultSet.getString(1) + "\t" + resultSet.getString(2) + " : " + resultSet.getString(3));
        }
    }

    public static ArrayList<Post> getuserPosts(String posterID, String visitorID) throws SQLException {
        String query = String.format("SELECT POSTID, USERID, CONTENT, FILEPATH, LIKENUM, COMMENTNUM, DATEMODIFIED FROM POSTS" +
                " WHERE USERID = \"%s\"", username);

        ResultSet resultSet = statement.executeQuery(query);

        ArrayList<Post> posts = new ArrayList<>();

        while (resultSet.next()) {
            String postID = resultSet.getString(1);
            String content = resultSet.getString(3);
            int likeNum = resultSet.getInt(5);
            int commentNum = resultSet.getInt(6);
            LocalDateTime dateModified = resultSet.getTimestamp(7).toLocalDateTime();

            if (resultSet.getString(4) != null) {
                String filePath = resultSet.getString(4);

                posts.add(new Post(postID, content, posterID, filePath, likeNum, commentNum, dateModified));
            }
            else {
                posts.add(new Post(postID, content, posterID, likeNum, commentNum, dateModified));
            }
        }

        for (Post post : posts) {
            query = String.format("INSERT INTO VISITEDDATES VALUES (\"%s\", \"%s\", \"%s\")",
                    posterID, visitorID, LocalDate.now().toString());

            statement.execute(query);

            query = String.format("INSERT INTO ADVERTISEMENTS VALUES (\"%s\", \"%s\", \"%s\", 0)", post.getPostID(), posterID, visitorID);

            statement.execute(query);
        }

        return posts;
    }
    // -----------------------------------------

    //Interact directly with Message class!!
    // -----------------------------------------
    public static String getSenderID(String messsageID) {
        String query = String.format("SELECT USER1 FROM MESSAGES WHERE MESSAGEID = \"%s\"", messsageID);

        try {
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                return resultSet.getString(1);
            }
            else
                return "";
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return "NaN";
        }
    }

    public static int setReply(String formerMessageID, String newMessageID) {
        String query = String.format("UPDATE MESSAGES SET REPLIEDMESSAGEID = \"%s\" WHERE MESSAGEID = \"%s\"", formerMessageID, newMessageID);

        try {
            statement.execute(query);

            query = String.format("SELECT CONTENT FROM MESSAGES WHERE MESSAGEID = \"%s\"", formerMessageID);

            ResultSet resultSet = statement.executeQuery(query);

            resultSet.next();

            String content = resultSet.getString(1).substring(0, 7);

            query = String.format("INSERT INTO REPLYMESSAGES VALUES (\"%s\", \"%s\")", formerMessageID, content);

            try {
                statement.execute(query);
            }
            catch (SQLException exception) {

            }

            return 0;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());

            return -1;
        }
    }

    public static String getContent(String messageID)  {
        String query = String.format("SELECT CONTENT FROM MESSAGES WHERE MESSAGEID = \"%s\"", messageID);

        try {
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                return resultSet.getString(1);
            }
            else
                return "";
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return "NaN";
        }
    }

    public static void setForward(String messageID) {
        String query = String.format("UPDATE MESSAGES SET FORWARDED = 1 WHERE MESSAGEID = \"%s\"", messageID);

        try {
            statement.execute(query);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static int editMessage(String messageID, String senderID, String content) {
        String query = String.format("SELECT MESSAGEID, FORWARDED FROM MESSAGES WHERE USER1 = \"%s\" ORDER BY MESSAGEID DESC LIMIT 10", senderID);

        try {
            ResultSet resultSet = statement.executeQuery(query);

            boolean found = false;
            while (resultSet.next()) {
                if (messageID.equals(resultSet.getString(1)) && !resultSet.getBoolean(2)) {
                    found = true;
                    break;
                }
            }

            if (found) {
                query = String.format("UPDATE MESSAGES SET CONTENT = \"%s\", MODIFIED = 1 WHERE MESSAGEID = \"%s\"", content, messageID);

                statement.execute(query);

                return 0;
            }
            else
                return -1;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return -2;
        }
    }

    public static int deleteMessage(String messageID) {
        String query = String.format("DELETE FROM MESSAGES WHERE MESSAGEID = \"%s\"", messageID);

        try {
            statement.execute(query);
            return 0;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    public static void addToInbox(String userID, String messageID) throws SQLException, UnauthorisedEditException {
        String query = String.format("SELECT USERID FROM INBOX WHERE USERID = \"%s\" AND MESSAGEID = \"%s\"", userID, messageID);

        ResultSet resultSet = statement.executeQuery(query);

        if (resultSet.next())
            throw new UnauthorisedEditException("Message Already added to inbox...");

        query = String.format("INSERT INTO INBOX VALUES (\"%s\", 0, \"%s\")", messageID, userID);

        statement.execute(query);
    }

    public static void showMessage(String userID, String messageID) throws SQLException {
        String query = String.format("SELECT MESSAGES.CONTENT FROM MESSAGES INNER JOIN INBOX USING (MESSAGEID) WHERE" +
                " INBOX.USERID = \"%s\" AND INBOX.MESSAGEID = \"%s\"", userID, messageID);

        ResultSet resultSet = statement.executeQuery(query);

        if (resultSet.next()) {
            System.out.println("Complete content is: ");
            System.out.println(resultSet.getString(1));
        }
    }
    // -----------------------------------------

    //Interact directly with GroupMessage class!!
    // -----------------------------------------
    public static int sendGM(String messageID, String senderID, String groupID, String content) {
        String query = String.format("SELECT BANNED FROM GROUPSPARTICIPANTS WHERE GROUPID = \"%s\" && USERID = \"%s\"", groupID, senderID);

        try {
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                if (resultSet.getBoolean(1) == true)
                    return -2;
                else {
                    query = String.format("INSERT INTO MESSAGES VALUES (\"%s\", \"%s\", NULL, \"%s\", \"%s\", 0, 0, NULL, \"G\", \"%s\")"
                            , messageID, senderID, groupID, content, LocalDateTime.now().toString());

                    statement.execute(query);
                    return 0;
                }
            }
            else
                return -1;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return -3;
        }
    }

    public static int validateIsMyMessage(String userID, String messageID) {
        String query = String.format("SELECT GROUPSPARTICIPANTS.USERID FROM MESSAGES INNER JOIN GROUPSPARTICIPANTS USING (GROUPID) WHERE MESSAGES.MESSAGEID = \"%s\"", messageID);

        try {
            ResultSet resultSet = statement.executeQuery(query);

            boolean found = false;
            while (resultSet.next()) {
                if (resultSet.getString(1).equalsIgnoreCase(userID)) {
                    found = true;
                    break;
                }
            }

            if (found) {
                return 0;
            }
            else
                return -1;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return -2;
        }
    }
    // -----------------------------------------


    //------------------------------------------
    //Interact directly with Like class!!
    public static int determineLike(String userID, String postID) throws SQLException {
        String query = String.format("SELECT * FROM LIKES WHERE POSTID = \"%s\" AND USERID = \"%s\"", postID, userID);

        ResultSet resultSet = statement.executeQuery(query);

        if (resultSet.next())
            return 0;
        else
            return -1;
    }
    //------------------------------------------


    //Interact directly with Comment class!!
    //------------------------------------------
    public static int determineComment(String userID, String postID) throws SQLException {
        String query = String.format("SELECT * FROM COMMENTS WHERE POSTID = \"%s\" AND USERID = \"%s\"", postID, userID);

        ResultSet resultSet = statement.executeQuery(query);

        if (resultSet.next())
            return 0;
        else
            return -1;
    }
    //------------------------------------------


    //Interact directly with Chat class!!
    //------------------------------------------
    public static ArrayList<Chat> getChats(String userID) throws SQLException {
        String query = String.format("SELECT USERS.USERNAME, USERS.PROFILEPHOTO, INBOX.SEEN, MESSAGES.TIMESENT FROM MESSAGES" +
                " INNER JOIN USERS ON USERS.ID = MESSAGES.USER1" +
                " INNER JOIN INBOX ON INBOX.MESSAGEID = MESSAGES.MESSAGEID AND INBOX.USERID = \"%s\"" +
                " WHERE MESSAGES.USER2 = \"%s\"", userID, userID);

        ResultSet resultSet = statement.executeQuery(query);

        ArrayList<Chat> chats = new ArrayList<>();

        while (resultSet.next()) {
            String senderID = resultSet.getString(1);
            boolean isSeen = resultSet.getBoolean(3);
            LocalDateTime timeSent = resultSet.getTimestamp(4).toLocalDateTime();

            if (!Chat.containsChat(chats, senderID)) {
                if (resultSet.getString(2) != null) {
                    String photoPath = resultSet.getString(2);

                    if (isSeen)
                        chats.add(new Chat(senderID, photoPath, false, 0, timeSent));
                    else
                        chats.add(new Chat(senderID, photoPath, false, 1, timeSent));
                }
                else
                    if (isSeen)
                    chats.add(new Chat(senderID, false, 0, timeSent));
                    else
                    chats.add(new Chat(senderID, false, 1, timeSent));
            }
            else {
                Chat thisChat = Chat.getChat(chats, senderID);

                if (timeSent.compareTo(thisChat.getSentTime()) == 1)
                    thisChat.setSentTime(timeSent);

                if (!isSeen)
                    Chat.getChat(chats, senderID).incrementNewMessage();
            }
        }

        query = String.format("SELECT USERS.USERNAME, USERS.PROFILEPHOTO, MESSAGES.TIMESENT FROM MESSAGES" +
                " INNER JOIN USERS ON USERS.ID = MESSAGES.USER2" +
                " WHERE MESSAGES.USER1 = \"%s\"", userID);

        resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            String receiverID = resultSet.getString(1);
            LocalDateTime timeSent = resultSet.getTimestamp(3).toLocalDateTime();

            if (!Chat.containsChat(chats, receiverID)) {
                if (resultSet.getString(2) != null) {
                    String photoPath = resultSet.getString(2);

                    chats.add(new Chat(receiverID, photoPath, false, -1, timeSent));
                }
                else
                    chats.add(new Chat(receiverID, false, -1, timeSent));
            }
            else {
                Chat thisChat = Chat.getChat(chats, receiverID);

                if (timeSent.compareTo(thisChat.getSentTime()) > 0)
                    thisChat.setSentTime(timeSent);
            }
        }

        query = String.format("SELECT DIRECTGROUPS.GROUPNAME, DIRECTGROUPS.GROUPPHOTO, MESSAGES.TIMESENT, INBOX.SEEN FROM DIRECTGROUPS" +
                " INNER JOIN GROUPSPARTICIPANTS USING (GROUPID)" +
                " LEFT JOIN MESSAGES USING (GROUPID) LEFT JOIN INBOX ON MESSAGES.MESSAGEID = INBOX.MESSAGEID AND INBOX.USERID = \"%s\"" +
                " WHERE GROUPSPARTICIPANTS.USERID = \"%s\"", userID ,userID);

        resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            String groupID = resultSet.getString(1);

            if (!Chat.containsChat(chats, groupID))
                chats.add(new Chat(groupID));

            Chat thisChat = Chat.getChat(chats, groupID);

            String groupPhoto = resultSet.getString(2);
            if (groupPhoto != null)
                Chat.setPhoto(chats, groupID, groupPhoto);

            if (resultSet.getString(3) != null) {
                LocalDateTime timeSent = resultSet.getTimestamp(3).toLocalDateTime();

                if (thisChat.getSentTime() == null)
                    thisChat.setSentTime(timeSent);
                else {
                    if (timeSent.compareTo(thisChat.getSentTime()) == 1)
                        thisChat.setSentTime(timeSent);
                }
            }

            if (resultSet.getString(4) != null) {
                if (!resultSet.getBoolean(4)) {
                    Chat.incrementNewMessage(chats, groupID);
                }
            }
        }

        return chats;
    }
    //------------------------------------------

    public static void finalizeForOnce(){
        try {
            con.close();
        }
        catch (SQLException e) {
            System.out.println("Failed to close connection with SQL Database. Terminating the program anyway...");
            System.exit(-1);
        }
    }
}
