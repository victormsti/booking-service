package com.bookstar.bookingservice.configuration.exception.handler;

import com.bookstar.bookingservice.configuration.exception.BadRequestException;
import com.bookstar.bookingservice.configuration.exception.ConflictException;
import com.bookstar.bookingservice.configuration.exception.NotFoundException;
import com.bookstar.bookingservice.configuration.exception.RestException;
import com.bookstar.bookingservice.configuration.exception.UnauthorizedException;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({RestException.class})
    public ResponseEntity<ErrorMessageResponse> handleRestException(Exception ex,
                                                                    WebRequest request) {
        RestException restException = (RestException) ex;

        int status = restException.getStatus().value();

        ErrorMessageResponse error = new ErrorMessageResponse(status,
                restException.getMessage());

        return new ResponseEntity<>(error, restException.getStatus());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorMessageResponse> handleRuntimeException(RuntimeException ex) {
        return new ResponseEntity<>(
                new ErrorMessageResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        ex.getMessage() != null ? ex.getMessage() : "An unexpected error occurred"
                ),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessageResponse> handleNotFoundException(NotFoundException ex) {
        return new ResponseEntity<>(
                new ErrorMessageResponse(
                        ex.getStatus().value(),
                        ex.getMessage()
                ),ex.getStatus()
        );
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorMessageResponse> handleConflictException(ConflictException ex) {
        return new ResponseEntity<>(
                new ErrorMessageResponse(
                        ex.getStatus().value(),
                        ex.getMessage()
                ),ex.getStatus()
        );
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorMessageResponse> handleUnauthorizedException(UnauthorizedException ex) {
        return new ResponseEntity<>(
                new ErrorMessageResponse(
                        ex.getStatus().value(),
                        ex.getMessage()
                ),ex.getStatus()
        );
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorMessageResponse> handleBadRequestException(BadRequestException ex) {
        return new ResponseEntity<>(
                new ErrorMessageResponse(
                        ex.getStatus().value(),
                        ex.getMessage()
                ),ex.getStatus()
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorMessageResponse> handleBadCredentialsException(BadCredentialsException ex) {
        return new ResponseEntity<>(
                new ErrorMessageResponse(
                        HttpStatus.UNAUTHORIZED.value(),
                        ex.getMessage()
                ),HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorMessageResponse> handleNotFoundException(ExpiredJwtException ex) {
        return new ResponseEntity<>(
                new ErrorMessageResponse(
                        HttpStatus.FORBIDDEN.value(),
                        "The token has been expired"
                ),HttpStatus.FORBIDDEN
        );
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ErrorMessageResponse {
        private int status;
        private String message;

        private ErrorMessageResponse(int status, String message) {
            this.status = status;
            this.message = message;
        }

        private ErrorMessageResponse(RestException ex) {
            status = ex.getStatus().value();
            message = ex.getMessage();
        }
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ValidationMessage> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();
        ValidationMessage validationMessage = new ValidationMessage(ex);
        validationMessage.fields.forEach(f->{
            errors.add(f.message);
        });

        return new ResponseEntity(errors, HttpStatus.BAD_REQUEST);
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    private static class ValidationMessage extends ErrorMessageResponse {
        private List<FieldMessage> fields;

        ValidationMessage(MethodArgumentNotValidException ex) {
            this.setStatus(HttpStatus.BAD_REQUEST.value());
            this.setMessage("VALIDATION_ERROR");

            this.fields = new ArrayList<>();
            for (FieldError error : ex.getBindingResult().getFieldErrors()) {

                addField(error.getField(), error.getDefaultMessage(), error.getObjectName());
            }
        }

        private void addField(String field, String message, String objectName) {
            FieldMessage fieldMessage = new FieldMessage(field, message, objectName);
            this.fields.add(fieldMessage);
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class FieldMessage {
        private String field;
        private String message;
        private String objectName;
    }
}
