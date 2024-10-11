package se.kth.emmajoh2.sudokuapp.model;

import java.io.Serializable;

/**
 * The {@code SudokuLevel} enum defines the difficulty levels for the Sudoku puzzle.
 * <p>
 * The difficulty levels control how many cells are pre-filled in the puzzle,
 * with fewer filled cells making the puzzle harder to solve.
 * </p>
 * <ul>
 *     <li>{@code EASY}: A large number of cells are pre-filled, making the puzzle easier.</li>
 *     <li>{@code MEDIUM}: A moderate number of cells are pre-filled, providing a balanced challenge.</li>
 *     <li>{@code HARD}: Few cells are pre-filled, making the puzzle more difficult to solve.</li>
 * </ul>
 * This enum implements {@link Serializable}, which allows the difficulty level to be saved and restored when the game is serialized.
 */
public enum SudokuLevel implements Serializable {
    EASY, MEDIUM, HARD
}
