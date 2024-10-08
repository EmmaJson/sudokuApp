package se.kth.emmajoh2.sudokuapp;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        Button button = new Button();
        button.setText("Say 'Hello World'");
        button.setOnAction(new ButtonHandler());

        StackPane root = new StackPane();
        root.getChildren().add(button);

        Scene scene = new Scene(root, 300, 250);
        primaryStage.setScene(scene);

        primaryStage.setTitle("Hello JavaFX!");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private class ButtonHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            Alert helloAlert = new Alert(Alert.AlertType.INFORMATION);
            helloAlert.setTitle("FX alert!");
            helloAlert.setHeaderText("Hello!");
            helloAlert.setContentText("Welcome to the Java FX world!");
            helloAlert.show();
        }
    }
}