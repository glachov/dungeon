/* 
 * Copyright (C) 2014 Bernardo Sulzbach
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.dungeon.gui;

import org.dungeon.core.game.Game;
import org.dungeon.utils.CommandHistory;
import org.dungeon.utils.Constants;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameWindow extends JFrame {

    // The window configuration is fixed so that the text formatting makes sense.
    private static final int WIDTH = 830;
    private static final int HEIGHT = 630;
    private static Font textFont;
    private final SimpleAttributeSet attributeSet = new SimpleAttributeSet();
    private CommandHistory commandHistory;
    private int commandIndex;
    private StyledDocument document;

    private JTextField textField;
    private JTextPane textPane;

    public GameWindow() {
        initTextFont();
        initComponents();
        document = textPane.getStyledDocument();
        setVisible(true);
    }

    /**
     * Initializes the common Font object used to format all the text in the window.
     * If Consolas is not found in the available fonts, Monospaced is used.
     */
    private void initTextFont() {
        String[] availableFonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        for (String font : availableFonts) {
            if (font.equals("Consolas")) {
                textFont = new Font(font, Font.PLAIN, 14);
                return;
            }
        }
        textFont = new Font("Monospaced", Font.PLAIN, 14);
    }

    private void initComponents() {
        textPane = new javax.swing.JTextPane();
        textField = new javax.swing.JTextField();

        JScrollPane scrollPane = new JScrollPane();

        textPane.setEditable(false);
        textPane.setBackground(Color.BLACK);
        textPane.setForeground(Color.LIGHT_GRAY);
        textPane.setFont(textFont);

        scrollPane.setViewportView(textPane);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        textField.setBackground(Color.BLACK);
        textField.setForeground(Color.LIGHT_GRAY);
        textField.setCaretColor(Color.WHITE);
        textField.setFont(textFont);

        textField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent actionEvent) {
                textFieldActionPerformed();
            }
        });

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                textFieldKeyPressed(e);
            }
        });

        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(textField, BorderLayout.SOUTH);

        setTitle(Constants.TITLE);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        setSize(new java.awt.Dimension(WIDTH, HEIGHT));
        setResizable(false);
        setLocationRelativeTo(null);
    }

    // The method that gets called when the player presses ENTER.
    private void textFieldActionPerformed() {
        String text = textField.getText().trim();
        if (!text.isEmpty()) {
            if (!Game.renderTurn(text)) {
                System.exit(0);
            }
            clearTextField();
            resetCommandIndex();
        }
    }

    private void textFieldKeyPressed(KeyEvent e) {
        if (commandHistory == null) {
            commandHistory = Game.getGameState().getCommandHistory();
            resetCommandIndex();
        }
        boolean validKeyPress = false;
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            if (commandIndex > 0) {
                validKeyPress = true;
                commandIndex--;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            if (commandIndex < commandHistory.getCommandCount()) {
                commandIndex++;
                // When the index is set to one past the last saved command, just clear the text field.
                if (commandIndex == commandHistory.getCommandCount()) {
                    clearTextField();
                    return;
                }
                validKeyPress = true;
            }
        }
        if (validKeyPress) {
            textField.setText(commandHistory.getCommandAt(commandIndex));
        }
    }

    private void resetCommandIndex() {
        // This is correct.
        // The index should point to one after the last command, so the up key retrieves the last command.
        commandIndex = commandHistory.getCommandCount();
    }

    /**
     * Adds a string with a specific foreground color to the text pane.
     *
     * @param string the string to be written.
     * @param color  the color of the text.
     */
    public void writeToTextPane(String string, Color color) {
        StyleConstants.setForeground(attributeSet, color);
        try {
            document.insertString(document.getLength(), string, attributeSet);
        } catch (BadLocationException ignored) {
        }
    }

    public void clearTextPane() {
        try {
            document.remove(0, document.getLength());
        } catch (BadLocationException ignored) {
        }
    }

    public void requestFocusOnTextField() {
        textField.requestFocusInWindow();
    }

    private void clearTextField() {
        textField.setText(null);
    }

}
