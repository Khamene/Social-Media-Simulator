package org.twitter.ObjectClasses;

import org.twitter.Exceptions.*;
import org.twitter.Functionality.SQLManager;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class GroupMessage extends Message{
    static int idCounter = 0;

    String senderID, messageID, content, photoPath, groupName;
    LocalDateTime timeSent;
    boolean mine;

    public GroupMessage(String senderID, String messageID, String content, String photoPath, boolean mine, String groupName, LocalDateTime timeSent) {
        this.senderID = senderID;
        this.messageID = messageID;
        this.content = content;
        this.photoPath = photoPath;
        this.mine = mine;
        this.groupName = groupName;
        this.timeSent = timeSent;
    }

    public GroupMessage(String senderID, String messageID, String content, boolean mine, String groupName, LocalDateTime timeSent) {
        this.senderID = senderID;
        this.messageID = messageID;
        this.content = content;
        this.photoPath = null;
        this.mine = mine;
        this.groupName = groupName;
        this.timeSent = timeSent;
    }

    public String getSenderID() {
        return senderID;
    }

    public String getMessageID() {
        return messageID;
    }

    public String getContent() {
        return content;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public boolean getMine() {
        return mine;
    }

    public String getGroupName() {
        return groupName;
    }

    public static ArrayList<GroupMessage> getMessages(String groupName) throws UserDoesNotExistException {
        try {
            ArrayList<GroupMessage> messages = SQLManager.getChat(groupName);

            Collections.sort(messages, new Comparator<GroupMessage>() {
                @Override
                public int compare(GroupMessage o1, GroupMessage o2) {
                    if (o1.timeSent.compareTo(o2.timeSent) > 0)
                        return 1;
                    else if (o1.timeSent.compareTo(o2.timeSent) == 0)
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

    public static void SendGroupMessage(String senderID, String groupID, String content)
            throws MessageContentNullException, UserNotMemberException, UserWasBannedException, GroupDoesNotExistException, UnauthorisedEditException {

        if (content.equals(""))
            throw new MessageContentNullException("Can not send a message with null content");

        String messageID = assignID();

        int result = SQLManager.sendGM(messageID, senderID, groupID, content);

        switch (result) {
            case 0:
                System.out.println("Message delivered successfully...");

                ArrayList<String> groupMemberIDs = Group.getParticipantsIDs(groupID);

                for (String groupMemberID : groupMemberIDs) {
                    if (!groupMemberID.equals(senderID))
                        Message.addToInbox(messageID, groupMemberID);
                }
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
            throws MessageContentNullException, UserNotMemberException, UserWasBannedException, GroupDoesNotExistException, UnauthorisedEditException {

        if (content.equals(""))
            throw new MessageContentNullException("Can not send a message with null content");

        String messageID = assignID();

        int result = SQLManager.sendGM(messageID, senderID, groupID, content);

        switch (result) {
            case 0:
                Message.setReply(formerMessageID, messageID);

                ArrayList<String> groupMemberIDs = Group.getParticipantsIDs(groupID);

                for (String groupMemberID : groupMemberIDs) {
                    Message.addToInbox(messageID, groupMemberID);
                }

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
