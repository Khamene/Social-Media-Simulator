package ObjectClasses;

import Exceptions.*;
import Functionality.SQLManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class Group {
    static int idCounter = 0;

    public static void createGroup(String groupName,String ownerID, boolean isPrivate) {
        String groupID = assignGroupID();

        //ADD SQL CODE HERE LATER ON
        //--------------------------

        //--------------------------
    }

    public static String getGroupID(String groupName) throws GroupDoesNotExistException {
        try {
            String groupID = SQLManager.getGroupID(groupName);

            if (groupID.equals(""))
                throw new GroupDoesNotExistException("No group exists with such name...");

            return groupID;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void changeGroupName(String lastGroupName,String groupName, String attemptorID) throws UserNotAdminException, GroupDoesNotExistException, NullPointerException {
        String groupID = Group.getGroupID(groupName);

        if (groupID == null)
            throw new NullPointerException("Failed due to SQL Exception...");

        try {
            int result = SQLManager.checkAdmin(groupID, attemptorID);

            if (result == -1)
                throw new UserNotAdminException("You are not an admin...");

            SQLManager.changeGroupName(lastGroupName, groupName);

            System.out.printf("Group name successfully changed to %s...%n", groupName);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getMemberNum(String groupName) throws GroupDoesNotExistException{
        //ADD SQL CODE HERE LATER ON
        //--------------------------

        //--------------------------

        return 0;
    }

    public static int getAdminNum(String groupName) throws GroupDoesNotExistException{
        //ADD SQL CODE HERE LATER ON
        //--------------------------

        //--------------------------

        return 0;
    }

    public static void determineAdmin(String groupID, String userID) {

    }

    public static void addMember(String memberID, String attemptorID, String groupName) throws UserNotAdminException, GroupDoesNotExistException, UserDoesNotExistException, NullPointerException {
        String groupID = Group.getGroupID(groupName);

        if (groupID == null)
            throw new NullPointerException("Failed due to SQL Exception...");

        try {
            int result = SQLManager.checkAdmin(groupID, attemptorID);

            if (result == -1)
                throw new UserNotAdminException("You are not an admin...");

            result = SQLManager.addMember(groupID, memberID);

            if (result == -1)
                throw new UserDoesNotExistException("No such user exists...");

            System.out.printf("User %s added successfully to group %s...%n", memberID, groupName);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeMember(String memberID, String attemptorID,String groupName) throws UserNotAdminException, GroupDoesNotExistException, UserDoesNotExistException, NullPointerException{
        String groupID = Group.getGroupID(groupName);

        if (groupID == null)
            throw new NullPointerException("Failed due to SQL Exception...");

        try {
            int result = SQLManager.checkAdmin(groupID, attemptorID);

            if (result == -1)
                throw new UserNotAdminException("You are not an admin...");

            result = SQLManager.removeMember(groupID, memberID);

            if (result == -1)
                throw new UserDoesNotExistException("No such user exists...");

            System.out.printf("User removed successfully from group %s...%n", groupName);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void banMember(String memberID, String attemptorID, String groupName) throws UserNotAdminException, GroupDoesNotExistException, UserDoesNotExistException, NullPointerException{
        String groupID = Group.getGroupID(groupName);

        if (groupID == null)
            throw new NullPointerException("Failed due to SQL Exception...");

        try {
            int result = SQLManager.checkAdmin(groupID, attemptorID);

            if (result == -1)
                throw new UserNotAdminException("You are not an admin...");

            result = SQLManager.banMember(groupID, memberID);

            if (result == -1)
                throw new UserDoesNotExistException("No such user exists...");

            System.out.printf("User %s successfully restricted...%n", memberID);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void unbanMember(String memberID, String attemptorID,String groupName) throws UserNotAdminException, GroupDoesNotExistException, UserDoesNotExistException, NullPointerException{
        String groupID = Group.getGroupID(groupName);

        if (groupID == null)
            throw new NullPointerException("Failed due to SQL Exception...%n");

        try {
            int result = SQLManager.checkAdmin(groupID, attemptorID);

            if (result == -1)
                throw new UserNotAdminException("You are not an admin...");

            result = SQLManager.unbanMember(groupID, memberID);

            if (result == -1)
                throw new UserDoesNotExistException("No such user exists...");

            System.out.printf("User %s successfully unrestricted...", memberID);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addAdmin(String adminID, String attemptorID,String groupName) throws UserNotAdminException,
            GroupDoesNotExistException, UserDoesNotExistException, UserNotMemberException, NullPointerException {
        String groupID = Group.getGroupID(groupName);

        if (groupID == null)
            throw new NullPointerException("Failed due to SQL Exception...%n");

        Group.checkGroupOwner(groupID, attemptorID);

        Group.checkInGroup(adminID, groupID);

        try {
            int result = SQLManager.addAdmin(groupID, adminID);

            if (result == -1)
                throw new UserDoesNotExistException("No such user exists...");

            System.out.printf("User %s is now an admin...%n", adminID);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeAdmin(String adminID, String attemptorID,String groupName) throws UserNotAdminException,
            GroupDoesNotExistException, UserDoesNotExistException, UserNotMemberException, NullPointerException {
        String groupID = Group.getGroupID(groupName);

        if (groupID == null)
            throw new NullPointerException("Failed due to SQL Exception...%n");

        Group.checkGroupOwner(groupID, attemptorID);

        Group.checkInGroup(adminID, groupID);

        try {
            int result = SQLManager.removeAdmin(groupID, adminID);

            if (result == -1)
                throw new UserDoesNotExistException("No such user exists...");

            System.out.printf("User %s is an admin no more...%n", adminID);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void checkInGroup(String userID, String groupID) throws UserNotMemberException {
        int result = SQLManager.checkInGroup(userID, groupID);

        if (result != 0)
            throw new UserNotMemberException("User is not a member of selected group...");
    }

    public static void checkGroupOwner(String groupID, String userID) throws UserNotAdminException, GroupDoesNotExistException{
        try {
           int result =  SQLManager.checkGroupOwner(groupID, userID);

           if (result == -1)
               throw new GroupDoesNotExistException("No such group exists...");
           else if (result == -2)
               throw new UserNotAdminException("You are not the owner...");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> getParticipantsIDs(String groupID) throws GroupDoesNotExistException {
        try {
            ArrayList<String> result = SQLManager.getParticipants(groupID);

            return result;
        }
        catch (SQLException e) {
            throw new GroupDoesNotExistException("No such group exists...");
        }
    }

    public static String assignGroupID() {
        return String.format("#G" + idCounter++);
    }
}
