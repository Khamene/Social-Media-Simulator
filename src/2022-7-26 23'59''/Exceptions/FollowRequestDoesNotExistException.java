package Exceptions;

public class FollowRequestDoesNotExistException extends Exception{
    public FollowRequestDoesNotExistException(String errorMessage) {
        super(errorMessage);
    }
}
