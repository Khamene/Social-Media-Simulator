package org.twitter.Exceptions;

public class GroupDoesNotExistException extends Exception{
    public GroupDoesNotExistException(String message){
        super(message);
    }
}