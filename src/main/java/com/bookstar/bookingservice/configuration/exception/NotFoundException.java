package com.bookstar.bookingservice.configuration.exception;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class NotFoundException extends RestException {

    @Serial
    private static final long serialVersionUID = 1L;

    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }

    public NotFoundException() {
        super(HttpStatus.NOT_FOUND, "Not Found");
    }
}
