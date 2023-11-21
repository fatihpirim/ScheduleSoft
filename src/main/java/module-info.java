module com.example.helloworldjfxtemplate {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.schedulesoft to javafx.fxml;
    exports com.example.schedulesoft;

    exports com.example.schedulesoft.controller;
    opens com.example.schedulesoft.controller to javafx.fxml;

}