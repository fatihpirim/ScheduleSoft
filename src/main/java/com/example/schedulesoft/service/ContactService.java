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

public class ContactService {

    private final ContactDAO contactDAO = new ContactDAO();

    public ContactService() {
    }

    public Contact getContactById(int id) {

        return ContactMapper.toContact(contactDAO.getById(id));
    }

    public ArrayList<Contact> getAllContacts() {

        List<ContactDTO> contactDTOs = contactDAO.getAll();

        return contactDTOs.stream()
                .map(ContactMapper::toContact)
                .collect(Collectors.toCollection(ArrayList::new));
    }

}
