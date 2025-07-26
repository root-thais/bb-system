package com.barber.systembarber.exception.builder;

import org.springframework.http.HttpStatus;
import com.barber.systembarber.exception.error.Error;
import com.barber.systembarber.exception.severity.Severity;

public class ErrorBuilder {

	private static Error error;

	public ErrorBuilder() {
		error = new Error();
	}

	public static ErrorBuilder builder() {
		return new ErrorBuilder();
	}

	public ErrorBuilder withStatus(HttpStatus status) {
		error.setStatus(status);
		error.setCode(status.value());
		return this;
	}

	public ErrorBuilder withMessage(String message) {
		error.setMessage(message);
		return this;
	}

	public ErrorBuilder withDetails(Throwable ex) {
		error.setDetails(ex);
		return this;
	}

	public ErrorBuilder withSeverity(Severity severity) {
		error.setSeverity(severity.name());
		return this;
	}

	public Error build() {
		return error;
	}

}
