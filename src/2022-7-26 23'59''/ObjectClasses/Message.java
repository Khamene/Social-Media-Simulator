package ObjectClasses;

import Exceptions.MessageContentNullException;
import Exceptions.MessageDoesNotExistException;

public abstract class Message {
    public static String getSenderID(String messageID) throws MessageDoesNotExistException {
        //ADD SQL CODE HERE LATER ON
        //--------------------------

        //--------------------------

        return "";
    }

    public static String getContent(String messageID) throws MessageDoesNotExistException {
        //ADD SQL CODE HERE LATER ON
        //--------------------------

        //--------------------------

        return "";
    }

    public static void showMessage(String messageID) throws MessageDoesNotExistException{
        //ADD SQL CODE HERE LATER ON
        //--------------------------

        //--------------------------

        //POSSIBLE REL WITH JAVAFX
    }

    public static void editMessage(String messageID, String content) throws MessageDoesNotExistException, MessageContentNullException {

    }

    public static void setReply(String repliedMessageID, String newMessageID) throws MessageDoesNotExistException {

    }

    public static void deleteMessage(String messageID) throws MessageDoesNotExistException {
        //ADD SQL CODE HERE LATER ON
        //--------------------------

        //--------------------------

        //POSSIBLE REL WITH JAVAFX
    }
}
