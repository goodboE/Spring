package com.ko.shop.exception;

public class NotItemException extends RuntimeException{

    public NotItemException() {
    }

    public NotItemException(String message) {
        super(message);
    }

    public NotItemException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotItemException(Throwable cause) {
        super(cause);
    }
}
