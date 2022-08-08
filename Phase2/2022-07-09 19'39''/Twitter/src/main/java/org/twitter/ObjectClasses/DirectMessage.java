package org.twitter.ObjectClasses;

import org.twitter.Exceptions.*;
import org.twitter.Functionality.SQLManager;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class DirectMessage extends Message{
    static int idCounter = 0;

    String otherUserID;
    String messageID;
    String content;
    LocalDateTime sentTime;
    boolean mine;

    public DirectMessage(String otherUserID, String content, boolean mine, String messageID, LocalDateTime sentTime) {
        this.otherUserID = otherUserID;
        this.content = content;
        this.mine = mine;
        this.messageID = messageID;
        this.sentTime = sentTime;
    }

    public String getOtherUserID() {
        return otherUserID;
    }

    public String getMessageID() {
        return messageID;
    }

    public String getContent() {
        return content;
    }

    public boolean getMine() {
        return mine;
    }

    public static ArrayList<DirectMessage> getChatMessages(String otherUserID) throws UserDoesNotExistException {
        try {
            ArrayList<DirectMessage> messages = SQLManager.getChat(User.getUserID(User.LoggedInUsername), otherUserID);

            Collections.sort(messages, new Comparator<DirectMessage>() {
                @Override
                public int compare(DirectMessage o1, DirectMessage o2) {
                    if (o1.sentTime.compareTo(o2.sentTime) > 0)
                        return 1;
                    else if (o1.sentTime.compareTo(o2.sentTime) == 0)
                        return 0;
                    else
                        return -1;
                }
            });

            return messages;
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Failed");
        }
    }

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
