package se.kth.emmajoh2.sudokuapp.model;

import java.io.Serializable;

import static se.kth.emmajoh2.sudokuapp.model.MatrixGenerator.GRID_SIZE;

/**
 * Represents an individual tile in a Sudoku grid.
 * <p>
 * Each tile holds its solution, the initial number (whether it's part of the
 * original puzzle or not), and its current number (the number entered by the player).
 * The class provides methods to manage the tile's current state, reset it,
 * and check if the tile is correctly placed.
 * </p>
 *
 * This class implements {@link Serializable}, allowing it to be serialized
 * for saving the state of a Sudoku game.
 */
public class SelectedTile implements Serializable {
    private final int solution;
    private final int initialNumber;
    private int currentNumber;

    /**
     * Constructs a new {@code SelectedTile} with the given current number, solution, and initial number.
     *
     * @param currentNumber The current number entered by the player (0 if blank).
     * @param solution The correct number for the tile (the solution).
     * @param initialNumber Indicates whether the tile was pre-filled in the initial puzzle (non-zero if pre-filled).
     */
    SelectedTile(int currentNumber, int solution, int initialNumber) {
        this.solution = solution;
        this.initialNumber = initialNumber;
        this.currentNumber = currentNumber;
    }

    /**
     * Returns the current number on the tile.
     *
     * @return The current number entered by the player, or 0 if the tile is empty.
     */
    int getTileNb() {
        return currentNumber;
    }

    /**
     * Returns the correct number for the tile (the solution).
     *
     * @return The correct solution for this tile.
     */
    int getCorrectTile() {
        return solution;
    }

    /**
     * Resets the tile's current number to 0 (blank) if it is not an initial tile.
     *
     * @return {@code true} if the tile was reset, {@code false} if it is an initial tile and cannot be reset.
     */
    boolean resetTile() {
        if (!isInitialTile()) {
            currentNumber = 0;
            return true;
        }
        else return false;
    }

    /**
     * Checks if the tile is an initial tile, meaning it was pre-filled in the puzzle.
     *
     * @return {@code true} if the tile is an initial tile, {@code false} otherwise.
     */
    boolean isInitialTile() {
        if (initialNumber!=1) return true;
        else return false;
    }

    /**
     * Checks if the tile's current number matches the correct solution.
     *
     * @return {@code true} if the tile's current number is correct, {@code false} otherwise.
     */
    boolean isCorrectlyPlaced() {
        if (currentNumber == solution) return true;
        else return false;
    }

    /**
     * Sets the tile's current number, provided the tile is not an initial tile and the number is valid.
     *
     * @param number The number to set on the tile.
     * @return {@code true} if the tile's number was successfully set, {@code false} if the tile is an initial tile.
     * @throws IllegalArgumentException If the provided number is invalid (not between 0 and the grid size).
     */
    boolean setTile(int number) {
        if(!isSudokuNb(number)) throw new IllegalArgumentException();
        if (!isInitialTile()) {
            currentNumber = number;
            return true;
        }
        else return false;
    }

    /**
     * Checks if the given number is valid for a Sudoku tile.
     *
     * @param number The number to check.
     * @return {@code true} if the number is between 0 and the grid size, inclusive.
     */
    private boolean isSudokuNb(int number) {
        if (number >= 0 && number<= GRID_SIZE) return true; //om 0 clear
        else return false;
    }
}
