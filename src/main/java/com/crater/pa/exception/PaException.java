package com.crater.pa.exception;

public class PaException extends RuntimeException{

    public PaException() {
    }

    public PaException(String message) {
        super(message);
    }

    public PaException(String message, Throwable cause) {
        super(message, cause);
    }
}
