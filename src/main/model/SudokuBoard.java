package model;

import org.json.JSONArray;
import persistence.Writable;
import java.util.Arrays;
import java.util.Objects;
import org.json.JSONObject;


/**
 * This class represents that sudoku board where elements could be added
 * or removed from the board. It contains all the method responsible for
 * the checking of the sudoku game rules.
 * (i.e. numbers shouldn't be repeated on the same row,column, and box).
 **/
public class SudokuBoard implements Writable {

    public static final int BOARD_SIZE = 9;
    private final Cell[][] board = new Cell[BOARD_SIZE][BOARD_SIZE]; //Representing the board as a 2D array
    protected int availableCells = BOARD_SIZE * BOARD_SIZE;

    //REQUIRES: a Non-Null Object
    //MODIFIES: This.
    //EFFECTS: copies the objects values to This.
    public SudokuBoard(SudokuBoard toCopy) {
        createNewBoard();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j].setValue(toCopy.getCellOnBoard(i,j).getValue());
                if (toCopy.getCellOnBoard(i,j).getValue() != 0) {
                    availableCells--;
                }
            }
        }
    }

    //REQUIRES: a Non-Null Object that is 2D Array
    //MODIFIES: This.
    //EFFECTS: copies the Arrays values to This.
    public SudokuBoard(int[][] newBoard) {
        createNewBoard();
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (newBoard[row][col] != 0) {
                    board[row][col].setValue(newBoard[row][col]);
                    availableCells--;
                }
            }
        }

    }

    //EFFECTS: generates a default board.
    public SudokuBoard() {
        int[][] newBoard = {
                { 8, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 3, 6, 0, 0, 0, 0, 0 },
                { 0, 7, 0, 0, 9, 0, 2, 0, 0 },
                { 0, 5, 0, 0, 0, 7, 0, 0, 0 },
                { 0, 0, 0, 0, 4, 5, 7, 0, 0 },
                { 0, 0, 0, 1, 0, 0, 0, 3, 0 },
                { 0, 0, 1, 0, 0, 0, 0, 6, 8 },
                { 0, 0, 8, 5, 0, 0, 0, 1, 0 },
                { 0, 9, 0, 0, 0, 0, 4, 0, 0 }
        };
        createNewBoard();
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (newBoard[row][col] != 0) {
                    board[row][col].setValue(newBoard[row][col]);
                    availableCells--;
                }
            }
        }
    }

    //REQUIRES: valid coordinates [(0,0)-(8,8)].
    //EFFECTS: returns the value at the given position
    public Cell getCellOnBoard(int x, int y) {
        return board[x][y];
    }

    //REQUIRES: valid Cell on board.
    //EFFECTS: returns the value at the given Cell
    public int getCellOnBoard(Cell curr) {
        return getCellOnBoard(curr.getRow(), curr.getColumn()).getValue();
    }

    public int getAvailableCells() {
        return availableCells;
    }

    //REQUIRES: availableCells >= 0.
    //MODIFIES: This.
    //EFFECTS: sets the remaining empty cells to availableCells.
    public void setAvailableCells(int availableCells) {
        this.availableCells = availableCells;
    }

    //REQUIRES: a Non-Null object & Cell objects needs to be valid for insertion.
    //MODIFIES: this.
    //EFFECTS: sets the Desired cell on the Board if possible
    public boolean setCellOnBoard(Cell cell) {
        if (isInsertValid(cell)) {
            String action = "User added " + cell.getValue();
            action += " at position [" + (cell.getRow() + 1) + ", " + (cell.getColumn() + 1) + "]";
            board[cell.getRow()][cell.getColumn()] = cell;
            availableCells--;
            EventLog.getInstance().logEvent(new Event(action));
            if (availableCells == 0) {
                EventLog.getInstance().logEvent(new Event("Board is Full"));
            }
            return true;
        }
        return false;
    }

    //REQUIRES: a valid position
    //EFFECTS: returns if duplicates in the same row
    private boolean isNumberInRow(Cell pos) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board[i][pos.getColumn()].equals(pos)) {
                return true;
            }
        }
        return false;
    }

    //REQUIRES: valid Cell on Board.
    //EFFECTS: returns if duplicates in the same row
    private boolean isNumberInRow(Cell pos, int value) {

        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board[i][pos.getColumn()].getValue() == value) {
                return true;
            }
        }
        return false;
    }

    //REQUIRES: a valid position
    //EFFECTS: returns if duplicates in the same column
    private boolean isNumberInCol(Cell pos) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board[pos.getRow()][i].equals(pos)) {
                return true;
            }
        }
        return false;
    }

    //REQUIRES: a valid position
    //EFFECTS: returns if duplicates in the same column
    private boolean isNumberInCol(Cell pos, int value) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board[pos.getRow()][i].getValue() == value) {
                return true;
            }
        }
        return false;
    }

    //REQUIRES: a valid position
    //EFFECTS: returns if duplicates in the same 3x3 box
    private boolean isNumInBox(Cell pos) {
        int[] topLeft = getBoxTopLeft(pos);

        for (int x = topLeft[0]; x < topLeft[0] + 3; x++) {
            for (int y = topLeft[1]; y < topLeft[1] + 3; y++) {
                if (board[x][y].equals(pos)) {
                    return true;
                }
            }
        }
        return false;
    }

    //REQUIRES: a valid position
    //EFFECTS: returns if duplicates in the same 3x3 box
    private boolean isNumInBox(Cell pos, int value) {
        int[] topLeft = getBoxTopLeft(pos);

        for (int x = topLeft[0]; x < topLeft[0] + 3; x++) {
            for (int y = topLeft[1]; y < topLeft[1] + 3; y++) {
                if (board[x][y].getValue() == value) {
                    return true;
                }
            }
        }
        return false;
    }

    //REQUIRES: a valid position
    //EFFECTS: returns if the position is empty.
    public boolean isPositionEmpty(Cell pos) {
        Cell curr = board[pos.getRow()][pos.getColumn()];
        return curr ==  null || curr.getValue() == 0;
    }

    //REQUIRES: a valid position
    //EFFECTS: returns the top left coordinates of the box
    //         where the cell is located.
    private int[] getBoxTopLeft(Cell cell) {
        int topLeftRow = cell.getRow() - (cell.getRow() % 3);
        int topLeftCol = cell.getColumn() - (cell.getColumn() % 3);

        return new int[]{topLeftRow,topLeftCol};
    }

    //REQUIRES: a valid position
    //EFFECTS: returns if insertion is possible at the given position.
    public boolean isInsertValid(Cell cell) {
        if (cell.getValue() < 1 || cell.getValue() > 9) {
            return false;
        }
        return !isNumberInCol(cell) && !isNumberInRow(cell) && !isNumInBox(cell);
    }

    public boolean isInsertValid(Cell cell, int value) {
        if (value < 1 || value > 9) {
            return false;
        }
        return !isNumberInCol(cell,value) && !isNumberInRow(cell, value) && !isNumInBox(cell, value);
    }


    //MODIFIES: this
    //EFFECTS: generates a new empty board.
    protected void createNewBoard() {
        for (int x = 0; x < BOARD_SIZE;x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                board[x][y] = new Cell(0, x, y);
            }
        }
    }

    //EFFECTS: returns if every single Cell is identical with the given board
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SudokuBoard that = (SudokuBoard) o;
        //comparing each value on the board with 'that'
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Cell curr = that.getCellOnBoard(row,col);
                if (curr.getValue() != board[row][col].getValue()) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder("---------------------\n");

        for (int i = 0; i < BOARD_SIZE; i++) {
            if (i % 3 == 0 && i != 0) {
                output.append("---------------------\n");
            }
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (j % 3 == 0 && j != 0) {
                    output.append("| ");
                }
                output.append(getCellOnBoard(i,j));

                if (j == BOARD_SIZE - 1) {
                    output.append("\n");
                }

            }
        }
        output.append("---------------------\n");
        return output.toString();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Board", thingiesToJson());
        return json;
    }

    // EFFECTS: returns the current board as a JSON 2D array
    private JSONArray thingiesToJson() {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < BOARD_SIZE; i++) {
            JSONArray ja = new JSONArray();
            for (int j = 0;j < BOARD_SIZE; j++) {
                ja.put(getCellOnBoard(i,j).toJson());
            }
            jsonArray.put(ja);
        }
        return jsonArray;
    }
}
