package com.example.schedulesoft.dto;

/**
 * Encapsulates contact data as read from the SQL database
 */
public class ContactDTO {
    private int id;
    private String name;
    private String email;

    public ContactDTO(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
