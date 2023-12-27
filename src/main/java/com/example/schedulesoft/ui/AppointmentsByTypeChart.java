package com.example.schedulesoft.ui;

import com.example.schedulesoft.domain.Appointment;
import com.example.schedulesoft.service.AppointmentService;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Creates a bar chart displaying the number of appointments under each appointment type
 * This is a UI class that creates a UI object that can be used a re-usable component.
 */
public class AppointmentsByTypeChart {

    private final ResourceBundle resources;
    private final CategoryAxis xAxis = new CategoryAxis();
    private final NumberAxis yAxis = new NumberAxis();
    private final BarChart<String,Number> chart = new BarChart<String,Number>(xAxis,yAxis);

    public AppointmentsByTypeChart(ResourceBundle resources) {

        this.resources = resources;

        chart.setTitle(resources.getString("appointments_by_type"));
        xAxis.setLabel(resources.getString("type"));
        yAxis.setLabel(resources.getString("no_of_appointments"));

        chart.setLegendVisible(false);

        XYChart.Series<String, Number> series1 = new XYChart.Series<String, Number>();

        List<String> types = getTypes();
        HashMap<String, Integer> typeToNumberOfAppointments = getTypeToNoOfAppointments();
        for(String type: types) {
            int numberOfAppointments = typeToNumberOfAppointments.get(type);
            series1.getData().add(new XYChart.Data<>(type, numberOfAppointments));
        }

        chart.getData().add(series1);
    }

    /**
     * Gets all appointments and applies a stream on it that maps each appointment to a type and filters distinct types.
     * @return list of all appointment types
     */
    public List<String> getTypes() {
        return getAppointments().stream().map(Appointment::getType).distinct().toList();
    }

    /**
     * @return all persisted appointments
     */
    private List<Appointment> getAppointments() {
        AppointmentService appointmentService = new AppointmentService();
        return appointmentService.getAllAppointments();
    }

    /**
     *
     * @param type type 0 or more appointments will have
     * @return all appointments with the specified type
     */
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

    /**
     *
     * @param type type 0 or more appointments will have
     * @return number of appointments with the specified type
     */
    private int getNoOfAppointmentsWithType(String type) {
        List<Appointment> appointmentsWithType = getAppointmentsWithType(type);
        return appointmentsWithType.size();
    }

    /**
     * @return a map of each type as a key and the number of appointments associated with it as a value
     */
    private HashMap<String, Integer> getTypeToNoOfAppointments() {

        HashMap<String, Integer> typeToNumberOfAppointments = new HashMap<>();

        List<String> types = getTypes();

        for(String type: types) {
            Integer numberOfAppointments = getNoOfAppointmentsWithType(type);
            typeToNumberOfAppointments.put(type, numberOfAppointments);
        }

        return typeToNumberOfAppointments;
    }

    /**
     * @return the chart object
     */
    public BarChart<String,Number> create() {
        return chart;
    }


}
