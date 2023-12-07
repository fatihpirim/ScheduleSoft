package com.example.schedulesoft.dao;

import com.example.schedulesoft.domain.Appointment;
import com.example.schedulesoft.dto.AppointmentDTO;
import com.example.schedulesoft.mapper.AppointmentMapper;
import com.example.schedulesoft.util.Database;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentDAOTest {

    @Test
    void testTimeZoneConversion() {

        Database.getConnection();

        AppointmentDAO appointmentDAO = new AppointmentDAO();

        Appointment appointment =  new Appointment("Foo", "Bar", "FooLoc", "BarType", ZonedDateTime.now(),
                ZonedDateTime.now().plusHours(2), ZonedDateTime.now(), "UnitTest", ZonedDateTime.now(),
                "UnitTest", 1,1,1);
        System.out.println("Created Appointment: " + appointment);

        AppointmentDTO dto = AppointmentMapper.toDto(appointment);
        System.out.println("Created AppointmentDTO: " + dto);

        appointmentDAO.insert(dto);

        System.out.println(appointmentDAO.getAll());

        List<AppointmentDTO> appointments = appointmentDAO.getAll();

        System.out.println(AppointmentMapper.toAppointment(appointments.get(appointments.size()-1)).getStartDateTime());
        System.out.println(AppointmentMapper.toAppointment(appointments.get(appointments.size()-1)).getStartDateTime().getZone());

        Database.closeConnection();

    }
}