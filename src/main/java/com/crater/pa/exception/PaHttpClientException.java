package com.crater.pa.exception;

public class PaHttpClientException extends RuntimeException {

    public PaHttpClientException() {
    }

    public PaHttpClientException(String message) {
        super(message);
    }

    public PaHttpClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
