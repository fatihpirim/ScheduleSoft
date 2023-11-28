package com.example.schedulesoft.controller;

import com.example.schedulesoft.*;
import com.example.schedulesoft.enums.View;
import com.example.schedulesoft.service.UserAuth;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.net.URL;
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

    private ResourceBundle rb;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.rb = resourceBundle;
        zoneIdLabel.setText(AppConfig.getSystemZoneId().toString());
    }

    @FXML
    private void onLogin(Event event) {

        System.out.println("Clicked login");

        if(!allFieldsAreValid()) {
            return;
        }

        String username = usernameField.getText();
        String password = passwordField.getText();

        boolean userIsAuthenticated = UserAuth.authenticate(username, password);

        if(userIsAuthenticated) {
            PageManager.changePageTo(View.Home);
            System.out.println("Details: \n " + SessionHolder.getInstance().getSession().getUser());
        } else {
            displayError("incorrect_username_or_password");
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
           return false; // all fields are not valid
       } else {
           return true; // all fields are valid
       }
    }

    private void displayError(String message) {
        System.out.println(message);
        Text text = new Text(rb.getString(message)+"\n");
        errorTextFlow.getChildren().add(text);
        errorTextFlow.setVisible(true);
    }

    private void clearErrors() {
        errorTextFlow.getChildren().clear();
        errorTextFlow.setVisible(false);
    }

}
