package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import static model.SudokuBoard.BOARD_SIZE;
/**
 * This class is responsible for getting a solution to any given SudokuBoard
 * (if solvable of course). It behaves as the hidden board the user can't see,
 * that will be a reference to whether any input is correct or not.
 * */

public class BoardSolution {
    private final SudokuBoard solution;
    private final boolean isSolvable;



    //REQUIRES: a SudokuBoard
    //MODIFIES: this
    //EFFECTS: solves the given SudokuBoard
    public BoardSolution(SudokuBoard currentBoard) {
        solution = new SudokuBoard(currentBoard);
        isSolvable = solveBoard();
        if (isSolvable) {
            solution.setAvailableCells(0);
        }
    }

    //EFFECTS: returns whether the board is solvable or not
    public boolean isSolvable() {
        return isSolvable;
    }

    //REQUIRES: a valid Cell on board
    //EFFECTS: returns the value at the given Cell
    public Cell getCellInSolution(Cell pos) {

        if (pos.getRow() > 8 || pos.getColumn() < 0) {
            return null;
        }
        return solution.getCellOnBoard(pos.getRow(), pos.getColumn());
    }

    //MODIFIES: this
    //EFFECTS: solves the given SudokuBoard
    private boolean solveBoard() {
        //Using backtracking
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                Cell currentPosition = solution.getCellOnBoard(i, j);
                if (solution.isPositionEmpty(currentPosition)) {
                    for (int attempt = 1; attempt <= BOARD_SIZE; attempt++) {
                        if (solution.isInsertValid(currentPosition, attempt)) {
                            currentPosition.setValue(attempt);
                            solution.setCellOnBoard(currentPosition);
                            if (solveBoard()) {
                                return true;
                            } else {
                                currentPosition.setValue(0);
                            }

                        }
                    }
                    return false;

                }
            }
        }
        return true;
    }

    //EFFECTS: returns the solution
    public SudokuBoard getSolution() {
        return solution;
    }
}
