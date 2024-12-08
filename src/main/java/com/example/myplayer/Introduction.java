package com.example.myplayer;

import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

// Class representing the introduction screen
public class Introduction extends Application {

    // Method to show the introduction screen
    public void showIntroduction(Stage primaryStage, Runnable callback, Class<MainCode> mainCodeClass) {
        // Create a StackPane as the root node
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: black;");
        Scene scene = new Scene(root, 800, 800, Color.BLACK);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Intro Page");

        // Title text
        Text title = new Text("Roman CodeWave");
        title.setFill(Color.WHITE);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 50));

        // Fade transition for title text
        FadeTransition fadeTransitionTitle = new FadeTransition(Duration.seconds(4), title);
        fadeTransitionTitle.setFromValue(0);
        fadeTransitionTitle.setToValue(1);

        // Rotate transition for title text
        RotateTransition rotateTransitionTitle = new RotateTransition(Duration.seconds(2), title);
        rotateTransitionTitle.setByAngle(360);
        rotateTransitionTitle.setCycleCount(1);
        rotateTransitionTitle.setAutoReverse(false);

        // Name text
        Text name = new Text("Presents Black Jack");
        name.setFill(Color.WHITE);
        name.setFont(Font.font("Arial", FontWeight.NORMAL, 14));

        // Fade transition for name text
        FadeTransition fadeTransitionName = new FadeTransition(Duration.seconds(4), name);
        fadeTransitionName.setFromValue(0);
        fadeTransitionName.setToValue(1);

        // Text prompting to press Enter
        Text pressEnterText = new Text("Press Enter to continue");
        pressEnterText.setFill(Color.WHITE);
        pressEnterText.setFont(Font.font("Arial", FontWeight.NORMAL, 14));

        // Fade transition for press Enter text
        FadeTransition fadeTransitionPressEnter = new FadeTransition(Duration.seconds(1), pressEnterText);
        fadeTransitionPressEnter.setFromValue(1);
        fadeTransitionPressEnter.setToValue(0);
        fadeTransitionPressEnter.setCycleCount(Animation.INDEFINITE);
        fadeTransitionPressEnter.setAutoReverse(true);

        // Parallel transition for title text
        ParallelTransition titleParallelTransition = new ParallelTransition(title, fadeTransitionTitle, rotateTransitionTitle);

        // Sequential transition for name text
        SequentialTransition nameSequentialTransition = new SequentialTransition(
                new PauseTransition(Duration.seconds(1)),
                fadeTransitionName
        );

        // Set alignment and margins for text elements
        StackPane.setAlignment(title, javafx.geometry.Pos.CENTER);
        StackPane.setAlignment(name, javafx.geometry.Pos.CENTER);
        StackPane.setAlignment(pressEnterText, javafx.geometry.Pos.BOTTOM_CENTER);
        StackPane.setMargin(name, new javafx.geometry.Insets(85, 0, 0, 0));

        // Add text elements to the root node
        root.getChildren().addAll(title, name, pressEnterText);

        // Start animations
        titleParallelTransition.play();
        nameSequentialTransition.play();
        fadeTransitionPressEnter.play();

        // Handle key press events
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                primaryStage.close(); // Close the introduction window
                callback.run(); // Execute the callback to start the game
            }
        });

        primaryStage.show();
    }

    // Override start method (not used in this context)
    @Override
    public void start(Stage primaryStage) {
        // This start method is not used when integrating with MainCode
    }

    // Main method to launch the application
    public static void main(String[] args) {
        launch(args);
    }
}
