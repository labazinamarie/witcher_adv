package cz.vse.adv_witcher_labm02.main.game;

import cz.vse.adv_witcher_labm02.main.start.Subject;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The {@code Location} class represents a game location containing NPCs,
 * items, tracks, riddles, and creatures. It manages connections between
 * locations and player interactions with the environment.
 *
 * @author Mariia Labazina
 * @version ZS-2024, 2025-01-12
 */
public class Location {
    private final String name;
    private String description;
    private final List<NPC> characters;
    private final Map<Location, Boolean> exits; // Boolean indicates if the path to the location is locked
    private final Map<String, Item> items;
    private final Map<String, String> tracks;
    private Riddle riddle;
    private final boolean isDark;
    private final List<Creature> creatures;

    /**
     * Constructs a location with the given name, description, and darkness setting.
     *
     * @param name        the name of the location
     * @param description the description of the location
     * @param isDark      whether the location is dark and requires light
     */
    public Location(String name, String description, boolean isDark) {
        this.name = name;
        this.description = description;
        this.isDark = isDark;
        this.exits = new HashMap<>();
        this.items = new HashMap<>();
        this.characters = new ArrayList<>();
        this.creatures = new ArrayList<>();
        this.tracks = new HashMap<>();
        this.riddle = null;
    }

    public boolean requiresLight() {
        return isDark;
    }

    public boolean isTooDark(Inventory inventory) {
        return isDark && inventory.getItems().values().stream().noneMatch(Item::isLit);
    }

    public void setRiddle(Riddle riddle) {
        this.riddle = riddle;
    }

    public Riddle getRiddle() {
        return riddle;
    }

    public boolean requiresRiddle() {
        return riddle != null;
    }

    public boolean solveRiddle(String answer) {
        return riddle != null && riddle.checkAnswer(answer);
    }

    public boolean hasCreature() {
        return !creatures.isEmpty();
    }

    public Creature getCreatureByName(String creatureName) {
        return creatures.stream()
                .filter(creature -> creature.getName().equalsIgnoreCase(creatureName))
                .findFirst()
                .orElse(null);
    }

    public void addCreature(Creature creature) {
        creatures.add(creature);
    }

    public void addTrack(String clue, String destination) {
        tracks.put(clue, destination);
    }

    public Map<String, String> getTracks() {
        return tracks;
    }

    public boolean hasTracks() {
        return !tracks.isEmpty();
    }

    public String revealTrack() {
        if (tracks.isEmpty()) return "You sense nothing unusual.";
        List<String> clues = new ArrayList<>(tracks.keySet());
        String clue = clues.get(new Random().nextInt(clues.size()));
        return "Using your Witcher senses, you find: " + clue + " → " + tracks.get(clue);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void addCharacter(NPC character) {
        if (!characters.contains(character)) {
            characters.add(character);
        }
    }

    public List<NPC> getCharacters() {
        return characters;
    }

    public boolean hasCharacters() {
        return !characters.isEmpty();
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetailedDescription() {
        String detailedDescription = "You're in Location: " + name + "\n";
        detailedDescription += description + "\n";
        detailedDescription = detailedDescription + "\n\nExits: ";
        boolean isFirst = true;
        for (Map.Entry<Location, Boolean> entry : exits.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(Comparator.comparing(Location::getName)))
                .collect(Collectors.toList())) {
            if (!isFirst) {
                detailedDescription = detailedDescription + " | ";
            }
            detailedDescription = detailedDescription + entry.getKey().getName();
            if (entry.getValue()) {
                detailedDescription = detailedDescription + " (locked)";
            }
            isFirst = false;
        }

        detailedDescription = detailedDescription + "\nItems: ";
        if (items.isEmpty()) {
            detailedDescription = detailedDescription + " None";
        } else {
            isFirst = true;
            for (Item item : items.values()) {
                if (!isFirst) {
                    detailedDescription = detailedDescription + " | ";
                }
                detailedDescription = detailedDescription + item.getName();
                isFirst = false;
            }
        }

        detailedDescription = detailedDescription + "\nCharacters: ";
        if (!characters.isEmpty()) {
            isFirst = true;
            for (NPC character : characters) {
                if (!isFirst) {
                    detailedDescription = detailedDescription + " | ";
                }
                detailedDescription = detailedDescription + character.getName();
                isFirst = false;
            }
        } else {
            detailedDescription = detailedDescription + " None";
        }

        detailedDescription = detailedDescription + "\nCreatures: ";
        if (!creatures.isEmpty()) {
            isFirst = true;
            for (Creature creature : creatures) {
                if (!isFirst) {
                    detailedDescription= detailedDescription + " | ";
                }
                detailedDescription = detailedDescription + creature.getName();
                isFirst = false;
            }
        } else {
            detailedDescription = detailedDescription + " None";
        }

        if (!tracks.isEmpty()) {
            detailedDescription = detailedDescription + "\nTracks: There are signs of movement.";
        }

        if (riddle != null) {
            detailedDescription = detailedDescription + "\nA mysterious force blocks your path... A riddle must be solved.";
        }

        if (isDark) {
            detailedDescription = detailedDescription + "\nIt is very dark here. You will need a light source.";
        }

        return detailedDescription;
    }


    public void addExit(Location exit, boolean locked) {
        exits.put(exit, locked);
    }

    public boolean hasExit(String locationName) {
        return exits.keySet().stream()
                .anyMatch(location -> location.getName().equalsIgnoreCase(locationName));
    }
    public boolean isExitLocked(String locationName) {
        Location exit = returnExit(locationName);
        return exit != null && exits.get(exit);
    }
    public boolean unlockExit(String locationName) {
        Location exit = returnExit(locationName);
        if (exit != null && exits.get(exit)) {
            exits.put(exit, false);
            return true;
        }
        return false;
    }

    public Location returnExit(String locationName) {
        return exits.keySet().stream()
                .filter(location -> location.getName().equalsIgnoreCase(locationName))
                .findFirst()
                .orElse(null);
    }

    public Collection<Location> getExits() {
        return Collections.unmodifiableCollection(exits.keySet());
    }


    public void addItem(Item item) {
        items.put(item.getName().toLowerCase(), item);
    }

    public boolean hasItem(String name) {
        return items.containsKey(name.toLowerCase());
    }

    public Item returnItem(String name) {
        return items.get(name.toLowerCase());
    }

    public Item removeItem(String name) {
        return items.remove(name.toLowerCase());
    }

    public Collection<Item> getItems() {
        return Collections.unmodifiableCollection(items.values());
    }
}
