package Exceptions;

public class UnauthorisedEditException extends Exception{
    public UnauthorisedEditException(String errorMessage) {
        super(errorMessage);
    }
}
