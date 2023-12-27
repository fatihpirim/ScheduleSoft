package com.example.schedulesoft.exception;

/**
 * Throw exception when an unsupported language is selected
 */
public class UnsupportedLanguageException extends Exception {
    public UnsupportedLanguageException(String language) {
        super("The language " + language + " is not currently supported.");
    }
}
