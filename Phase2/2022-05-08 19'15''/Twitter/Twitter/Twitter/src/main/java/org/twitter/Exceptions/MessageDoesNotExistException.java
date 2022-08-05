package org.twitter.Exceptions;

public class MessageDoesNotExistException extends Exception{
    public MessageDoesNotExistException(String message) {
        super(message);
    }
}
