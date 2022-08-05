package org.twitter.Exceptions;

public class UserNotBlockedException extends Exception{
    public UserNotBlockedException(String errorMessage) {
        super(errorMessage);
    }
}
