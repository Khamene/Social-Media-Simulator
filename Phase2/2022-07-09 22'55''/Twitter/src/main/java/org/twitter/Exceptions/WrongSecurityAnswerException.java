package org.twitter.Exceptions;

public class WrongSecurityAnswerException extends Exception {
    public WrongSecurityAnswerException(String errorMessage) {
        super(errorMessage);
    }
}
