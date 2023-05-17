package persistence;

import model.Cell;
import model.SudokuBoard;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest{

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            SudokuBoard sb = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyBoard() {
        JsonReader reader = new JsonReader("./data/testReaderEmptySudokuBoard.json");
        try {
            SudokuBoard sb = reader.read();
            for (int i = 0; i < SudokuBoard.BOARD_SIZE; i++) {
                for (int j = 0; j < SudokuBoard.BOARD_SIZE; j++) {
                    assertTrue(sb.isPositionEmpty(sb.getCellOnBoard(i,j)));
                }
            }
            assertEquals(81, sb.getAvailableCells());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testGeneralSudokuBoard.json");
        try {
            SudokuBoard sb = reader.read();
            Cell curr = new Cell(1,0,0);
            checkBoard(sb,curr);
            curr.setValue(2);
            curr.setColumn(1);
            checkBoard(sb, curr);

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
