package se.kth.emmajoh2.sudokuapp.logic;


import java.util.Arrays;

import static se.kth.emmajoh2.sudokuapp.logic.MatrixGenerator.GRID_SIZE;

public class test {
    public static void main(String[] args) {
        //printMatrix(matrix);
        //System.out.println(generateRandomString());

        //System.out.println(generateRandomString());
        //matrix = convertStringToIntMatrix(generateRandomString());
        printMatrix(MatrixGenerator.initGameBoard(SudokuLevel.MEDIUM));

    }
    public static void printMatrix(int[][][] matrix) {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                System.out.print(" ");
                System.out.print(matrix[row][col][0]);
                if (col == 2||col == 5) {
                    System.out.print(" |");
                }
            }
            System.out.println();
            if (row == 2||row == 5) {
                System.out.println("----------------------");
            }
        }
        System.out.println();

        // solution values
        System.out.println("Solution");
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                System.out.print(" ");
                System.out.print(matrix[row][col][1]);
                if (col == 2||col == 5) {
                    System.out.print(" |");
                }
            }
            System.out.println();
            if (row == 2||row == 5) {
                System.out.println("----------------------");
            }
        }

    }
}

