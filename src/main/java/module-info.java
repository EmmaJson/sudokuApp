module se.kth.emmajoh2.sudokuapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens se.kth.emmajoh2.sudokuapp to javafx.fxml;
    exports se.kth.emmajoh2.sudokuapp;
}