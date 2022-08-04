package org.twitter.Exceptions;

public class UserNotMemberException extends Exception {
    public UserNotMemberException(String errorMessage) {
        super(errorMessage);
    }
}
