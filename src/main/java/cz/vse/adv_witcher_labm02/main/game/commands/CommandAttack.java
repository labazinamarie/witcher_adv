package cz.vse.adv_witcher_labm02.main.game.commands;

import cz.vse.adv_witcher_labm02.main.game.Creature;
import cz.vse.adv_witcher_labm02.main.game.Game;
import cz.vse.adv_witcher_labm02.main.game.GameState;
import cz.vse.adv_witcher_labm02.main.game.Location;


/**
 * Třída implementující příkaz pro útok na nepřátele ve hře.
 * Hráč může použít tento příkaz k boji proti bytostem přítomným v aktuální lokaci.
 *
 * @author Mariia Labazina
 * @version ZS-2024, 2025-01-12
 */
public class CommandAttack implements ICommand {
    private final Game game;

    /**
     * Konstruktor třídy.
     *
     * @param game hra, ve které se bude příkaz používat
     */
    public CommandAttack(Game game) {
        this.game = game;
    }

    /**
     * Metoda vrací název příkazu, tj.&nbsp;slovo <b>attack</b>.
     *
     * @return název příkazu
     */
    @Override
    public String getName() {
        return "attack";
    }

    /**
     * Metoda provádí útok hráče na specifikovaného nepřítele. Nejprve zkontroluje
     * počet parametrů. Pokud nebyl zadán žádný parametr <i>(tj. není specifikován
     * cíl útoku)</i>, vrátí chybové hlášení. Pokud je zadán parametr, zkontroluje,
     * zda je nepřítel s daným názvem přítomen v aktuální lokaci. Pokud ne, vrátí
     * chybové hlášení. Pokud je nepřítel nalezen, provede útok, vypočítá škody
     * způsobené nepříteli i hráči a podle výsledku boje vrátí příslušnou zprávu.
     *
     * @param argumentsCommand parametry příkazu <i>(očekává se pole s jedním prvkem - jméno nepřítele)</i>
     * @return informace pro hráče, které hra vypíše na konzoli
     */
    @Override
    public String run(String[] argumentsCommand) {
        if (argumentsCommand.length == 0) {
            return "You need to specify an enemy to attack.";
        }


        String enemyName = argumentsCommand[0];
        Location currentLocation = game.getGameWorld().getActualLocation();
        Creature enemy = currentLocation.getCreatureByName(enemyName);
        GameState gameState = game.getGameState();

        if (enemy == null) {
            return "There is no '" + enemyName + "' here to attack.";
        }

        int playerAttackStrength = 100;

        enemy.takeDamage(playerAttackStrength);

        String result = "You attack " + enemy.getName() + " for " + playerAttackStrength + " damage.";

        if (!enemy.isAlive()) {
            result += " " + enemy.getName() + " has been defeated!";

        } else {
            result += " " + enemy.getName() + " has " + enemy.getHealth() + " health remaining.";
            int damageToPlayer = enemy.attack();
            result += "\n" + enemy.getName() + " retaliates and deals " + damageToPlayer + " damage to you.";

            if (damageToPlayer >= game.getPlayerHealth()) {
                result += "\nYou have been defeated by " + enemy.getName() + "!";
                game.setGameOver(true);
                return result + "\n" + game.getEpilogue();
            } else {
                result += "\nYou survived the attack!";
            }

        }

        return result;
    }

}