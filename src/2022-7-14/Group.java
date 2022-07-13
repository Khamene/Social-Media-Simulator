public class Group {
    private String groupID;
    private String ownerID;
    private String groupName;
    private int memberNum;
    private int adminNum;

    static int idCounter = 0;

    public Group(String groupName,String ownerID, String groupID) {
            this.groupName = groupName;
            this.groupID = groupID;
            this.ownerID = ownerID;
            this.adminNum = 1;
            this.memberNum = 1;
    }

    public static void createGroup(String groupName,String ownerID) {
        String groupID = assignGroupID();
        Group newGroup = new Group(groupName, ownerID, groupID);

        //ADD SQL CODE HERE LATER ON
        //--------------------------

        //--------------------------
    }

    public static String getGroupID(String groupName) throws GroupDoesNotExistException{
        return "";
    }

    public void changeGroupName(String groupName) throws UserNotAdminException{
        //IF NOT ADMIN THROW EXCEPTION LATER ON
        this.groupName = groupName;

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

    public static void addMember(String memberID, String attemptorID,String groupName) throws UserNotAdminException, GroupDoesNotExistException{
        //IF NOT ADMIN THROW EXCEPTION LATER ON
    }

    public static void removeMember(String memberID, String attemptorID,String groupName) throws UserNotAdminException, GroupDoesNotExistException{
        //IF NOT ADMIN THROW EXCEPTION LATER ON
    }

    public static void banMember(String memberID, String attemptorID,String groupName) throws UserNotAdminException, GroupDoesNotExistException{
        //IF NOT ADMIN THROW EXCEPTION LATER ON
    }

    public static void unbanMember(String memberID, String attemptorID,String groupName) throws UserNotAdminException, GroupDoesNotExistException{
        //IF NOT ADMIN THROW EXCEPTION LATER ON
    }

    public static void addAdmin(String adminID, String attemptorID,String groupName) throws UserNotAdminException, GroupDoesNotExistException{
        //IF NOT ADMIN THROW EXCEPTION LATER ON
    }

    public static void removeAdmin(String adminID, String attemptorID,String groupName) throws UserNotAdminException, GroupDoesNotExistException{
        //IF NOT OWNER THROW EXCEPTION LATER ON
    }

    public static void sendMessage(String senderID, String messageContent,String groupName) throws UserNotParticiPantException, GroupDoesNotExistException {
        //IF NOT PARTICIPANT THROW EXCEPTION LATER ON
    }

    private static String assignGroupID() {
        return String.format("#G" + idCounter++);
    }
}
