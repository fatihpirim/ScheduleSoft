package com.example.schedulesoft.mapper;

import com.example.schedulesoft.dto.AppointmentDTO;
import com.example.schedulesoft.domain.Appointment;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * This class maps Appointment object to Appointment DTO and vice versa
 */
public class AppointmentMapper {

    public static AppointmentDTO toDto(Appointment appointment) {

        int id = appointment.getId();
        String title = appointment.getTitle();
        String description = appointment.getDescription();
        String location = appointment.getLocation();
        String type = appointment.getType();
        Timestamp start = Timestamp.valueOf(appointment.getStartDateTime().toLocalDateTime());
        Timestamp end = Timestamp.valueOf(appointment.getEndDateTime().toLocalDateTime());
        Timestamp createDate = Timestamp.valueOf(appointment.getCreatedOn().toLocalDateTime());
        String createdBy = appointment.getCreatedBy();
        Timestamp lastUpdate = Timestamp.valueOf(appointment.getLastUpdated().toLocalDateTime());
        String lastUpdatedBy = appointment.getLastUpdatedBy();
        int customerId = appointment.getCustomerId();
        int userId = appointment.getUserId();
        int contactId = appointment.getContactId();

        return new AppointmentDTO(id, title, description, location, type, start, end, createDate, createdBy, lastUpdate, lastUpdatedBy, customerId, userId, contactId);
    }

    public static Appointment toAppointment(AppointmentDTO appointmentDTO) {

        int id = appointmentDTO.getId();
        String title = appointmentDTO.getTitle();
        String description = appointmentDTO.getDescription();
        String location = appointmentDTO.getLocation();
        String type = appointmentDTO.getType();
        ZonedDateTime startDateTime = appointmentDTO.getStart().toInstant().atZone(ZoneId.of("UTC"));
        ZonedDateTime endDateTime = appointmentDTO.getEnd().toInstant().atZone(ZoneId.of("UTC"));
        ZonedDateTime createdOn = appointmentDTO.getCreateDate().toInstant().atZone(ZoneId.of("UTC"));
        String createdBy = appointmentDTO.getCreatedBy();
        ZonedDateTime lastUpdated = appointmentDTO.getLastUpdate().toInstant().atZone(ZoneId.of("UTC"));
        String lastUpdatedBy = appointmentDTO.getLastUpdatedBy();
        int customerId = appointmentDTO.getCustomerId();
        int userId = appointmentDTO.getUserId();
        int contactId = appointmentDTO.getContactId();

        Appointment appointment = new Appointment(title, description, location, type, startDateTime, endDateTime, createdOn, createdBy, lastUpdated, lastUpdatedBy,
                customerId, userId, contactId);
        appointment.setId(id);

        return appointment;
    }
}
