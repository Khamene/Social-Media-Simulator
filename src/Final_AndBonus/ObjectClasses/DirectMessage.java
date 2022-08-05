package ObjectClasses;

import Exceptions.*;
import Functionality.SQLManager;

public class DirectMessage extends Message{
    static int idCounter = 0;

//    public static String getReceiverID(String messageID) throws MessageDoesNotExistException {
//        //ADD SQL CODE HERE LATER ON
//        //--------------------------
//
//        //--------------------------
//
//        return "";
//    }

    public static void SendDirectMessage(String senderID, String receiverID, String content) throws MessageContentNullException,
            UserWasBannedException, UnauthorisedEditException {
        if (content.equals(""))
            throw new MessageContentNullException("Can not send a message with null content");

        String messageID = assignID();

        int result = SQLManager.sendDM(messageID, senderID, receiverID, content);

        if (result == 0) {
            System.out.println("Message delivered successfully...");
            Message.addToInbox(messageID, receiverID);
        }
        else if (result == -1)
            throw new UserWasBannedException("I'm afraid you were blocked by the selected user!");
    }

    public static void SendDirectMessage(String senderID, String receiverID, String content, String formerMessageID) throws MessageContentNullException,
            UserWasBannedException, UnauthorisedEditException {
        if (content.equals(""))
            throw new MessageContentNullException("Can not send a message with null content");

        String messageID = assignID();

        int result = SQLManager.sendDM(messageID, senderID, receiverID, content);

        if (result == -1)
            throw new UserWasBannedException("I'm afraid you were blocked by the selected user!");
        else if (result == 0) {
            Message.setReply(formerMessageID, messageID);
            Message.addToInbox(messageID, receiverID);
        }
    }

    public static void validateIsMyMessage(String messageID, String userID) throws MessageDoesNotExistException, MessageNotIntendedForUserException {
        int result = SQLManager.validatePossession(userID,messageID);

        switch (result) {
            case 0:
                return;
            case -1:
                throw new MessageDoesNotExistException("No such message exists");
            case -2:
                throw new MessageNotIntendedForUserException("This message was not intended for you");
            default:
                break;
        }
    }

    public static String assignID() {
        return String.format("#DM" + idCounter++);
    }
}
