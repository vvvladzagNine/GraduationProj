package ru.vladzag.util.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "User is not available vote twice and change vote after 11 a.m.")  // 403
public class VoteException extends RuntimeException {
    public VoteException(String message) {
        super(message);
    }
}
