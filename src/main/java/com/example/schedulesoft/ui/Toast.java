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

    /**
     * Container of the toast
     */
    private final StackPane container;

    /**
     * Constructor for Toast UI
     * @param title title of toast
     * @param message message (content) of toast
     * @param severity severity of toast
     */
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

    /**
     * Gets the background for the toast
     * @param severity indicates severity and associated color
     * @return background to be used by toast container
     */
    private Background getBackground(Severity severity) {
        Color primaryBackgroundColor = Color.WHITE;
        Color secondaryBackgroundColor = severity.getColor().deriveColor(0, 1, 1, 0.5);
        CornerRadii cornerRadii = new CornerRadii(3);

        BackgroundFill primaryFill = new BackgroundFill(primaryBackgroundColor, cornerRadii, null);
        BackgroundFill secondaryFill = new BackgroundFill(secondaryBackgroundColor.deriveColor(0, 1, 1, 0.3), cornerRadii, null);

        return new Background(primaryFill, secondaryFill);
    }


    /**
     *
     * Shows the toast on the stage
     *
     * <p>
     *     Uses lambda to handle event of pause transition
     * </p>
     * @param parentStage stage the toast will be shown on
     */
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

        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(3));
        pauseTransition.setOnFinished(event -> {
            fadeOut(stage);
        });

        pauseTransition.play();
    }

    /**
     * Fades the toast in
     * @param stage stage of toast
     */
    private void fadeIn(Stage stage) {

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), stage.getScene().getRoot());
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1.0);


        fadeTransition.play();
    }

    /**
     * Fades the toast out
     *
     * <p>
     *     Lambda handles event when the transition finishes
     * </p>
     * @param stage stage of toast
     */
    private void fadeOut(Stage stage) {

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), stage.getScene().getRoot());
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setOnFinished(event2 -> stage.close());
        fadeTransition.play();
    }







}
