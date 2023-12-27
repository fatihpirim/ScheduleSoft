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
        AppointmentDTO appointmentDTO = AppointmentMapper.toDto(appointment);

        // !!! delete all appointments of the customer

        int result = appointmentDAO.delete(appointmentDTO);
        if(result == 1) {
            System.out.println("Appointment deleted successfully");
            return true;
        } else {
            System.out.println("Appointment was unable to be deleted");
            return false;
        }
    }

    public ArrayList<Appointment> getAllAppointments() {
        List<AppointmentDTO> appointmentDTOs = appointmentDAO.getAll();

        return appointmentDTOs.stream()
                .map(AppointmentMapper::toAppointment)
                .collect(Collectors.toCollection(ArrayList::new));

    }

    public boolean appointmentIntervalIsValid(Appointment appointment) throws Exception {

        ZonedDateTime startDateTime = appointment.getStartDateTime();
        ZonedDateTime endDateTime = appointment.getEndDateTime();

        Interval appointmentInterval = new Interval(startDateTime, endDateTime);

        for(Appointment otherAppointment: getAllAppointments()) {
            Interval otherInterval = new Interval(otherAppointment.getStartDateTime(), otherAppointment.getEndDateTime());

            boolean appointmentIsOverlapping = appointmentInterval.isOverlapping(otherInterval);

            if(appointmentIsOverlapping && appointment.getId() != otherAppointment.getId()) {

                System.out.println(appointment.getTitle() + " is overlapping with " + otherAppointment.getTitle());

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd HH:mm");
                ZonedDateTime start = otherAppointment.getStartDateTime().withZoneSameInstant(ZoneId.systemDefault());
                ZonedDateTime end = otherAppointment.getEndDateTime().withZoneSameInstant(ZoneId.systemDefault());

                if (appointment.getCustomerId() == otherAppointment.getCustomerId()) {
                    System.out.println("StarDateTime" + otherAppointment.getStartDateTime());

                    throw new Exception("The selected customer has an existing appointment (starting at " + formatter.format(start)  +
                            " and ending at " + formatter.format(end) + ") that is overlapping with this appointment");
                }
                // If the overlapping appointment is with the same user
                if (appointment.getUserId() == otherAppointment.getUserId()) {
                    throw new Exception("The selected user has an existing appointment (starting at " + formatter.format(start)  +
                            " and ending at " + formatter.format(end) + ") that is overlapping with this appointment");
                }
            } else {
                System.out.println(appointment.getTitle() + " is NOT overlapping or Same as " + otherAppointment.getTitle());
            }
        }
        return true;
    }

    public Appointment getAppointmentWithinInterval(Interval interval) throws Exception {

        ZonedDateTime startDateTime = interval.getStartDateTime();
        List<Appointment> appointments = getAllAppointments();

        for(Appointment appointment: appointments) {
            Interval apptInterval = new Interval(appointment.getStartDateTime(), appointment.getEndDateTime());
            if(interval.isOverlapping(apptInterval) &&
                    (startDateTime.isBefore(appointment.getStartDateTime())) || (startDateTime.equals(appointment.getStartDateTime()))) {
                return appointment;
            }
        }
        return null;
    }


}
