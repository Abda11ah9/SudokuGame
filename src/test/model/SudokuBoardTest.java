package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;


import static org.junit.jupiter.api.Assertions.*;

public class SudokuBoardTest {
    protected SudokuBoard emptyBoard;
    protected SudokuBoard normalBoard;
    protected int size;
    protected Cell position;

    @BeforeEach
    void setup() {
        int[][] board = {
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
        normalBoard = new SudokuBoard(board);
        emptyBoard = new SudokuBoard();
        size = emptyBoard.getAvailableCells()- normalBoard.getAvailableCells();
        position = new Cell(2, 5, 1);

    }

    @Test
    void firstConstructorTest() {
        SudokuBoard copy = new SudokuBoard(normalBoard);
        assertEquals(size, 81 - copy.getAvailableCells());
        assertEquals(normalBoard.getCellOnBoard(0,8),copy.getCellOnBoard(0,8));
        assertEquals(copy, normalBoard);
    }

    @Test
    void secondConstructorTest() {
        int[][] board = new int [SudokuBoard.BOARD_SIZE][SudokuBoard.BOARD_SIZE];
        for (int[] arr1 : board) {
            Arrays.fill(arr1, 1);
        }
        SudokuBoard test = new SudokuBoard(board);
        assertEquals(0, test.getAvailableCells());
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board.length; col++) {
                assertEquals(board[row][col], test.getCellOnBoard(row,col).getValue());
            }
        }
    }

    @Test
    void thirdConstructorTest() {
        assertEquals(81,emptyBoard.getAvailableCells());
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                assertEquals(0, emptyBoard.getCellOnBoard(row,col).getValue());
            }
        }
    }

    @Test
    void setAvailableCellsTest() {
        normalBoard.setAvailableCells(9);
        assertEquals(9, normalBoard.getAvailableCells());
    }

    @Test
    void equalsTest() {
        assertNotEquals(emptyBoard, normalBoard);
        normalBoard.createNewBoard();
        assertEquals(emptyBoard, normalBoard);
        assertFalse(emptyBoard.equals(null));
        assertEquals(emptyBoard, emptyBoard);


    }

    @Test
    void setCellOnBoardTest() {
        assertTrue(normalBoard.setCellOnBoard(position));
        assertEquals(size+1, 81 - normalBoard.getAvailableCells());
        position.setValue(12);
        assertFalse(normalBoard.setCellOnBoard(position));
    }

    @Test
    void isInsertValidTest() {
        assertFalse(normalBoard.isInsertValid(position,12));
        assertFalse(normalBoard.isInsertValid(position,1));
        assertTrue(normalBoard.isInsertValid(position,2));
        assertFalse(normalBoard.isInsertValid(position,5));
        assertTrue(normalBoard.isInsertValid(position));
        position.setValue(1);
        assertFalse(normalBoard.isInsertValid(position));
        position.setRow(3);
        position.setColumn(4);
        assertFalse(normalBoard.isInsertValid(position));//checks the box condition
        Cell curr = new Cell(8,1,1);
        assertFalse(normalBoard.isInsertValid(curr,8));
        curr.setRow(0);
        assertFalse(normalBoard.isInsertValid(curr,8));

    }

    @Test
    void createNewBoardTest() {
        normalBoard.createNewBoard();
        assertEquals(emptyBoard,normalBoard);
    }

    @Test
    void isPositionEmptyTest() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                assertTrue(emptyBoard.isPositionEmpty(new Cell(9, row, col)));
            }
        }
        assertTrue(normalBoard.isPositionEmpty(position));
    }

    @Test
    void getCellOnBoard() {
        Cell curr  = new Cell(0,0,0);
        assertEquals(8,normalBoard.getCellOnBoard(curr));
        assertEquals(curr.getValue(), emptyBoard.getCellOnBoard(curr));
    }

    @Test
    void toStringTest() {
        StringBuilder output = new StringBuilder("---------------------\n");

        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0 && i != 0) {
                output.append("---------------------\n");
            } for (int j = 0; j < 9; j++) {
                if (j % 3 == 0 && j != 0) {
                    output.append("| ");
                }
                output.append(normalBoard.getCellOnBoard(i,j));
                if (j == 8) {
                    output.append("\n");
                }
            }
        }
        output.append("---------------------\n");
        assertEquals(normalBoard.toString(), output.toString());
    }


}
