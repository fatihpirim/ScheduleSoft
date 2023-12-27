package com.example.schedulesoft.mapper;

import com.example.schedulesoft.dto.CustomerDTO;
import com.example.schedulesoft.domain.Customer;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * This class maps Customer object to Customer DTO and vice versa
 */
public class CustomerMapper {

    public static CustomerDTO toDto(Customer customer)  {

        int id = customer.getId();
        String name = customer.getName();
        String address = customer.getAddress();
        String postalCode = customer.getPostalCode();
        String phone = customer.getPhoneNumber();
        Timestamp createDate = Timestamp.valueOf(customer.getCreatedOn().toLocalDateTime());
        String createdBy = customer.getCreatedBy();
        Timestamp lastUpdate = Timestamp.valueOf(customer.getLastUpdated().toLocalDateTime());
        String lastUpdatedBy = customer.getLastUpdatedBy();
        int divisionId = customer.getDivisionId();

        return new CustomerDTO(id, name, address, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdatedBy, divisionId);
    }

    public static Customer toCustomer(CustomerDTO customerDTO) {

        int id = customerDTO.getId();
        String name = customerDTO.getName();
        String address = customerDTO.getAddress();
        String postalCode = customerDTO.getPostalCode();
        String phoneNumber = customerDTO.getPhone();
        ZonedDateTime createdOn = customerDTO.getCreateDate().toInstant().atZone(ZoneId.of("UTC"));
        String createdBy = customerDTO.getCreatedBy();
        ZonedDateTime lastUpdated = customerDTO.getLastUpdate().toInstant().atZone(ZoneId.of("UTC"));
        String lastUpdatedBy = customerDTO.getLastUpdatedBy();
        int divisionId = customerDTO.getDivisionId();

        Customer customer = new Customer(name, address, postalCode, phoneNumber, createdOn, createdBy, lastUpdated, lastUpdatedBy, divisionId);
        customer.setId(id);

        return customer;
    }
}
