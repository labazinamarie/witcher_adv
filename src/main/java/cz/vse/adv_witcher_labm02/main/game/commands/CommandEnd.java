package cz.vse.adv_witcher_labm02.main.game.commands;
import java.util.*;
import cz.vse.adv_witcher_labm02.main.game.*;
/**
 * Třída implementující příkaz pro ukončení hry.
 * Hráč může použít tento příkaz k okamžitému ukončení hry,
 * ať už během jakékoli situace.
 *
 * @author Mariia Labazina
 * @version ZS-2024, 2025-01-12
 */
public class CommandEnd implements ICommand {
    private Game game;

    /**
     * Konstruktor třídy.
     *
     * @param game hra, ve které se bude příkaz používat
     */
    public CommandEnd(Game game) {
        this.game = game;
    }

    /**
     * Metoda vrací název příkazu, tj.&nbsp;slovo <b>end</b>.
     *
     * @return název příkazu
     */
    @Override
    public String getName(){
        return "end";
    }

    /**
     * Metoda okamžitě ukončí hru.
     * Nastaví příznak konce hry a vrátí zprávu informující hráče
     * o úspěšném ukončení hry pomocí příkazu 'END'.
     *
     * @param argumentsCommand parametry příkazu <i>(nejsou očekávány žádné parametry)</i>
     * @return zpráva potvrzující ukončení hry
     */
    @Override
    public String run(String[] argumentsCommand){
        game.setGameOver(true);
        return "The main.game was over by the command 'END'";
    }
}