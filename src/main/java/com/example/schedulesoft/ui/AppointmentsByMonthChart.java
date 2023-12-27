package com.example.schedulesoft.ui;

import com.example.schedulesoft.domain.Appointment;
import com.example.schedulesoft.service.AppointmentService;
import com.example.schedulesoft.util.AppConfig;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Creates a bar chart displaying the number of appointments in each month, starting from the current month and following with the next 11 months.
 * This is a UI class that creates a UI object that can be used a re-usable component.
 */
public class AppointmentsByMonthChart {

    private final ResourceBundle resources;
    private final CategoryAxis xAxis = new CategoryAxis();
    private final NumberAxis yAxis = new NumberAxis();
    private final BarChart<String,Number> chart = new BarChart<String,Number>(xAxis,yAxis);

    private final ZonedDateTime currentDateTime;


    public AppointmentsByMonthChart(ResourceBundle resources) {

        this.resources = resources;

        this.currentDateTime = ZonedDateTime.now();

        chart.setTitle(resources.getString("appointments_by_month"));
        xAxis.setLabel(resources.getString("month"));
        xAxis.setStyle("-fx-tick-label-rotation: 45;");
        yAxis.setLabel(resources.getString("no_of_appointments"));

        chart.setLegendVisible(false);

        XYChart.Series<String, Number> series1 = new XYChart.Series<String, Number>();

        List<ZonedDateTime> months = getMonths();
        HashMap<String, Integer> monthToNumberOfAppointments = getMonthToNumberOfAppointments();
        for(ZonedDateTime monthZDT: months) {
            String month = monthZDT.getMonth().toString();
            String translatedMonth = monthZDT.getMonth().getDisplayName(TextStyle.FULL, AppConfig.getResourceBundle().getLocale());
            int numberOfAppointments = monthToNumberOfAppointments.get(month);
            series1.getData().add(new XYChart.Data<>(translatedMonth, numberOfAppointments));
        }

        chart.getData().add(series1);
    }

    /**
     * @return a list containing a ZonedDateTime object at the start of the current month and the following 11 months.
     */
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

    /**
     * @return all persisted appointments
     */
    private List<Appointment> getAppointments() {
        AppointmentService appointmentService = new AppointmentService();
        return appointmentService.getAllAppointments();
    }

    /**
     * @param month month with appointments
     * @return all appointments in the month
     */
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

    /**
     *
     * @param month month with appointments
     * @return number of appointments in the month
     */
    private int getNumberOfAppointmentsInMonth(ZonedDateTime month) {
        List<Appointment> appointmentsInMonth = getAppointmentsInMonth(month);
        return appointmentsInMonth.size();
    }

    /**
     * @return a map of each month as a key and the number of appointments associated with it as a value
     */
    private HashMap<String, Integer> getMonthToNumberOfAppointments() {

        HashMap<String, Integer> monthToNumberOfAppointments = new HashMap<>();

        List<ZonedDateTime> months = getMonths();
        for(ZonedDateTime month: months) {
            Integer numberOfAppointments = getNumberOfAppointmentsInMonth(month);
            monthToNumberOfAppointments.put(month.getMonth().toString(), numberOfAppointments);
        }

        return monthToNumberOfAppointments;
    }

    /**
     * @return the chart object
     */
    public BarChart<String,Number> create() {
        return chart;
    }
}
