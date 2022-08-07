package org.twitter.Exceptions;

public class PostNotCommentedException extends Exception{
    public PostNotCommentedException(String errorMessage) {
        super(errorMessage);
    }
}
