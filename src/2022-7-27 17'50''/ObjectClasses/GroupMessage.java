package ObjectClasses;

import Exceptions.*;
import Functionality.SQLManager;

public class GroupMessage extends Message{
    static int idCounter = 0;

    //Recommended : deleteGroupMessageByTheOrderOfAdmin

    public static void SendGroupMessage(String senderID, String groupID, String content)
            throws MessageContentNullException, UserNotMemberException, UserWasBannedException {

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

    public static void SendGroupMessage(String senderID, String groupID, String content, String formerMessageID)
            throws MessageContentNullException, UserNotMemberException, UserWasBannedException {

        if (content.equals(""))
            throw new MessageContentNullException("Can not send a message with null content");

        String messageID = assignID();

        int result = SQLManager.sendGM(messageID, senderID, groupID, content);

        switch (result) {
            case 0:
                Message.setReply(formerMessageID, messageID);
                break;
            case -1:
                throw new UserNotMemberException("You are not a member of this group...");
            case -2:
                throw new UserWasBannedException("I'm afraid you were banned by the admin!");
            default:
                break;
        }
    }

    public static void validateIsMyMessage(String userID, String messageID) throws MessageNotIntendedForUserException {
        int result = SQLManager.validateIsMyMessage(userID, messageID);

        if (result == 0)
            return;
        else if (result == -1)
            throw new MessageNotIntendedForUserException("This message was not intended for you");
    }

    public static String assignID() {
        return String.format("#GM" + idCounter++);
    }
}
