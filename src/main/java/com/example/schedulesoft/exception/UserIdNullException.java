package com.example.schedulesoft.exception;

public class UserIdNullException extends Exception{
    public UserIdNullException() {
        super("User does not have an ID set. User was probably" +
                "trying to be created in an illegal way");
    }
}
