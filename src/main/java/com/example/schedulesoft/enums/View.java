package com.example.schedulesoft.enums;

public enum View {
    CustomerTable("view/CustomerTable.fxml"),
    AppointmentTable("view/AppointmentTable.fxml");

    private String fileName;

    View(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

}
