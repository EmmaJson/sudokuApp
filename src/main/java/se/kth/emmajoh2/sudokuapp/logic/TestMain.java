package se.kth.emmajoh2.sudokuapp.logic;


public class TestMain {
    public static void main(String[] args) {
        InteractiveOperations operations = new InteractiveOperations(SudokuLevel.EASY);
        operations.view();
        operations.addNumber(0,0,2);
        operations.view();
        operations.resetMoves();
        operations.view();
    }
}

