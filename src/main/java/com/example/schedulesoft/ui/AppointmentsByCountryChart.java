package com.example.schedulesoft.ui;

import com.example.schedulesoft.domain.Appointment;
import com.example.schedulesoft.domain.Country;
import com.example.schedulesoft.domain.Customer;
import com.example.schedulesoft.domain.Division;
import com.example.schedulesoft.service.AppointmentService;
import com.example.schedulesoft.service.CountryService;
import com.example.schedulesoft.service.CustomerService;
import com.example.schedulesoft.service.DivisionService;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class AppointmentsByCountryChart {

    private final ResourceBundle resources;
    private final CategoryAxis xAxis = new CategoryAxis();
    private final NumberAxis yAxis = new NumberAxis();
    private final BarChart<String,Number> chart = new BarChart<String,Number>(xAxis,yAxis);

    public AppointmentsByCountryChart(ResourceBundle resources) {

        this.resources = resources;

        chart.setTitle(resources.getString("appointments_by_country"));
        xAxis.setLabel(resources.getString("country"));
        xAxis.setStyle("-fx-tick-label-rotation: 45;");
        yAxis.setLabel(resources.getString("no_of_appointments"));

        chart.setLegendVisible(false);

        XYChart.Series<String, Number> series1 = new XYChart.Series<String, Number>();

        List<String> countries = getCountries();
        HashMap<String, Integer> countryToNumberOfAppointments = getCountryToNumberOfAppointments();
        for(String country: countries) {
            int numberOfAppointments = countryToNumberOfAppointments.get(country);
            series1.getData().add(new XYChart.Data<>(country, numberOfAppointments));
        }

        chart.getData().add(series1);

    }

    private List<Appointment> getAppointments() {
        AppointmentService appointmentService = new AppointmentService();
        return appointmentService.getAllAppointments();
    }
    private List<String> getCountries() {
        CountryService countryService = new CountryService();
        return countryService.getAllCountries().stream().map(Country::getName).toList();
    }

    private Country getCountry(Appointment appointment) {

        CustomerService customerService = new CustomerService();
        int customerId = appointment.getCustomerId();
        Customer customer = customerService.getCustomerById(customerId);

        DivisionService divisionService = new DivisionService();
        int customerDivisionId = customer.getDivisionId();
        Division customerDivision = divisionService.getDivisionById(customerDivisionId);

        CountryService countryService = new CountryService();
        int customerDivisionCountryId = customerDivision.getCountryId();

        return countryService.getCountryById(customerDivisionCountryId);
    }
    private List<Appointment> getAppointmentsWithCountry(String country) {

        List<Appointment> appointments = getAppointments();

        List<Appointment> appointmentsWithCountry = new ArrayList<>();

        for (Appointment appointment : appointments) {

            String appointmentCountry = getCountry(appointment).getName();

            if(appointmentCountry.equals(country)) {
                appointmentsWithCountry.add(appointment);
            }
        }

        return appointmentsWithCountry;
    }
    private int getNoOfAppointmentsWithCountry(String country) {
        List<Appointment> appointmentsWithCountry = getAppointmentsWithCountry(country);
        return appointmentsWithCountry.size();
    }

    private HashMap<String, Integer> getCountryToNumberOfAppointments() {

        HashMap<String, Integer> countryToNumberOfAppointments = new HashMap<>();

        List<String> countries = getCountries();

        for(String country: countries) {
            Integer numberOfAppointments = getNoOfAppointmentsWithCountry(country);
            countryToNumberOfAppointments.put(country, numberOfAppointments);
        }

        return  countryToNumberOfAppointments;
    }

    public BarChart<String,Number> create() {
        return chart;
    }


}
