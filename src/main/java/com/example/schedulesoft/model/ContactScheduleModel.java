package com.example.schedulesoft.model;

import com.example.schedulesoft.domain.Appointment;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class ContactScheduleModel {

    private static final ContactScheduleModel instance = new ContactScheduleModel();

    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();

    private ContactScheduleModel() {
        // Private constructor to enforce singleton pattern
    }

    public static ContactScheduleModel getInstance() {
        return instance;
    }

    public ObservableList<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(ObservableList<Appointment> appointments) {
        this.appointments = appointments;
    }



}
