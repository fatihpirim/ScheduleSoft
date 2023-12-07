package com.example.schedulesoft.mapper;

import com.example.schedulesoft.domain.Contact;
import com.example.schedulesoft.dto.ContactDTO;

public class ContactMapper {

    public static ContactDTO toDTO(Contact contact) {
        int id = contact.getId();
        String name = contact.getName();
        String email = contact.getEmail();

        return new ContactDTO(id, name, email);
    }

    public static Contact toContact(ContactDTO contactDTO) {
        int id = contactDTO.getId();
        String name = contactDTO.getName();
        String email = contactDTO.getEmail();

        Contact contact = new Contact(name, email);
        contact.setId(id);

        return contact;
    }
}
