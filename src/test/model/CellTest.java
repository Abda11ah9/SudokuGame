package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {
    // delete or rename this class!
    protected Cell cell1;
    protected Cell cell2;


    @BeforeEach
    void reset() {
        cell1 = new Cell();
        cell2 = new Cell(9,1,1);
    }

    @Test
    void emptyConstructorTest() {
        assertEquals(0, cell1.getValue());
        assertEquals(-1, cell1.getRow());
        assertEquals(-1, cell1.getColumn());
    }

    @Test
    void ConstructorTest() {
        assertEquals(9, cell2.getValue());
        assertEquals(1, cell2.getRow());
        assertEquals(1, cell2.getColumn());
    }

    @Test
    void equalsTest() {
        Cell c = new Cell(9,1,1);
        assertFalse(cell1.equals(null));
        assertTrue(cell2.equals(c));
        assertTrue(cell2.equals(cell2));
        assertTrue(cell1.equals(cell1));
    }

    @Test
    void toStringTest() {
        assertEquals("9 ", cell2.toString());
        assertEquals("0 ", cell1.toString());
    }
}