package com.example.schedulesoft.exception;

public class IdDoesNotExistException extends Exception {
    public IdDoesNotExistException() {
        super("The ID is not set for this object.");
    }
}
