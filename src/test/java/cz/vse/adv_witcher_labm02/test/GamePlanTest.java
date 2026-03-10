package cz.vse.adv_witcher_labm02.test;

import cz.vse.adv_witcher_labm02.main.game.Game;
import cz.vse.adv_witcher_labm02.main.game.Item;
import cz.vse.adv_witcher_labm02.main.game.commands.CommandGo;
import cz.vse.adv_witcher_labm02.main.game.commands.CommandLook;
import cz.vse.adv_witcher_labm02.main.game.commands.CommandTake;
import cz.vse.adv_witcher_labm02.main.game.commands.CommandSolveRiddle;
import cz.vse.adv_witcher_labm02.main.game.commands.CommandUnlock;
import cz.vse.adv_witcher_labm02.main.game.commands.CommandTrack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testovací třída pro plán hry, která kombinuje funkčnost příkazů pro <b>přesun</b>: {@link CommandGo},
 * {@link CommandTrack} and {@link CommandLook}, {@link CommandUnlock}, {@link CommandSolveRiddle}
 *
 * @author Mariia Labazina
 * @version ZS-2024, 2025-01-12
 */
public class GamePlanTest {

    private Game game;
    private CommandGo commandGo;
    private CommandLook commandLook;
    private CommandTake commandTake;

    @BeforeEach
    public void setUp() {
        game = new Game();
        commandGo = new CommandGo(game);
        commandLook = new CommandLook(game);
        commandTake = new CommandTake(game);
    }

    @Test
    public void goWithoutParameters() {
        String goResult = commandGo.run(new String[]{});

        assertEquals("You need to specify a location.", goResult, "Should indicate the player needs to specify an exit.");
        assertEquals("Blaviken's Market Square", game.getGameWorld().getActualLocation().getName(), "The player should still be in the initial location.");
    }

    @Test
    public void testGoToExistingExit() {
        String goResult = commandGo.run(new String[]{"Alchemist's House"});

        assertEquals("You move to Alchemist's House:\n" +
                "You're in Location: Alchemist's House\n" +
                "A dimly lit cottage filled with the scent of brewing potions and burning herbs. \n" +
                "The alchemist here has knowledge few possess, but his help comes at a cost.\n" +
                "\n" +
                "\n" +
                "Exits: Blaviken's Market Square | Haunted Hut | Hindar Woods\n" +
                "Items: Dog_tallow | Alchemical Compendium\n" +
                "Characters: Alchemist\n" +
                "Creatures:  None", goResult, "The result should confirm the player moved to 'Alchemist's Houset'.");
        assertEquals("Alchemist's House", game.getGameWorld().getActualLocation().getName(), "The player should be in the 'Alchemist's House' location.");

        String forestLook = commandLook.run(new String[]{});
        assertTrue(forestLook.contains("Alchemist's House"), "The description should include the current location 'Alchemist's House'.");
        assertTrue(forestLook.contains("Characters: Alchemist"), "The description should include the NPC 'Alchemist'.");
        assertTrue(forestLook.contains("Items: Dog_tallow | Alchemical Compendium"), "The description should include the items in the location.");
        assertTrue(forestLook.contains("Exits: Blaviken's Market Square | Haunted Hut | Hindar Woods"), "The description should include the exits from the location.");
        commandTake.run(new String[]{"Dog_tallow"});
        String forestLookAfterTake = commandLook.run(new String[]{});
        assertFalse(forestLookAfterTake.contains("Dog _tallow"), "The description should no longer include the taken item 'Dog _tallow'.");
    }

    @Test
    public void testGoToInvalidExit() {
        String invalidGo = commandGo.run(new String[]{"Castle"});

        assertEquals("There is no path to Castle.", invalidGo, "Should indicate the location is not accessible.");
        assertEquals("Blaviken's Market Square", game.getGameWorld().getActualLocation().getName(), "The player should still be in the initial location.");
    }

    @Test
    public void testGoToDarkLocation() {
        String darkGo = commandGo.run(new String[]{"Haunted Hut"});

        assertEquals("It's too dark to see. You need a light source.", darkGo, "Should indicate the location is too dark to enter.");
        assertEquals("Blaviken's Market Square", game.getGameWorld().getActualLocation().getName(), "The player should still be in the initial location.");

        Item torch = new Item("Torch", "A bright torch to light your way.", true, "Tool", 1, true, true, 1);
        game.getInventory().addItem(torch);
        assertFalse(game.getGameWorld().getLocationByName("Haunted Hut").isTooDark(game.getInventory()), "The location should no longer be too dark.");

        String lightGo = commandGo.run(new String[]{"Haunted Hut"});
        assertEquals("You move to Haunted Hut:\n" +
                "You're in Location: Haunted Hut\n" +
                "A decrepit ruin on the village outskirts. No one dares enter. \n" +
                "Shadows move inside even when there’s no wind, and the ground feels cursed beneath your feet.\n" +
                "\n" +
                "\n" +
                "Exits: Alchemist's House | Blaviken's Market Square | Chapel of Eternal Fire | Hindar Woods\n" +
                "Items: Cursed Scroll\n" +
                "Characters:  None\n" +
                "Creatures:  None\n" +
                "It is very dark here. You will need a light source.", lightGo, "Should confirm the player moved to 'Haunted Hut'.");
        assertEquals(game.getGameWorld().getActualLocation().getName(), "Haunted Hut", "The player should be in the 'Haunted Hut' location.");
    }

    @Test
    public void testGoLockedLocation() {
        String lockedGo = commandGo.run(new String[]{"Chapel Of Eternal Fire"}); //locked

        assertEquals("The path to Chapel Of Eternal Fire is locked. Unlock it first.", lockedGo, "Should indicate the location is locked.");
        assertEquals("Blaviken's Market Square", game.getGameWorld().getActualLocation().getName(), "The player should still be in the initial location.");

        String unlockWithoutKey = new CommandUnlock(game).run(new String[]{"Chapel Of Eternal Fire"});
        assertEquals("You don't have the key.", unlockWithoutKey, "Should indicate the player needs a key to unlock the location.");
        assertTrue(game.getGameWorld().getActualLocation().isExitLocked("Chapel Of Eternal Fire"), "The location should be unlocked.");

        Item key = new Item("Key", "A key to unlock the door.", true, "Tool", 1, true, true, 1);
        game.getInventory().addItem(key);

        String unlock = new CommandUnlock(game).run(new String[]{"Chapel Of Eternal Fire"});
        assertEquals("You unlocked the path to 'Chapel Of Eternal Fire'.", unlock, "Should confirm the player unlocked the location.");
        assertFalse(game.getGameWorld().getActualLocation().isExitLocked("Chapel Of Eternal Fire"), "The location should be unlocked.");

        String unlockedGo = commandGo.run(new String[]{"Hindar Woods"});
        assertEquals("You move to Hindar Woods:\n" +
                "You're in Location: Hindar Woods\n" +
                "A dense, ancient forest where even the trees seem to whisper. \n" +
                "The villagers speak of a Leshy that stalks its depths, a guardian twisted by rage. Best tread carefully.\n" +
                "\n" +
                "\n" +
                "Exits: Alchemist's House | Blaviken's Market Square | Chapel of Eternal Fire | Dantan Glade | Haunted Hut | Old Stone Bridge | Tulasens’ Cave\n" +
                "Items: Ancient Pine | Cortinarius\n" +
                "Characters:  None\n" +
                "Creatures: Leshy\n" +
                "Tracks: There are signs of movement.\n" +
                "It is very dark here. You will need a light source.", unlockedGo, "Should confirm the player moved to 'Hindar Woods'.");
        assertEquals("Hindar Woods", game.getGameWorld().getActualLocation().getName(), "The player should be in the 'Hindar Woods' location.");
    }
    @Test
    public void testRiddleSolve() {

        game.getGameWorld().setActualLocation(game.getGameWorld().getLocationByName("Old Stone Bridge"));

        String goUnsolved = commandGo.run(new String[]{"Rozkos River"});
        assertEquals("A mysterious voice whispers: \"I wont let you go... Solve my riddle first. What has to be broken before you can use it?\"", goUnsolved, "Should indicate the player needs to solve the riddle.");
        assertEquals("Old Stone Bridge", game.getGameWorld().getActualLocation().getName(), "The player should still be on the 'Old Stone Bridge'.");

        String solveIncorrect = new CommandSolveRiddle(game).run(new String[]{"idk"});
        assertEquals("Wrong answer! Try again.", solveIncorrect, "Should indicate the answer is incorrect.");

        String solveCorrectFirst = new CommandSolveRiddle(game).run(new String[]{"egg"});
        assertEquals("Correct! You may now proceed.", solveCorrectFirst, "Should indicate the answer is correct.");

        String goUnsolvedAfterFirstSolve = commandGo.run(new String[]{"Rozkos River"});
        assertEquals("A mysterious voice whispers: \"I wont let you go... Solve my riddle first. I speak without a mouth and hear without ears. I have no body, but I come alive with the wind. What am I?\"", goUnsolvedAfterFirstSolve, "Should give the next riddle.");
        assertEquals("Old Stone Bridge", game.getGameWorld().getActualLocation().getName(), "The player should still be on the 'Old Stone Bridge'.");

        String solveCorrectSecond = new CommandSolveRiddle(game).run(new String[]{"echo"});
        assertEquals("Correct! You may now proceed.", solveCorrectSecond, "Should indicate the answer is correct.");

        String goSolved = commandGo.run(new String[]{"Rozkos River"});
        assertEquals("You move to Rozkos River:\n" +
                "You're in Location: Rozkos River\n" +
                "A wide, churning river with waters as dark as a sorcerer’s soul. \n" +
                "A Djinn is rumored to be bound beneath its depths—disturbing it would be a grave mistake.\n" +
                "\n" +
                "\n" +
                "Exits: Old Stone Bridge\n" +
                "Items: Drowner Brain | Vitalizing Seaweed\n" +
                "Characters:  None\n" +
                "Creatures: Djinn", goSolved, "Should confirm the player moved to 'Rozkos River'.");
        assertEquals("Rozkos River", game.getGameWorld().getActualLocation().getName(), "The player should be in the 'Rozkos River' location.");
    }
    @Test
    public void testTrackCommand(){
        game.getGameWorld().setActualLocation(game.getGameWorld().getLocationByName("Hindar Woods"));
        String trackResult = new CommandTrack(game).run(new String[]{});
        assertEquals("Using your Witcher senses, you find: Footprints, deep and heavy, disappear into the thick foliage. → Tulasens’ Cave", trackResult, "Should indicate the player sees footprints.");

        game.getGameWorld().setActualLocation(game.getGameWorld().getLocationByName("Tulasens’ Cave"));
        String trackResult2 = new CommandTrack(game).run(new String[]{});
        assertEquals("Using your Witcher senses, you find: Scratch marks along the rocky walls lead further into darkness. → Vulkodlak's Den", trackResult2, "Should indicate the player sees footprints.");

        game.getGameWorld().setActualLocation(game.getGameWorld().getLocationByName("Old Stone Bridge"));
        String trackNoResult = new CommandTrack(game).run(new String[]{});
        assertEquals("You scan the area with your Witcher senses... but find nothing.", trackNoResult, "Should indicate the player doesn't see any footprints.");
    }
}
