package Functionality;

import Exceptions.UnauthorisedEditException;
import ObjectClasses.*;

import javax.print.DocFlavor;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import javax.swing.table.TableRowSorter;
import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Queue;

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
            statement = con.createStatement();
        }
        catch (SQLException e) {
            System.out.println("Failed to connect with SQL Database...");
            System.exit(-1);
        }


        try {
            String query = String.format("SELECT ID FROM USERS WHERE USERTYPE = \"P\" ORDER BY ID DESC LIMIT 1");

            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                int size = resultSet.getString(1).charAt(3) - '0' + 1;

                for (int s = 0; s < size; s++) {
                    PersonalUser.assignID();
                }
            }

            query = String.format("SELECT ID FROM USERS WHERE USERTYPE = \"B\" ORDER BY ID DESC LIMIT 1");

            resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                int size = resultSet.getString(1).charAt(3) - '0' + 1;

                for (int s = 0; s < size; s++) {
                    BusinessUser.assignID();
                }
            }

            query = String.format("SELECT MESSAGEID FROM MESSAGES WHERE MESSAGETYPE = \"D\" ORDER BY MESSAGEID DESC LIMIT 1");

            resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                int size = resultSet.getString(1).charAt(3) - '0' + 1;

                for (int s = 0; s < size; s++) {
                    DirectMessage.assignID();
                }
            }

            query = String.format("SELECT MESSAGEID FROM MESSAGES WHERE MESSAGETYPE = \"G\" ORDER BY MESSAGEID DESC LIMIT 1");

            resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                int size = resultSet.getString(1).charAt(3) - '0' + 1;

                for (int s = 0; s < size; s++) {
                    GroupMessage.assignID();
                }
            }

            query = String.format("SELECT POSTID FROM POSTS ORDER BY POSTID DESC LIMIT 1");

            resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                int size = resultSet.getString(1).charAt(3) - '0' + 1;

                for (int s = 0; s < size; s++) {
                    Post.assignID();
                }
            }

            query = String.format("SELECT GROUPID FROM DIRECTGROUPS ORDER BY GROUPID DESC LIMIT 1");

            resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                int size = resultSet.getString(1).charAt(3) - '0' + 1;

                for (int s = 0; s < size; s++) {
                    GroupMessage.assignID();
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    public static void nothing() {
//        String query = "SELECT AGE FROM USERS";
//
//        try {
//            ResultSet resultSet = statement.executeQuery(query);
//
//            resultSet.next();
//
//            System.out.println(resultSet.getString(1));
//        }
//        catch (SQLException e) {
//
//        }
//    }

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

            System.out.printf("User %s created successfully...%n", username);
        }
        catch (SQLException e) {
//            System.out.println(e.getMessage());
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
            System.out.printf("User %s created successfully...%n", username);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
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

    public static int changeName(String username, String name) {
        String query = String.format("UPDATE USERS SET NAME = \"%s\" WHERE USERNAME = \"%s\"", name, username);

        try {
            statement.execute(query);
            return 0;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return -1;
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

    public static int changeAccountType(String username,boolean accountType) {
        String query;

        if (accountType)
            query = String.format("UPDATE USERS SET USERTYPE = \"B\" WHERE USERNAME = \"%s\"", username);
        else
            query = String.format("UPDATE USERS SET USERTYPE = \"P\" WHERE USERNAME = \"%s\"", username);

        try {
            statement.execute(query);
            return 0;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return -1;
        }
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

    public static int sendFollowRequest(String userID, String otherUserID) {
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

    public static void manageFollowRequests(String username) {
        String query = String.format("SELECT FOLLOWREQUESTS.FROMID, USERS.USERNAME FROM FOLLOWREQUESTS INNER JOIN " +
                "USERS ON FROMID = ID WHERE USERS.USERNAME = \"%s\"", username);

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

    public static void showMyGroups(String userID) {
        String query = String.format("SELECT GROUPSPARTICIPANTS.GROUPID, DIRECTGROUPS.GROUPNAME, DIRECTGROUPS.OWNERID FROM GROUPSPARTICIPANTS" +
                " INNER JOIN DIRECTGROUPS USING (GROUPID) WHERE GROUPSPARTICIPANTS.USERID = \"%s\"", userID);

        try {
            ResultSet resultSet = statement.executeQuery(query);

            System.out.println("Groups in which you participate: ");

            while (resultSet.next()) {
                System.out.println(resultSet.getString(1) + "\t" + resultSet.getString(2) + "\t" + resultSet.getString(3));
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
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
                query = String.format("INSERT INTO MESSAGES VALUES (\"%s\", \"%s\", \"%s\", NULL, \"%s\", 0, 0, NULL, \"D\")"
                        , messageID, senderID, receiverID, content);

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
    public static String getGroupID(String groupName) {
        String query = String.format("SELECT GROUPID FROM DIRECTGROUPS WHERE GROUPNAME = \"%s\"", groupName);

        try {
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                return resultSet.getString(1);
            }
            else
                return "";
        }
        catch (SQLException e ) {
            System.out.println(e.getMessage());
            return "NaN";
        }
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
        String query = String.format("SELECT USERID FROM GROUPSPARTICIPANTS WHERE GROUPID = \"%s\"", groupID);

        try {
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
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return -2;
        }
    }

    public static ArrayList<String> getParticipants(String groupID) throws SQLException {
        String query = String.format("SELECT USERID FROM GROUPSPARTICPANTS WHERE GROUPID = \"%s\"", groupID);

        ResultSet resultSet = statement.executeQuery(query);

        ArrayList<String> result = new ArrayList<>();

        while (resultSet.next()) {
            result.add(resultSet.getString(1));
        }

        return result;
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

            statement.execute(query);

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
            throw new UnauthorisedEditException("Message Alreadty added to inbox...");

        query = String.format("INSERT INTO INBOX VALUES (\"%s\", 0, \"%s\")", messageID, userID);

        statement.execute(query);
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
                    query = String.format("INSERT INTO MESSAGES VALUES (\"%s\", \"%s\", NULL, \"%s\", \"%s\", 0, 0, NULL, \"G\")"
                            , messageID, senderID, groupID, content);

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
