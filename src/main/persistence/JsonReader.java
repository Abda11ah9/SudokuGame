package persistence;

import model.Cell;
import model.SudokuBoard;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public SudokuBoard read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseSudokuBoard(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses board from JSON object and returns it
    private SudokuBoard parseSudokuBoard(JSONObject jsonObject) {
        SudokuBoard sb = new SudokuBoard();
        addBoard(sb, jsonObject);
        return sb;
    }

    // MODIFIES: sb
    // EFFECTS: parses cells from JSON object and adds them to board
    private void addBoard(SudokuBoard sb, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Board");

        for (Object json : jsonArray) {
            JSONArray nextRow = (JSONArray) json;
            for (Object pos : nextRow) {
                JSONObject cell = (JSONObject) pos;
                addCell(sb, cell);
            }

        }
    }

    // MODIFIES: sb
    // EFFECTS: parses cell from JSON object and adds puts it on Board
    private void addCell(SudokuBoard sb, JSONObject jsonObject) {

        int row = (int) jsonObject.get("row");
        int col = (int) jsonObject.get("column");
        int value = (int) jsonObject.get("value");
        Cell pos = new Cell(value,row,col);
        sb.setCellOnBoard(pos);

    }

}
