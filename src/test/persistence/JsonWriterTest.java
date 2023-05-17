package persistence;

import model.Cell;
import model.SudokuBoard;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest extends JsonTest{

    @Test
    void testWriterInvalidFile() {
        try {
            SudokuBoard sb = new SudokuBoard();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            SudokuBoard sb = new SudokuBoard();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkroom.json");
            writer.open();
            writer.write(sb);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWorkroom.json");
            sb = reader.read();
            for (int i = 0; i < SudokuBoard.BOARD_SIZE; i++) {
                for (int j = 0; j < SudokuBoard.BOARD_SIZE; j++) {
                    assertTrue(sb.isPositionEmpty(sb.getCellOnBoard(i,j)));
                }
            }
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            SudokuBoard sb = new SudokuBoard();
            sb.setCellOnBoard(new Cell(1,0,0));
            sb.setCellOnBoard(new Cell(2,0,1));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWorkroom.json");
            writer.open();
            writer.write(sb);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralWorkroom.json");
            sb = reader.read();
            assertEquals(79, sb.getAvailableCells());
            Cell curr = new Cell(1,0,0);
            checkBoard(sb,curr);
            curr.setValue(2);
            curr.setColumn(1);
            checkBoard(sb, curr);

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
