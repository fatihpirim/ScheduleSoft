package com.example.schedulesoft.exception;

public class UnsupportedLanguageException extends Exception {
    public UnsupportedLanguageException(String language) {
        super("The language " + language + " is not currently supported.");
    }
}
