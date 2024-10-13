package se.kth.emmajoh2.sudokuapp;

import se.kth.emmajoh2.sudokuapp.model.SudokuModel;

public class TestApp {
    public static void main(String[] args) {
        System.out.println("initial board:");
        SudokuModel model = new SudokuModel();
        System.out.println(model.toString());

        /*System.out.println("change in row 1 and col 1, to: 5");
        model.addNumber(1,1,5);
        System.out.println(model.toString());
         */

        System.out.println("hint added");
        model.addhint();
        System.out.println(model.toString());

        if (model.allTilesCorrect()) {
            System.out.println("all tiles are correct");
        } else System.out.println("all tiles are NOT correct");

        if (model.placedTilesCorrect()) {
            System.out.println("placed tiles are correct");
        } else System.out.println("placed tiles are NOT correct");
    }
}
