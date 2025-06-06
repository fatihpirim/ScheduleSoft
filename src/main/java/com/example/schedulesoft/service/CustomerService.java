package com.example.schedulesoft.service;

import com.example.schedulesoft.dao.CustomerDAO;
import com.example.schedulesoft.domain.Country;
import com.example.schedulesoft.dto.CustomerDTO;
import com.example.schedulesoft.mapper.CountryMapper;
import com.example.schedulesoft.mapper.CustomerMapper;
import com.example.schedulesoft.domain.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Intermediary between controllers (and other objects) and the customer data access object (DAO).
 */
public class CustomerService {

    private final CustomerDAO customerDAO = new CustomerDAO();

    public CustomerService() {
    }

    /**
     * Maps Customer object to dto
     * If the Customer does not have an id, adds customer to database
     * If the customer DOES have an id, updates the existing customer in the database
     * @param customer Customer being saved
     * @return true if saved successfully
     */
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

    /**
     * Maps Customer to DTO
     * Deletes customer from database
     * @param customer Customer being deleted
     * @return true if deleted successfully
     */
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

    /**
     * @param id customer id
     * @return Customer with id
     */
    public Customer getCustomerById(int id) {

        return CustomerMapper.toCustomer(customerDAO.getById(id));

    }

    /**
     * @return get all customers in database
     */
    public ArrayList<Customer> getAllCustomers() {

        List<CustomerDTO> customerDTOs = customerDAO.getAll();

        return customerDTOs.stream()
                .map(CustomerMapper::toCustomer)
                .collect(Collectors.toCollection(ArrayList::new));

    }

}
