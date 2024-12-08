package com.example.myplayer;
import javafx.animation.*;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class credits extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create a stack pane as the root node

        StackPane pane = new StackPane();

        // Create and style the project name
        Text groupName = new Text("BLACKJACK PROJECT , Roman CodeWave ");
        groupName.setFill(Color.BLACK);
        groupName.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30));

        // Create and style your name

        Text name = new Text("THANK YOU");
        name.setFill(Color.BLACK);
        name.setFont(Font.font("Times New Roman", FontWeight.BOLD, 22));

        // Create and style credits

        Text credits = new Text("Credits: W3Schools , StackOverFlow");
        credits.setFill(Color.BLACK);
        credits.setFont(Font.font("Times New Roman", FontWeight.BOLD, 22));

        // Create and style contributors
        Text contributors = new Text("Contributors:" +
                "JAI , ROHAN,FRANCIS ");
        contributors.setFill(Color.BLACK);
        contributors.setFont(Font.font("Times New Roman", FontWeight.BOLD, 22));

        // Apply fade-in animation to project name
        FadeTransition fadeGroupName = new FadeTransition(Duration.seconds(2), groupName);
        fadeGroupName.setFromValue(0);
        fadeGroupName.setToValue(1);

        // Apply fade-in animation to your name
        FadeTransition fadeName = new FadeTransition(Duration.seconds(2), name);
        fadeName.setFromValue(0);
        fadeName.setToValue(1);

        // Apply fade-in animation to credits
        FadeTransition fadeCredits = new FadeTransition(Duration.seconds(2), credits);
        fadeCredits.setFromValue(0);
        fadeCredits.setToValue(1);

        // Apply fade-in animation to contributors
        FadeTransition fadeContributors = new FadeTransition(Duration.seconds(2), contributors);
        fadeContributors.setFromValue(0);
        fadeContributors.setToValue(1);
        //apply rotate
        RotateTransition rotateGroupName= new RotateTransition(Duration.seconds(2),groupName);
        rotateGroupName.setByAngle(360);

        RotateTransition rotateName= new RotateTransition(Duration.seconds(2),name);
        rotateName.setByAngle(360);

        RotateTransition rotateCredits= new RotateTransition(Duration.seconds(2),credits);
        rotateCredits.setByAngle(360);

        RotateTransition rotateContributors= new RotateTransition(Duration.seconds(2),contributors);
        rotateContributors.setByAngle(360);

        FillTransition fillName = new FillTransition(Duration.seconds(9), name, Color.AQUA, Color.RED);
        fillName.setAutoReverse(true);

        FillTransition fillGroupName = new FillTransition(Duration.seconds(9), groupName, Color.AQUA, Color.RED);
        fillGroupName.setAutoReverse(true);

        PauseTransition stop1 = new PauseTransition(Duration.seconds(2));
        PauseTransition stop2 = new PauseTransition(Duration.seconds(2));

        // Create a parallel transition for all fade-in animations
        ParallelTransition parallel = new ParallelTransition();
        parallel.getChildren().addAll(fadeGroupName,fadeName,fadeCredits, fadeContributors,fillGroupName,fillName);
        parallel.play();
        //sequential
        SequentialTransition sequential = new SequentialTransition();
        sequential.getChildren().addAll(rotateName,stop1,rotateGroupName,stop2,rotateContributors,rotateCredits);
        sequential.play();


        //add elements to the root
        pane.getChildren().addAll(groupName, name, credits, contributors);

        //set aligment so the ystay in place
        contributors.setTranslateY(200);
        credits.setTranslateY(220);
        name.setTranslateY(0);
        groupName.setTranslateY(-200);


        // Show the stage
        pane.setPrefSize(600, 500);
        pane.setStyle("-fx-background-color: black;");
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("PROJECT");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
