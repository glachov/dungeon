package org.dungeon.core.game;

import org.dungeon.core.creatures.CreatureBlueprint;
import org.dungeon.core.items.ItemBlueprint;
import org.dungeon.utils.Poem;
import org.dungeon.utils.PoemBuilder;
import org.dungeon.utils.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The class that stores all the game data that is loaded and not serialized.
 * <p/>
 * Created by Bernardo on 22/10/2014.
 */
public final class GameData {

    public static final HashMap<String, CreatureBlueprint> CREATURE_BLUEPRINTS = new HashMap<String, CreatureBlueprint>();
    public static final HashMap<String, ItemBlueprint> ITEM_BLUEPRINTS = new HashMap<String, ItemBlueprint>();
    public static final List<Poem> POEMS = new ArrayList<Poem>();
    static LocationPreset[] LOCATION_PRESETS;
    private static ClassLoader loader;

    static void loadGameData() {
        loader = Thread.currentThread().getContextClassLoader();

        loadItemBlueprints();
        loadCreatureBlueprints();

        loadPoems();
        LOCATION_PRESETS = loadLocationPresets();

    }

    // TODO: write more uniform loaders. Benchmark to test what are the most efficient methods for every task.
    private static void loadItemBlueprints() {
        BufferedReader br = new BufferedReader(new InputStreamReader(loader.getResourceAsStream("res/items.txt")));
        String line;
        ItemBlueprint blueprint = new ItemBlueprint();
        try {
            while ((line = br.readLine()) != null) {
                if (StringUtils.isNotBlankString(line)) {
                    if (line.startsWith("ID:")) {
                        if (blueprint.getId() != null) {
                            ITEM_BLUEPRINTS.put(blueprint.getId(), blueprint);
                            blueprint = new ItemBlueprint();
                        }
                        blueprint.setId(line.split(":")[1].trim());
                    } else if (line.startsWith("TYPE:")) {
                        blueprint.setType(line.split(":")[1].trim());
                    } else if (line.startsWith("NAME:")) {
                        blueprint.setName(line.split(":")[1].trim());
                    } else if (line.startsWith("CUR_INTEGRITY:")) {
                        blueprint.setCurIntegrity(Integer.parseInt(line.split(":")[1].trim()));
                    } else if (line.startsWith("MAX_INTEGRITY:")) {
                        blueprint.setMaxIntegrity(Integer.parseInt(line.split(":")[1].trim()));
                    } else if (line.startsWith("REPAIRABLE:")) {
                        blueprint.setRepairable(Integer.parseInt(line.split(":")[1].trim()) == 1);
                    } else if (line.startsWith("WEAPON:")) {
                        blueprint.setWeapon(Integer.parseInt(line.split(":")[1].trim()) == 1);
                    } else if (line.startsWith("DAMAGE:")) {
                        blueprint.setDamage(Integer.parseInt(line.split(":")[1].trim()));
                    } else if (line.startsWith("HIT_RATE:")) {
                        blueprint.setHitRate(Double.parseDouble(line.split(":")[1].trim()));
                    } else if (line.startsWith("INTEGRITY_DECREMENT_ON_HIT:")) {
                        blueprint.setIntegrityDecrementOnHit(Integer.parseInt(line.split(":")[1].trim()));
                    } else if (line.startsWith("FOOD:")) {
                        blueprint.setFood(Integer.parseInt(line.split(":")[1].trim()) == 1);
                    } else if (line.startsWith("NUTRITION:")) {
                        blueprint.setNutrition(Integer.parseInt(line.split(":")[1].trim()));
                    } else if (line.startsWith("EXPERIENCE_ON_EAT:")) {
                        blueprint.setExperienceOnEat(Integer.parseInt(line.split(":")[1].trim()));
                    } else if (line.startsWith("INTEGRITY_DECREMENT_ON_EAT:")) {
                        blueprint.setIntegrityDecrementOnEat(Integer.parseInt(line.split(":")[1].trim()));
                    } else if (line.startsWith("CLOCK:")) {
                        blueprint.setClock(Integer.parseInt(line.split(":")[1].trim()) == 1);
                    }
                }
            }
        } catch (IOException ignored) {
        }
        ITEM_BLUEPRINTS.put(blueprint.getId(), blueprint);
    }

    private static void loadCreatureBlueprints() {
        BufferedReader br = new BufferedReader(new InputStreamReader(loader.getResourceAsStream("res/creatures.txt")));
        String line;
        CreatureBlueprint blueprint = new CreatureBlueprint();
        try {
            while ((line = br.readLine()) != null) {
                if (StringUtils.isNotBlankString(line)) {
                    if (line.startsWith("ID:")) {
                        if (blueprint.getId() != null) {
                            CREATURE_BLUEPRINTS.put(blueprint.getId(), blueprint);
                            blueprint = new CreatureBlueprint();
                        }
                        blueprint.setId(line.split(":")[1].trim());
                    } else if (line.startsWith("TYPE:")) {
                        blueprint.setType(line.split(":")[1].trim());
                    } else if (line.startsWith("NAME:")) {
                        blueprint.setName(line.split(":")[1].trim());
                    } else if (line.startsWith("CUR_HEALTH:")) {
                        blueprint.setCurHealth(Integer.parseInt(line.split(":")[1].trim()));
                    } else if (line.startsWith("MAX_HEALTH:")) {
                        blueprint.setMaxHealth(Integer.parseInt(line.split(":")[1].trim()));
                    } else if (line.startsWith("MAX_HEALTH_INCREMENT:")) {
                        blueprint.setMaxHealthIncrement(Integer.parseInt(line.split(":")[1].trim()));
                    } else if (line.startsWith("ATTACK:")) {
                        blueprint.setAttack(Integer.parseInt(line.split(":")[1].trim()));
                    } else if (line.startsWith("ATTACK_INCREMENT:")) {
                        blueprint.setAttackIncrement(Integer.parseInt(line.split(":")[1].trim()));
                    } else if (line.startsWith("ATTACK_ALGORITHM_ID:")) {
                        blueprint.setAttackAlgorithmID(line.split(":")[1].trim());
                    } else if (line.startsWith("EXPERIENCE_DROP_FACTOR:")) {
                        blueprint.setExperienceDropFactor(Integer.parseInt(line.split(":")[1].trim()));
                    }
                }
            }
        } catch (IOException ignored) {
        }
        CREATURE_BLUEPRINTS.put(blueprint.getId(), blueprint);
    }


    private static LocationPreset[] loadLocationPresets() {
        ArrayList<LocationPreset> locationPresets = new ArrayList<LocationPreset>();

        String[] clearingCreatures = {"BAT", "RAT", "BEAR"};
        String[] clearingItems = {"STONE"};
        locationPresets.add(new LocationPreset("Clearing", 0.9, clearingCreatures, clearingItems));

        String[] desertCreatures = {"RABBIT", "ZOMBIE", "SPIDER"};
        String[] desertItems = {"AXE"};
        locationPresets.add(new LocationPreset("Desert", 1.0, desertCreatures, desertItems));

        String[] forestCreatures = {"BAT"};
        String[] forestItems = {"CLOCK"};
        locationPresets.add(new LocationPreset("Forest", 0.7, forestCreatures, forestItems));

        String[] graveyardCreatures = {"BAT"};
        String[] graveyardItems = {"LONGSWORD", "APPLE"};
        locationPresets.add(new LocationPreset("Graveyard", 0.9, graveyardCreatures, graveyardItems));

        String[] meadowCreatures = {"BAT"};
        String[] meadowItems = {"STONE"};
        locationPresets.add(new LocationPreset("Meadow", 1.0, meadowCreatures, meadowItems));

        String[] pondCreatures = {"BAT"};
        String[] pondItems = {"WATERMELON"};
        locationPresets.add(new LocationPreset("Pond", 1.0, pondCreatures, pondItems));

        LocationPreset[] locationPresetsArray = new LocationPreset[locationPresets.size()];
        locationPresets.toArray(locationPresetsArray);
        return locationPresetsArray;
    }

    private static void loadPoems() {
        String IDENTIFIER_TITLE = "TITLE:";
        String IDENTIFIER_AUTHOR = "AUTHOR:";
        String IDENTIFIER_CONTENT = "CONTENT:";

        BufferedReader br = new BufferedReader(new InputStreamReader(loader.getResourceAsStream("res/poems.txt")));

        String line;

        StringBuilder contentBuilder = new StringBuilder();
        PoemBuilder pb = new PoemBuilder();

        try {
            while ((line = br.readLine()) != null) {
                if (line.startsWith(IDENTIFIER_TITLE)) {
                    if (pb.isComplete()) {
                        POEMS.add(pb.createPoem());
                        pb = new PoemBuilder();
                        contentBuilder.setLength(0);
                    }
                    pb.setTitle(line.substring(IDENTIFIER_TITLE.length()).trim());
                } else if (line.startsWith(IDENTIFIER_AUTHOR)) {
                    pb.setAuthor(line.substring(IDENTIFIER_AUTHOR.length()).trim());
                } else if (line.startsWith(IDENTIFIER_CONTENT)) {
                    contentBuilder.append(line.substring(IDENTIFIER_CONTENT.length()).trim());
                    while ((line = br.readLine()) != null && !line.isEmpty() && !line.startsWith(IDENTIFIER_TITLE)) {
                        contentBuilder.append('\n').append(line.trim());
                    }
                    pb.setContent(contentBuilder.toString());
                }
            }
        } catch (IOException ignored) {
        }
        if (pb.isComplete()) {
            POEMS.add(pb.createPoem());
        }
    }

}
