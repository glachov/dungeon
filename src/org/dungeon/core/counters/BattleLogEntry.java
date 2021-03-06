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
package org.dungeon.core.counters;

import org.dungeon.core.creatures.Creature;

import java.io.Serializable;

class BattleLogEntry implements Serializable {

    private static final long serialVersionUID = 1L;

    final String defenderID;
    final String attackerWeapon;
    final String defenderType;
    final boolean attackerWon;

    public BattleLogEntry(Creature attacker, Creature defender, boolean attackerWon) {
        this.defenderID = defender.getId();
        if (attacker.hasWeapon()) {
            this.attackerWeapon = attacker.getWeapon().getId();
        } else {
            // If the creature was not equipping a weapon, consider an empty string as the weaponID.
            this.attackerWeapon = "";
        }
        this.defenderType = defender.getType();
        this.attackerWon = attackerWon;
    }
}
