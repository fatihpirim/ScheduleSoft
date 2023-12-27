package com.example.schedulesoft.service;

import com.example.schedulesoft.dao.ContactDAO;
import com.example.schedulesoft.domain.Contact;
import com.example.schedulesoft.domain.Country;
import com.example.schedulesoft.dto.ContactDTO;
import com.example.schedulesoft.dto.CountryDTO;
import com.example.schedulesoft.mapper.ContactMapper;
import com.example.schedulesoft.mapper.CountryMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Intermediary between controllers (and other objects) and the contact data access object (DAO).
 */
public class ContactService {

    private final ContactDAO contactDAO = new ContactDAO();

    public ContactService() {
    }

    /**
     *
     * @param id contact id
     * @return Contact with the id
     */
    public Contact getContactById(int id) {

        return ContactMapper.toContact(contactDAO.getById(id));
    }

    /**
     * @return all contacts in database
     */
    public ArrayList<Contact> getAllContacts() {

        List<ContactDTO> contactDTOs = contactDAO.getAll();

        return contactDTOs.stream()
                .map(ContactMapper::toContact)
                .collect(Collectors.toCollection(ArrayList::new));
    }

}
