package org.twitter.Exceptions;

public class PostDoesNotExistException extends Exception{
    public PostDoesNotExistException(String message) {
        super(message);
    }
}
