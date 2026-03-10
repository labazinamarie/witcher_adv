package cz.vse.adv_witcher_labm02.main.game.commands;
import java.util.*;
import cz.vse.adv_witcher_labm02.main.game.*;
/**
 * Třída implementující příkaz pro řešení hádanek.
 * Hráč může použít tento příkaz k pokusu o vyřešení hádanky,
 * která může blokovat postup v aktuální lokaci.
 *
 * @author Mariia Labazina
 * @version ZS-2024, 2025-01-12
 */
public class CommandSolveRiddle implements ICommand {
    private Game game;

    /**
     * Konstruktor třídy.
     *
     * @param game hra, ve které se bude příkaz používat
     */
    public CommandSolveRiddle(Game game) {
        this.game = game;
    }

    /**
     * Metoda vrací název příkazu, tj.&nbsp;slova <b>solve riddle</b>.
     *
     * @return název příkazu
     */
    @Override
    public String getName() {
        return "solve_riddle";
    }

    /**
     * Metoda umožňuje hráči pokusit se vyřešit hádanku v aktuální lokaci.
     * Nejprve zkontroluje, zda byla poskytnuta odpověď na hádanku. Poté ověří,
     * zda aktuální lokace obsahuje hádanku. Pokud ano, zkontroluje správnost odpovědi.
     * Pokud je odpověď správná, hádanka se změní na novou nebo zmizí. Pokud je odpověď
     * nesprávná, vrátí odpovídající chybové hlášení.
     *
     * @param argumentsCommand parametry příkazu <i>(očekává se odpověď na hádanku)</i>
     * @return zpráva potvrzující vyřešení hádanky nebo chybové hlášení
     */
    @Override
    public String run(String[] argumentsCommand) {
        if (argumentsCommand.length == 0) {
            return "You need to provide an answer to the riddle.";
        }

        Location currentLocation = game.getGameWorld().getActualLocation();

        if (!currentLocation.requiresRiddle()) {
            return "There's no riddle to solve here.";
        }

        String answer = String.join(" ", argumentsCommand);
        if (currentLocation.solveRiddle(answer)) {
            currentLocation.setRiddle(game.getGameWorld().getNextRiddle());
            return "Correct! You may now proceed.";
        } else {
            return "Wrong answer! Try again.";
        }
    }
}