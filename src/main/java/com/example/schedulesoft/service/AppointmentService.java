package com.example.schedulesoft.service;

import com.example.schedulesoft.dao.AppointmentDAO;
import com.example.schedulesoft.domain.Appointment;
import com.example.schedulesoft.domain.Interval;
import com.example.schedulesoft.dto.AppointmentDTO;
import com.example.schedulesoft.dto.CustomerDTO;
import com.example.schedulesoft.mapper.AppointmentMapper;
import com.example.schedulesoft.mapper.CustomerMapper;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AppointmentService {

    private final AppointmentDAO appointmentDAO = new AppointmentDAO();

    public AppointmentService() {
    }

    public boolean saveAppointment(Appointment appointment) throws Exception {

        AppointmentDTO appointmentDTO = AppointmentMapper.toDto(appointment);

        if(!appointmentIntervalIsValid(appointment)) {
            System.out.println("Failed to save appointment!");
            return false;
        }

        if(appointment.getId() == 0) {

            int result = appointmentDAO.insert(appointmentDTO);
            if(result == 1) {
                System.out.println("Appointment added successfully");
                return true;
            } else {
                System.out.println("Appointment was unable to be added");
                return false;
            }
        } else {
            int result = appointmentDAO.update(appointmentDTO);
            if(result == 1) {
                System.out.println("Customer updated successfully");
                return true;
            } else {
                System.out.println("Customer was unable to be updated");
                return false;
            }
        }
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

    public boolean appointmentIntervalIsValid(Appointment appointment) throws Exception {

        ZonedDateTime startDateTime = appointment.getStartDateTime();
        ZonedDateTime endDateTime = appointment.getEndDateTime();

        Interval appointmentInterval = new Interval(startDateTime, endDateTime);

        for(Appointment otherAppointment: getAllAppointments()) {
            Interval otherInterval = new Interval(otherAppointment.getStartDateTime(), otherAppointment.getEndDateTime());

            boolean appointmentIsOverlapping = appointmentInterval.isOverlapping(otherInterval);

            if(appointmentIsOverlapping) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd HH:mm");

                // If the overlapping appointment is the same appointment
                System.out.println("Check 1: " + appointment.getId() + " == " + otherAppointment.getId());
                if(appointment.getId() == otherAppointment.getId()) {
                    System.out.println("Same appointment. New time interval is valid.");
                    return true;
                }
                // If the overlapping appointment is with the same customer
                System.out.println("Check 2: " + appointment.getCustomerId() + " == " + otherAppointment.getCustomerId());
                if (appointment.getCustomerId() == otherAppointment.getCustomerId()) {
                    throw new Exception("The selected customer has an existing appointment (starting at " + formatter.format(otherAppointment.getStartDateTime())  +
                            " and ending at " + formatter.format(otherAppointment.getEndDateTime()) + ") that is overlapping with this appointment");
                }
                // If the overlapping appointment is with the same user
                System.out.println("Check 3: " + appointment.getUserId() + " == " + otherAppointment.getUserId());
                if (appointment.getUserId() == otherAppointment.getUserId()) {
                    throw new Exception("The selected user has an existing appointment (starting at " + formatter.format(otherAppointment.getStartDateTime())  +
                            " and ending at " + formatter.format(otherAppointment.getEndDateTime()) + ") that is overlapping with this appointment");
                }
            }
        }
        return true;
    }



}
