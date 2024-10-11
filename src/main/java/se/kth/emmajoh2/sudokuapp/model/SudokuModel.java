package se.kth.emmajoh2.sudokuapp.model;

import java.util.Random;
import static se.kth.emmajoh2.sudokuapp.model.MatrixGenerator.GRID_SIZE;

/**
 * The {@code SudokuModel} class represents the underlying data structure for a Sudoku puzzle.
 * <p>
 * It manages the Sudoku grid, the game state, and supports operations like initialization, adding numbers,
 * checking for correctness, providing hints, and resetting moves. The Sudoku board is represented as a
 * 2D array of {@link SelectedTile} objects.
 */
public class SudokuModel {
    private SelectedTile[][] sudokuBoard;
    private SudokuLevel level;

    /**
     * Constructs a new {@code SudokuModel} with a default difficulty level of {@code MEDIUM}.
     * Initializes the game board by generating a new Sudoku puzzle based on the default level.
     */
    public SudokuModel() {
        this.sudokuBoard = new SelectedTile[GRID_SIZE][GRID_SIZE];
        this.level = SudokuLevel.MEDIUM;
        initGame(level);
    }

    /**
     * Loads a previously saved Sudoku board into the current game model.
     *
     * @param loadedTiles The 2D array of {@link SelectedTile} objects representing the saved Sudoku board.
     */
    public void loadBoard(SelectedTile[][] loadedTiles) {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                sudokuBoard[row][col] = loadedTiles[row][col];
            }
        }
    }

    /**
     * Returns the current Sudoku board.
     *
     * @return A 2D array of {@link SelectedTile} objects representing the current Sudoku board.
     */
    public SelectedTile[][] getSudokuBoard() {
        return sudokuBoard;
    }

    /**
     * Initializes a new game based on the specified difficulty level.
     *
     * @param level The difficulty level of the Sudoku puzzle (EASY, MEDIUM, HARD).
     */
    public void initGame(SudokuLevel level) {
        this.level = level;
        int[][][] allMatrixes = MatrixGenerator.generateSudokuMatrix(level);

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                sudokuBoard[row][col] = new SelectedTile(allMatrixes[row][col][0],
                                                        allMatrixes[row][col][1],
                                                        allMatrixes[row][col][2]);
            }
        }
    }

    /**
     * Initializes a new game using the current difficulty level.
     */
    public void initGame() {
        int[][][] allMatrixes = MatrixGenerator.generateSudokuMatrix(level);

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                sudokuBoard[row][col] = new SelectedTile(allMatrixes[row][col][0],
                        allMatrixes[row][col][1],
                        allMatrixes[row][col][2]);
            }
        }
    }

    /**
     * Retrieves the current value of a tile at the specified position.
     *
     * @param row The row of the tile.
     * @param col The column of the tile.
     * @return A string representing the current number on the tile, or an empty string if the tile is empty.
     */
    public String getTile(int row, int col) {
        int tempInt = sudokuBoard[row][col].getTileNb();
        String s = new String();
        if (tempInt != 0) {
            s = String.valueOf(tempInt);
        }
        return s;
    }

    /**
     * Checks if a tile at the specified position is an initial (pre-filled) tile.
     *
     * @param row The row of the tile.
     * @param col The column of the tile.
     * @return {@code true} if the tile is an initial tile, {@code false} otherwise.
     */
    public boolean isInitTile(int row, int col) {
        return sudokuBoard[row][col].isInitialTile();
    }

    /**
     * Adds a number to the specified tile, provided the tile is not an initial tile.
     *
     * @param row The row of the tile.
     * @param col The column of the tile.
     * @param numberToAdd The number to be added to the tile.
     */
    public void addNumber(int row, int col, int numberToAdd) {
        sudokuBoard[row][col].setTile(numberToAdd);
    }

    /**
     * Adds a hint by randomly selecting a tile that is incorrect or empty and setting it to its correct value.
     *
     * @return {@code true} if a hint was successfully added, {@code false} otherwise.
     */
    public boolean addhint() {
        Random random = new Random();
        int randRow, randCol;
        while(true) {
            randRow = random.nextInt(GRID_SIZE);
            randCol = random.nextInt(GRID_SIZE);
            if ((!sudokuBoard[randRow][randCol].isCorrectlyPlaced())) {
                sudokuBoard[randRow][randCol].setTile(sudokuBoard[randRow][randCol].getCorrectTile());
                return true;
            }
        }
    }

    /**
     * Checks if all tiles on the board are correctly placed.
     *
     * @return {@code true} if all tiles are correct, {@code false} otherwise.
     */

    public boolean allTilesCorrect() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (!sudokuBoard[row][col].isCorrectlyPlaced()) return false;
            }
        }
        return true;
    }

    /**
     * Checks if all placed (non-empty) tiles are correctly placed.
     *
     * @return {@code true} if all placed tiles are correct, {@code false} otherwise.
     */
    public boolean placedTilesCorrect() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (!sudokuBoard[row][col].isCorrectlyPlaced() && (sudokuBoard[row][col].getTileNb() != 0)) return false;
            }
        }
        return true;
    }

    /**
     * Resets all non-initial tiles on the board, clearing the numbers entered by the player.
     */
    public void resetMoves() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                sudokuBoard[row][col].resetTile();
            }
        }
    }
}
