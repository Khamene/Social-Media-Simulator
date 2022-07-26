package ObjectClasses;

import Exceptions.GroupDoesNotExistException;
import Exceptions.UserDoesNotExistException;
import Exceptions.UserNotAdminException;
import Exceptions.UserNotParticiPantException;
import Functionality.SQLManager;

public class Group {
    static int idCounter = 0;

    public static void createGroup(String groupName,String ownerID, boolean isPrivate) {
        String groupID = assignGroupID();

        //ADD SQL CODE HERE LATER ON
        //--------------------------

        //--------------------------
    }

    public static String getGroupID(String groupName) throws GroupDoesNotExistException {
        String groupID = SQLManager.getGroupID(groupName);

        if (groupID.equals(""))
            throw new GroupDoesNotExistException("No group exists with such name...");

        return groupID;
    }

    public static void changeGroupName(String lastGroupName,String groupName) throws UserNotAdminException, GroupDoesNotExistException {
        //IF NOT ADMIN THROW EXCEPTION LATER ON

        //ADD SQL CODE HERE LATER ON
        //--------------------------

        //--------------------------
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

    public static void addMember(String memberID, String attemptorID,String groupName) throws UserNotAdminException, GroupDoesNotExistException, UserDoesNotExistException {
        //IF NOT ADMIN THROW EXCEPTION LATER ON
    }

    public static void removeMember(String memberID, String attemptorID,String groupName) throws UserNotAdminException, GroupDoesNotExistException, UserDoesNotExistException{
        //IF NOT ADMIN THROW EXCEPTION LATER ON
    }

    public static void banMember(String memberID, String attemptorID,String groupName) throws UserNotAdminException, GroupDoesNotExistException, UserDoesNotExistException{
        //IF NOT ADMIN THROW EXCEPTION LATER ON
    }

    public static void unbanMember(String memberID, String attemptorID,String groupName) throws UserNotAdminException, GroupDoesNotExistException, UserDoesNotExistException{
        //IF NOT ADMIN THROW EXCEPTION LATER ON
    }

    public static void addAdmin(String adminID, String attemptorID,String groupName) throws UserNotAdminException, GroupDoesNotExistException, UserDoesNotExistException{
        //IF NOT ADMIN THROW EXCEPTION LATER ON
    }

    public static void removeAdmin(String adminID, String attemptorID,String groupName) throws UserNotAdminException, GroupDoesNotExistException, UserDoesNotExistException{
        //IF NOT OWNER THROW EXCEPTION LATER ON
    }

    public static void sendMessage(String senderID, String messageContent,String groupName) throws UserNotParticiPantException, GroupDoesNotExistException {
        //IF NOT PARTICIPANT THROW EXCEPTION LATER ON
    }

    private static String assignGroupID() {
        return String.format("#G" + idCounter++);
    }
}
