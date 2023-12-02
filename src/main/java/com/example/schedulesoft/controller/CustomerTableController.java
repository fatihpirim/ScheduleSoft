package com.example.schedulesoft.controller;

import com.example.schedulesoft.model.CustomerModel;
import com.example.schedulesoft.util.AppConfig;
import com.example.schedulesoft.PanelManager;
import com.example.schedulesoft.enums.View;
import com.example.schedulesoft.domain.Customer;
import com.example.schedulesoft.service.CustomerService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;


/*
    The customerTable is re-initialized with the most up-to-date data
    since going back and forth between the CustomerForm re-initializes
    the controller. As a result, the ObservableList in the CustomerModel
    does not need to be modified on addition/update of a Customer. However,
    deleting does necessitate updating the models observable list. This behavior
    may be changed later.
 */
public class CustomerTableController implements Initializable {

    @FXML
    Label zoneIdLabel;

    @FXML
    ResourceBundle resources;

    @FXML
    private TableView<Customer> customerTable;

    @FXML
    private Button addButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;

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

        setCellValueFactoryOfColumns(); // method extraction

        customerModel.setCustomers(customerService.getAllCustomers());

        customerTable.setItems(customerModel.getCustomers());

        editButton.setVisible(false);
        deleteButton.setVisible(false);

        customerTable.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {

            // cool use of ternary operator
            System.out.println("Currently selected: " +  ((newSelection != null) ? newSelection.getName() : "N/A") + ". Previously selected: " +
                    ((oldSelection != null) ? oldSelection.getName() : "N/A"));

            customerModel.setSelectedCustomer(newSelection);

            editButton.setVisible(true);
            deleteButton.setVisible(true);

        });

        System.out.println(customerModel.getCustomers());
    }

    @FXML
    private void onAdd() {
        System.out.println("Clicked Add (customer)");

        customerModel.setSelectedCustomer(null);

        PanelManager.changePanelTo(View.CustomerForm);
    }

    @FXML
    private void onEdit() {
        System.out.println("Clicked Edit (customer)");

        PanelManager.changePanelTo(View.CustomerForm);
    }

    @FXML
    private void onDelete() {
        System.out.println("Clicked Delete (customer)");

        // Deleting customer will delete all its appointments (notify user)

        customerService.deleteCustomer(customerModel.getSelectedCustomer());

        customerModel.removeSelectedCustomer();

        // Notify user of deletion
    }

    private void setCellValueFactoryOfColumns() {

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

}
