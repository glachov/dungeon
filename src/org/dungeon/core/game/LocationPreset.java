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
package org.dungeon.core.game;


import java.io.Serializable;

class LocationPreset implements Serializable {

    private final String name;
    private final String[] creatures;
    private final String[] items;
    private final double lightPermittivity;

    public LocationPreset(String name, double lightPermittivity, String[] creatures, String[] items) {
        this.name = name;
        this.lightPermittivity = lightPermittivity;
        this.creatures = creatures;
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public double getLightPermittivity() {
        return lightPermittivity;
    }

    public String[] getCreatures() {
        return creatures;
    }

    public String[] getItems() {
        return items;
    }
}
