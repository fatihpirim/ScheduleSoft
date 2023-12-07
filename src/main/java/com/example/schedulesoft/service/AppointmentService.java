package com.example.schedulesoft.service;

import com.example.schedulesoft.dao.AppointmentDAO;
import com.example.schedulesoft.domain.Appointment;
import com.example.schedulesoft.domain.Interval;
import com.example.schedulesoft.dto.AppointmentDTO;
import com.example.schedulesoft.dto.CustomerDTO;
import com.example.schedulesoft.mapper.AppointmentMapper;
import com.example.schedulesoft.mapper.CustomerMapper;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AppointmentService {

    private final AppointmentDAO appointmentDAO = new AppointmentDAO();

    public AppointmentService() {
    }

    public boolean saveAppointment(Appointment appointment) throws Exception {

        if(isOverlappingExistingAppointment(appointment) ) {
            return false;
        }

        AppointmentDTO appointmentDTO = AppointmentMapper.toDto(appointment);

        if(appointment.getId() == 0) {

            int result = appointmentDAO.insert(appointmentDTO);

        } else {

            int result = appointmentDAO.update(appointmentDTO);
        }

        return true;
    }

    public boolean deleteAppointment(Appointment appointment) {
        return true;
    }

    public ArrayList<Appointment> getAllAppointments() {
        List<AppointmentDTO> appointmentDTOs = appointmentDAO.getAll();

        return appointmentDTOs.stream()
                .map(AppointmentMapper::toAppointment)
                .collect(Collectors.toCollection(ArrayList::new));

    }

    public ArrayList<Appointment> getAllAppointmentsByCustomerId() {
        ArrayList<Appointment> allAppointments = getAllAppointments();
        // filter all appointments
        return null;
    }

    public boolean isOverlappingExistingAppointment(Appointment appointment) throws Exception {

        Interval appointmentInterval = new Interval(appointment.getStartDateTime(), appointment.getEndDateTime());


        for(Appointment otherAppointment: getAllAppointments()) {
            Interval otherInterval = new Interval(otherAppointment.getStartDateTime(), otherAppointment.getEndDateTime());
            // Checks if the customer has an overlapping appointment
            int customerId = appointment.getCustomerId();
            if(appointmentInterval.isOverlapping(otherInterval) && customerId == otherAppointment.getCustomerId()) {
                System.out.println("This customer has an existing appointment overlapping with the desired time frame");
                return true;
            }
            int userId = appointment.getUserId();
            if(appointmentInterval.isOverlapping(otherInterval) && customerId == otherAppointment.getCustomerId()) {
                System.out.println("This user has an existing appointment overlapping with the desired time frame");
                return true;
            }
        }
        return false;
    }

}
