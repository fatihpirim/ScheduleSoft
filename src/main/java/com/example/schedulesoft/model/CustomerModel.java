package com.example.schedulesoft.model;

import com.example.schedulesoft.domain.Customer;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * The purpose of this class is to integrate persisted customer data with javafx classes.
 * It is also a local, temporary copy of customer data in heap
 * <br>
 * Ex: For example, selecting a customer in a table and having a reference to the selected customer
 * when the view is switched from customer table to the customer form
 * <br>
 * This class is implemented using Singleton pattern.
 */
public class CustomerModel {

    private static final CustomerModel instance = new CustomerModel();

    /**
     * Customer currently selected (in table0
     */
    private final ObjectProperty<Customer> selectedCustomer = new SimpleObjectProperty<>(null);

    /**
     * Local copy of all customers
     */
    private ObservableList<Customer> customers = FXCollections.observableArrayList();

    private CustomerModel() {
        // Private constructor to enforce singleton pattern
    }

    public static CustomerModel getInstance() {
        return instance;
    }

    /**
     * @return selected customer
     */
    public Customer getSelectedCustomer() {
        return selectedCustomer.get();
    }

    public ObjectProperty<Customer> selectedCustomerProperty() {
        return selectedCustomer;
    }

    /**
     * @param selectedCustomer new selected customer
     */
    public void setSelectedCustomer(Customer selectedCustomer) {
        this.selectedCustomer.set(selectedCustomer);
    }

    /**
     * @return get all customers
     */
    public ObservableList<Customer> getCustomers() {
        return customers;
    }

    /**
     * @param customers customers to set all customers to
     */
    public void setCustomers(ObservableList<Customer> customers) {
        this.customers = customers;
    }

    /**
     * @param customers customers to set all customers to
     */
    public void setCustomers(ArrayList<Customer> customers) {
        this.customers = FXCollections.observableArrayList(customers);
    }

    /**
     * @return unselect the selected customer
     */
    public boolean removeSelectedCustomer() {

        Customer customer = getSelectedCustomer();
        setSelectedCustomer(null);
        return customers.remove(customer);
    }

}
