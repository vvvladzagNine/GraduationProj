package ru.vladzag.util.exception;


//TODO add controller advice exception
public class ScoreAccessException extends RuntimeException {

    public ScoreAccessException(String message) {
        super(message);
    }
}
