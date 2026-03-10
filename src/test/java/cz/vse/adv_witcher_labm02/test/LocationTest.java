package cz.vse.adv_witcher_labm02.test;

import cz.vse.adv_witcher_labm02.main.game.Creature;
import cz.vse.adv_witcher_labm02.main.game.Item;
import cz.vse.adv_witcher_labm02.main.game.Location;
import cz.vse.adv_witcher_labm02.main.game.Riddle;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

/**
 * Testovací třída pro komplexní otestování třídy {@link Location}.
 *
 * @author Mariia Labazina
 * @version ZS-2024, 2025-01-15
 */
public class LocationTest {

    @Test
    public void testExits() {
        Location location1 = new Location("Hall", "This is the main hall.", false);
        Location location2 = new Location("Buffet", "This is the buffet area.", false);

        assertFalse(location1.hasExit(location2.getName()));
        assertNull(location1.returnExit(location2.getName()));

        location1.addExit(location2, false);

        assertTrue(location1.hasExit(location2.getName()));
        assertEquals(location2, location1.returnExit(location2.getName()));
    }

    @Test
    public void testItems() {
        Location location = new Location("Hall", "This is the main hall.", false);

        Item item1 = new Item("Key", "A small rusty key.", true, "Unlocks doors", 1, 1);
        Item item2 = new Item("Lamp", "An old oil lamp.", true, "Provides light", 1, 1);

        assertFalse(location.hasItem(item1.getName()));
        assertNull(location.returnItem(item1.getName()));

        location.addItem(item1);
        location.addItem(item2);

        assertTrue(location.hasItem(item1.getName()));
        assertEquals(item1, location.returnItem(item1.getName()));

        assertEquals(item1, location.removeItem(item1.getName()));
        assertFalse(location.hasItem(item1.getName()));
        assertNull(location.returnItem(item1.getName()));
    }

    @Test
    public void testRiddles() {
        Location location = new Location("Cave", "A dark and mysterious cave.", true);
        Riddle riddle = new Riddle("What has to be broken before you can use it?", "egg");

        assertFalse(location.requiresRiddle());
        location.setRiddle(riddle);

        assertTrue(location.requiresRiddle());
        assertEquals(riddle, location.getRiddle());
        assertTrue(location.solveRiddle("egg"));
        assertFalse(location.solveRiddle("stone"));
    }

    @Test
    public void testTracks() {
        Location location = new Location("Woods", "Dense forest with rustling leaves.", false);

        assertFalse(location.hasTracks());

        location.addTrack("Footprints lead north", "Cave");
        location.addTrack("Broken branches to the east", "Clearing");

        assertTrue(location.hasTracks());
        Map<String, String> tracks = location.getTracks();

        assertEquals(2, tracks.size());
        assertTrue(tracks.containsKey("Footprints lead north"));
        assertTrue(tracks.containsKey("Broken branches to the east"));

        String trackMessage = location.revealTrack();
        assertNotNull(trackMessage);
    }

    @Test
    public void testCreatures() {
        Location location = new Location("Den", "A smelly monster den.", true);
        Creature creature = new Creature("Wolf", "A ferocious wolf.", location, 50, 100);

        assertFalse(location.hasCreature());
        assertNull(location.getCreatureByName("Wolf"));

        location.addCreature(creature);

        assertTrue(location.hasCreature());
        assertEquals(creature, location.getCreatureByName("Wolf"));
    }

    @Test
    public void testDetailedDescription() {
        Location location = new Location("Cave", "A dark cave with echoes.", true);
    
        location.addTrack("Footprints", "Leading towards the forest");
    
        String description = location.getDetailedDescription();
    
        System.out.println(description);

        assertTrue(description.contains("Tracks"), "Should contain tracks.");
    }
}
