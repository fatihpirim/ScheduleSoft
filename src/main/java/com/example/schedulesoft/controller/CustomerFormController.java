package com.example.schedulesoft.controller;

import com.example.schedulesoft.enums.Severity;
import com.example.schedulesoft.ui.Toast;
import com.example.schedulesoft.util.PanelManager;
import com.example.schedulesoft.auth.SessionHolder;
import com.example.schedulesoft.domain.Country;
import com.example.schedulesoft.domain.Customer;
import com.example.schedulesoft.domain.Division;
import com.example.schedulesoft.enums.View;
import com.example.schedulesoft.model.CustomerModel;
import com.example.schedulesoft.service.CountryService;
import com.example.schedulesoft.service.DivisionService;
import com.example.schedulesoft.util.AppConfig;
import com.example.schedulesoft.service.CustomerService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
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
    ComboBox<Country> countryComboBox;
    @FXML
    ComboBox<Division> divisionComboBox;
    @FXML
    TextField phoneNumberField;
    @FXML
    TextField customerIdField;

    @FXML
    Button saveButton;

    private final CustomerService customerService = new CustomerService();
    private final CustomerModel customerModel = CustomerModel.getInstance();

    private final boolean customerIsSelected = customerModel.getSelectedCustomer() != null;

    private final CountryService countryService = new CountryService();
    private final DivisionService divisionService = new DivisionService();

    @Override
    public void initialize(URL url, ResourceBundle resources) {

        this.resources = resources;

        zoneIdLabel.setText(AppConfig.getAppZoneId().toString());

        Customer selectedCustomer = customerModel.getSelectedCustomer();

        if(customerIsSelected) {
            firstNameField.setText(selectedCustomer.getName().split(" ", 2)[0]);
            lastNameField.setText(selectedCustomer.getName().split(" ", 2)[1]);

            addressField.setText(selectedCustomer.getAddress());
            postalCodeField.setText(selectedCustomer.getPostalCode());

            phoneNumberField.setText(selectedCustomer.getPhoneNumber());
            customerIdField.setText(String.valueOf(selectedCustomer.getId()));

            int customerDivisionId = selectedCustomer.getDivisionId();
            Division customerDivision = divisionService.getDivisionById(customerDivisionId);
            int customerDivisionCountryId = customerDivision.getCountryId();
            Country customerCountry = countryService.getCountryById(customerDivisionCountryId);

            ObservableList<Country> countryOptions = FXCollections.observableArrayList(countryService.getAllCountries());
            countryComboBox.setItems(countryOptions);
            countryComboBox.setValue(customerCountry);

            ObservableList<Division> divisionOptions = FXCollections.observableArrayList(divisionService.getAllDivisionsByCountryId(customerDivisionCountryId));
            divisionComboBox.setItems(divisionOptions);
            divisionComboBox.setValue(customerDivision);
        } else {
            ObservableList<Country> countryOptions = FXCollections.observableArrayList(countryService.getAllCountries());
            countryComboBox.setItems(countryOptions);
            Country defaultCountry = countryOptions.get(0);
            countryComboBox.setValue(defaultCountry);
            int defaultCountryId = defaultCountry.getId();

            ObservableList<Division> divisionOptions = FXCollections.observableArrayList(divisionService.getAllDivisionsByCountryId(defaultCountryId));
            divisionComboBox.setItems(divisionOptions);
            Division defaultDivision = divisionOptions.get(0);
            divisionComboBox.setValue(defaultDivision);
        }


        countryComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldCountry, newCountry) -> {
            int newCountryId = newCountry.getId();
            ObservableList<Division> divisionOptions = FXCollections.observableArrayList(divisionService.getAllDivisionsByCountryId(newCountryId));
            divisionComboBox.setItems(divisionOptions);
            Division newDivision = divisionOptions.get(0);
            divisionComboBox.setValue(newDivision);

        });
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
        ZonedDateTime createdOn = ZonedDateTime.now();
        String createdBy = SessionHolder.getInstance().getSession().getUser().getUsername();
        ZonedDateTime lastUpdated = ZonedDateTime.now();
        String lastUpdatedBy = SessionHolder.getInstance().getSession().getUser().getUsername();
        int divisionId = divisionComboBox.getValue().getId();
        System.out.println("DIVISION IS SAVED: " + divisionId);
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
            selectedCustomer.setLastUpdated(lastUpdated);
            selectedCustomer.setLastUpdatedBy(lastUpdatedBy);
            selectedCustomer.setDivisionId(divisionId);

            boolean customerWasSaved = customerService.saveCustomer(selectedCustomer);
            if(customerWasSaved) {
                Stage stage = (Stage) saveButton.getScene().getWindow();
                Toast toast = new Toast(resources.getString("success"), resources.getString("updated_customer")  + " " +
                        selectedCustomer.getId(), Severity.SUCCESS);
                toast.show(stage);

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
                Stage stage = (Stage) saveButton.getScene().getWindow();
                Toast toast = new Toast(resources.getString("success"), resources.getString("added_customer") + " " + name, Severity.SUCCESS);
                toast.show(stage);

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

        if(!errorMessage.toString().isEmpty()) {
            System.out.println(errorMessage);
            showErrorAlert(errorMessage.toString());
        }

        return errorMessage.toString().isEmpty();
    }

    private void showErrorAlert(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Please fix the following errors:");
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }


}
