package org.twitter.Exceptions;

public class PasswordIncorrectException extends Exception{
    public PasswordIncorrectException(String errorMessage) {
        super(errorMessage);
    }
}
