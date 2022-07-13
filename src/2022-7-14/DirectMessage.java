public class DirectMessage extends Message{
    private String recieverID;

    static int idCounter = 0;

    public DirectMessage(String senderID, String recieverID, String content, String messageID) {
        super(senderID, content, messageID);
        this.recieverID = recieverID;
    }

    public static String getRecieverID(String messageID) throws MessageDoesNotExistException{
        //ADD SQL CODE HERE LATER ON
        //--------------------------

        //--------------------------

        return "";
    }

    public static void SendDirectMessage(String senderID, String recieverID, String content) throws MessageContentNullException /*ADD EXCEPTION FROM Parsa f*/ {
        String messageID = assignID();
        DirectMessage directMessage = new DirectMessage(senderID, recieverID, content, messageID);

        //ADD SQL CODE HERE LATER ON
        //--------------------------

        //--------------------------
    }

    public static String assignID() {
        return String.format("#DM" + idCounter++);
    }
}
