package com.example.schedulesoft.exception;

public class UsernameNotFoundException extends Exception {
    public UsernameNotFoundException() {
        super("Username not found. Please try again.");
    }
}
