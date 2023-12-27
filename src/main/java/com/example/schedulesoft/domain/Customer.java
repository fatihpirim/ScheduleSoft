package com.example.schedulesoft.domain;

import javafx.beans.property.*;

import java.time.ZonedDateTime;

/**
 * Class representing a customer
 */
public class Customer {

    private final IntegerProperty id;
    private final StringProperty name;
    private final StringProperty address;
    private final StringProperty postalCode;
    private final StringProperty phoneNumber;
    private final Property<ZonedDateTime> createdOn;
    private final StringProperty createdBy;
    private final Property<ZonedDateTime> lastUpdated;
    private final StringProperty lastUpdatedBy;
    private final IntegerProperty divisionId;

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

    public void setName(String name) {
        this.name.set(name);
    }

    public String getAddress() {
        return address.get();
    }

    public StringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getPostalCode() {
        return postalCode.get();
    }

    public StringProperty postalCodeProperty() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode.set(postalCode);
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public StringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public ZonedDateTime getCreatedOn() {
        return createdOn.getValue();
    }

    public Property<ZonedDateTime> createdOnProperty() {
        return createdOn;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn.setValue(createdOn);
    }

    public String getCreatedBy() {
        return createdBy.get();
    }

    public StringProperty createdByProperty() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy.set(createdBy);
    }

    public ZonedDateTime getLastUpdated() {
        return lastUpdated.getValue();
    }

    public Property<ZonedDateTime> lastUpdatedProperty() {
        return lastUpdated;
    }

    public void setLastUpdated(ZonedDateTime lastUpdated) {
        this.lastUpdated.setValue(lastUpdated);
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy.get();
    }

    public StringProperty lastUpdatedByProperty() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy.set(lastUpdatedBy);
    }

    public int getDivisionId() {
        return divisionId.get();
    }

    public IntegerProperty divisionIdProperty() {
        return divisionId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId.set(divisionId);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name=" + name +
                ", address=" + address +
                ", postalCode=" + postalCode +
                ", phoneNumber=" + phoneNumber +
                ", createdOn=" + createdOn +
                ", createdBy=" + createdBy +
                ", lastUpdated=" + lastUpdated +
                ", lastUpdatedBy=" + lastUpdatedBy +
                ", divisionId=" + divisionId +
                '}';
    }
}
