package com.example.schedulesoft.domain;

import javafx.beans.property.*;

import java.time.ZonedDateTime;

public class Customer {

    private IntegerProperty id;
    private StringProperty name;
    private StringProperty address;
    private StringProperty postalCode;
    private StringProperty phoneNumber;
    private Property<ZonedDateTime> createdOn;
    private StringProperty createdBy;
    private Property<ZonedDateTime> lastUpdated;
    private StringProperty lastUpdatedBy;
    private IntegerProperty divisionId;


    public Customer(String name, String address, String postalCode, String phoneNumber, ZonedDateTime createdOn, String createdBy, ZonedDateTime lastUpdated,
                    String lastUpdatedBy, Integer divisionId) {

        this.id = new SimpleIntegerProperty(0);
        this.name = new SimpleStringProperty(name);
        this.address = new SimpleStringProperty(address);
        this.postalCode = new SimpleStringProperty(postalCode);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.createdOn = new SimpleObjectProperty<>(createdOn);
        this.createdBy = new SimpleStringProperty(createdBy);
        this.lastUpdated = new SimpleObjectProperty<>(lastUpdated);
        this.lastUpdatedBy = new SimpleStringProperty(lastUpdatedBy);
        this.divisionId = new SimpleIntegerProperty(divisionId);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getAddress() {
        return address.get();
    }

    public StringProperty addressProperty() {
        return address;
    }

    public String getPostalCode() {
        return postalCode.get();
    }

    public StringProperty postalCodeProperty() {
        return postalCode;
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public StringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public ZonedDateTime getCreatedOn() {
        return createdOn.getValue();
    }

    public Property<ZonedDateTime> createdOnProperty() {
        return createdOn;
    }

    public String getCreatedBy() {
        return createdBy.get();
    }

    public StringProperty createdByProperty() {
        return createdBy;
    }

    public ZonedDateTime getLastUpdated() {
        return lastUpdated.getValue();
    }

    public Property<ZonedDateTime> lastUpdatedProperty() {
        return lastUpdated;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy.get();
    }

    public StringProperty lastUpdatedByProperty() {
        return lastUpdatedBy;
    }

    public int getDivisionId() {
        return divisionId.get();
    }

    public IntegerProperty divisionIdProperty() {
        return divisionId;
    }
}
