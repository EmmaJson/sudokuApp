package se.kth.emmajoh2.sudokuapp.model;

import java.util.Random;
import static se.kth.emmajoh2.sudokuapp.model.MatrixGenerator.GRID_SIZE;
import static se.kth.emmajoh2.sudokuapp.model.MatrixGenerator.SECTION_SIZE;

/**
 * The {@code SudokuModel} class represents the underlying data structure for a Sudoku puzzle.
 * <p>
 * It manages the Sudoku grid, the game state, and supports operations like initialization, adding numbers,
 * checking for correctness, providing hints, and resetting moves. The Sudoku board is represented as a
 * 2D array of {@link SelectedTile} objects.
 */
public class SudokuModel {
    private final SelectedTile[][] sudokuBoard;
    private SudokuLevel level;
    private int pressedButtonNumber;


    /**
     * Constructs a new {@code SudokuModel} with a default difficulty level of {@code MEDIUM}.
     * Initializes the game board by generating a new Sudoku puzzle based on the default level.
     */
    public SudokuModel() {
        this.pressedButtonNumber = 0;
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

    public void setPressedButtonNumber(int pressedButtonNumber) {
        this.pressedButtonNumber = pressedButtonNumber;
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
        int tempInt = sudokuBoard[row][col].getCurrentTileNb();
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
     */
    public void addNumber(int row, int col) {
        sudokuBoard[row][col].setCurrentTile(pressedButtonNumber);
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
                sudokuBoard[randRow][randCol].setCurrentTile(sudokuBoard[randRow][randCol].getSolutionTile());
                // TODO: Debugging System.out.println("Row:" + randRow + ", Col:" + randCol);
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
                if (!sudokuBoard[row][col].isCorrectlyPlaced() && (sudokuBoard[row][col].getCurrentTileNb() != 0)) return false;
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

    /**
     * Generates a formatted string representation of the Sudoku board.
     * <p>
     * This method iterates through each row and column of the `sudokuBoard` and
     * constructs a string that visually represents the board. The board is divided
     * into sections, and the method adds visual markers such as vertical bars ("|")
     * between the sections and horizontal lines ("------------------------")
     * after every section of rows. This helps to present the Sudoku board in a
     * clean and readable format.
     * Example output for a 9x9 Sudoku board:
     * -------------------------
     * | 4 8 0 | 0 0 3 | 7 0 9 |
     * | 2 0 0 | 0 0 0 | 4 0 0 |
     * | 0 0 0 | 0 8 0 | 0 0 3 |
     * -------------------------
     * | 8 0 2 | 5 0 0 | 0 0 0 |
     * | 0 0 0 | 0 9 0 | 8 0 2 |
     * | 0 0 0 | 0 1 2 | 0 0 7 |
     * -------------------------
     * | 1 0 0 | 3 0 0 | 9 0 0 |
     * | 0 7 6 | 9 0 0 | 1 0 0 |
     * | 0 0 8 | 1 0 0 | 3 7 6 |
     * -------------------------
     * @return A string that visually represents the Sudoku board, with section
     *         dividers between every 3x3 block (or customizable depending on the
     *         `GRID_SIZE` and `SECTION_SIZE`).
     */
    @Override
    public String toString() {
        StringBuilder info = new StringBuilder();
        int rowCounter = 0, colCounter = 0;
        info.append("-------------------------").append('\n');
        for (int row = 0; row < GRID_SIZE; row++) {
            info.append("|");
            rowCounter++;
            for (int col = 0; col < GRID_SIZE; col++) {
                colCounter++;
                info.append(" ").append(sudokuBoard[row][col].toString());
                if (colCounter == SECTION_SIZE) {
                    info.append(" |");
                    colCounter = 0;
                }
            }
            info.append('\n');
            if (rowCounter == SECTION_SIZE) {
                info.append("-------------------------").append('\n');
                rowCounter = 0;
            }
        }
        return info.toString();
    }
}
