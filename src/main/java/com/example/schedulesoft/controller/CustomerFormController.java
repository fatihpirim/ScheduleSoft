package com.example.schedulesoft.controller;

import com.example.schedulesoft.PanelManager;
import com.example.schedulesoft.auth.SessionHolder;
import com.example.schedulesoft.domain.Customer;
import com.example.schedulesoft.enums.View;
import com.example.schedulesoft.model.CustomerModel;
import com.example.schedulesoft.util.AppConfig;
import com.example.schedulesoft.service.CustomerService;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

public class CustomerFormController implements Initializable {

    @FXML
    Label zoneIdLabel;

    @FXML
    ResourceBundle resources;

    @FXML
    TextField firstNameField;
    @FXML
    TextField lastNameField;
    @FXML
    TextField addressField;
    @FXML
    TextField postalCodeField;
    @FXML
    ComboBox<String> countryComboBox;
    @FXML
    ComboBox<String> regionComboBox;
    @FXML
    TextField phoneNumberField;
    @FXML
    TextField customerIdField;

    private final CustomerService customerService = new CustomerService();
    private final CustomerModel customerModel = CustomerModel.getInstance();

    private final boolean customerIsSelected = customerModel.getSelectedCustomer() != null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        zoneIdLabel.setText(AppConfig.getSystemZoneId().toString());

        Customer selectedCustomer = customerModel.getSelectedCustomer();

        if(customerIsSelected) {
            firstNameField.setText(selectedCustomer.getName().split(" ", 2)[0]);
            lastNameField.setText(selectedCustomer.getName().split(" ", 2)[1]);
            addressField.setText(selectedCustomer.getAddress());
            postalCodeField.setText(selectedCustomer.getPostalCode());
            countryComboBox.setValue("country"); // temporary
            regionComboBox.setValue("region"); // temporary
            phoneNumberField.setText(selectedCustomer.getPhoneNumber());
            customerIdField.setText(String.valueOf(selectedCustomer.getId()));
        }
    }

    @FXML
    private void onSave(Event event) {
        System.out.println("Clicked Save");

        // check if all forms are valid
        boolean allFormsAreValid = validateAllForms();
        if(!allFormsAreValid) {
            return;
        }

        String name = firstNameField.getText() + " " + lastNameField.getText();
        String address = addressField.getText();
        String postalCode = postalCodeField.getText();
        String phoneNumber = phoneNumberField.getText();
        ZonedDateTime createdOn = ZonedDateTime.now().withZoneSameInstant(ZoneOffset.UTC);
        String createdBy = SessionHolder.getInstance().getSession().getUser().getUsername();
        ZonedDateTime lastUpdated = ZonedDateTime.now().withZoneSameInstant(ZoneOffset.UTC);
        String lastUpdatedBy = SessionHolder.getInstance().getSession().getUser().getUsername();
        int divisionId = 29; // DEFAULT CHANGE LATER AFTER IMPLEMENTATION

        if(customerIsSelected) {
            //
            // UPDATE CUSTOMER
            //
            System.out.println("UPDATING CUSTOMER");
            Customer selectedCustomer = customerModel.getSelectedCustomer();
            selectedCustomer.setName(name);
            selectedCustomer.setAddress(address);
            selectedCustomer.setPostalCode(postalCode);
            selectedCustomer.setPhoneNumber(phoneNumber);
            selectedCustomer.setLastUpdated(lastUpdated); // set last updated to current time with offset to UTC
            selectedCustomer.setLastUpdatedBy(lastUpdatedBy); // set last updated by to the logged-in user
            // set the division id based on the country/region ** not yet implemented

            boolean customerWasSaved = customerService.saveCustomer(selectedCustomer);
            if(customerWasSaved) {
                PanelManager.changePanelTo(View.CustomerTable);
            }

        } else {
            //
            // ADD CUSTOMER
            //
            System.out.println("ADDING CUSTOMER");
            Customer newCustomer = new Customer(name, address, postalCode, phoneNumber, createdOn, createdBy, lastUpdated, lastUpdatedBy, divisionId);

            boolean customerWasSaved = customerService.saveCustomer(newCustomer);
            if(customerWasSaved) {
                PanelManager.changePanelTo(View.CustomerTable);
            }
        }

    }

    @FXML
    private void onCancel(Event event) {
        System.out.println("Clicked Cancel");

        // Throw warning "are you sure you want to exit without saving?"
        // Change view to customer table
        customerModel.setSelectedCustomer(null);
        PanelManager.changePanelTo(View.CustomerTable);
    }

    private void validateFirstName() throws Exception {
        StringBuilder errorMessage = new StringBuilder();
        if(firstNameField.getText().length() > 24) {
            errorMessage.append("First name is too long\n");
        }
        if(firstNameField.getText().isEmpty()) {
            errorMessage.append("First name is empty\n");
        }

        if(!errorMessage.isEmpty()) {
            throw new Exception(errorMessage.toString());
        }
    }

    private void validateLastName() throws Exception {
        StringBuilder errorMessage = new StringBuilder();
        if(lastNameField.getText().length() > 24) {
            errorMessage.append("Last name is too long.\n");
        }
        if(lastNameField.getText().isEmpty()) {
            errorMessage.append("Last name is empty.\n");
        }

        if(!errorMessage.isEmpty()) {
            throw new Exception(errorMessage.toString());
        }
    }

    private void validateAddress() throws Exception {
        StringBuilder errorMessage = new StringBuilder();
        if(addressField.getText().length() > 99) {
            errorMessage.append("Address is too long.\n");
        }
        if(addressField.getText().isEmpty()) {
            errorMessage.append("Address is empty.\n");
        }

        if(!errorMessage.isEmpty()) {
            throw new Exception(errorMessage.toString());
        }
    }

    private void validatePostalCode() throws Exception {
        StringBuilder errorMessage = new StringBuilder();
        if(postalCodeField.getText().length() > 49) {
            errorMessage.append("Postal code is too long.\n");
        }
        if(postalCodeField.getText().isEmpty()) {
            errorMessage.append("Postal code is empty.\n");
        }

        if(!errorMessage.isEmpty()) {
            throw new Exception(errorMessage.toString());
        }
    }

    private void validatePhoneNumber() throws Exception {
        StringBuilder errorMessage = new StringBuilder();
        if(postalCodeField.getText().length() > 20) {
            errorMessage.append("Phone number is too long.\n");
        }
        if(!phoneNumberField.getText().matches("^(?:(?:\\+|00)\\d{1,3})?[-.\\s]?\\(?" + "\\d{1,4}\\)?[-.\\s]?\\d{1,6}[-.\\s]?\\d{1,10}$")) {
            errorMessage.append("Invalid phone number.\n");
        }
        if(phoneNumberField.getText().isEmpty()) {
            errorMessage.append("Phone number is empty.\n");
        }

        if(!errorMessage.isEmpty()) {
            throw new Exception(errorMessage.toString());
        }
    }


    private boolean validateAllForms() {

        StringBuilder errorMessage = new StringBuilder();

        try {
            validateFirstName();
        } catch (Exception e) {
            errorMessage.append(e.getMessage());
        }

        try {
            validateLastName();
        } catch (Exception e) {
            errorMessage.append(e.getMessage());
        }

        try {
            validateAddress();
        } catch (Exception e) {
            errorMessage.append(e.getMessage());
        }

        try {
            validatePostalCode();
        } catch (Exception e) {
            errorMessage.append(e.getMessage());
        }

        try {
            validatePhoneNumber();
        } catch (Exception e) {
            errorMessage.append(e.getMessage());
        }

        System.out.println(errorMessage);

        return errorMessage.toString().isEmpty();
    }


}
