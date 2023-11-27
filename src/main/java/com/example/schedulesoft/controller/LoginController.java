package com.example.schedulesoft.controller;

import com.example.schedulesoft.PageManager;
import com.example.schedulesoft.SessionHolder;
import com.example.schedulesoft.UserAuth;
import com.example.schedulesoft.UserSession;
import com.example.schedulesoft.enums.View;
import com.example.schedulesoft.exception.UsernameNotFoundException;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;

public class LoginController {

    @FXML
    TextField usernameField;

    @FXML
    PasswordField passwordField;

    @FXML
    TextFlow errorTextFlow;

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
            displayError("Password or username is incorrect");
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
               String message = "Username is not valid";
               displayError(message);
           }
           if(passwordIsNotValid()) {
               String message = "Password is not valid";
               displayError(message);
           }
           return false; // all fields are not valid
       } else {
           return true; // all fields are valid
       }
    }

    private void displayError(String message) {
        System.out.println(message);
        Text text = new Text(message+"\n");
        errorTextFlow.getChildren().add(text);
        errorTextFlow.setVisible(true);
    }

    private void clearErrors() {
        errorTextFlow.getChildren().clear();
        errorTextFlow.setVisible(false);
    }

}
