package cz.vse.adv_witcher_labm02.test.commands;

import cz.vse.adv_witcher_labm02.main.game.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import cz.vse.adv_witcher_labm02.main.game.commands.CommandLight;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testovací třída pro ověření funkčnosti příkazu {@link CommandLight}.
 *
 * @author Mariia Labazina
 * @version ZS-2024, 2025-01-12
 */
public class CommandLightTest {

    private Game game;
    private CommandLight commandLight;

    @BeforeEach
    public void setUp() {
        game = new Game();
        commandLight = new CommandLight(game);

        Inventory inventory = game.getInventory();
        inventory.addItem(new Item("Torch", "A wooden torch.", true, "Lighting the way", 1, true, false, 1));
        inventory.addItem(new Item("Lantern", "A small oil lantern.", true, "Lighting the way", 1, true, false, 1));
        inventory.addItem(new Item("Book", "An old book.", true, "Contains knowledge", 1, false, false, 1));
    }

    @Test
    public void testGetName() {
        assertEquals("light", commandLight.getName(), "The command should return the name 'light'.");
    }

    @Test
    public void testLightWithoutArguments() {
        String result = commandLight.run(new String[]{});
        assertEquals("You need to specify what you want to light.", result, "Should return a message about missing item to light.");
    }

    @Test
    public void testLightNonexistentItem() {
        String result = commandLight.run(new String[]{"Candle"});
        assertEquals("You don't have Candle in your inventory.", result, "Should return a message about item not being in the inventory.");
    }

    @Test
    public void testLightNonLightableItem() {
        String result = commandLight.run(new String[]{"Book"});
        assertEquals("You cannot light Book.", result, "Should return a message indicating the item cannot be lit.");
    }

    @Test
    public void testLightAlreadyLitItem() {
        Item torch = game.getInventory().getItemByName("Torch");
        torch.light();

        String result = commandLight.run(new String[]{"Torch"});
        assertEquals("The Torch is already lit.", result, "Should return a message indicating the item is already lit.");
    }

   /* @Test
    public void testLightValidItem() {
        String result = commandLight.run(new String[]{"Lantern"});
        assertEquals("You light the Lantern.", result, "Should confirm successful lighting of the item.");

        Item lantern = game.getInventory().getItemByName("Lantern");
        assertTrue(lantern.isLit(), "The Lantern should now be lit.");
    }*/
}