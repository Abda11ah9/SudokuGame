package persistence;
import model.Cell;
import model.SudokuBoard;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class JsonTest {
    protected void checkBoard(SudokuBoard board, Cell curr) {
        assertEquals(curr.getValue(), board.getCellOnBoard(curr));
    }
}
