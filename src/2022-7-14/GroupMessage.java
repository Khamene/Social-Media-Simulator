public class GroupMessage extends Message{
    String groupID;

    static int idCounter = 0;

    public GroupMessage(String senderID, String groupID, String content, String messageID) {
        super(senderID, content, messageID);
        this.groupID = groupID;
    }

    public static String getGroupID(String messageID) throws MessageDoesNotExistException{
        //ADD SQL CODE HERE LATER ON
        //--------------------------

        //--------------------------

        return "";
    }

    public static void SendGroupMessage(String senderID, String groupID, String content) throws MessageContentNullException, GroupDoesNotExistException /*ADD EXCEPTION FROM Parsa f*/ {
        String messageID = assignID();
        GroupMessage groupMessage = new GroupMessage(senderID, groupID, content, messageID);

        //ADD SQL CODE HERE LATER ON
        //--------------------------

        //--------------------------
    }

    public static String assignID() {
        return String.format("#GM" + idCounter++);
    }
}
