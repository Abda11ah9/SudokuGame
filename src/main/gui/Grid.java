package gui;

import model.BoardSolution;
import model.Cell;
import model.EventLog;
import model.SudokuBoard;
import model.exceptions.LogException;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import static model.SudokuBoard.BOARD_SIZE;

public class Grid extends JPanel implements ActionListener {
    private static final String JSON_STORE = "./data/testGeneralSudokuBoard.json";
    private final ImageIcon bye = new ImageIcon("/Users/abdallahosama/Documents/project_s2i2t/src/main/icons/bye.png");
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final Font FONT = new Font("Verdana",
            Font.CENTER_BASELINE,
            20);
    private final JTextField[][] grid = new JTextField[BOARD_SIZE][BOARD_SIZE];
    private final Map<JTextField, Cell> fieldToCoordinates = new HashMap<>();
    private final JPanel gridPanel;
    private final JPanel buttonsPanel;
    private SudokuBoard playingBoard;
    private  BoardSolution solution;
    private  JPanel[][] miniSquarePanels;
    private final JFrame frame = new JFrame("Sudoku solver");
    private JButton save = new JButton("Save");
    private JButton continuePrev = new JButton("Continue");
    private JButton solve = new JButton("Solve");
    private JButton exit = new JButton("Quit");

    //MODIFIES: This
    //EFFECTS: Initializes all the components of the game
    public Grid() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        this.gridPanel = new JPanel();
        buttonsPanel = new JPanel();
        playingBoard = new SudokuBoard();
        solution = new BoardSolution(playingBoard);
        initializeFrame();

    }



   //MODIFIES: This
   //EFFECTS: initializes the text fields for each cell
    private void initializeGridFields() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                JTextField field = new JTextField();
                field.addKeyListener(new CellKeyListener(this));
                fieldToCoordinates.put(field, new Cell(0, col, row));
                grid[row][col] = field;

            }
        }
    }


    //MODIFIES: This
    //EFFECTS: initializes the Minisquares panel with required font and borders
    private void initializeFieldDimensions() {
        int minisquareDimension = (int) Math.sqrt(BOARD_SIZE);
        this.gridPanel.setLayout(new GridLayout(minisquareDimension, minisquareDimension));

        this.miniSquarePanels = new JPanel[minisquareDimension][minisquareDimension];

        Border minisquareBorder = BorderFactory.createLineBorder(Color.BLACK, 1);

        for (int y = 0; y < minisquareDimension; ++y) {
            for (int x = 0; x < minisquareDimension; ++x) {
                JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(minisquareDimension,
                        minisquareDimension));
                panel.setBorder(minisquareBorder);
                miniSquarePanels[y][x] = panel;
                gridPanel.add(panel);
            }
        }

        for (int y = 0; y < BOARD_SIZE; ++y) {
            for (int x = 0; x < BOARD_SIZE; ++x) {
                int minisquareX = x / minisquareDimension;
                int minisquareY = y / minisquareDimension;

                miniSquarePanels[minisquareY][minisquareX].add(grid[y][x]);
            }
        }

        this.gridPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK,
                2));
    }

    //MODIFIES: This
    //EFFECTS: initializes the view of the text fields to the user
    public void setBoard(SudokuBoard board) {
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
        Dimension fieldDimension = new Dimension(50, 50);
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                JTextField field = grid[row][col];
                field.setBorder(border);
                field.setFont(FONT);
                field.setPreferredSize(fieldDimension);
                field.setHorizontalAlignment(JTextField.CENTER);
                if (board.getCellOnBoard(row,col).getValue() == 0) {
                    grid[row][col].setText("");
                } else {
                    grid[row][col].setText(String.valueOf(board.getCellOnBoard(row,col).getValue()));
                    grid[row][col].setEditable(false);
                }
                int xcoord  = board.getCellOnBoard(row,col).getRow();
                int ycoord = board.getCellOnBoard(row,col).getColumn();
                int value = board.getCellOnBoard(row,col).getValue();
                fieldToCoordinates.put(grid[row][col], new Cell(value,xcoord,ycoord));
            }
        }

    }

    //REQUIRES: a valid field on board & 1<=num<=9
    //MODIFIES: This
    //EFFECTS: sets the number on board if possible
    public void putNumber(JTextField field, int num) {
        Cell pos = fieldToCoordinates.get(field);
        grid[pos.getRow()][pos.getColumn()].requestFocus();
        pos.setValue(num);
        if (playingBoard.setCellOnBoard(pos)) {
            setOnGrid(pos);
        } else {
            grid[pos.getRow()][pos.getColumn()].setText("");
        }

    }

    //EFFECTS: set the number on board (helper method for putNumber())
    private void setOnGrid(Cell pos) {
        if (pos.getValue() != 0) {
            grid[pos.getRow()][pos.getColumn()].setText(String.valueOf(pos.getValue()));
            grid[pos.getRow()][pos.getColumn()].setEditable(false);
            fieldToCoordinates.put(grid[pos.getRow()][pos.getColumn()], pos);
        }
    }

    //REQUIRES: output = "invalid input"
    //EFFECTS: prints an error to the user on console
    public void error(String output) {
        JOptionPane.showMessageDialog(this, output,"Invalid", JOptionPane.WARNING_MESSAGE);
    }

    //REQUIRES: a valid text field on board
    //MODIFIES: this
    //EFFECTS: clears cell/field
    public void setEmpty(JTextField field) {
        Cell pos = fieldToCoordinates.get(field);
        grid[pos.getRow()][pos.getColumn()].setText("");
    }

    //MODIFIES: this
    //EFFECTS: initializes all the buttons for the game
    private void initializeButtonsPanel() {
        exit.addActionListener(this);
        save.addActionListener(this);
        solve.addActionListener(this);
        buttonsPanel.add(save);
        buttonsPanel.add(solve);
        buttonsPanel.add(exit);
        SudokuBoard current = loadBoard();
        boolean flag = false;
        if (current != null) {
            for (int i = 0; i < BOARD_SIZE; i++) {
                for (int j = 0; j < BOARD_SIZE; j++) {
                    if (current.getCellOnBoard(i, j).getValue() == 0) {
                        flag = true;
                    }
                }
            }
        }
        if (flag) {
            continuePrev.addActionListener(this);
            buttonsPanel.add(continuePrev);
        }

    }

    //MODIFIES: this
    //EFFECTS: initializes the frame (helper method to Grid())
    private void initializeFrame() {
        initializeButtonsPanel();
        initializeGridFields();
        initializeFieldDimensions();
        setBoard(playingBoard);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000,1000);
        frame.setLayout(new FlowLayout());
        frame.add(gridPanel);
        frame.add(buttonsPanel);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }

    //EFFECTS: save the board on save button clicked
    private void onSaveClick() {
        saveBoard();
        JOptionPane.showMessageDialog(this,"Board has been saved!","Save",JOptionPane.INFORMATION_MESSAGE);

    }

    //MODIFIES: this
    //EFFECTS: retrieves the previous board to display
    private void onContinueClick() {
        playingBoard = loadBoard();
        solution = new BoardSolution(playingBoard);
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                setOnGrid(playingBoard.getCellOnBoard(i,j));
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: shows the solution of the board on solve button click
    private void onSolveClick() {
        if (solution != null) {
            if (solution.isSolvable()) {
                for (int i = 0; i < BOARD_SIZE; i++) {
                    for (int j = 0; j < BOARD_SIZE; j++) {
                        Cell pos = new Cell(0, i, j);
                        setOnGrid(solution.getCellInSolution(pos));
                        grid[i][j].setBackground(Color.GREEN);

                    }
                }
                playingBoard = solution.getSolution();
            }
        }
    }

    //EFFECTS: saves current board to Json file
    private void saveBoard() {
        try {
            jsonWriter.open();
            jsonWriter.write(playingBoard);
            jsonWriter.close();
            System.out.println("Saved " + playingBoard + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private SudokuBoard loadBoard() {
        try {
            SudokuBoard current = jsonReader.read();
            System.out.println("Loaded " + playingBoard + " from " + JSON_STORE);
            return current;
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
            return null;
        }
    }

    //EFFECTS: shows a msg to the user on the pop-up window
    private void onExit() {
        int answer = JOptionPane.showConfirmDialog(this,"Are you sure?","quit",JOptionPane.YES_NO_OPTION);

        if (answer == 0) {
            JOptionPane.showMessageDialog(null,"","See you!",JOptionPane.INFORMATION_MESSAGE,bye);
            LogPrinter lp = new ConsolePrinter();
            frame.setVisible(false);
            try {
                lp.printLog(EventLog.getInstance());
            } catch (LogException ex) {
                ex.printStackTrace();
            }
            System.exit(0);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == save) {
            onSaveClick();
        } else if (e.getSource() == solve) {
            onSolveClick();
        } else if (e.getSource() == continuePrev) {
            onContinueClick();
        } else if (e.getSource() == exit) {
            onExit();
        }
    }
}
