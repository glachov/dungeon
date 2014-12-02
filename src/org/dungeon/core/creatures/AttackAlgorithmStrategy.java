package org.dungeon.core.creatures;

public interface AttackAlgorithmStrategy {

	public void attack(Creature attacker, Creature defender, String algorithmID);
}
