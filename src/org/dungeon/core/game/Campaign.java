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

import org.dungeon.core.achievement.Achievement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.dungeon.core.counter.CreatureCounter;
import org.dungeon.utils.Constants;
import org.dungeon.utils.Utils;

public class Campaign implements Serializable {

    private final List<Achievement> campaignAchievements;
    private final CreatureCounter campaignBattleCounter;
    private final World campaignWorld;
    private final Hero campaignHero;
    private final Point heroPoint;

    private boolean saved;

    private int unlockedAchievementsCounter;

    public Campaign() {
        campaignBattleCounter = new CreatureCounter();

        campaignAchievements = createDemoAchievements();

        campaignHero = new Hero("Seth");
        heroPoint = new Point(0, 0);
        campaignHero.setWeapon(new Weapon("Stick", 6, 20));

        campaignWorld = createDemoWorld();
    }

    private List<Achievement> createDemoAchievements() {
        List<Achievement> achievements = new ArrayList<>();

        CreatureCounter suicideSolutionRequirements = new CreatureCounter();

        // Suicide Solution requires one battle against the Hero himself.
        suicideSolutionRequirements.incrementCreatureCount(CreatureID.HERO);

        achievements.add(new Achievement("Suicide Solution", "Attempt to kill yourself.", suicideSolutionRequirements));

        return achievements;
    }

    private World createDemoWorld() {
        World world = new World();
        Point startingPoint = new Point(0, 0);
        world.addLocation(new Location("Clearing"), startingPoint);
        // The hero
        world.addCreature(campaignHero, startingPoint);
        campaignHero.setLocation(world.getLocation(startingPoint));
        // Beasts
        world.addCreature(new Creature(CreatureID.BAT, 1), startingPoint);
        world.addCreature(new Creature(CreatureID.BEAR, 1), startingPoint);
        world.addCreature(new Creature(CreatureID.RABBIT, 1), startingPoint);
        world.addCreature(new Creature(CreatureID.RAT, 1), startingPoint);
        world.addCreature(new Creature(CreatureID.SPIDER, 1), startingPoint);
        world.addCreature(new Creature(CreatureID.WOLF, 1), startingPoint);
        world.addCreature(new Creature(CreatureID.ZOMBIE, 1), startingPoint);
        // Items
        Weapon longSword = new Weapon("Longsword", 18, 15);
        longSword.setDestructible(true);
        world.addItem(longSword, startingPoint);

        Point rightPoint = new Point(1, 0);
        world.addLocation(new Location("Road to The Fort"), rightPoint);
        // Beasts
        world.addCreature(new Creature(CreatureID.RAT, 1), rightPoint);
        // Items
        world.addItem(new Weapon("Mace", 20, 10), rightPoint);

        return world;
    }

    public CreatureCounter getBattleCounter() {
        return campaignBattleCounter;
    }

    public World getWorld() {
        return campaignWorld;
    }

    public Hero getHero() {
        return campaignHero;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    private int getUnlockedAchievementsCounter() {
        return unlockedAchievementsCounter;
    }

    private void incrementUnlockedAchievementsCounter() {
        this.unlockedAchievementsCounter++;
    }

    /**
     * Prints all unlocked achievements.
     */
    public void printUnlockedAchievements() {
        StringBuilder builder = new StringBuilder();
        builder.append("Progress: ").append(getUnlockedAchievementsCounter()).append('/');
        builder.append(campaignAchievements.size());
        for (Achievement a : campaignAchievements) {
            if (a.isUnlocked()) {
                builder.append("\n").append(a.toOneLineString());
            }
        }
        IO.writeString(builder.toString());
    }

    /**
     * Refreshes the campaign. Should be called after the player plays a turn.
     */
    public void refresh() {
        refreshAchievements();
    }

    private void refreshAchievements() {
        for (Achievement a : campaignAchievements) {
            if (a.update(campaignBattleCounter)) {
                incrementUnlockedAchievementsCounter();
            }
        }
    }

    public void parseHeroWalk(String[] inputWords) {
        if (inputWords.length == 1) {
            Direction walkDirection = Utils.selectFromList(Arrays.asList(Direction.values()));
            if (walkDirection != null) {
                heroWalk(walkDirection);
            }
            return;
        } else {
            String secondWord = inputWords[1];
            for (Direction dir : Direction.values()) {
                if (dir.toString().equalsIgnoreCase(secondWord)) {
                    heroWalk(dir);
                    return;
                }
            }
        }
        IO.writeString(Constants.INVALID_INPUT);
    }

    public void heroWalk(Direction dir) {
        Point destination = new Point(heroPoint, dir);
        if (getWorld().hasLocation(destination)) {
            getWorld().moveCreature(campaignHero, heroPoint, destination);
            campaignHero.setLocation(getWorld().getLocation(destination));
        } else {
            IO.writeString(Constants.WALK_BLOCKED);
        }
    }

}