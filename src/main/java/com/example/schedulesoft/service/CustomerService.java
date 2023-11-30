package com.example.schedulesoft.service;

import com.example.schedulesoft.dao.CustomerDAO;
import com.example.schedulesoft.dto.CustomerDTO;
import com.example.schedulesoft.mapper.CustomerMapper;
import com.example.schedulesoft.domain.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerService {

    private final CustomerDAO customerDAO = new CustomerDAO();

    public CustomerService() {
    }

    // save inserts or updates customer depending on if hte customer is in the table or not
    public boolean saveCustomer(Customer customer) {

        CustomerDTO customerDTO = CustomerMapper.toDto(customer);

        // if customer does NOT have an id
        if(customer.getId() == 0 ) {
            // add the customer
            int result = customerDAO.insert(customerDTO);
            if(result == 1) {
                System.out.println("Customer added successfully");
                return true;
            } else {
                System.out.println("Customer was unable to be added");
                return false;
            }
        // if the customer does have an id
        } else {
            // update the customer
            int result = customerDAO.update(customerDTO);
            if(result == 1) {
                System.out.println("Customer updated successfully");
                return true;
            } else {
                System.out.println("Customer was unable to be updated");
                return false;
            }
        }
    }

    // deletes customer if it is in the table
    public boolean deleteCustomer(Customer customer) {

        CustomerDTO customerDTO = CustomerMapper.toDto(customer);

        // !!! delete all appointments of the customer

        int result = customerDAO.delete(customerDTO);
        if(result == 1) {
            System.out.println("Customer deleted successfully");
            return true;
        } else {
            System.out.println("Customer was unable to be deleted");
            return false;
        }

    }

    public ArrayList<Customer> getAllCustomers() {

        List<CustomerDTO> customerDTOs = customerDAO.getAll();

        return customerDTOs.stream()
                .map(CustomerMapper::toCustomer)
                .collect(Collectors.toCollection(ArrayList::new));

    }



}
