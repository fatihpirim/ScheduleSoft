package com.example.schedulesoft.enums;

public enum View {

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
