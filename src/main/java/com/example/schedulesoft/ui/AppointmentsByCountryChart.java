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

/**
 * Creates a bar chart displaying the number of appointments associated with each country.
 * This is a UI class that creates a UI object that can be used a re-usable component.
 *
 * <p>
 *     This is the extra report asked for in the requirements.
 * </p>
 */
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

    /**
     * @return all persisted appointments
     */
    private List<Appointment> getAppointments() {
        AppointmentService appointmentService = new AppointmentService();
        return appointmentService.getAllAppointments();
    }

    /**
     * @return all persisted countries
     */
    private List<String> getCountries() {
        CountryService countryService = new CountryService();
        return countryService.getAllCountries().stream().map(Country::getName).toList();
    }

    /**
     *
     * Customer id is read from appointment and used to fetch Customer from database.
     * The division id is read from the customer and used to fetch Division from database.
     * The country id is read from the division and used to fetch the Country from the database.
     *
     * @param appointment appointment with a customer in a country
     * @return country associated with appointments
     */
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

    /**
     * @param country country which has 0 or more appointments associated with it
     * @return all appointments associated with the country
     */
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

    /**
     * @param country country which has 0 or more appointments associated with it
     * @return the number of appointments associated with the country
     */
    private int getNoOfAppointmentsWithCountry(String country) {
        List<Appointment> appointmentsWithCountry = getAppointmentsWithCountry(country);
        return appointmentsWithCountry.size();
    }

    /**
     * @return a map of each country as a key and the number of appointments associated with it as a value
     */
    private HashMap<String, Integer> getCountryToNumberOfAppointments() {

        HashMap<String, Integer> countryToNumberOfAppointments = new HashMap<>();

        List<String> countries = getCountries();

        for(String country: countries) {
            Integer numberOfAppointments = getNoOfAppointmentsWithCountry(country);
            countryToNumberOfAppointments.put(country, numberOfAppointments);
        }

        return countryToNumberOfAppointments;
    }

    /**
     * @return the chart object
     */
    public BarChart<String,Number> create() {
        return chart;
    }


}
