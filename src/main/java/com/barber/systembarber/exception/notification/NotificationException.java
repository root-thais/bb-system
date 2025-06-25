package com.barber.systembarber.exception.notification;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.barber.systembarber.exception.severity.Severity;

@Getter
@ResponseStatus(code = HttpStatus.MULTI_STATUS)
public class NotificationException extends RuntimeException {

	private static final long serialVersionUID = -5127860015543252750L;
	private final Severity severity;

    public NotificationException(final String message) {
        super(message);
        this.severity = Severity.ERROR;
    }

    public NotificationException(final String message, final Severity severity) {
        super(message);
        this.severity = severity;
    }

}
