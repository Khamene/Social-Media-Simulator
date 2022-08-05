package org.twitter.Exceptions;

public class NoAccessToUserException extends Exception{
    public NoAccessToUserException(String errorMessage){
        super(errorMessage);
    }
}
