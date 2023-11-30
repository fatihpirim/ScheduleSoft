package com.example.schedulesoft.model;

import com.example.schedulesoft.domain.Customer;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Arrays;

public class CustomerModel {

    private static CustomerModel instance = new CustomerModel();

    private final ObjectProperty<Customer> selectedCustomer = new SimpleObjectProperty<>(null);

    private ObservableList<Customer> customers = FXCollections.observableArrayList();

    private CustomerModel() {
        // Private constructor to enforce singleton pattern
    }

    public static CustomerModel getInstance() {
        return instance;
    }

    public static void setInstance(CustomerModel instance) {
        CustomerModel.instance = instance;
    }

    public Customer getSelectedCustomer() {
        return selectedCustomer.get();
    }

    public ObjectProperty<Customer> selectedCustomerProperty() {
        return selectedCustomer;
    }

    public void setSelectedCustomer(Customer selectedCustomer) {
        this.selectedCustomer.set(selectedCustomer);
    }

    public ObservableList<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(ObservableList<Customer> customers) {
        this.customers = customers;
    }

    public void setCustomers(ArrayList<Customer> customers) {
        this.customers = FXCollections.observableArrayList(customers);
    }

}
