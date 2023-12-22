package com.example.schedulesoft.ui;

import com.example.schedulesoft.domain.Appointment;
import com.example.schedulesoft.service.AppointmentService;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AppointmentsByTypeChart {

    private final CategoryAxis xAxis = new CategoryAxis();
    private final NumberAxis yAxis = new NumberAxis();
    private final BarChart<String,Number> chart = new BarChart<String,Number>(xAxis,yAxis);

    public AppointmentsByTypeChart() {

        chart.setTitle("Appointments By Type");
        xAxis.setLabel("Type");
        yAxis.setLabel("No. of Appointments");

        chart.setLegendVisible(false);

        XYChart.Series<String, Number> series1 = new XYChart.Series<String, Number>();

        List<String> types = getTypes();
        HashMap<String, Integer> typeToNumberOfAppointments = getTypeToNumberOfAppointments();
        for(String type: types) {
            int numberOfAppointments = typeToNumberOfAppointments.get(type);
            series1.getData().add(new XYChart.Data<>(type, numberOfAppointments));
        }

        chart.getData().addAll(series1);
    }

    public List<String> getTypes() {
        return getAppointments().stream().map(Appointment::getType).distinct().toList();
    }

    private List<Appointment> getAppointments() {
        AppointmentService appointmentService = new AppointmentService();
        return appointmentService.getAllAppointments();
    }

    private List<Appointment> getAppointmentsWithType(String type) {

        List<Appointment> appointments = getAppointments();

        List<Appointment> appointmentsWithType = new ArrayList<>();

        for (Appointment appointment : appointments) {
           if(appointment.getType().equals(type)) {
               appointmentsWithType.add(appointment);
           }
        }

        return appointmentsWithType;
    }

    private int getNoOfAppointmentsWithType(String type) {
        List<Appointment> appointmentsWithType = getAppointmentsWithType(type);
        return appointmentsWithType.size();
    }

    private HashMap<String, Integer> getTypeToNumberOfAppointments() {

        HashMap<String, Integer> typeToNumberOfAppointments = new HashMap<>();

        List<String> types = getTypes();

        for(String type: types) {
            Integer numberOfAppointments = getNoOfAppointmentsWithType(type);
            typeToNumberOfAppointments.put(type, numberOfAppointments);
        }

        return typeToNumberOfAppointments;
    }

    public BarChart<String,Number> create() {
        return chart;
    }


}
