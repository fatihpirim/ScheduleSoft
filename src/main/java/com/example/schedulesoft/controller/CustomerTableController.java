package com.example.schedulesoft.controller;

import com.example.schedulesoft.model.CustomerModel;
import com.example.schedulesoft.util.AppConfig;
import com.example.schedulesoft.PanelManager;
import com.example.schedulesoft.enums.View;
import com.example.schedulesoft.domain.Customer;
import com.example.schedulesoft.service.CustomerService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;


public class CustomerTableController implements Initializable {

    @FXML
    Label zoneIdLabel;

    @FXML
    ResourceBundle resources;

    @FXML
    private TableView<Customer> customerTable;

    @FXML
    private TableColumn<Customer, Number> idCol;
    @FXML
    private TableColumn<Customer, String> nameCol;
    @FXML
    private TableColumn<Customer, String> addressCol;
    @FXML
    private TableColumn<Customer, String> postalCodeCol;
    @FXML
    private TableColumn<Customer, String> countryCol;
    @FXML
    private TableColumn<Customer, String> divisionCol;
    @FXML
    private TableColumn<Customer, String> phoneCol;


    private final CustomerService customerService = new CustomerService();
    private final CustomerModel customerModel = CustomerModel.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        zoneIdLabel.setText(AppConfig.getSystemZoneId().toString());

        configureColumns();

        populateTable();

        monitorCustomerSelection();
    }

    @FXML
    private void onAdd() {
        System.out.println("Clicked Add (customer)");

        PanelManager.changePanelTo(View.CustomerForm);
    }

    @FXML
    private void onEdit() {

    }

    @FXML
    private void onDelete() {

    }

    private void configureColumns() {

        idCol.setCellValueFactory(customer -> {
            int id = customer.getValue().getId();
            return new SimpleIntegerProperty(id);
        });

        nameCol.setCellValueFactory(customer -> {
            String name = customer.getValue().getName();
            return new SimpleStringProperty(name);
        });

        addressCol.setCellValueFactory(customer -> {
            String address = customer.getValue().getAddress();
            return new SimpleStringProperty(address);
        });

        postalCodeCol.setCellValueFactory(customer -> {
            String postalCode = customer.getValue().getPostalCode();
            return new SimpleStringProperty(postalCode);
        });

        countryCol.setCellValueFactory(customer -> {
            String country = "country"; // implement later
            return new SimpleStringProperty(country);
        });

        divisionCol.setCellValueFactory(customer -> {
            String division = "division";
            return new SimpleStringProperty(division);
        });

        phoneCol.setCellValueFactory(customer -> {
            String phone = customer.getValue().getPhoneNumber();
            return new SimpleStringProperty(phone);
        });

    }

    private void populateTable()  {

        customerModel.setCustomers(customerService.getAllCustomers());
        customerTable.setItems(customerModel.getCustomers());
    }

    private void monitorCustomerSelection() {

        // listens for selection of a customer by mouse clicking a customer on the table GUI
        customerTable.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {

            // cool use of ternary operator
            System.out.println("Currently selected: " + newSelection.getName() + ". Previously selected: " +
                    ((oldSelection != null) ? oldSelection.getName() : "N/A"));

            CustomerModel.getInstance().setSelectedCustomer(newSelection);
        });

        // listens for selection through model (newly created customer is selected upon being saved)
        CustomerModel.getInstance().selectedCustomerProperty().addListener((observable, oldSelection, newSelection) -> {

            if(newSelection == null) {
                customerTable.getSelectionModel().clearSelection();
            } else {

                customerTable.getSelectionModel().select(newSelection);
            }
        });


    }

}
