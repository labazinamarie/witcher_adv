package cz.vse.adv_witcher_labm02.test.commands;

import cz.vse.adv_witcher_labm02.main.game.Game;
import cz.vse.adv_witcher_labm02.main.game.Item;
import cz.vse.adv_witcher_labm02.main.game.Location;
import cz.vse.adv_witcher_labm02.main.game.commands.CommandLook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testovací třída pro komplexní otestování třídy {@link CommandLook}.
 *
 * @author Mariia Labazina
 * @version ZS-2024, 2025-01-15
 */
public class CommandLookAroundTest {

    private Game game;
    private CommandLook commandLookAround;

    @BeforeEach
    public void setUp() {
        game = new Game();

        commandLookAround = new CommandLook(game);

        Location kaerMorhen = new Location("Kaer Morhen", "The ancient keep of the Witchers.", false);
        Location forest = new Location("Forest", "A dense forest filled with mysteries.", false);

        kaerMorhen.addExit(forest, false);
        forest.addExit(kaerMorhen, false);

        kaerMorhen.addItem(new Item("Sword", "A sharp steel sword.", true, "Combat", 1, 1));
        forest.addItem(new Item("Potion", "A healing potion.", true, "Healing", 2, 1));

        game.getGameWorld().setActualLocation(kaerMorhen);
    }

    @Test
    public void testGetName() {
        assertEquals("Look", commandLookAround.getName());
    }

    @Test
    public void testLookAroundInCurrentLocation() {
        String result = commandLookAround.run(new String[]{});

        assertNotNull(result);
        assertTrue(result.contains("Kaer Morhen"));
        assertTrue(result.contains("The ancient keep of the Witchers."));
        assertTrue(result.contains("Sword"));
    }
    @Test
    public void testLookAroundAfterChangingLocation() {
        Location woods = game.getGameWorld().getLocationByName("Hindar Woods");
        assertNotNull(woods, "The location 'Hindar Woods' should exist in the game world.");
        game.getGameWorld().setActualLocation(woods);
    
        String result = commandLookAround.run(new String[]{});
    
        assertNotNull(result, "The result should not be null.");
        assertTrue(result.contains("Hindar Woods"), "The result should include the location name.");
        assertTrue(result.contains("A dense, ancient forest"), "The result should include the location description.");
        assertTrue(result.contains("Ancient Pine"), "The result should mention an item in the location.");
    }

    @Test
    public void testInvalidArguments() {
        String result = commandLookAround.run(new String[]{"unexpected_argument"});

        assertEquals("Hmm, I don't understand. I don't know how to look at something", result);
    }
}
