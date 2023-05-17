package model;


import java.util.Objects;
import org.json.JSONObject;
import persistence.Writable;
/**This Class represents each cell on the Sudoku Board
where each cell contains its coordinates on the board & the value it contains.
 **/

public class Cell implements Writable {
    private int value;
    private int row;
    private int column;

    //REQUIRES: a value [1-9] and (x,y) coordinates [(0,0)-(8,8)]
    //Modifies: this
    public Cell(int value, int x, int y) {
        row = x;
        column = y;
        this.value = value;
    }

    //MODIFIES: This
    //EFFECTS: sets the values to their default state
    public Cell() {
        row = -1;
        column = -1;
        value = 0;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    //EFFECTS: returns if the cell has the same value as This
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cell cell = (Cell) o;
        return value == cell.value;
    }

    @Override
    public String toString() {
        return String.format("%d ",getValue());
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("value", value);
        json.put("row", row);
        json.put("column", column);
        return json;
    }
}
