package cz.vse.adv_witcher_labm02.test;

import cz.vse.adv_witcher_labm02.main.game.*;
import cz.vse.adv_witcher_labm02.main.game.commands.CommandAttack;
import cz.vse.adv_witcher_labm02.main.game.commands.CommandMeditate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test  třída pro ověření <b>battle</b> funkcionality. Příkazy {@link CommandAttack}, {@link CommandMeditate}.
 *
 * @author Mariia Labazina
 * @version ZS-2024, 2025-01-12
 */
public class BattleTest {

    private Game game;
    private CommandAttack commandAttack;

    @BeforeEach
    public void setUp() {
        game = new Game();
        commandAttack = new CommandAttack(game);

        Location kaerMorhen = new Location("Kaer Morhen", "The ancient keep of the Witchers.", false);
        Creature grifin = new Creature("Griffin", "A fierce beast", kaerMorhen, 9999, 9999);
        Creature weaken = new Creature("Weaken", "Weaken creature", kaerMorhen, 1, 1);

        kaerMorhen.addCreature(grifin);
        kaerMorhen.addCreature(weaken);
        game.getGameWorld().setActualLocation(kaerMorhen);
    }

    @Test
    public void testGetName() {
        assertEquals("attack", commandAttack.getName(), "The command should return the name 'attack'.");
    }

    @Test
    public void testAttackWithNoArguments() {
        String result = commandAttack.run(new String[]{});

        assertEquals("You need to specify an enemy to attack.", result, "Should return a message about missing attack target.");
    }

    @Test
    public void testAttackNonExistingEnemy() {
        String result = commandAttack.run(new String[]{"Dragon"});

        assertEquals("There is no 'Dragon' here to attack.", result, "Should return a message indicating the enemy does not exist in the location.");
    }

    @Test
    public void testAttackExistingEnemy() {
        String result = commandAttack.run(new String[]{"Griffin"});

        assertNotNull(result, "The attack result should not be null.");
        assertTrue(result.contains("You attack Griffin for 100 damage."), "The message should contain information about the inflicted damage.");
    }

    @Test
    public void testAttackEnemyDefeated() {
        Creature enemy = game.getGameWorld().getActualLocation().getCreatureByName("Weaken");
        assertNotNull(enemy, "Weaken should be present in the location.");

        String result = commandAttack.run(new String[]{"Weaken"});

        assertTrue(result.contains("Weaken has been defeated!"),"The message should indicate the enemy was defeated.");
    }

    @Test
    public void testPlayerDefeatedByEnemy() {
        Creature enemy = game.getGameWorld().getActualLocation().getCreatureByName("Griffin");
        assertNotNull(enemy, "Griffin should be present in the location.");

        String result = commandAttack.run(new String[]{"Griffin"});

        assertTrue(result.contains("You have been defeated by Griffin!"), "The message should indicate the player was defeated.");
        assertTrue(game.isGameOver(), "The game should end after the player is defeated.");
    }

    @Test
    public void testMediateRestoreHealth() {
        game.setPlayerHealth(50);

        String result = new CommandMeditate(game).run(new String[]{});
        assertEquals("You meditate and restored your health.", result, "The message should indicate the player's health was restored.");
        assertEquals(100, game.getPlayerHealth(), "The player's health should be restored to the maximum value.");
    }
    @Test
    public void testMediateUnsafeZone() {
        game.setPlayerHealth(50);
        game.getGameWorld().setActualLocation(new Location("haunted field", "A haunted field", false));

        String result = new CommandMeditate(game).run(new String[]{});
        assertEquals("You cannot meditate in dangerous areas!", result, "The message should indicate the player cannot meditate in a dangerous area.");
        assertEquals(50, game.getPlayerHealth(), "The player's health should remain unchanged.");
    }

}
