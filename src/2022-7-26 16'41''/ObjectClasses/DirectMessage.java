package ObjectClasses;

import Exceptions.MessageContentNullException;
import Exceptions.MessageDoesNotExistException;
import Exceptions.UserWasBannedException;

public class DirectMessage extends Message{
    private String receiverID;

    static int idCounter = 0;

    public DirectMessage(String senderID, String receiverID, String content, String messageID) {
        super(senderID, content, messageID);
        this.receiverID = receiverID;
    }

    public static String getReceiverID(String messageID) throws MessageDoesNotExistException {
        //ADD SQL CODE HERE LATER ON
        //--------------------------

        //--------------------------

        return "";
    }

    public static void SendDirectMessage(String senderID, String receiverID, String content) throws MessageContentNullException, UserWasBannedException {
        String messageID = assignID();

        //ADD SQL CODE HERE LATER ON
        //--------------------------

        //--------------------------
    }

    public static void forwardMessage(String messageID, String receiverID) throws MessageDoesNotExistException{
        //---------------------------

        String newMessageID = assignID();
    }

    public static String assignID() {
        return String.format("#DM" + idCounter++);
    }
}
