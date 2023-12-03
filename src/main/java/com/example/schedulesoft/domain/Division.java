package com.example.schedulesoft.domain;

import javafx.beans.property.*;

import java.time.ZonedDateTime;

public class Division {
    private final IntegerProperty id;
    private final StringProperty name;
    private final Property<ZonedDateTime> createdOn;
    private final StringProperty createdBy;
    private final Property<ZonedDateTime> lastUpdated;
    private final StringProperty lastUpdatedBy;
    private final IntegerProperty countryId;

    public Division(String name, ZonedDateTime createdOn, String createdBy, ZonedDateTime lastUpdated, String lastUpdatedBy, int countryId) {
        this.id = new SimpleIntegerProperty(0);
        this.name = new SimpleStringProperty(name);
        this.createdOn = new SimpleObjectProperty<>(createdOn);
        this.createdBy = new SimpleStringProperty(createdBy);
        this.lastUpdated = new SimpleObjectProperty<>(lastUpdated);
        this.lastUpdatedBy = new SimpleStringProperty(lastUpdatedBy);
        this.countryId = new SimpleIntegerProperty(countryId);
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

    public int getCountryId() {
        return countryId.get();
    }

    public IntegerProperty countryIdProperty() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId.set(countryId);
    }

    @Override
    public String toString() {
        return getName();
    }
}
