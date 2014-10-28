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
package org.dungeon.io;

import org.dungeon.core.game.Game;
import org.dungeon.utils.Constants;
import org.dungeon.utils.Poem;
import org.dungeon.utils.StringUtils;

import java.awt.*;

/**
 * IO class that encapsulates all Input/Output operations.
 * This is the only class that should call the writing methods of the game window.
 *
 * @author Bernardo Sulzbach - 13/09/2014
 */
public class IO {

    private static final Color DEFAULT_COLOR_NORMAL = Color.LIGHT_GRAY;
    private static final Color DEFAULT_COLOR_DARKER = Color.DARK_GRAY;


    /**
     * Writes a string of text using the default output color.
     *
     * @param string the string of text to be written.
     */
    public static void writeString(String string) {
        writeString(string, DEFAULT_COLOR_NORMAL);
    }

    /**
     * Writes a string of text using a specific color.
     *
     * @param string the string of text to be written.
     * @param color  the color of the text.
     */
    public static void writeString(String string, Color color) {
        writeString(string, color, true);
    }

    /**
     * Writes a string of text using a specific color.
     *
     * @param string the string of text to be written.
     * @param color  the color of the text.
     */
    public static void writeString(String string, Color color, boolean newLine) {
        if (newLine) {
            Game.gameWindow.writeToTextPane(StringUtils.clearEnd(string) + '\n', color);
        } else {
            Game.gameWindow.writeToTextPane(StringUtils.clearEnd(string), color);
        }
    }

    /**
     * Writes a key, value pair separated with enough dots to fill a line.
     * The key and value are written using the default color and the filling dots are written using a darker color.
     *
     * @param key   the key string.
     * @param value the value string.
     */
    public static void writeKeyValueString(String key, String value) {
        writeKeyValueString(key, value, DEFAULT_COLOR_NORMAL, DEFAULT_COLOR_DARKER);
    }

    /**
     * Writes a key, value pair separated with enough dots to fill a line using the specified colors.
     *
     * @param key       the key string.
     * @param value     the value string.
     * @param textColor the color used to write the key and the value.
     * @param fillColor the color used to write the dots.
     */
    public static void writeKeyValueString(String key, String value, Color textColor, Color fillColor) {
        int dots = Constants.WIDTH - key.length() - value.length();  // The amount of dots necessary.
        if (dots < 0) {
            throw new IllegalArgumentException("strings are too large.");
        }
        writeString(key, textColor, false);
        StringBuilder stringBuilder = new StringBuilder();
        for (; dots > 0; dots--) {
            stringBuilder.append('.');
        }
        writeString(stringBuilder.toString(), fillColor, false);
        writeString(value, textColor, true);
    }

    public static void writePoem(Poem poem) {
        writeString(poem.getTitle() + "\n\n" + poem.getContent() + "\n\n" + poem.getAuthor());
    }
}
