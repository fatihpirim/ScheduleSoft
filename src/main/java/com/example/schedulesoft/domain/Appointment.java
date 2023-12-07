package com.example.schedulesoft.domain;

import javafx.beans.property.*;

import java.time.ZonedDateTime;

public class Appointment {

    private final IntegerProperty id;
    private final StringProperty title;
    private final StringProperty description;
    private final StringProperty location;
    private final StringProperty type;

    private final Property<ZonedDateTime> startDateTime;
    private final Property<ZonedDateTime> endDateTime;

    private final Property<ZonedDateTime> createdOn;
    private final StringProperty createdBy;

    private final Property<ZonedDateTime> lastUpdated;
    private final StringProperty lastUpdatedBy;

    private final IntegerProperty customerId;
    private final IntegerProperty userId;
    private final IntegerProperty contactId;

    public Appointment(String title, String description, String location, String type, ZonedDateTime startDateTime,
                       ZonedDateTime endDateTime, ZonedDateTime createdOn, String createdBy, ZonedDateTime lastUpdated,
                       String lastUpdatedBy, int customerId, int userId, int contactId) {

        this.id = new SimpleIntegerProperty(0);
        this.title = new SimpleStringProperty(title);
        this.description = new SimpleStringProperty(description);
        this.location = new SimpleStringProperty(location);
        this.type = new SimpleStringProperty(type);
        this.startDateTime = new SimpleObjectProperty<>(startDateTime);
        this.endDateTime = new SimpleObjectProperty<>(endDateTime);
        this.createdOn = new SimpleObjectProperty<>(createdOn);
        this.createdBy = new SimpleStringProperty(createdBy);
        this.lastUpdated = new SimpleObjectProperty<>(lastUpdated);
        this.lastUpdatedBy = new SimpleStringProperty(lastUpdatedBy);
        this.customerId = new SimpleIntegerProperty(customerId);
        this.userId = new SimpleIntegerProperty(userId);
        this.contactId = new SimpleIntegerProperty(contactId);
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

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public String getLocation() {
        return location.get();
    }

    public StringProperty locationProperty() {
        return location;
    }

    public void setLocation(String location) {
        this.location.set(location);
    }

    public String getType() {
        return type.get();
    }

    public StringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public ZonedDateTime getStartDateTime() {
        return startDateTime.getValue();
    }

    public Property<ZonedDateTime> startDateTimeProperty() {
        return startDateTime;
    }

    public void setStartDateTime(ZonedDateTime startDateTime) {
        this.startDateTime.setValue(startDateTime);
    }

    public ZonedDateTime getEndDateTime() {
        return endDateTime.getValue();
    }

    public Property<ZonedDateTime> endDateTimeProperty() {
        return endDateTime;
    }

    public void setEndDateTime(ZonedDateTime endDateTime) {
        this.endDateTime.setValue(endDateTime);
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

    public int getCustomerId() {
        return customerId.get();
    }

    public IntegerProperty customerIdProperty() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId.set(customerId);
    }

    public int getUserId() {
        return userId.get();
    }

    public IntegerProperty userIdProperty() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId.set(userId);
    }

    public int getContactId() {
        return contactId.get();
    }

    public IntegerProperty contactIdProperty() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId.set(contactId);
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + getId() +
                ", title=" + getTitle() +
                ", description=" + getDescription() +
                ", location=" + getLocation() +
                ", type=" + getType() +
                ", startDateTime=" + getStartDateTime() +
                ", endDateTime=" + getEndDateTime() +
                ", createdOn=" + getCreatedOn() +
                ", createdBy=" + getCreatedBy() +
                ", lastUpdated=" + getLastUpdated() +
                ", lastUpdatedBy=" + getLastUpdatedBy() +
                ", customerId=" + getCustomerId() +
                ", userId=" + getUserId() +
                ", contactId=" + getContactId() +
                '}';
    }
}
