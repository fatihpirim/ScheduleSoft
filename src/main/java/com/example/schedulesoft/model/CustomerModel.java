package com.example.schedulesoft.model;

import com.example.schedulesoft.domain.Customer;

public class CustomerViewModel {

    private static CustomerViewModel instance = new CustomerViewModel();

    private Customer selectedCustomer;

    private CustomerViewModel() {
        // Private constructor to enforce singleton pattern
    }

    public static CustomerViewModel getInstance() {
        return instance;
    }

    public Customer getSelectedCustomer() {
        return selectedCustomer;
    }

    public void setSelectedCustomer(Customer customer) {
        this.selectedCustomer = customer;
    }

}
