package cz.vse.adv_witcher_labm02.main.game.commands;

import cz.vse.adv_witcher_labm02.main.game.Game;
import cz.vse.adv_witcher_labm02.main.game.Location;

import java.util.Objects;

/**
 * Třída implementující příkaz pro přesun hráče mezi lokacemi.
 * Hráč může použít tento příkaz k přechodu do jiné lokace v herním světě,
 * pokud je k ní dostupná cesta.
 *
 * @author Mariia Labazina
 * @version ZS-2024, 2025-01-12
 */
public class CommandGo implements ICommand {
    private Game game;
    public static final String NAME = "go";

    /**
     * Konstruktor třídy.
     *
     * @param game hra, ve které se bude příkaz používat
     */
    public CommandGo(Game game) {
        this.game = game;
    }

    /**
     * Metoda vrací název příkazu, tj.&nbsp;slovo <b>go</b>.
     *
     * @return název příkazu
     */
    @Override
    public String getName() {
        return "go";
    }

    /**
     * Metoda provádí přesun hráče do cílové lokace.
     * Nejprve zkontroluje, zda byl zadán název lokace. Pokud ne, vrátí chybové hlášení.
     * Poté ověří, zda existuje cesta do cílové lokace. Pokud je cesta zamčená
     * hádankou nebo lokace vyžaduje světlo, zpracuje příslušné podmínky.
     * Pokud jsou všechny kontroly splněny, nastaví cílovou lokaci jako aktuální.
     *
     * @param argumentsCommand parametry příkazu <i>(očekává se název lokace)</i>
     * @return zpráva potvrzující přesun nebo chybové hlášení
     */
    @Override
    public String run(String[] argumentsCommand) {
        if (argumentsCommand.length == 0) {
            return "You need to specify a location.";
        }
        String locationName = String.join(" ", argumentsCommand);
        Location currentLocation = game.getGameWorld().getActualLocation();
        Location destination = game.getGameWorld().getLocationByName(locationName);

        if (destination == null || !currentLocation.hasExit(locationName)) {
            return "There is no path to " + locationName + ".";
        }

        if (currentLocation.isExitLocked(locationName)) {
            return "The path to " + locationName + " is locked. Unlock it first.";
        }

        if (currentLocation.requiresRiddle() && !(locationName.equals("Hindar Woods"))) {
            return "A mysterious voice whispers: \"I wont let you go... Solve my riddle first. "  + currentLocation.getRiddle().getQuestion() + "\"";
        }
        if (destination.requiresLight() && destination.isTooDark(game.getInventory())) {
            return "It's too dark to see. You need a light source.";
        }
        game.getGameWorld().setActualLocation(destination);
        return "You move to " + destination.getName() + ":\n" + destination.getDetailedDescription();
    }

}