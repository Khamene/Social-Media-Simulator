import Exceptions.GroupDoesNotExistException;
import Exceptions.UserDoesNotExistException;
import Exceptions.UserNotAdminException;
import Exceptions.UserNotParticiPantException;

import java.util.ArrayList;

public class Group {
    private String groupID;
    private String ownerID;
    private String groupName;
    private int memberNum;
    private int adminNum;
    private boolean isPrivate;
    private ArrayList<String> members_ID;
    private ArrayList<String> admins;
    private ArrayList<GroupMessage> groupMessages;

    //Giving access to ordinary users to add other users to group

    static int idCounter = 0;

    public Group(String groupName,String ownerID, String groupID, boolean isPrivate) {
        this.groupName = groupName;
        this.groupID = groupID;
        this.ownerID = ownerID;
        this.isPrivate = isPrivate;
        this.adminNum = 1;
        this.memberNum = 1;
        groupMessages = new ArrayList<>(); admins = new ArrayList<>();
        members_ID=new ArrayList<>();
        members_ID.add(ownerID); admins.add(ownerID);
    }

    public static void createGroup(String groupName,String ownerID, boolean isPrivate) {
        String groupID = assignGroupID();
        Group newGroup = new Group(groupName, ownerID, groupID, isPrivate);

        //ADD SQL CODE HERE LATER ON
        //--------------------------

        //--------------------------
    }

    public static String getGroupID(String groupName) throws GroupDoesNotExistException {
        return "";
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
