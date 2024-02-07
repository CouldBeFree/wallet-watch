package com.expenses.walletwatch.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFound extends RuntimeException {
    public NotFound() {
    }

    public NotFound(String message) {
        super(message);
    }

    public NotFound(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFound(Throwable cause) {
        super(cause);
    }

    public NotFound(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
