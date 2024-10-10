package se.kth.emmajoh2.sudokuapp.logic;

public class InteractiveOperations {
    private final SudokuOperations sudokuOperations;



    public InteractiveOperations(SudokuLevel level) {
        this.sudokuOperations = new SudokuOperations(level);
    }


    public void view() {
        sudokuOperations.printMatrix();
    }

    public void addNumber(int row, int col, int numberToAdd) {
        sudokuOperations.writeNumber(row,col,numberToAdd);
    }

    public void resetMoves() {
        sudokuOperations.clearMatrix();
    }


}
