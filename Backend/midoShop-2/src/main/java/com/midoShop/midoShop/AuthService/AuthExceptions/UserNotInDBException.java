package com.midoShop.midoShop.AuthService.AuthExceptions;

public class UserNotInDBException extends RuntimeException {
    public UserNotInDBException(String message) {
        super(message);
    }

    public UserNotInDBException() {
    }
}
