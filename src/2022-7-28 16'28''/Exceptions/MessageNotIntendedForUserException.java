package Exceptions;

public class MessageNotIntendedForUserException extends Exception{
    public MessageNotIntendedForUserException(String errorMessage) {
        super(errorMessage);
    }
}
