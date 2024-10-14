package se.kth.emmajoh2.sudokuapp.model;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

import static se.kth.emmajoh2.sudokuapp.model.MatrixGenerator.GRID_SIZE;

/**
 * Utility class for serialization and deserialization of SelectedTile[][] objects.
 */
public class SudokuIO {

    /**
     * Serializes a 2D array of {@link SelectedTile} objects to the specified file.
     * This method writes the provided 2D array of {@code SelectedTile} objects to the given file,
     * so that it can be deserialized later. The serialized object is written in a binary format.
     * @param data The 2D array of {@code SelectedTile} objects to be serialized.
     * @throws IOException If an I/O error occurs while writing to the file.
     */
    public static void serializeToFile(SelectedTile[][] data) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("SudokuFiles", "*.ser"));
        File selectedFile = fileChooser.showSaveDialog(null);


        ObjectOutputStream objectOutputStream = null;
        try {
            FileOutputStream fout = new FileOutputStream(selectedFile);
            objectOutputStream = new ObjectOutputStream(fout);
            objectOutputStream.writeObject(data);
        }
        catch (IOException e) {
            throw e;
        }
        finally {
            objectOutputStream.close();
        }
    }

    /**
     * Deserializes a 2D array of {@link SelectedTile} objects from the specified file.
     * This method reads the serialized object data from the given file and returns the
     * deserialized {@code SelectedTile[][]} grid. The file is expected to contain a previously
     * serialized 2D array of {@code SelectedTile} objects.
     * </p>
     * @return A 2D array of {@code SelectedTile} objects representing the deserialized Sudoku board.
     * @throws IOException If an I/O error occurs while reading from the file.
     * @throws ClassNotFoundException If the class of a serialized object cannot be found.
     */
    public static SelectedTile[][] deserializeFromFile() throws IOException, ClassNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Ser Files", "*.ser")
        );
        File file = fileChooser.showOpenDialog(null);
        ObjectInputStream objectInputStream = null;
        SelectedTile[][] loadTiles = new SelectedTile[GRID_SIZE][GRID_SIZE];
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            objectInputStream = new ObjectInputStream(fileInputStream);

            loadTiles = (SelectedTile[][]) objectInputStream.readObject();
        }
        catch (ClassNotFoundException e) {
            throw e;
        }
        finally {
            objectInputStream.close();
        }

        return loadTiles;
    }
    // Private constructor to prevent instantiation of utility class
    private SudokuIO() {}
}
