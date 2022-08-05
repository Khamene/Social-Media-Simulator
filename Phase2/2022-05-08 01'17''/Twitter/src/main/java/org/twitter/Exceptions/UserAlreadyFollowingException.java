package org.twitter.Exceptions;

public class UserAlreadyFollowingException extends Exception{
    public UserAlreadyFollowingException(String errorMessage){
        super(errorMessage);
    }
}
