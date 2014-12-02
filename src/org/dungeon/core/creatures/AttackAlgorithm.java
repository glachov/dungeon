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
package org.dungeon.core.creatures;

import org.dungeon.core.game.Game;
import org.dungeon.core.items.Item;
import org.dungeon.io.IO;
import org.dungeon.utils.Constants;
import org.dungeon.utils.Utils;

import java.awt.*;

/**
 * AttackAlgorithm class that defines all the attack algorithms. Specific attack algorithms are used by using invoking
 * the AttackAlgorithm.attack() method with the right parameters.
 * <p/>
 * Created by Bernardo Sulzbach on 29/09/14.
 */
class AttackAlgorithm {

    public static void attack(Creature attacker, Creature defender, String algorithmID) {
        if (algorithmID.equals("BAT")) {
        	BatAttack b = new BatAttack();
        	b.attack(attacker, defender, null);
        } else if (algorithmID.equals("BEAST")) {
        	BeastAttack be = new BeastAttack();
        	be.attack(attacker, defender, null);
        } else if (algorithmID.equals("CRITTER")) {
        	CritterAttack c = new CritterAttack();
        	c.attack(attacker, null, null);
        } else if (algorithmID.equals("UNDEAD")) {
        } else if (algorithmID.equals("HERO")) {
        	HeroAttack h = new HeroAttack();
        	h.attack(attacker, defender, null);
        } else {
            throw new IllegalArgumentException("algorithmID does not match any implemented algorithm.");
        }
    }



}
