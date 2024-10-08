package se.kth.emmajoh2.sudokuapp.logic;
import java.util.Random;

/**
 * The {@code MatrixGenerator} class provides methods to generate a complete and valid Sudoku board,
 * which can be used to initialize and solve the puzzle. It also allows creating a Sudoku board with varying
 * difficulty levels by removing a specified number of values from the board.
 *
 * The Sudoku grid size is fixed at 9x9, and the board is represented as a 3-dimensional array, where:
 * <ul>
 *     <li>{@code [row][col][0]} holds the initial values (with 0 indicating an empty cell).</li>
 *     <li>{@code [row][col][1]} holds the solution of the puzzle.</li>
 * </ul>
 */
public class MatrixGenerator {
    public static final int GRID_SIZE = 9;
    public static final int SECTIONS_PER_ROW = 3;
    public static final int SECTION_SIZE = 3;

    /**
     * Generates a Sudoku matrix with an initial standing and solution, based on the specified difficulty level.
     *
     * @param level The difficulty level of the Sudoku puzzle (EASY, MEDIUM, HARD).
     * @return A 3-dimensional array where [row][col][0] holds the initial values (0 for empty cells)
     *         and [row][col][1] holds the solution.
     */
    public static int[][][] generateSudokuMatrix(SudokuLevel level) {
        switch (level) {
            case EASY:
                return setLevel(MatrixGenerator.generateFullBoard(), 40);
            case MEDIUM:
                return setLevel(MatrixGenerator.generateFullBoard(), 30);
            case HARD:
                return setLevel(MatrixGenerator.generateFullBoard(), 17);
            default:
                return setLevel(MatrixGenerator.generateFullBoard(), 30);
        }
    }

    /**
     * Sets the difficulty level of the Sudoku board by randomly removing a specified number of values.
     *
     * @param matrix The complete Sudoku matrix to modify.
     * @param level The number of cells to leave filled, defining the difficulty.
     * @return A 3-dimensional array representing the Sudoku board with empty cells (0) according to the difficulty level.
     */
    private static int[][][] setLevel(int[][][] matrix, int level) {
        Random random = new Random();
        int randomRow, randomCol;
        for (int i = GRID_SIZE * GRID_SIZE; i > level; i--) {
            randomRow = random.nextInt(GRID_SIZE);
            randomCol = random.nextInt(GRID_SIZE);
            if (matrix[randomRow][randomCol][0] != 0) {
                matrix[randomRow][randomCol][0] = 0;
            } else {
                i++;  // Retry if the cell is already empty.
            }
        }
        return matrix;
    }

    /**
     * Generates a fully solved Sudoku board using a backtracking algorithm.
     *
     * @return A 3-dimensional array representing the fully solved Sudoku board.
     * @throws IllegalStateException if the board cannot be solved.
     */
    private static int[][][] generateFullBoard() throws IllegalStateException {
        int[][][] matrixToReturn = new int[GRID_SIZE][GRID_SIZE][2];
        int[][] randomizedMatrix = new int[GRID_SIZE][GRID_SIZE];
        if (fillBoard(0, 0, randomizedMatrix)) {
            for (int row = 0; row < GRID_SIZE; row++) {
                for (int col = 0; col < GRID_SIZE; col++) {
                    matrixToReturn[row][col][0] = randomizedMatrix[row][col];
                    matrixToReturn[row][col][1] = randomizedMatrix[row][col];
                }
            }
        } else {
            throw new IllegalStateException("Failed to generate a valid Sudoku board.");
        }
        return matrixToReturn;
    }

    /**
     * Recursively fills the Sudoku board by placing numbers in each cell according to Sudoku rules.
     *
     * @param row               The current row index.
     * @param col               The current column index.
     * @param randomizedMatrix   The 2D array representing the Sudoku board to fill.
     * @return {@code true} if the board is successfully filled, {@code false} otherwise.
     */
    private static boolean fillBoard(int row, int col, int[][] randomizedMatrix) {
        if (row == GRID_SIZE) {
            return true;
        }
        int nextRow = (col == GRID_SIZE - 1) ? row + 1 : row;
        int nextCol = (col == GRID_SIZE - 1) ? 0 : col + 1;
        int[] nums = getRandomizedNumbers();
        for (int num : nums) {
            if (isValidPlacement(row, col, num, randomizedMatrix)) {
                randomizedMatrix[row][col] = num;

                if (fillBoard(nextRow, nextCol, randomizedMatrix)) {
                    return true;
                }
                randomizedMatrix[row][col] = 0;
            }
        }
        return false;
    }

    /**
     * Generates an array of randomized numbers from 1 to 9.
     *
     * @return An array of integers from 1 to 9 in randomized order.
     */
    private static int[] getRandomizedNumbers() {
        Random random = new Random();
        int[] nums = new int[GRID_SIZE];
        for (int i = 0; i < GRID_SIZE; i++) {
            nums[i] = i + 1;
        }
        for (int i = 0; i < GRID_SIZE; i++) {
            int randomIndex = random.nextInt(GRID_SIZE);
            int temp = nums[i];
            nums[i] = nums[randomIndex];
            nums[randomIndex] = temp;
        }
        return nums;
    }

    /**
     * Checks whether placing a number in the specified row and column is valid according to Sudoku rules.
     *
     * @param row The row index.
     * @param col The column index.
     * @param num The number to place.
     * @param randomizedMatrix The Sudoku board.
     * @return {@code true} if the placement is valid, {@code false} otherwise.
     */
    private static boolean isValidPlacement(int row, int col, int num, int[][] randomizedMatrix) {
        for (int i = 0; i < GRID_SIZE; i++) {
            if (randomizedMatrix[row][i] == num) {
                return false;
            }
        }
        for (int i = 0; i < GRID_SIZE; i++) {
            if (randomizedMatrix[i][col] == num) {
                return false;
            }
        }
        int boxRowStart = (row / SECTION_SIZE) * SECTION_SIZE;
        int boxColStart = (col / SECTIONS_PER_ROW) * SECTION_SIZE;
        for (int i = 0; i < SECTION_SIZE; i++) {
            for (int j = 0; j < SECTION_SIZE; j++) {
                if (randomizedMatrix[boxRowStart + i][boxColStart + j] == num) {
                    return false;
                }
            }
        }
        return true;
    }
}
