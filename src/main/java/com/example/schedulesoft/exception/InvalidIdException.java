package com.example.schedulesoft.exception;

/**
 * Throws exception when id is invalid
 */
public class InvalidIdException extends Exception{
    public InvalidIdException() {
        super("The ID is set to a invalid value.");
    }
}
