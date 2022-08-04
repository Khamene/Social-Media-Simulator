package org.twitter.Exceptions;

public class UserNotMemberOfGroupException extends Exception{
    public UserNotMemberOfGroupException(String errorMessage){
        super(errorMessage);
    }
}
