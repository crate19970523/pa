package com.crater.pa.exception;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class RequestErrorException extends RuntimeException {
    private final List<String> errorRequestKey = new ArrayList<>();

    public RequestErrorException() {
        super();
    }

    public RequestErrorException(String message) {
        super(message);
    }

    public RequestErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public void addRequestKey(String requestKey) {
        errorRequestKey.add(requestKey);
    }

    public List<String> getRequestKey() {
        return errorRequestKey;
    }

    @Override
    public String getMessage() {
        var originErrorMessage = super.getMessage();
        if (StringUtils.isBlank(originErrorMessage) && !errorRequestKey.isEmpty()) {
            return String.join(", ", errorRequestKey) + "format error, please check";
        } else {
            return originErrorMessage;
        }
    }
}
