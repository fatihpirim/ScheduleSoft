package com.example.schedulesoft.mapper;

import com.example.schedulesoft.domain.Country;
import com.example.schedulesoft.domain.Customer;
import com.example.schedulesoft.dto.CountryDTO;
import com.example.schedulesoft.dto.CustomerDTO;

import java.sql.Timestamp;

public class CountryMapper {

    public static CountryDTO toDto(Country country)  {

        int id = country.getId();
        String name = country.getName();
        Timestamp createDate = Timestamp.valueOf(country.getCreatedOn().toLocalDateTime());
        String createdBy = country.getCreatedBy();
        Timestamp lastUpdate = Timestamp.valueOf(country.getLastUpdated().toLocalDateTime());
        String lastUpdatedBy = country.getLastUpdatedBy();

        return new CountryDTO(id, name, createDate, createdBy, lastUpdate, lastUpdatedBy);
    }


}
