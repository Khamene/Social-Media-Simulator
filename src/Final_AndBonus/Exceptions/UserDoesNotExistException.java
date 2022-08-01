package Exceptions;

public class UserDoesNotExistException extends Exception{
    public UserDoesNotExistException(String errorMessage){
        super(errorMessage);
    }
}
