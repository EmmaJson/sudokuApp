package se.kth.emmajoh2.sudokuapp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import se.kth.emmajoh2.sudokuapp.model.SudokuModel;
import se.kth.emmajoh2.sudokuapp.view.SudokuView;


public class SudokuApp extends Application {
    @Override
    public void start(Stage stage) { //Stage == fönster

        SudokuModel model = new SudokuModel();
        SudokuView view = new SudokuView(model); // Skapar vyn för Sudoku-spelet och även kontrollern inuti vyn.

        MenuBar menuBar = view.getMenuBar(); // Hämtar menyraden från vyn och lägger den i en VBox tillsammans med vyn.
        VBox root = new VBox(menuBar, view);

        // Skapar och sätter scenen för applikationen, inklusive storlek och titel.
        Scene scene = new Scene(root);
        stage.setTitle("Sudoku");
        stage.setScene(scene)   ;
        stage.sizeToScene();
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);                               //Anroper förr eller senare start();
    }
}