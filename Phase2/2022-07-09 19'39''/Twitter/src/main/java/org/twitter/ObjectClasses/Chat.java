package org.twitter.ObjectClasses;

import org.twitter.Exceptions.NoUserLoggedInException;
import org.twitter.Exceptions.UserDoesNotExistException;
import org.twitter.Functionality.SQLManager;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class Chat {
    String name, photoPath;
    boolean group;
    int newMessageNum;
    LocalDateTime sentTime;

    public Chat(String name, String photoPath, boolean group, int newMessageNum, LocalDateTime sentTime) {
        this.name = name;
        this.photoPath = photoPath;
        this.group = group;
        this.newMessageNum = newMessageNum;
        this.sentTime = sentTime;
    }

    public Chat(String name, boolean group, int newMessageNum, LocalDateTime sentTime) {
        this.name = name;
        this.photoPath = null;
        this.group = group;
        this.newMessageNum = newMessageNum;
        this.sentTime = sentTime;
    }

    public Chat(String name) {
        this.name = name;
        this.photoPath = null;
        this.group = true;
        this.newMessageNum = 0;
        this.sentTime = null;
    }

    public String getName() {
        return name;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public boolean getGroup() {
        return group;
    }

    public int getNewMessageNum() {
        return newMessageNum;
    }

    public LocalDateTime getSentTime() {
        return sentTime;
    }

    public void setSentTime(LocalDateTime sentTime) {
        this.sentTime = sentTime;
    }

    public static ArrayList<Chat> getChats() throws NoUserLoggedInException, UserDoesNotExistException {
        String userID = User.getUserID(User.getLoggedInUsername());

        try {
            ArrayList<Chat> chats =  SQLManager.getChats(userID);

            Collections.sort(chats, new Comparator<Chat>() {
                @Override
                public int compare(Chat o1, Chat o2) {
                    if (o1.sentTime == null && o2.sentTime == null)
                        return 0;
                    else if (o1.sentTime == null && o2.sentTime != null)
                        return 1;
                    else if (o2.sentTime == null && o1.sentTime != null)
                        return -1;

                    if (o1.sentTime.compareTo(o2.getSentTime()) > 0)
                        return -1;
                    else if (o1.sentTime.compareTo(o2.getSentTime()) == 0)
                        return 0;
                    else
                        return 1;
                }
            });

            return chats;
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new NullPointerException("Failure");
        }
    }

    public static boolean containsChat(ArrayList<Chat> chats, String chatID) {
        for (Chat chat : chats) {
            if (chat.name.equals(chatID))
                return true;
        }

        return false;
    }

    @Override
    public boolean equals(Object another) {
        if (((Chat)another).getName().equalsIgnoreCase(this.name))
            return true;
        else
            return false;
    }

    public static Chat getChat(ArrayList<Chat> chats, String chatID) {
        for (Chat chat : chats) {
            if (chat.name.equals(chatID))
                return chat;
        }

        return null;
    }

    public static void setPhoto(ArrayList<Chat> chats, String groupName, String photo) {
        for (Chat chat : chats) {
            if (chat.name.equals(groupName)) {
                chat.photoPath = photo;
                break;
            }
        }
    }

    public static void setTime(ArrayList<Chat> chats, String groupName, LocalDateTime timeSent) {
        for (Chat chat : chats) {
            if (chat.name.equals(groupName)) {
                chat.sentTime = timeSent;
                break;
            }
        }
    }

    public static void incrementNewMessage(ArrayList<Chat> chats, String groupName) {
        for (Chat chat : chats) {
            if (chat.name.equals(groupName)) {
                chat.newMessageNum++;
                break;
            }
        }
    }

    public void incrementNewMessage() {
        this.newMessageNum++;
    }
}
