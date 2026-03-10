package cz.vse.adv_witcher_labm02.test;

import cz.vse.adv_witcher_labm02.main.game.Game;
import cz.vse.adv_witcher_labm02.main.game.Inventory;
import cz.vse.adv_witcher_labm02.main.game.commands.CommandInventory;
import cz.vse.adv_witcher_labm02.main.game.commands.CommandTake;
import cz.vse.adv_witcher_labm02.main.game.commands.CommandDrop;
import cz.vse.adv_witcher_labm02.main.game.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testovací třída pro ověření funkčnosti {@link Inventory} a souvisejících funkcí,
 * které používají věci v inventáři. {@link CommandTake}, {@link CommandDrop},
 * {@link CommandInventory}
 *
 * @author Mariia Labazina
 * @version ZS-2024, 2025-01-12
 */
public class InventoryTest {

    private Game game;
    private Inventory inventory;
    private CommandTake commandTake;
    private CommandDrop commandDrop;
    private CommandInventory commandInventory;

    @BeforeEach
    public void setUp() {
        game = new Game();
        inventory = game.getInventory();
        inventory.removeItem("Sword");

        commandTake = new CommandTake(game);
        commandDrop = new CommandDrop(game);
        commandInventory = new CommandInventory(game);

        game.getGameWorld().getActualLocation().addItem(new Item("Sword", "A sharp steel sword.", true, "Weapon", 1, 1));
        game.getGameWorld().getActualLocation().addItem(new Item("Shield", "A sturdy wooden shield.", true, "Armor", 1, 1));
        game.getGameWorld().getActualLocation().addItem(new Item("Potion", "A healing potion.", true, "Consumable", 1, 1));
        game.getGameWorld().getActualLocation().addItem(new Item("Anvil", "A heavy blacksmith's anvil.", false, "Crafting", 1, 1));
    }

    @Test
    public void testTakeItem() {
        String result = commandTake.run(new String[]{"Sword"});

        assertEquals("You take Sword", result, "Should confirm that the Sword has been taken.");
        assertTrue(inventory.hasItem("Sword"), "Inventory should contain the Sword.");
        assertFalse(game.getGameWorld().getActualLocation().hasItem("Sword"), "World should no longer contain the Sword.");
    }
    @Test
    public void testTakeUnmovableItem() {
        String result = commandTake.run(new String[]{"Anvil"});

        assertEquals("Well, you can't take this, it's heavy", result, "Should indicate that the Anvil cannot be taken.");
        assertFalse(inventory.hasItem("Anvil"), "Inventory should not contain the Anvil.");
        assertTrue(game.getGameWorld().getActualLocation().hasItem("Anvil"), "World should still contain the Anvil.");
    }
    @Test
    public void testTakeNonExistentItem() {
        String result = commandTake.run(new String[]{"Bow"});

        assertEquals("There is no 'Bow' here to take.", result, "Should indicate that the item does not exist in the world.");
        assertFalse(inventory.hasItem("Bow"), "Inventory should not contain a non-existent item.");
    }

    @Test
    public void testDropItem() {
        commandTake.run(new String[]{"Sword"});

        String result = commandDrop.run(new String[]{"Sword"});

        assertEquals("You have dropped the Sword.", result, "Should confirm that the Sword has been dropped.");
        assertFalse(inventory.hasItem("Sword"), "Inventory should no longer contain the Sword.");
        assertTrue( game.getGameWorld().getActualLocation().hasItem("Sword"), "World should now contain the Sword.");
    }

    @Test
    public void testDropNonExistentItem() {
        String result = commandDrop.run(new String[]{"Bow"});

        assertEquals("You don't have that item.", result, "Should indicate that the item is not in the inventory.");
        assertFalse(game.getGameWorld().getActualLocation().hasItem("Bow"), "World should not contain a non-existent item.");
    }

    @Test
    public void testInventoryCommand() {
        commandTake.run(new String[]{"Sword"});
        commandTake.run(new String[]{"Shield"});

        String result = commandInventory.run(new String[]{});

        assertTrue(result.contains("Sword"), "Inventory command output should include 'Sword'.");
        assertTrue(result.contains("Shield"), "Inventory command output should include 'Shield'.");
    }

    @Test
    public void testInventoryEmpty() {
        String result = commandInventory.run(new String[]{});
        assertEquals("Your inventory is empty.", result, "Should indicate that the inventory is empty.");
    }
}
