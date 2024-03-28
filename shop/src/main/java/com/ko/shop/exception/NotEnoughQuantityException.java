package com.ko.shop.exception;

public class NotEnoughQuantityException extends RuntimeException{

    public NotEnoughQuantityException() {
    }

    public NotEnoughQuantityException(String message) {
        super(message);
    }

    public NotEnoughQuantityException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughQuantityException(Throwable cause) {
        super(cause);
    }
}
