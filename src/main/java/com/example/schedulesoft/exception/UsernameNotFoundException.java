package com.example.schedulesoft.exception;

/**
 * Throw exception when the username is not found
 */
public class UsernameNotFoundException extends Exception {
    public UsernameNotFoundException() {
        super("Username not found. Please try again.");
    }
}
