package org.dungeon.core.creatures;

import java.awt.Color;

import org.dungeon.core.game.Game;
import org.dungeon.io.IO;

public class CritterAttack implements AttackAlgorithmStrategy{

	@Override
	public void attack(Creature attacker, Creature defender, String algorithmID) {
		// TODO Auto-generated method stub
		if (Game.RANDOM.nextBoolean()) {
            IO.writeString(attacker.getName() + " does nothing.", Color.YELLOW);
        } else {
            IO.writeString(attacker.getName() + " tries to run away.", Color.YELLOW);
        }
	}

}
