package Exceptions;

public class NoUserLoggedInException extends Exception{
    public NoUserLoggedInException(String errorMessage) {
        super(errorMessage);
    }
}
