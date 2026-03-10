package cz.vse.adv_witcher_labm02.main.game.commands;
import cz.vse.adv_witcher_labm02.main.game.*;
/**
 * Třída implementující příkaz pro rozhlédnutí se v aktuální lokaci.
 * Hráč může použít tento příkaz k zobrazení detailního popisu aktuální lokace,
 * včetně jejích vlastností, postav a dalších prvků.
 *
 * @author Mariia Labazina
 * @version ZS-2024, 2025-01-12
 */
public class CommandLook implements ICommand {
    private Game game;

    /**
     * Konstruktor třídy.
     *
     * @param game hra, ve které se bude příkaz používat
     */
    public CommandLook(Game game) {
        this.game = game;
    }

    /**
     * Metoda vrací název příkazu, tj.&nbsp;slovo <b>Look_around</b>.
     *
     * @return název příkazu
     */
    @Override
    public String getName(){
        return "Look";
    }

    /**
     * Metoda zobrazí detailní popis aktuální lokace.
     * Nejprve zkontroluje, zda byl zadán parametr. Pokud je parametr přítomen,
     * vrátí chybové hlášení. Pokud není parametr zadán, vrátí popis aktuální
     * lokace.
     *
     * @param argumentsCommand parametry příkazu <i>(neočekávají se žádné)</i>
     * @return popis aktuální lokace nebo chybové hlášení
     */
    @Override
    public String run(String[] argumentsCommand){

        if(argumentsCommand.length >0) {
            return "Hmm, I don't understand. I don't know how to look at something";
        }
        return game.getGameWorld().getActualLocation().getDetailedDescription();
    }
}