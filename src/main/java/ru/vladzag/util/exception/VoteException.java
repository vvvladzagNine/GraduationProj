package ru.vladzag.util.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Score is not available")  // 403
public class VoteException extends RuntimeException {
    public VoteException(String message) {
        super(message);
    }
}
