package Exceptions;

public class UserNotAdminException extends Exception {
    public UserNotAdminException(String message) {
        super(message);
    }
}
