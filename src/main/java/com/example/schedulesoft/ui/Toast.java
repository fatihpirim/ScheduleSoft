package com.example.schedulesoft.ui;

import com.example.schedulesoft.enums.Severity;
import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;


public class Toast {

    private final StackPane container;

    public Toast(String title, String message, Severity severity) {
        container = new StackPane();

        container.setMaxWidth(200);
        container.setPrefHeight(75);

        Background background = getBackground(severity);

        container.setBackground(background);

        Text titleText = new Text(title+"\n");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        titleText.setFill(severity.getColor());

        Text messageText = new Text(message);
        messageText.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        messageText.setFill(severity.getColor());

        container.getChildren().addAll(titleText, messageText);

        StackPane.setAlignment(titleText, Pos.TOP_LEFT);
        StackPane.setAlignment(messageText, Pos.CENTER_LEFT);

        container.setPadding(new Insets(5));

    }

    private Background getBackground(Severity severity) {
        Color primaryBackgroundColor = Color.WHITE;
        Color secondaryBackgroundColor = severity.getColor().deriveColor(0, 1, 1, 0.5);
        CornerRadii cornerRadii = new CornerRadii(3);

        BackgroundFill primaryFill = new BackgroundFill(primaryBackgroundColor, cornerRadii, null);
        BackgroundFill secondaryFill = new BackgroundFill(secondaryBackgroundColor.deriveColor(0, 1, 1, 0.3), cornerRadii, null);

        return new Background(primaryFill, secondaryFill);
    }


    public void show(Stage parentStage) {

        Stage stage = new Stage(StageStyle.TRANSPARENT);
        stage.initOwner(parentStage);
        stage.setResizable(false);
        stage.setAlwaysOnTop(true);
        stage.setScene(new Scene(this.container, Color.TRANSPARENT));
        stage.setWidth(300);

        double offsetX = 30;
        double offsetY = 50;

        stage.setX(parentStage.getScene().getWindow().getX() + parentStage.getScene().getWindow().getWidth() - stage.getWidth() - offsetX);
        stage.setY(parentStage.getScene().getWindow().getY() + offsetY);

        stage.show();

        fadeIn(stage);

        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(6));
        pauseTransition.setOnFinished(event -> {
            fadeOut(stage);
        });

        pauseTransition.play();
    }

    private void fadeIn(Stage stage) {

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), stage.getScene().getRoot());
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1.0);


        fadeTransition.play();
    }

    private void fadeOut(Stage stage) {

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), stage.getScene().getRoot());
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setOnFinished(event2 -> stage.close());
        fadeTransition.play();
    }







}
