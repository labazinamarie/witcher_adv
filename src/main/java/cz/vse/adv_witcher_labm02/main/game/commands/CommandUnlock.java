package cz.vse.adv_witcher_labm02.main.game.commands;

import cz.vse.adv_witcher_labm02.main.game.Game;
import cz.vse.adv_witcher_labm02.main.game.Location;

/**
 * Třída implementující příkaz pro odemknutí cesty mezi lokacemi.
 * Hráč může použít tento příkaz k odemknutí zamčené cesty pomocí specifického klíče.
 *
 * @author Mariia Labazina
 * @version ZS-2024, 2025-01-12
 */
public class CommandUnlock implements ICommand {
    private Game game;

    /**
     * Konstruktor třídy.
     *
     * @param game hra, ve které se bude příkaz používat
     */
    public CommandUnlock(Game game) {
        this.game = game;
    }

    /**
     * Metoda vrací název příkazu, tj.&nbsp;slovo <b>unlock</b>.
     *
     * @return název příkazu
     */
    @Override
    public String getName() {
        return "unlock";
    }

    /**
     * Metoda provádí odemknutí cesty mezi lokacemi.
     *
     * @param argumentsCommand parametry příkazu <i>(očekávají se jeden parametr: název lokace)</i>
     * @return zpráva potvrzující akci nebo chybové hlášení
     */
    @Override
    public String run(String[] argumentsCommand) {
        if (argumentsCommand.length < 1) {
            return "You need to specify what you want to unlock.";
        }
        String locationName = String.join(" ", argumentsCommand);
        Location currentLocation = game.getGameWorld().getActualLocation();

        if (!currentLocation.hasExit(locationName)) {
            return "Where's the door? I can't see it.";
        }

        if (!game.getInventory().hasItem("key")) {
            return "You don't have the key.";
        }

        if (!currentLocation.isExitLocked(locationName)) {
            return "The path to '" + locationName + "' is already unlocked.";
        }

        boolean unlocked = currentLocation.unlockExit(locationName);
        if (unlocked) {
            return "You unlocked the path to '" + locationName + "'.";
        } else {
            return "Something went wrong. The path couldn't be unlocked.";
        }
    }
}