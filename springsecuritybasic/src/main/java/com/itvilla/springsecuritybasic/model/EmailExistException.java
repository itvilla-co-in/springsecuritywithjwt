package com.itvilla.springsecuritybasic.model;

public class EmailExistException extends Exception {
    public EmailExistException(String message) {
        super(message);
    }
}
