package com.twitter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FollowingValidationException extends RuntimeException {

    public FollowingValidationException(String message) {
        super(message);
    }
}
