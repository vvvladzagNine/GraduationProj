package ru.vladzag.util.exception;

public class VoteExpiredException extends Exception {
    public VoteExpiredException(String message) {
        super(message);
    }
}
