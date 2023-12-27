package com.example.schedulesoft.mapper;

import com.example.schedulesoft.domain.Country;
import com.example.schedulesoft.dto.CountryDTO;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * This class maps Country object to Country DTO and vice versa
 */
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

    public static Country toCountry(CountryDTO countryDTO) {

        int id = countryDTO.getId();
        String name = countryDTO.getName();
        ZonedDateTime createdOn = countryDTO.getCreatedOn().toInstant().atZone(ZoneId.of("UTC"));
        String createdBy = countryDTO.getCreatedBy();
        ZonedDateTime lastUpdated = countryDTO.getLastUpdated().toInstant().atZone(ZoneId.of("UTC"));
        String lastUpdatedBy = countryDTO.getLastUpdatedBy();

        Country country = new Country(name, createdOn, createdBy, lastUpdated, lastUpdatedBy);
        country.setId(id);

        return country;
    }
}
