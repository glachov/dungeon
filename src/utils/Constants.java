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
package utils;

/**
 * Constants class is a general-purpose constant storing class.
 *
 * @author Bernardo Sulzbach
 */
public class Constants {

    public static final String TITLE = "Dungeon";

    public static final int WIDTH = 79;

    /**
     * String used to improve output readability.
     */
    public static final String MARGIN = "  ";

    public static final String CAMPAIGN_PATH = "campaign.dungeon";
    public static final String SAVE_FOUND = "A saved campaign was found.";
    public static final String SAVE_ERROR = "Could not save the game.";
    public static final String SAVE_SUCCESS = "Successfully saved the game.";
    public static final String LOAD_ERROR = "Could not load the saved campaign.";
    public static final String LOAD_SUCCESS = "Successfully loaded the saved campaign.";

    // Two 79-character long strings used to improve readability.
    public static final String LINE_1 = StringUtils.makeRepeatedCharacterString(WIDTH, '-');
    public static final String LINE_2 = StringUtils.makeRepeatedCharacterString(WIDTH, '=');

    public static final String HEADING = LINE_2 + '\n' + StringUtils.centerString(TITLE, WIDTH, '-') + '\n' + LINE_2;
}
