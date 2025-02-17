package com.bookstar.bookingservice.configuration.exception;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class UnauthorizedException extends RestException {

    @Serial
    private static final long serialVersionUID = 1L;

    public UnauthorizedException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }

    public UnauthorizedException() {
        super(HttpStatus.UNAUTHORIZED, "Unauthorized");
    }
}
