package com.barber.systembarber.exception.error;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Error {

    @Setter
    private String message;

    @Setter
    private Throwable details;

    @Setter
    private HttpStatus status;

    @Setter
    private Integer code;

    @Setter
    private String severity;

    private final LocalDate date = LocalDate.now();

    private final LocalTime time = LocalTime.now();
}
