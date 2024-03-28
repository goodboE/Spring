package com.ko.shop.exception;

public class NotUserException extends RuntimeException{

    public NotUserException() {
    }

    public NotUserException(String message) {
        super(message);
    }

    public NotUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotUserException(Throwable cause) {
        super(cause);
    }
}
