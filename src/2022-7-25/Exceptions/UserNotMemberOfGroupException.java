package Exceptions;

public class UserNotMemberOfGroupException extends Exception{
    public UserNotMemberOfGroupException(String errorMessage){
        super(errorMessage);
    }
}
