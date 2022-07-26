package Exceptions;

public class UserWasBannedException extends Exception{
    public UserWasBannedException(String errorMessage){
        super(errorMessage);
    }
}
