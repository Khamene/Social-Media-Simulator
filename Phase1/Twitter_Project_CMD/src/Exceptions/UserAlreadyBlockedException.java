package Exceptions;

public class UserAlreadyBlockedException extends Exception{
    public UserAlreadyBlockedException(String errorMessage) {
        super(errorMessage);
    }
}
