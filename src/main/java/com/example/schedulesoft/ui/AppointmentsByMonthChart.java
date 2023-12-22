package com.example.schedulesoft.ui;

import com.example.schedulesoft.domain.Appointment;
import com.example.schedulesoft.service.AppointmentService;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AppointmentsByMonthChart {

    private final CategoryAxis xAxis = new CategoryAxis();
    private final NumberAxis yAxis = new NumberAxis();
    private final BarChart<String,Number> chart = new BarChart<String,Number>(xAxis,yAxis);

    private final ZonedDateTime currentDateTime;

    public AppointmentsByMonthChart() {

        this.currentDateTime = ZonedDateTime.now();

        chart.setTitle("Appointments By Month");
        xAxis.setLabel("Month");
        xAxis.setStyle("-fx-tick-label-rotation: 45;");
        yAxis.setLabel("No. of Appointments");

        chart.setLegendVisible(false);

        XYChart.Series<String, Number> series1 = new XYChart.Series<String, Number>();

        List<ZonedDateTime> months = getMonths();
        HashMap<String, Integer> monthToNumberOfAppointments = getMonthToNumberOfAppointments();
        for(ZonedDateTime monthZDT: months) {
            String month = monthZDT.getMonth().toString();
            int numberOfAppointments = monthToNumberOfAppointments.get(month);
            series1.getData().add(new XYChart.Data<>(month, numberOfAppointments));
        }

        chart.getData().add(series1);

//        for (XYChart.Data<String, Number> data : series1.getData()) {
//            Tooltip tooltip = new Tooltip("Hello" + data.getYValue().toString());
//            Tooltip.install(data.getNode(), tooltip);
//        }
    }

    private List<ZonedDateTime> getMonths() {

        int currentMonth = currentDateTime.getMonthValue();
        int currentYear = currentDateTime.getYear();

        List<ZonedDateTime> monthsList = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            int month = currentMonth + i;
            int year = currentYear + (month - 1) / 12;  // Adjust the year for overflow
            month = (month - 1) % 12 + 1;  // Adjust the month to the correct range
            ZonedDateTime firstDayOfMonth = currentDateTime.withYear(year).withMonth(month).withDayOfMonth(1);
            monthsList.add(firstDayOfMonth);
        }
        return monthsList;
    }

    private List<Appointment> getAppointments() {
        AppointmentService appointmentService = new AppointmentService();
        return appointmentService.getAllAppointments();
    }

    private List<Appointment> getAppointmentsInMonth(ZonedDateTime month) {

        // Get all appointments
        List<Appointment> appointments = getAppointments();

        List<Appointment> appointmentsInMonth = new ArrayList<>();

        // For each appointment, add the appointment if its start time is in the month
        for (Appointment appointment : appointments) {
            ZonedDateTime startDateTime = appointment.getStartDateTime();
            if((startDateTime.equals(month) || (startDateTime.isAfter(month) && startDateTime.isBefore(month.plusMonths(1))))) {
                appointmentsInMonth.add(appointment);
            }
        }

        // Return all appointment in the month
        return appointmentsInMonth;
    }

    private int getNumberOfAppointmentsInMonth(ZonedDateTime month) {
        List<Appointment> appointmentsInMonth = getAppointmentsInMonth(month);
        return appointmentsInMonth.size();
    }

    private HashMap<String, Integer> getMonthToNumberOfAppointments() {

        HashMap<String, Integer> monthToNumberOfAppointments = new HashMap<>();

        List<ZonedDateTime> months = getMonths();
        for(ZonedDateTime month: months) {
            Integer numberOfAppointments = getNumberOfAppointmentsInMonth(month);
            monthToNumberOfAppointments.put(month.getMonth().toString(), numberOfAppointments);
        }

        return monthToNumberOfAppointments;
    }

    public BarChart<String,Number> create() {
        return chart;
    }
}
