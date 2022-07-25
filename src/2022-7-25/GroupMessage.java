import Exceptions.*;

public class GroupMessage extends Message{
    String groupID;

    static int idCounter = 0;

    public GroupMessage(String senderID, String groupID, String content, String messageID) {
        super(senderID, content, messageID);
        this.groupID = groupID;
    }

    //Recommended : deleteGroupMessageByTheOrderOfAdmin




    public static String getGroupID(String messageID) throws MessageDoesNotExistException {
        //ADD SQL CODE HERE LATER ON
        //--------------------------

        //--------------------------

        return "";
    }

    public static void SendGroupMessage(String senderID, String groupID, String content)
            throws MessageContentNullException {
        try {
            Group.sendMessage(senderID, content, groupID);
            String messageID = assignID();

            //ADD SQL CODE HERE LATER ON
            //--------------------------

            //--------------------------
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void forwardMessage(String messageID, String groupID) throws MessageDoesNotExistException, GroupDoesNotExistException {
        //---------------------------

        String newMessageID = assignID();
    }

    public static String assignID() {
        return String.format("#GM" + idCounter++);
    }
}
