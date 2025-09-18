package com.midoShop.midoShop.AuthService.AuthExceptions;

public class CannotCreateAccountException extends RuntimeException {
    public CannotCreateAccountException(String message) {
        super(message);
    }

    public CannotCreateAccountException() {
    }
}
