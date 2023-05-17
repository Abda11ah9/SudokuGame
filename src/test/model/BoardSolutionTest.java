package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class BoardSolutionTest {
    protected SudokuBoard normal;
    protected SudokuBoard notSolvable;
    protected BoardSolution solutionNormal;
    protected BoardSolution solutionNotSolv;

    @BeforeEach
    void setup() {
        int[][] board = {
                {3, 0, 1, 0, 0, 0, 5, 2, 7},
                {7, 2, 4, 5, 1 ,0, 0, 0, 6},
                {0, 9, 0, 2,  7, 8, 3, 0, 0},
                {9, 5, 6, 7, 3, 0, 0, 8, 0},
                {8, 4, 0, 0, 0, 2, 6, 0, 3},
                {1, 2, 3, 6, 8, 0, 0, 4, 0},
                {4, 6, 9, 8, 5, 7,0 ,0 , 0},
                {0, 0, 0, 9, 2, 1, 4, 0, 8},
                {2, 1, 0, 3, 4, 0, 9, 7, 0}
        };
        int[][] board2 = {
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
        normal = new SudokuBoard(board2);
        notSolvable = new SudokuBoard(board);
        solutionNotSolv = new BoardSolution(notSolvable);
        solutionNormal = new BoardSolution(normal);
    }

    @Test
    void constructorTest() {
        assertFalse(solutionNotSolv.isSolvable());
        assertTrue(solutionNormal.isSolvable());
    }

    @Test
    void getCellInSolutionTest() {
        Cell valid = new Cell(1, 0, 1);
        Cell notValid = new Cell(2, 9, 1);
        assertNull(solutionNormal.getCellInSolution(notValid));
        assertEquals(1, solutionNormal.getCellInSolution(valid).getValue());
    }

}
