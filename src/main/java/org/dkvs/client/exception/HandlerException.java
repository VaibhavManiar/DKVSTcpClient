package org.dkvs.client.exception;

public class HandlerException extends RuntimeException {
    public HandlerException(String message) {
        super(message);
    }

    public HandlerException(String message, Throwable cause) {
        super(message, cause);
    }
}
