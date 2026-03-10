package cz.vse.adv_witcher_labm02.test.commands;

import cz.vse.adv_witcher_labm02.main.game.Game;
import cz.vse.adv_witcher_labm02.main.game.Inventory;
import cz.vse.adv_witcher_labm02.main.game.Item;
import cz.vse.adv_witcher_labm02.main.game.commands.CommandCombineItems;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for verifying the {@link CommandCombineItems} functionality.
 *
 * @author Mariia Labazina
 * @version ZS-2024, 2025-01-12
 */
public class CommandCombineItemsTest {

    private Game game;
    private CommandCombineItems commandCombineItems;
    private Inventory inventory;

    @BeforeEach
    public void setUp() {
        game = new Game();
        commandCombineItems = new CommandCombineItems(game);
        inventory = game.getInventory();

        inventory.addItem(new Item("Stick", "A simple wooden stick.", true, "Material", 1, 0));
        inventory.addItem(new Item("String", "A durable string.", true, "Material", 1, 0));

        inventory.addItem(new Item("Wolfsbane", "also known as the \"queen of poisons\" and \"monk's hood\"," +
                " is used in many witcher potions and alchemical brews.", true,
                "use it for brewing the cursed oil", 1, true, 0));

        inventory.addItem(new Item("Dog_tallow", "an alchemy ingredient that is needed to craft many blade oils",
                true, "use it for crafting the cursed oil", 1, true,1));

    }

    @Test
    public void testCombineCorrectItems() {
        String result = commandCombineItems.run(new String[]{"Wolfsbane", "Dog_tallow"});

        assertNotNull(result, "The result of combining items should not be null.");
        assertEquals("You combined Wolfsbane, Dog_tallow to create Cursed Oil.", result, "The message should indicate the successful combination of items.");

        assertTrue(inventory.hasItem("Cursed Oil"), "The inventory should now contain the new item 'Cursed Oil'.");
        assertFalse(inventory.hasItem("Wolfsbane"), "The inventory should no longer contain 'Wolfsbane'.");
        assertFalse(inventory.hasItem("Dog_tallow"), "The inventory should no longer contain 'Dog_Tallow'.");
    }

    @Test
    public void testGetName() {
        assertEquals("combine", commandCombineItems.getName(), "The command should return the name 'combine'.");
    }

    @Test
    public void testCombineWithoutArguments() {
        String result = commandCombineItems.run(new String[]{});

        assertEquals("Please specify at least two items to combine.", result, "Should return a message about missing items to combine.");
    }

    @Test
    public void testCombineNonExistentItems() {
        String result = commandCombineItems.run(new String[]{"Rock", "Leaf"});
        assertEquals("Item 'Rock' not found in inventory.", result, "Should return a message indicating missing items.");

    }

    @Test
    public void testCombineWithInsufficientItems() {
        Inventory inventory = game.getInventory();
        inventory.removeItem("wolfsbane");

        String result = commandCombineItems.run(new String[]{"Dog_tallow", "Wolfsbane"});

        assertEquals("Item 'Wolfsbane' not found in inventory.", result, "Should return a message indicating insufficient items.");
        assertFalse(inventory.hasItem("Cursed Oil"), "The inventory shouldn't contain the new item 'Cursed Oil'.");
        assertFalse(inventory.hasItem("Wolfsbane"), "The inventory shouldn't contain 'Wolfsbane'.");
        assertTrue(inventory.hasItem("Dog_tallow"), "The inventory should contain 'Dog Tallow'.");
    }

    @Test
    public void testCombineInvalidCombination() {
        String result = commandCombineItems.run(new String[]{"Stick", "Wolfsbane"});

        assertEquals("No valid combination found for Stick, Wolfsbane.", result, "Should return a message indicating invalid combination.");
    }
}
