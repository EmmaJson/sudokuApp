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
class MatrixGenerator {
    public static final int GRID_SIZE = 9;
    public static final int SECTIONS_PER_ROW = 3;
    public static final int SECTION_SIZE = 3;

     static int[][][] initGameBoard(SudokuLevel level) {
        return generateSudokuMatrix(level);
    }

    /**
     * Generates a Sudoku matrix with an initial standing and solution, based on the specified difficulty level.
     *
     * @param level The difficulty level of the Sudoku puzzle (EASY, MEDIUM, HARD).
     * @return A 3-dimensional array where [row][col][0] holds the initial values (0 for empty cells)
     *         and [row][col][1] holds the solution.
     */
    private static int[][][] generateSudokuMatrix(SudokuLevel level) {
        switch (level) {
            case EASY:
                return setLevel(generateRandomizedBoard(), 40);
            case MEDIUM:
                return setLevel(generateRandomizedBoard(), 30);
            case HARD:
                return setLevel(generateRandomizedBoard(), 17);
            default:
                return setLevel(generateRandomizedBoard(), 30);
        }
    }

    private static int[][][] generateRandomizedBoard() {
        Random random = new Random();
        int[][] matrix = generateInitBoard();
        int[][][] newMatrix = new int[GRID_SIZE][GRID_SIZE][3];
        int randomNb1, randomNb2;
        for (int i = 0; i < GRID_SIZE; i++) {
            randomNb1 = random.nextInt(GRID_SIZE)+1;
            randomNb2 = random.nextInt(GRID_SIZE)+1;
            for (int row = 0; row < GRID_SIZE; row++) {
                for (int col = 0; col < GRID_SIZE; col++) {
                    if      (matrix[row][col] == randomNb1)     matrix[row][col] = randomNb2;
                    else if (matrix[row][col] == randomNb2)     matrix[row][col] = randomNb1;
                    newMatrix[row][col][0] = matrix[row][col];
                    newMatrix[row][col][1] = matrix[row][col];
                }
            }
        }
        return newMatrix;
    }

    private static int[][] generateInitBoard() {
        int[][] matrix = new int[GRID_SIZE][GRID_SIZE];
        int[] sudokuNbs = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int shift;
        for (int row = 0; row < GRID_SIZE; row++) {
           shift = row * SECTION_SIZE + row / SECTION_SIZE;
            for (int col = 0; col < GRID_SIZE; col++) {
                matrix[row][col] = sudokuNbs[(shift + col) % GRID_SIZE];
                // exempel Row 0:
                //(0 / 3) + (0 % 3) * 3 = 0 + 0 = 0 → No shift
                //0+2 % 9 = 2 ==> SudokuNb = 3
            }
        }
        return matrix;
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
                matrix[randomRow][randomCol][2] = 1;
            } else {
                i++;  // Retry if the cell is already empty.
            }

        }
        return matrix;
    }
}
