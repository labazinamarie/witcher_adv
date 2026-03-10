package cz.vse.adv_witcher_labm02.main.game.commands;
import java.util.*;
import cz.vse.adv_witcher_labm02.main.game.*;
/**
 * Třída implementující příkaz pro sledování stop.
 * Hráč může použít tento příkaz k využití svých smyslů k odhalení stop
 * v aktuální lokaci, pokud jsou dostupné.
 *
 * @author Mariia Labazina
 * @version ZS-2024, 2025-01-12
 */
public class CommandTrack implements ICommand {
    private Game game;

    /**
     * Konstruktor třídy.
     *
     * @param game hra, ve které se bude příkaz používat
     */
    public CommandTrack(Game game) {
        this.game = game;
    }

    /**
     * Metoda vrací název příkazu, tj.&nbsp;slovo <b>track</b>.
     *
     * @return název příkazu
     */
    @Override
    public String getName() {
        return "track";
    }

    /**
     * Metoda umožňuje hráči použít smysly k odhalení stop v aktuální lokaci.
     * Pokud lokace obsahuje stopy, odhalí jednu z nich. Jinak vrátí zprávu,
     * že v lokaci nejsou žádné stopy k nalezení.
     *
     * @param argumentsCommand parametry příkazu <i>(nejsou očekávány žádné parametry)</i>
     * @return zpráva s odhalenou stopou nebo hlášení, že stopy nebyly nalezeny
     */
    @Override
    public String run(String[] argumentsCommand) {
        Location currentLocation = game.getGameWorld().getActualLocation();

        if (currentLocation.hasTracks()) {
            return currentLocation.revealTrack();
        } else {
            return "You scan the area with your Witcher senses... but find nothing.";
        }
    }
}