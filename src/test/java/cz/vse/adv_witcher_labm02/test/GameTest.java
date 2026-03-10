package cz.vse.adv_witcher_labm02.test;

import cz.vse.adv_witcher_labm02.main.game.*;
import cz.vse.adv_witcher_labm02.main.game.commands.CommandGo;
import cz.vse.adv_witcher_labm02.main.game.commands.CommandSolveRiddle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testovací třída pro komplexní otestování třídy {@link Game}.
 *
 * @author Mariia Labazina
 * @version ZS-2024, 2025-01-12
 */
public class GameTest {

    private Game game;
    private Inventory inventory;
    private CommandGo commandGo;
    private CommandSolveRiddle commandSolveRiddle;

    @BeforeEach
    public void setUp() {
        game = new Game();
        inventory = game.getInventory();
        commandGo = new CommandGo(game);
        commandSolveRiddle = new CommandSolveRiddle(game);
        Item torch = new Item("Torch", "old rusty torch that can come in handy sometimes", true,
                "it will help you light the way through the darkness", 2, true, false, 1);
        torch.light();
        inventory.addItem(torch);
    }

    @Test
    public void testInitialState() {
        assertNotNull(game.getGameWorld());
        assertNotNull(game.getInventory());
        assertEquals(100, game.getPlayerHealth());
        assertFalse(game.isGameOver());
        assertEquals(GameState.PLAYING, game.getGameState());
    }

    @Test
    public void testSetGameOver() {
        assertFalse(game.isGameOver());
        game.setGameOver(true);
        assertTrue(game.isGameOver());
    }

    @Test
    public void testRestoreHealth() {
        game.restoreHealth();
        assertEquals(100, game.getPlayerHealth());
    }

    @Test
    public void testProcessActionValidCommand() {
        String result = game.processAction("help");
        assertNotNull(result);
        assertTrue(result.contains("Available commands"));
    }

    @Test
    public void testProcessActionInvalidCommand() {
        String result = game.processAction("invalid");
        assertEquals("I don't understand that, I don't know this command.", result);
    }

    @Test
    public void testIsInSafeZone() {
        game.getGameWorld().setActualLocation(new Location("Safe Zone", "A peaceful area.", false));
        assertTrue(game.isInSafeZone());

        game.getGameWorld().setActualLocation(new Location("Haunted Forest", "An eerie and dangerous area.", false));
        assertFalse(game.isInSafeZone());
    }

    @Test
    public void testGetPrologue() {
        String prologue = game.getPrologue();
        assertNotNull(prologue);
        assertTrue(prologue.contains("Welcome to the Continent!"));
        assertTrue(prologue.contains("the boots of a Witcher"));
    }

    @Test
    public void testGetEpilogueWin() {
        game.setGameState(GameState.WON);
        String epilogue = game.getEpilogue();
        assertTrue(epilogue.contains("Congratulations! You have slain the Vulkodlak"));
    }

    @Test
    public void testGetEpilogueLoss() {
        game.setGameState(GameState.LOST);
        Location location = new Location("Haunted Cave", "A dark and eerie cave.", true);
        Creature vulkodlak = new Creature("Vulkodlak", "A ferocious werewolf.", location, 100, 50);
        location.addCreature(vulkodlak);
        game.getGameWorld().setActualLocation(location);
        String epilogue = game.getEpilogue();
        assertTrue(epilogue.contains("You were slain by the Vulkodlak"), "Expected message about being slain by Vulkodlak is missing.");
    }


    @Test
    public void testGameStateLost() {

        assertFalse(inventory.hasItem("Cursed Oil"), "Should confirm the item was not in the inventory.");
        assertFalse(game.isGameOver());

        game.getGameWorld().setActualLocation(game.getGameWorld().getLocationByName("Old Stone Bridge"));

        game.getGameWorld().getActualLocation().unlockExit("Vulkodlak's Den");
        commandSolveRiddle.run(new String[]{"egg"});
        commandSolveRiddle.run(new String[]{"echo"});

        String result = commandGo.run(new String[]{"Vulkodlak's Den"});
        assertEquals("You move to Vulkodlak's Den:\n" +
                "You're in Location: Vulkodlak's Den\n" +
                "A foul-smelling lair deep in the wilderness. The remains of animals—and perhaps men—litter the ground. \n" +
                "Something monstrous lurks inside, and it does not take kindly to intruders.\n" +
                "\n" +
                "\n" +
                "Exits: Old Stone Bridge\n" +
                "Items:  None\n" +
                "Characters:  None\n" +
                "Creatures: Vulkodlak\n" +
                "It is very dark here. You will need a light source.", result, "Should indicate the player cannot enter the location without the Cursed Oil.");
        assertSame(game.getGameState(), GameState.LOST, "The game should be over and the state should be LOST.");
        assertTrue(game.isGameOver(), "The game should be over.");
    }

    @Test
    public void testGameStateWin() {

        inventory.addItem(new Item("Cursed Oil", "A vial of cursed oil.",
                true, "it will help you defeat the Vulkodlak", 1, true, false, 1));
        assertTrue(inventory.hasItem("Cursed Oil"), "Should confirm the item is in the inventory.");
        assertFalse(game.isGameOver());

        game.getGameWorld().setActualLocation(game.getGameWorld().getLocationByName("Old Stone Bridge"));

        game.getGameWorld().getActualLocation().unlockExit("Vulkodlak's Den");
        commandSolveRiddle.run(new String[]{"egg"});
        commandSolveRiddle.run(new String[]{"echo"});

        String result = commandGo.run(new String[]{"Vulkodlak's Den"});
        assertEquals("You move to Vulkodlak's Den:\n" +
                "You're in Location: Vulkodlak's Den\n" +
                "A foul-smelling lair deep in the wilderness. The remains of animals—and perhaps men—litter the ground. \n" +
                "Something monstrous lurks inside, and it does not take kindly to intruders.\n" +
                "\n" +
                "\n" +
                "Exits: Old Stone Bridge\n" +
                "Items:  None\n" +
                "Characters:  None\n" +
                "Creatures: Vulkodlak\n" +
                "It is very dark here. You will need a light source.", result, "Should indicate the player cannot enter the location without the Cursed Oil.");
        assertSame(game.getGameState(), GameState.WON, "The game should be over and the state should be LOST.");
        assertTrue(game.isGameOver(), "The game should be over.");

    }

    @Test
    public void testEndCommand() {
        String result = game.processAction("end");
        assertEquals("The main.game was over by the command 'END'", result);
    }
    @Test
    public void testHelp(){
        String result = game.processAction("help");
        assertTrue(result.contains("Available commands"));
    }
}
