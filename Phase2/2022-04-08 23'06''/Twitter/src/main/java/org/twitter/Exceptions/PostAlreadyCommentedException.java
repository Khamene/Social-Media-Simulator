package org.twitter.Exceptions;

public class PostAlreadyCommentedException extends Exception{
    public PostAlreadyCommentedException(String errorMessage) {
        super(errorMessage);
    }
}
