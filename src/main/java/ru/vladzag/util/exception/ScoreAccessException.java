package ru.vladzag.util.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//TODO add controller advice exception
@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Score is not available")  // 403
public class ScoreAccessException extends RuntimeException {

    public ScoreAccessException(String message) {
        super(message);
    }
}
