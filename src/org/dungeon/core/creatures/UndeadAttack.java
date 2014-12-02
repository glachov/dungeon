package org.dungeon.core.creatures;

import java.awt.Color;
import org.dungeon.core.game.Game;
import org.dungeon.core.items.Item;
import org.dungeon.io.IO;
import org.dungeon.utils.Constants;
import org.dungeon.utils.Utils;

public class UndeadAttack implements AttackAlgorithmStrategy {

	@Override
	public void attack(Creature attacker, Creature defender, String algorithmID) {
		// TODO Auto-generated method stub
		Item weapon = attacker.getWeapon();
        int hitDamage;
        // Check that there is a weapon and that it is not broken.
        if (weapon != null && !weapon.isBroken()) {
            if (weapon.rollForHit()) {
                hitDamage = weapon.getDamage() + attacker.getAttack();
                printInflictedDamage(attacker, hitDamage, defender, false);
                weapon.decrementIntegrityByHit();
                if (weapon.isBroken()) {
                    if (!weapon.isRepairable()) {
                        attacker.getInventory().removeItem(weapon);
                    }
                }
            } else {
                printMiss(attacker);
                return;
            }
        } else {
            // Hardcoded 15% miss chance.
            if (0.85 > Game.RANDOM.nextDouble()) {
                hitDamage = attacker.getAttack();
                printInflictedDamage(attacker, hitDamage, defender, false);
            } else {
                printMiss(attacker);
                return;
            }
        }
        defender.takeDamage(hitDamage);
	}

	/**
     * Prints a message about the inflicted damage based on the parameters.
     *
     * @param attacker    the Creature that performed the attack.
     * @param hitDamage   the damage inflicted by the attacker.
     * @param defender    the target of the attack.
     * @param criticalHit a boolean indicating if the attack was a critical hit or not.
     */
    private static void printInflictedDamage(Creature attacker, int hitDamage, Creature defender, boolean criticalHit) {
        StringBuilder builder = new StringBuilder();
        builder.append(attacker.getName());
        builder.append(" inflicted ");
        builder.append(hitDamage);
        builder.append(" damage points to ");
        builder.append(defender.getName());
        if (criticalHit) {
            builder.append(" with a critical hit");
        }
        builder.append(".");
        IO.writeString(builder.toString(), attacker.getId().equals(Constants.HERO_ID) ? Color.GREEN : Color.RED);
    }

    // Simple method that prints a miss message.
    private static void printMiss(Creature attacker) {
        IO.writeString(attacker.getName() + " missed.", Color.YELLOW);
    }
    
    private static void beastAttack(Creature attacker, Creature defender) {
        // 10% miss chance.
        if (Utils.roll(0.9)) {
            int hitDamage = attacker.getAttack();
            defender.takeDamage(hitDamage);
            printInflictedDamage(attacker, hitDamage, defender, false);
        } else {
            printMiss(attacker);
        }
    }
}
