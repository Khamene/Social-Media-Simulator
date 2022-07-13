public abstract class Message {
    private String messageID;
    private String senderID;
    private String content;

    public Message(String senderID, String content, String messageID) {
        this.senderID = senderID;
        this.content = content;
        this.messageID = messageID;
    }

    public static String getSenderID(String messageID) throws MessageDoesNotExistException{
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

    public static void deleteMessage(String messageID) throws MessageDoesNotExistException {
        //ADD SQL CODE HERE LATER ON
        //--------------------------

        //--------------------------

        //POSSIBLE REL WITH JAVAFX
    }
}
