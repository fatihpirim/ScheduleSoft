package com.example.schedulesoft.exception;

public class InvalidIdException extends Exception{
    public InvalidIdException() {
        super("The ID is set to a invalid value.");
    }
}
