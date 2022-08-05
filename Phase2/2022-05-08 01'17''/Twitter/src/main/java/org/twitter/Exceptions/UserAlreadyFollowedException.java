package org.twitter.Exceptions;

public class UserAlreadyFollowedException extends Exception{
    public UserAlreadyFollowedException(String errorMessage){
        super(errorMessage);
    }
}
