package com.example.schedulesoft.exception;

/**
 * Throw exception when an incorrect password is entered
 * (not used)
 */
public class IncorrectPasswordException extends Exception {
    public IncorrectPasswordException() {
        super("Password is incorrect. Please try again");
    }
}
