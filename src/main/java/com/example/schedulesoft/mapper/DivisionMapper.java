package com.example.schedulesoft.mapper;

import com.example.schedulesoft.domain.Division;
import com.example.schedulesoft.dto.DivisionDTO;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * This class maps Division object to Division DTO and vice versa
 */
public class DivisionMapper {
    public static DivisionDTO toDTO(Division division) {
        int id = division.getId();
        String name = division.getName();
        Timestamp createDate = Timestamp.valueOf(division.getCreatedOn().toLocalDateTime());
        String createdBy = division.getCreatedBy();
        Timestamp lastUpdate = Timestamp.valueOf(division.getLastUpdated().toLocalDateTime());
        String lastUpdatedBy = division.getLastUpdatedBy();
        int countryId = division.getCountryId();

        return new DivisionDTO(id, name, createDate, createdBy, lastUpdate, lastUpdatedBy, countryId);
    }

    public static Division toDivision(DivisionDTO divisionDTO) {
        int id = divisionDTO.getId();
        String name = divisionDTO.getName();
        ZonedDateTime createdOn = divisionDTO.getCreatedOn().toInstant().atZone(ZoneId.of("UTC"));
        String createdBy = divisionDTO.getCreatedBy();
        ZonedDateTime lastUpdated = divisionDTO.getLastUpdated().toInstant().atZone(ZoneId.of("UTC"));
        String lastUpdatedBy = divisionDTO.getLastUpdatedBy();
        int countryId = divisionDTO.getCountryId();

        Division division = new Division(name, createdOn, createdBy, lastUpdated, lastUpdatedBy, countryId);
        division.setId(id);

        return division;
    }
}
