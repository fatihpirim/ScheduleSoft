package com.example.schedulesoft.exception;

/**
 * Throw exception when id does not exist for an object
 */
public class IdDoesNotExistException extends Exception {
    public IdDoesNotExistException() {
        super("The ID is not set for this object.");
    }
}
