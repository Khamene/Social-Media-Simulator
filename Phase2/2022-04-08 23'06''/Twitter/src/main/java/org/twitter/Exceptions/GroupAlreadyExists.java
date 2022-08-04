package org.twitter.Exceptions;

public class GroupAlreadyExists extends Exception {
    public GroupAlreadyExists(String errorMessage) {
        super(errorMessage);
    }
}
