package cz.vse.adv_witcher_labm02.test.commands;

import cz.vse.adv_witcher_labm02.main.game.*;
import cz.vse.adv_witcher_labm02.main.game.commands.CommandRead;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for verifying the {@link CommandRead} functionality.
 *
 * @author Mariia Labazina
 * @version ZS-2024, 2025-01-12
 */
public class CommandReadTest {

    private Game game;
    private CommandRead commandRead;

    @BeforeEach
    public void setUp() {
        game = new Game();
        commandRead = new CommandRead(game);

        Inventory inventory = game.getInventory();
        inventory.addItem(new Item("Book", "An old book.", true, "Contains knowledge", 1, true, "This is the content of the book.", 1));
        inventory.addItem(new Item("Scroll", "A magical scroll.", true, "Contains a spell", 1, true, "The spell reads: Fireball!", 1));
        inventory.addItem(new Item("Sword", "A sharp blade.", true, "Used for combat", 1, false, null, 1));
    }

    @Test
    public void testGetName() {
        assertEquals("read", commandRead.getName(), "The command should return the name 'read'.");
    }

    @Test
    public void testReadWithoutArguments() {
        String result = commandRead.run(new String[]{});
        assertEquals("You need to specify what you want to read.", result, "Should return a message about missing item to read.");
    }

    @Test
    public void testReadNonexistentItem() {
        String result = commandRead.run(new String[]{"Map"});
        assertEquals("You don't have an item named 'Map' in your inventory.", result, "Should return a message about item not being in the inventory.");
    }

    @Test
    public void testReadNonReadableItem() {
        String result = commandRead.run(new String[]{"Sword"});
        assertEquals("You cannot read the Sword.", result, "Should return a message indicating the item cannot be read.");
    }

    @Test
    public void testReadReadableItem() {
        String result = commandRead.run(new String[]{"Book"});
        assertEquals("This is the content of the book.", result, "Should return the content of the readable item.");
    }

    @Test
    public void testReadAnotherReadableItem() {
        String result = commandRead.run(new String[]{"Scroll"});
        assertEquals("The spell reads: Fireball!", result, "Should return the content of the readable item.");
    }
}
