package com.example.schedulesoft.controller;

import com.example.schedulesoft.domain.*;
import com.example.schedulesoft.enums.Severity;
import com.example.schedulesoft.model.AppointmentModel;
import com.example.schedulesoft.model.CustomerModel;
import com.example.schedulesoft.service.AppointmentService;
import com.example.schedulesoft.service.CountryService;
import com.example.schedulesoft.service.DivisionService;
import com.example.schedulesoft.ui.Toast;
import com.example.schedulesoft.util.AppConfig;
import com.example.schedulesoft.util.PanelManager;
import com.example.schedulesoft.enums.View;
import com.example.schedulesoft.service.CustomerService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;


/**
 * Controller managing the interactions between the customer table (view) and the customer model and table (back-end)
 */
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

    private final AppointmentService appointmentService = new AppointmentService();
    private final AppointmentModel appointmentModel = AppointmentModel.getInstance();
    private final CountryService countryService = new CountryService();
    private final DivisionService divisionService = new DivisionService();

    @Override
    public void initialize(URL url, ResourceBundle resources) {

        this.resources = resources;

        PanelManager.changePanelTo(View.CustomerForm);

        zoneIdLabel.setText(AppConfig.getAppZoneId().toString());

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

    /**
     * Sets selected customer to null in model and changes view to customer form
     */
    @FXML
    private void onAdd() {
        System.out.println("Clicked Add (customer)");

        customerModel.setSelectedCustomer(null);

        PanelManager.changePanelTo(View.CustomerForm);
    }

    /**
     * Changes view to customer form
     */
    @FXML
    private void onEdit() {
        System.out.println("Clicked Edit (customer)");

        PanelManager.changePanelTo(View.CustomerForm);
    }

    /**
     * Attempts to delete the selected customer in table from the database
     *
     * <p>
     *     An alert dialog box will be shown indicating that the customer's appointments will be deleted if the customer is deleted
     *     If the users selects "OK", the customer's appointments will be deleted along with the customer
     * </p>
     */
    @FXML
    private void onDelete() {
        System.out.println("Clicked Delete (customer)");

        Customer customer = customerModel.getSelectedCustomer();

        List<Appointment> appointments = getAppointments(customer);
        boolean customerHasNoAppointments = appointments.isEmpty();

        if(customerHasNoAppointments) {
            System.out.println("The customer " + customer.getName() + " has no appointments.");

            String headerText = resources.getString("customer_deletion_confirmation");
            String contentText = customer.getName() + " with id: " + customer.getId();

            Optional<ButtonType> confirmation = showConfirmationAlert(headerText, contentText);

            if(confirmation.isPresent() && confirmation.get() == ButtonType.OK) {
                System.out.println("Deleting " + customer.getName() + " with id: " + customer.getId() +"\n");

                boolean customerIsDeleted = customerService.deleteCustomer(customerModel.getSelectedCustomer());
                if(customerIsDeleted) {
                    System.out.println("Deleted customer");
                    customerModel.removeSelectedCustomer();
                    Stage stage = (Stage) deleteButton.getScene().getWindow();
                    Toast toast = new Toast(resources.getString("success"), resources.getString("deleted_customer") + " " +
                            customer.getId(), Severity.SUCCESS);
                    toast.show(stage);
                }

            } else if(confirmation.isPresent() && confirmation.get() == ButtonType.CANCEL) {
                System.out.println("Cancelled deletion");
            }

        } else {
            System.out.println(customer.getName() + " " + resources.getString("has_following_appts") +"\n" + appointments);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd HH:mm");

            String headerText = resources.getString("are_you_sure");
            StringBuilder contentText = new StringBuilder();
            contentText.append(customer.getName() + " "+ resources.getString("with_id") + " " + customer.getId() +"\n\n");
            contentText.append(resources.getString("deleting_the_customer_will_delete_appts") + " \n\n");
            appointments.forEach(appointment -> {
                String formattedAppointment = appointment.getTitle() + " (" + formatter.format(appointment.getStartDateTime()) + " - " +
                        formatter.format(appointment.getEndDateTime()) + ") \n";
                contentText.append(formattedAppointment);
            });

            Optional<ButtonType> confirmation = showConfirmationAlert(headerText, contentText.toString());

            if(confirmation.isPresent() && confirmation.get() == ButtonType.OK) {
                System.out.println("Deleting " + customer.getName() + " with id: " + customer.getId() +"\n");
                System.out.println("Deleting the customers appointments as well");

                appointments.forEach(appointment -> {
                    System.out.println("Deleting " + appointment);
                    boolean appointmentDeleted = appointmentService.deleteAppointment(appointment);
                    if(appointmentDeleted) {
                        appointmentModel.removeSelectedCustomer();
                    }
                });

                if(getAppointments(customer).isEmpty()) {
                    boolean customerIsDeleted = customerService.deleteCustomer(customerModel.getSelectedCustomer());
                    if(customerIsDeleted) {
                        customerModel.removeSelectedCustomer();
                    }
                }

            } else if(confirmation.isPresent() && confirmation.get() == ButtonType.CANCEL) {
                System.out.println("Cancelled deletion");
            }
        }
    }

    /**
     * Sets the cell value factory for the customer table columns
     */
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
            int divisionId = customer.getValue().getDivisionId();
            Division division = divisionService.getDivisionById(divisionId);
            int countryId = division.getCountryId();
            Country country = countryService.getCountryById(countryId);

            return new SimpleStringProperty(country.getName());
        });

        divisionCol.setCellValueFactory(customer -> {
            int divisionId = customer.getValue().getDivisionId();
            Division division = divisionService.getDivisionById(divisionId);

            return new SimpleStringProperty(division.getName());
        });

        phoneCol.setCellValueFactory(customer -> {
            String phone = customer.getValue().getPhoneNumber();
            return new SimpleStringProperty(phone);
        });

    }

    /**
     * Gets all the appointments of the given customer
     * @param customer customer with 0 or more appointments
     * @return a list of all the customer's appointments
     */
    private List<Appointment> getAppointments(Customer customer) {
        return appointmentService.getAllAppointments().stream()
                .filter(appointment -> appointment.getCustomerId() == customer.getId())
                .toList();
    }

    /**
     * Confirmation alert shown to user when deleting a customer
     * @param headerText header text for alert
     * @param contentText body text for alert
     */
    private Optional<ButtonType> showConfirmationAlert(String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        return alert.showAndWait();
    }



}
