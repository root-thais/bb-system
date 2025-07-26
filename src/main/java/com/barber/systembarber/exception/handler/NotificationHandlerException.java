package com.barber.systembarber.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.barber.systembarber.exception.builder.ErrorBuilder;
import com.barber.systembarber.exception.notification.NotificationException;
import com.barber.systembarber.exception.error.Error;
import java.util.List;

@RestControllerAdvice
public class NotificationHandlerException extends ResponseEntityExceptionHandler {

    @ExceptionHandler({NotificationException.class})
    public ResponseEntity<List<Error>> handleNotificationException(NotificationException ex) {
        List<Error> errors = List.of((ErrorBuilder.builder()
                .withStatus(HttpStatus.MULTI_STATUS)
                .withMessage(ex.getMessage())
                .withDetails(ex)
                .withSeverity(ex.getSeverity())
                .build()));
        return ResponseEntity.badRequest().body(errors);
    }
}