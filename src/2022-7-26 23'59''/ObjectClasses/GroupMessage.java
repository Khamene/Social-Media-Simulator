package ObjectClasses;

import Exceptions.*;
import Functionality.SQLManager;

public class GroupMessage extends Message{
    static int idCounter = 0;

    //Recommended : deleteGroupMessageByTheOrderOfAdmin

    public static void SendGroupMessage(String senderID, String groupID, String content)
            throws MessageContentNullException, UserNotMemberException, UserWasBannedException {
        try {
            if (content.equals(""))
                throw new MessageContentNullException("Can not send a message with null content");

            String messageID = assignID();

            int result = SQLManager.sendGM(messageID, senderID, groupID, content);

            switch (result) {
                case 0:
                    System.out.println("Message delivered successfully...");
                    break;
                case -1:
                    throw new UserNotMemberException("You are not a member of this group...");
                case -2:
                    throw new UserWasBannedException("I'm afraid you were banned by the admin!");
                default:
                    break;
            }
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
