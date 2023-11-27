package com.example.schedulesoft.exception;

public class IncorrectPasswordException extends Exception {
    public IncorrectPasswordException() {
        super("Password is incorrect. Please try again");
    }
}
