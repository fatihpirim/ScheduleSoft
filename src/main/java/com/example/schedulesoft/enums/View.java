package com.example.schedulesoft.enums;

/**
 * Enum to represent different Views (fxml files)
 * This enum was mainly created to extract away long file path strings
 */
public enum View {
    Dashboard(View.rootPath+"/Dashboard.fxml"),
    CustomerTable(View.rootPath+"/CustomerTable.fxml"),
    AppointmentTable(View.rootPath+"/AppointmentTable.fxml"),
    CustomerForm(View.rootPath+"/CustomerForm.fxml"),
    AppointmentForm(View.rootPath+"/AppointmentForm.fxml"),
    Home(View.rootPath+"/Home.fxml"),
    Login(View.rootPath+"/Login.fxml");

    private static final String rootPath  = "/com/example/schedulesoft/view";

    private String fileName;

    View(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

}
