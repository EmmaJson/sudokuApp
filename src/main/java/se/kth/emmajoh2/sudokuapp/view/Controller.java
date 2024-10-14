package se.kth.emmajoh2.sudokuapp.view;

import se.kth.emmajoh2.sudokuapp.model.SudokuIO;
import se.kth.emmajoh2.sudokuapp.model.SudokuLevel;
import se.kth.emmajoh2.sudokuapp.model.SudokuModel;

import java.io.File;
import java.io.IOException;

import static se.kth.emmajoh2.sudokuapp.model.SudokuIO.deserializeFromFile;

/**
 * The {@code Controller} class manages the communication between the {@code SudokuModel} and {@code SudokuView}.
 * <p>
 * It handles user actions such as selecting tiles, pressing buttons, saving/loading the game, and interacting
 * with the Sudoku game logic. The controller listens for user input, updates the model, and refreshes the view
 * accordingly.
 * </p>
 */
public class Controller {
    private final SudokuModel model;
    private final SudokuView view;

    /**
     * Constructs a {@code Controller} to manage communication between the model and view.
     *
     * @param model The {@link SudokuModel} that holds the game data and logic.
     * @param view The {@link SudokuView} that displays the game board and handles user interaction.
     */
    public Controller(SudokuModel model, SudokuView view) {
        this.model = model;
        this.view = view;
    }

    /**
     * Handles when a tile is selected and a number is input by the user.
     * Updates the model with the selected number for the given row and column, and refreshes the view.
     * If all tiles are correct after the input, it displays a "Game over" message.
     * @param row The row of the selected tile.
     * @param col The column of the selected tile.
     */
    public void onTileSelectedOrSomeSuch(int row, int col) {
        System.out.println("Tile Pressed: " + row + col);
        //model.addNumber(row, col, number);
        model.addNumber(row, col);
        view.updateBoard(model);
        if (model.allTilesCorrect()) view.alert("Game over", "You solved the board");
    }

    /**
     * Handles the event when a number button is pressed.
     * This method is a placeholder to handle logic when number buttons are pressed.
     * @param number The number entered by pressing a button.
     */
    public void onNumberButton(int number) {
        //System.out.println("Button pressed: " + number);
        model.setPressedButtonNumber(number);
    }

    /**
     * Checks the current placement of the tiles to determine if they are all placed correctly.
     * <p>
     * If all placed tiles are correct, an alert will display that the placement is correct.
     * Otherwise, an alert will display that some tiles are not in the right place.
     * </p>
     */
    public void onCheck() {
        if (model.placedTilesCorrect()) {
            view.alert("Check placement", "Currently all the tiles are in the right place");
        } else view.alert("Check placement", "Currently all the tiles are in not the right place");
    }

    /**
     * Provides a hint to the player by automatically filling one incorrect or empty tile.
     * <p>
     * The view is updated with the hint, and if all tiles are correct after the hint, a "Game over" message is displayed.
     * </p>
     */
    public void onHint() {
        if (model.addhint()) {
            view.alert("Hint", "A hint has been placed");
            view.updateBoard(model);
        }
        if (model.allTilesCorrect()) view.alert("Game over", "You solved the board");
    }

    /**
     * Starts a new game with the specified difficulty level.
     * <p>
     * Depending on the mode passed, the game is initialized with either {@code EASY}, {@code MEDIUM}, or {@code HARD} difficulty.
     * The view is updated to reflect the new game state.
     * </p>
     *
     * @param mode The difficulty mode for the new game (1 for EASY, 2 for MEDIUM, 3 for HARD).
     */
    public void onNewGame(int mode) {
        switch (mode) {
            case 1 :    model.initGame(SudokuLevel.EASY); break;
            case 2 :    model.initGame(SudokuLevel.MEDIUM); break;
            case 3 :    model.initGame(SudokuLevel.HARD); break;
            default:    model.initGame();
        }
        view.updateBoard(model);
    }

    /**
     * Displays the Sudoku rules to the player.
     * <p>
     * The rules explain the objective of the game and a link is provided to a detailed explanation.
     * </p>
     */
    public void onRules() {
        view.alert("Sudoku Rules",
                "Sudoku is played on a grid of 9 x 9 spaces. " +
                        "Within the rows and columns are 9 “squares” (made up of 3 x 3 spaces). " +
                        "Each row, column and square (9 spaces each) needs to be filled out with the numbers 1-9, " +
                        "without repeating any numbers within the row, column or square." + '\n' +
                        "https://sudoku.com/how-to-play/sudoku-rules-for-complete-beginners/");
    }

    /**
     * Clears all moves made by the player by resetting all non-initial tiles.
     * <p>
     * The game board is refreshed after clearing the tiles.
     * </p>
     */
    public void onClear() {
        model.resetMoves();
        view.updateBoard(model);
    }

    /**
     * Saves the current game state to a file named "sudoku.ser".
     * <p>
     * The Sudoku board is serialized and saved to the file. An alert is displayed if the game is successfully saved,
     * or if an error occurs during saving.
     * </p>
     */
    public void onSave() {
        File file = new File("sudoku.ser");
        try {
            SudokuIO.serializeToFile(file, model.getSudokuBoard());
            view.alert("Save", "Game saved successfully!");
            System.out.println("Game saved successfully!");
        } catch (IOException e) {
            view.alert("Save Error", "Failed to save the game: " + e.getMessage());
            e.printStackTrace();  // Print full error to help with debugging
        }
    }

    /**
     * Loads a previously saved game state from the file "sudoku.ser".
     * <p>
     * The Sudoku board is deserialized from the file. The game board is updated after loading the saved state,
     * and an alert is displayed to confirm that the game was successfully loaded or if an error occurs during loading.
     * </p>
     */
    public void onLoad() {
        File file = new File("sudoku.ser");
        try {

            model.loadBoard(deserializeFromFile(file));
            view.updateBoard(model);
            view.alert("Load", "Game loaded successfully!");
            System.out.println("Game loaded successfully!");
        } catch (IOException | ClassNotFoundException e) {
            view.alert("Load Error", "Failed to load the game: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
