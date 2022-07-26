package Functionality;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

            System.out.printf("User %s created successfully...%n", username);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createBusinessUser(String userID, String username, String password, String firstName,
                                          String lastName, String email, String phoneNumber, boolean gender,
                                          boolean isPrivate, LocalDate birthday, int q1, String a1, int q2, String a2, int age) {
        String query = String.format("INSERT INTO USERS (ID, USERNAME, PASSWORD, FIRSTNAME, LASTNAME, EMAIL, PHONENUMBER," +
                        " PRIVACYMODE, BIRTHDAY, USERTYPE, DATECREATED, SECURITYQUESTION1," +
                        " SECURITYANSWER1, SECURITYQUESTION2, SECURITYANSWER2) VALUES" +
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
    //-----------------------------------------

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
