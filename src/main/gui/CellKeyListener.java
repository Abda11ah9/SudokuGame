package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class CellKeyListener implements KeyListener {
    private Grid grid;

    //REQUIRES: a valid Grid
    //MODIFIES: this
    //EFFECTS: sets the sudoku grid
    public CellKeyListener(Grid g) {
        this.grid = g;
    }


    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        switch (c) {
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                e.consume();
                grid.putNumber((JTextField) e.getSource(), Character.getNumericValue(c));

        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

        int keycode = e.getKeyCode();
        if (keycode != KeyEvent.VK_1 && keycode != KeyEvent.VK_2 && keycode != KeyEvent.VK_3) {
            if (keycode != KeyEvent.VK_4 && keycode != KeyEvent.VK_5 && keycode != KeyEvent.VK_6) {
                if (keycode != KeyEvent.VK_7 && keycode != KeyEvent.VK_8 && keycode != KeyEvent.VK_9) {
                    grid.setEmpty((JTextField) e.getSource());
                    grid.error("Invalid Input");
                }
            }

        }
    }
}
