package se.kth.emmajoh2.sudokuapp.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import se.kth.emmajoh2.sudokuapp.model.SudokuModel;

import static se.kth.emmajoh2.sudokuapp.model.MatrixGenerator.*;

/**
 * The {@code SudokuView} class represents the visual interface for the Sudoku game.
 * <p>
 * It extends {@link BorderPane} and manages the grid of tiles, buttons, and menus that allow the player
 * to interact with the Sudoku puzzle. The view is responsible for updating the game board and handling
 * user input such as tile selections, button clicks, and menu actions.
 * </p>
 */
public class  SudokuView extends BorderPane {
    private transient Label[][] numberTiles; // the tiles/squares to show in the ui grid
    private transient GridPane numberPane;
    private SudokuModel model;
    private transient MenuBar menuBar;
    private Controller controller;
    private transient Button check;
    private transient Button hint;
    private int pressedButtonNumber;

    /**
     * Constructs a new {@code SudokuView} to display the Sudoku game.
     * <p>
     * The view initializes the grid of tiles, buttons, and menus, and connects them to the controller.
     * </p>
     *
     * @param model The {@link SudokuModel} representing the game's current state.
     */
    public SudokuView(SudokuModel model) {
        super();
        this.model = model;
        controller = new Controller(model,this);

        numberTiles = new Label[GRID_SIZE][GRID_SIZE];
        initNumberTiles();
        numberPane = makeNumberPane();
        this.setPadding(new Insets(10));

        this.setCenter(numberPane);
        this.setRight(createButtons(controller));

        check = new Button("Check");
        hint = new Button("Hint");
        VBox hintAndClear = new VBox();
        hintAndClear.getChildren().addAll(check,hint);
        hintAndClear.setAlignment(Pos.CENTER);
        hintAndClear.setPadding(new Insets(10));
        hintAndClear.setSpacing(1);
        setLeft(hintAndClear);

        Controller controller = new Controller(model,this);
        createButtons(controller);
        addEventHandlers(controller);
        createMenuBar(controller);
    }

    /**
     * Creates a vertical box (VBox) containing a set of number buttons (1-9) and a clear button ('C').
     * <p>
     * Each button's label represents a number (or 'C' for clearing). When clicked, the button's label
     * is used to determine the value to send to the controller. The clear button ('C') sets the value
     * to 0, while the number buttons set their corresponding numeric values.
     * </p>
     * <p>
     * The buttons are displayed vertically in the VBox. Each button is linked to a single event handler,
     * which extracts the button's label, processes it, and calls the corresponding method in the controller.
     * </p>
     *
     * @param controller The {@link Controller} responsible for handling the button actions.
     * @return A {@link VBox} containing the number buttons and the clear button.
     */
    private VBox createButtons(Controller controller) {
        VBox v2 = new VBox();
        v2.setAlignment(Pos.CENTER);
        String[] buttonLabels = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "C"};
        EventHandler<ActionEvent> buttonEventHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Button sourceButton = (Button) actionEvent.getSource();
                String buttonText = sourceButton.getText();
                if (buttonText.equals("C")) {
                    pressedButtonNumber = 0;
                } else {
                    pressedButtonNumber = Integer.parseInt(buttonText);
                }
                controller.onNumberButton(pressedButtonNumber);
            }
        };
        for (String label : buttonLabels) {
            Button button = new Button(label);
            if (label.equals("C")) {
                button.setUserData(0); // For the clear button
            } else {
                button.setUserData(Integer.parseInt(label)); // For number buttons
            }
            button.addEventHandler(ActionEvent.ACTION, buttonEventHandler);
            v2.getChildren().add(button);
        }
        v2.setPadding(new Insets(10));
        v2.setSpacing(1);
        return v2;
    }

    /**
     * Updates the game board by refreshing the UI tiles based on the current model state.
     * <p>
     * The font of each tile is adjusted depending on whether it is an initial tile (bold) or a user-input tile (normal).
     * </p>
     */
    public void updateBoard() {
        Font font = Font.font("Monospaced", FontWeight.NORMAL, 20);
        Font boldFont = Font.font("Monospaced", FontWeight.BOLD, 20);
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                String newValue = model.getTile(row, col); // Get updated data from the model

                Label tile = numberTiles[row][col];
                tile.setText(newValue); // Update the text with the new value

                // Update the font depending on whether it's an initial tile or not
                if (model.isInitTile(row, col)) {
                    tile.setFont(boldFont); // Use bold for pre-filled tiles
                } else {
                    tile.setFont(font); // Use normal font for user-modifiable tiles
                }

                // Optionally, you can update the tile's style or other properties here if needed
            }
        }
    }


    // use this method to get a reference to the number (called by some other class)
    public GridPane getNumberPane() {
        return numberPane;
    }

    /**
     * Initializes the tiles displayed in the grid based on the current state of the model.
     * <p>
     * This method is called by the constructor to populate the UI with the appropriate labels for each tile.
     * called by constructor (only)
     */
    private final void initNumberTiles() {
        Font font = Font.font("Monospaced", FontWeight.NORMAL, 20);
        Font boldFont = Font.font("Monospaced", FontWeight.BOLD, 20);
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                Label tile = new Label(model.getTile(row, col)); // data from model
                tile.setPrefWidth(32);
                tile.setPrefHeight(32);
                if (model.isInitTile(row, col)) tile.setFont(boldFont);
                else tile.setFont(font);
                tile.setAlignment(Pos.CENTER);
                tile.setStyle("-fx-border-color: black; -fx-border-width: 0.5px;"); // css style
                tile.setOnMouseClicked(tileClickHandler); // add your custom event handler
                // add new tile to grid
                numberTiles[row][col] = tile;
            }
        }
    }

    /**
     * Creates the 9x9 grid pane that holds the number tiles, organizing them into 3x3 sections.
     *
     * @return The constructed {@link GridPane} containing the number tiles.
     */
    private final GridPane makeNumberPane() {
        // create the root grid pane
        GridPane root = new GridPane();
        root.setStyle(
                "-fx-border-color: black; -fx-border-width: 1.0px; -fx-background-color: white;");

        // create the 3*3 sections and add the number tiles
        for (int srow = 0; srow < SECTIONS_PER_ROW; srow++) {
            for (int scol = 0; scol < SECTIONS_PER_ROW; scol++) {
                GridPane section = new GridPane();
                section.setStyle( "-fx-border-color: black; -fx-border-width: 0.5px;");

                // add number tiles to this section
                for (int row = 0; row < SECTION_SIZE; row++) {
                    for (int col = 0; col < SECTION_SIZE; col++) {
                        // calculate which tile and add
                        section.add(
                                numberTiles[srow * SECTION_SIZE + row][scol * SECTION_SIZE + col],
                                col, row);
                    }
                }

                // add the section to the root grid pane
                root.add(section, scol, srow);
            }
        }

        return root;
    }


    /**
     * Handles click events for the tiles in the Sudoku grid.
     * <p>
     * When a tile is clicked, it passes the row, column, and the number selected to the controller.
     * </p>
     */
    EventHandler<MouseEvent> tileClickHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            for (int row = 0; row < GRID_SIZE; row++) {
                for (int col = 0; col < GRID_SIZE; col++) {
                    if (event.getSource() == numberTiles[row][col]) {
                        System.out.println("Tile Pressed: " + row + col);
                        controller.onTileSelectedOrSomeSuch(row, col, pressedButtonNumber);
                    }
                }
            }
        }
    };

    /**
     * Adds event handlers to the "Check" and "Hint" buttons.
     * <p>
     * The "Check" button is connected to the {@code onCheck()} method in the {@link Controller}, which checks
     * if all placed tiles are correct. The "Hint" button is connected to the {@code onHint()} method in the {@link Controller},
     * which provides a hint by filling in one incorrect or empty tile.
     * </p>
     *
     * @param controller The {@link Controller} responsible for handling button actions.
     */
    private void addEventHandlers(Controller controller) {
        EventHandler<ActionEvent> clearHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                controller.onCheck();
            }
        };
        check.addEventHandler(ActionEvent.ACTION, clearHandler);

        EventHandler<ActionEvent> hintHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                controller.onHint();
            }
        };
        hint.addEventHandler(ActionEvent.ACTION, hintHandler);
    }

    /**
     * Creates the menu bar with "File", "Game", and "Help" menus.
     * <p>
     * The "File" menu contains options to load and save the game, and exit the application.
     * The "Game" menu allows starting new games with different difficulty levels.
     * The "Help" menu contains options to reset the game, check the current state of the board,
     * display Sudoku rules, and get a hint.
     * Each menu item is connected to the corresponding method in the {@link Controller} to handle the action.
     * </p>
     *
     * @param controller The {@link Controller} responsible for handling menu item actions.
     */
    private void createMenuBar(Controller controller) {
        Menu fileMenu = new Menu("File");
        MenuItem loadItem = new MenuItem("Load Game");
        MenuItem saveItem = new MenuItem("Save Game");
        MenuItem exitItem = new MenuItem("Exit");

        fileMenu.getItems().addAll(loadItem, saveItem, new SeparatorMenuItem(), exitItem);
        EventHandler<ActionEvent> loadHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                controller.onLoad();
            }
        };
        EventHandler<ActionEvent> saveHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                controller.onSave();
            }
        };
        EventHandler<ActionEvent> exitHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.exit(0);     //Save data?
            }
        };
        loadItem.addEventHandler(ActionEvent.ACTION, loadHandler);
        saveItem.addEventHandler(ActionEvent.ACTION, saveHandler);
        exitItem.addEventHandler(ActionEvent.ACTION, exitHandler);

        Menu gameMenu = new Menu("Game");
        MenuItem newItem = new MenuItem("Start new game");
        MenuItem easyItem = new MenuItem("New Easy Game");
        MenuItem mediumItem = new MenuItem("New Medium Game");
        MenuItem hardItem = new MenuItem("New Hard Game");

        gameMenu.getItems().addAll(newItem, new SeparatorMenuItem(), easyItem, mediumItem, hardItem);
        EventHandler<ActionEvent> newGameHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //newItem.
                controller.onNewGame(0);
            }
        };
        EventHandler<ActionEvent> easyHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //newItem.
                controller.onNewGame(1);
            }
        };
        EventHandler<ActionEvent> mediumHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //newItem.
                controller.onNewGame(2);
            }
        };
        EventHandler<ActionEvent> hardHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //newItem.
                controller.onNewGame(3);
            }
        };
        newItem.addEventHandler(ActionEvent.ACTION, newGameHandler);
        easyItem.addEventHandler(ActionEvent.ACTION, easyHandler);
        mediumItem.addEventHandler(ActionEvent.ACTION, mediumHandler);
        hardItem.addEventHandler(ActionEvent.ACTION, hardHandler);

        Menu helpMenu = new Menu("Help");
        MenuItem clearItem = new MenuItem("Reset Game");
        MenuItem checkItem = new MenuItem("Check");
        MenuItem rulesItem = new MenuItem("Rules");
        MenuItem hintItem = new MenuItem("Hint");

        helpMenu.getItems().addAll(clearItem, checkItem, rulesItem, hintItem);
        EventHandler<ActionEvent> clearHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                controller.onClear();
            }
        };
        EventHandler<ActionEvent> checkHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                controller.onCheck();
            }
        };
        EventHandler<ActionEvent> rulesHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //newItem.
                controller.onRules();
            }
        };
        EventHandler<ActionEvent> hintHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                controller.onHint();
            }
        };
        clearItem.addEventHandler(ActionEvent.ACTION, clearHandler);
        checkItem.addEventHandler(ActionEvent.ACTION, checkHandler);
        rulesItem.addEventHandler(ActionEvent.ACTION, rulesHandler);
        hintItem.addEventHandler(ActionEvent.ACTION, hintHandler);

        menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, gameMenu, helpMenu);
    }

    /**
     * Returns the menu bar that was created by the view.
     *
     * @return The {@link MenuBar} containing the file, game, and help menus.
     */
    public MenuBar getMenuBar() {
        return this.menuBar;
    }

    /**
     * Displays an alert dialog with the specified title and content text.
     * <p>
     * This method is used to show information messages to the user, such as game status or errors.
     * </p>
     *
     * @param title The title of the alert dialog.
     * @param contentText The message to display in the dialog.
     */
    public void alert(String title, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}

