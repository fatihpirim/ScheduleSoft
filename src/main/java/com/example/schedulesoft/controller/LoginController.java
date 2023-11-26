package com.example.schedulesoft.controller;

import com.example.schedulesoft.PageManager;
import com.example.schedulesoft.enums.View;
import javafx.event.Event;
import javafx.fxml.FXML;

import java.io.IOException;

public class LoginController {

    @FXML
    private void onLogin(Event event) throws IOException {
        System.out.println("Clicked login");

        PageManager.changePageTo(View.Home);
    }
}
