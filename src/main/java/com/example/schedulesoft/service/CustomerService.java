package com.example.schedulesoft.service;

import com.example.schedulesoft.dao.CustomerDAO;
import com.example.schedulesoft.model.Customer;

public class CustomerService {

    private CustomerDAO customerDAO;

    public CustomerService() {
    }

    // save inserts or updates customer depending on if hte customer is in the table or not
    public boolean save(Customer customer) {


        return true;
    }

    // deletes customer if it is in the table
    public boolean delete(Customer customer) {

        return true;
    }


}
