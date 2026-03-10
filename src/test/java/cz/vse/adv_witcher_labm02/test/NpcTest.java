package cz.vse.adv_witcher_labm02.test;

import cz.vse.adv_witcher_labm02.main.game.*;
import cz.vse.adv_witcher_labm02.main.game.commands.CommandGive;
import cz.vse.adv_witcher_labm02.main.game.commands.CommandTalk;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testovací třída {@link NPC} a souvisejících příkazů: {@link CommandGive},  {@link CommandTalk}
 *
 * @author Mariia Labazina
 * @version ZS-2024, 2025-01-12
 */
public class NpcTest {

    private Inventory inventory;
    private CommandGive commandGive;
    private CommandTalk commandTalk;

    @BeforeEach
    public void setUp() {
        Game game = new Game();
        inventory = game.getInventory();
        commandGive = new CommandGive(game);
        commandTalk = new CommandTalk(game);
        Location actualLocation = game.getGameWorld().getActualLocation();

        NPC alchemist = new NPC("Alchemist", actualLocation,
                List.of("Ah, a Witcher! You seek knowledge, or perhaps something more... potent?"), 1);
        actualLocation.addCharacter(alchemist);

        inventory.addItem(new Item("Gold Coin", "A shiny gold coin.", true, "Currency", 1, 1));
        inventory.addItem(new Item("Iron Ingot", "A bar of iron used for crafting.", true, "Material", 1, 1));
    }

    @Test
    public void testGiveItemToNPC() {
        String result = commandGive.run(new String[]{"Gold Coin", "Alchemist"});

        assertEquals("You have given Gold Coin to Alchemist.", result, "Should confirm the item was given to the NPC.");
        assertFalse(inventory.hasItem("Gold Coin"), "Inventory should no longer contain the Gold Coin.");

    }

    @Test
    public void testGiveNonExistentItem() {
        String result = commandGive.run(new String[]{"Diamond", "Alchemist"});

        assertEquals("You don't have that item.", result, "Should indicate the item is not in the inventory.");
    }

    @Test
    public void testGiveItemToNonExistentNPC() {
        String result = commandGive.run(new String[]{"Gold Coin", "Dragon"});

        assertEquals("There is no character named 'Dragon' here.", result, "Should indicate the NPC does not exist.");
        assertTrue(inventory.hasItem("Gold Coin"), "Inventory should still contain the Gold Coin.");
    }

    @Test
    public void testGiveItemNotInInventory() {
        String result = commandGive.run(new String[]{"Iron Sword", "Alchemist"});

        assertEquals("You don't have that item.", result, "Should indicate the item is not in the inventory.");
    }

    @Test
    public void testGiveWithoutArguments() {
        String result = commandGive.run(new String[]{});

        assertEquals("You need to specify an item and a character to give it to.", result, "Should prompt the user to specify both an item and an NPC.");
    }

    @Test
    public void testTalkToNPC() {
        String result = commandTalk.run(new String[]{"Alchemist"});

        assertEquals("Alchemist: Ah, a Witcher! You seek knowledge, or perhaps something more... potent?", result, "Should return the NPC's dialogue.");
    }
    @Test
    public void testTalkToNonExistentNPC() {
        String result = commandTalk.run(new String[]{"Dragon"});

        assertEquals("There is no character named 'Dragon' here.", result, "Should indicate the NPC does not exist.");
    }
}
