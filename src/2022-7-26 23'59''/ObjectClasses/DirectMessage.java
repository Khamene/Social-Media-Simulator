package ObjectClasses;

import Exceptions.MessageContentNullException;
import Exceptions.MessageDoesNotExistException;
import Exceptions.UserWasBannedException;
import Functionality.SQLManager;

public class DirectMessage extends Message{
    static int idCounter = 0;

    public static String getReceiverID(String messageID) throws MessageDoesNotExistException {
        //ADD SQL CODE HERE LATER ON
        //--------------------------

        //--------------------------

        return "";
    }

    public static void SendDirectMessage(String senderID, String receiverID, String content) throws MessageContentNullException,
            UserWasBannedException {
        if (content.equals(""))
            throw new MessageContentNullException("Can not send a message with null content");

        String messageID = assignID();

        int result = SQLManager.sendDM(messageID, senderID, receiverID, content);

        if (result == 0)
            System.out.println("Message delivered successfully...");
        else if (result == -1)
            System.out.println("I'm afraid you were blocked by the selected user!");
    }

    public static void forwardMessage(String messageID, String receiverID) throws MessageDoesNotExistException{
        //---------------------------

        String newMessageID = assignID();
    }

    public static String assignID() {
        return String.format("#DM" + idCounter++);
    }
}
