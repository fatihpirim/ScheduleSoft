package com.example.schedulesoft.controller;

import com.example.schedulesoft.auth.SessionHolder;
import com.example.schedulesoft.domain.Appointment;
import com.example.schedulesoft.domain.Interval;
import com.example.schedulesoft.enums.Message;
import com.example.schedulesoft.enums.Severity;
import com.example.schedulesoft.enums.View;
import com.example.schedulesoft.auth.UserAuth;
import com.example.schedulesoft.service.AppointmentService;
import com.example.schedulesoft.ui.Toast;
import com.example.schedulesoft.util.AppConfig;
import com.example.schedulesoft.util.LocaleUtil;
import com.example.schedulesoft.util.PageManager;
import com.example.schedulesoft.util.UserActivityLogger;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.net.URL;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    TextField usernameField;

    @FXML
    PasswordField passwordField;

    @FXML
    TextFlow errorTextFlow;

    @FXML
    Label zoneIdLabel;

    @FXML
    Button loginButton;

    private ResourceBundle resources;

    UserActivityLogger logger = UserActivityLogger.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resources) {

        this.resources = resources;

        zoneIdLabel.setText(AppConfig.getSystemZoneId().toString());
    }

    @FXML
    private void onLogin(Event event) throws Exception {

        System.out.println("Clicked login");

        Stage stage = (Stage) loginButton.getScene().getWindow();

        if(!allFieldsAreValid()) {
            return;
        }

        String username = usernameField.getText();
        String password = passwordField.getText();

        boolean userIsAuthenticated = UserAuth.authenticate(username, password);

        if(userIsAuthenticated) {

            Interval interval = new Interval(ZonedDateTime.now(), ZonedDateTime.now().plusMinutes(15));
            AppointmentService appointmentService = new AppointmentService();
            Appointment appointment = appointmentService.getAppointmentWithinInterval(interval);
            if(appointment != null) {
                System.out.println("Upcoming appointment " + appointment.getTitle());
                Toast toast = new Toast(resources.getString("alert"), resources.getString("upcoming_appointment") + " " + appointment.getId() + "\n " +
                       resources.getString("starting_at") + " " + LocaleUtil.formatToLocale(appointment.getStartDateTime()), Severity.WARNING);
                toast.show(stage);
            } else {
                System.out.println("No appointments upcoming");
                Toast toast = new Toast(resources.getString("info"), resources.getString("no_upcoming_appointment"), Severity.INFO);
                toast.show(stage);
            }

            PageManager.changePageTo(View.Home);
            System.out.println("Details: \n " + SessionHolder.getInstance().getSession().getUser());

            logger.logLoginAttempt(Message.SUCCESS, username);
        } else {
            displayError("incorrect_username_or_password");

            Toast toast = new Toast(resources.getString("error"), resources.getString("login_fail"), Severity.ERROR);
            toast.show(stage);

            logger.logLoginAttempt(Message.FAILURE, username);
        }

    }

    private boolean usernameIsNotValid() {
        return usernameField.getText().isEmpty();
    }

    private boolean passwordIsNotValid() {
        return passwordField.getText().isEmpty();
    }

    private boolean allFieldsAreValid() {

        clearErrors();

       if(usernameIsNotValid() || passwordIsNotValid()) {
           if(usernameIsNotValid()) {
               String message =  "invalid_username";
               displayError(message);
           }
           if(passwordIsNotValid()) {
               String message = "invalid_password";
               displayError(message);
           }

           logger.logLoginAttempt(Message.FAILURE);
           return false; // all fields are not valid
       } else {
           return true; // all fields are valid
       }
    }

    private void displayError(String message) {
        System.out.println(message);
        Text text = new Text(resources.getString(message)+"\n");
        errorTextFlow.getChildren().add(text);
        errorTextFlow.setVisible(true);
    }

    private void clearErrors() {
        errorTextFlow.getChildren().clear();
        errorTextFlow.setVisible(false);
    }

}
