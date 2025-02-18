package com.bookstar.bookingservice.configuration.exception;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class BadRequestException extends RestException {

    @Serial
    private static final long serialVersionUID = 1L;

    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

    public BadRequestException() {
        super(HttpStatus.BAD_REQUEST, "Bad Request");
    }
}
