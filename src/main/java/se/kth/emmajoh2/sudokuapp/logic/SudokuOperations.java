package se.kth.emmajoh2.sudokuapp.logic;

import static se.kth.emmajoh2.sudokuapp.logic.MatrixGenerator.GRID_SIZE;

class SudokuOperations {
    private final int[][][] matrix;
    private final SudokuLevel level;

    SudokuOperations(SudokuLevel level) {
        this.level = level;
        switch (this.level) {
            case EASY   ->  this.matrix = MatrixGenerator.initGameBoard(SudokuLevel.EASY);
            case MEDIUM ->  this.matrix = MatrixGenerator.initGameBoard(SudokuLevel.MEDIUM);
            case HARD   ->  this.matrix = MatrixGenerator.initGameBoard(SudokuLevel.HARD);
            default     ->  this.matrix = MatrixGenerator.initGameBoard(SudokuLevel.MEDIUM);
        }
    }


    int[][][] getMatrix() {
        return this.matrix;
    }

     boolean writeNumber(int row, int col, int number) throws IllegalArgumentException {
        if(!isSudokuNb(number)) throw new IllegalArgumentException();
        if(isWritingNbLegal(row, col)) {
            matrix[row][col][0] = number;
            return true;
        }

        return false;
    }

     boolean isPlacementsCorrect() {
        int[] nbOfnbs = new int[GRID_SIZE];
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (matrix[row][col][0] != matrix[row][col][1] && matrix[row][col][0] != 0) return false;
            }
        }
        return true;
    }

    boolean isAllCorrect() {
        int[] nbOfnbs = new int[GRID_SIZE];
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (matrix[row][col][0] != matrix[row][col][1]) return false;
            }
        }
        return true;
    }

    private boolean isSudokuNb(int number) {
        if (number >= 0 && number<= GRID_SIZE) return true;
        return false;
    }

    private boolean isWritingNbLegal(int row, int col) {
        if (matrix[row][col][2] != 1) {
            return false;
        }
        return true;
    }

    void clearMatrix() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (matrix[row][col][2] == 1) matrix[row][col][0] = 0;
            }
        }
    }

     void printMatrix() {
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
    }
    void printSolutionMatrix() {
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
