package org.twitter.Exceptions;

public class UserNotBusinessException extends Exception{
    public UserNotBusinessException(String errorMessage) {
        super(errorMessage);
    }
}
