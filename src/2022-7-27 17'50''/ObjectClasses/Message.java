package ObjectClasses;

import Exceptions.MessageContentNullException;
import Exceptions.MessageDoesNotExistException;
import Exceptions.UnauthorisedEditException;
import Functionality.SQLManager;

public abstract class Message {
    public static String getSenderID(String messageID) throws MessageDoesNotExistException {
        String senderID = SQLManager.getSenderID(messageID);

        if (senderID.equals(""))
            throw new MessageDoesNotExistException("No such message exists...");
        else if (!senderID.equals("NaN"))
            return senderID;
        else
            return "";
    }

    public static String getGroupID(String messageID) throws MessageDoesNotExistException {
        String groupID = SQLManager.getGroupIDFromMessage(messageID);

        if (groupID.equals(""))
            throw new MessageDoesNotExistException("No such message exists...");
        else if (groupID == null)
            throw new MessageDoesNotExistException("This message was not sent in a group...");
        else if (!groupID.equals("NaN"))
            return groupID;
        else
            return "";
    }

    public static String getContent(String messageID) throws MessageDoesNotExistException {
        String content = SQLManager.getContent(messageID);

        if (content.equals(""))
            throw new MessageDoesNotExistException("No such message exists...");
        else if (!content.equalsIgnoreCase("NaN"))
            return content;
        else
            return "";
    }



    public static void showMessage(String messageID) throws MessageDoesNotExistException{
        //ADD SQL CODE HERE LATER ON
        //--------------------------

        //--------------------------

        //POSSIBLE REL WITH JAVAFX
    }

    public static void editMessage(String messageID, String senderID, String content) throws UnauthorisedEditException, MessageContentNullException {
        if (content.equals(""))
            throw new MessageContentNullException("Can not assign null content to selected message");

        int result = SQLManager.editMessage(messageID, senderID, content);

        if (result == 0) {
            System.out.println("Message edited successfully...");
        }
        else if (result == -1)
            throw new UnauthorisedEditException("You can no longer edit this message");
    }

    public static void setReply(String repliedMessageID, String newMessageID) {
        int result = SQLManager.setReply(repliedMessageID, newMessageID);

        if (result == 0) {
            System.out.println("Reply to selected message successfully carried out...");
        }
    }

    public static void setForward(String messageID) {
        SQLManager.setForward(messageID);
    }

    public static void deleteMessage(String messageID) {
        int result = SQLManager.deleteMessage(messageID);

        if (result == 0)
            System.out.println("Message deleted successfully...");
    }
}
