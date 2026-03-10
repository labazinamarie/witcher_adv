package cz.vse.adv_witcher_labm02.main.game.commands;
import java.util.*;
import cz.vse.adv_witcher_labm02.main.game.*;
/**
 * Třída implementující příkaz pro zobrazení nápovědy ve hře.
 * Hráč může použít tento příkaz k zobrazení seznamu všech dostupných příkazů
 * spolu s jejich stručným popisem.
 *
 * @author Mariia Labazina
 * @version ZS-2024, 2025-01-12
 */
public class CommandHelp implements ICommand {
    private Game game;

    /**
     * Konstruktor třídy.
     *
     * @param game hra, ve které se bude příkaz používat
     */
    public CommandHelp(Game game) {
        this.game = game;
    }

    /**
     * Metoda vrací název příkazu, tj.&nbsp;slovo <b>help</b>.
     *
     * @return název příkazu
     */
    @Override
    public String getName() {
        return "help";
    }

    /**
     * Metoda zobrazí seznam všech dostupných příkazů ve hře a jejich stručný popis.
     * Neočekává žádné parametry a vrací nápovědu jako textovou reprezentaci.
     *
     * @param argumentsCommand parametry příkazu <i>(neočekávají se žádné)</i>
     * @return textový seznam dostupných příkazů a jejich popisů
     */
    @Override
    public String run(String[] argumentsCommand) {
        return "Available commands:\n" +
                " - go [location]: Travel to a different location.\n" +
                " - look: Observe your surroundings for details.\n" +
                " - inspect[item]: Examine an item in detail.\n" +
                " - inventory: Look what do you have in inventory.\n" +
                " - take [item]: Pick up an item.\n" +
                " - drop [item]: Drop an item from your inventory.\n" +
                " - use [item]: Use an item.\n" +
                " - combine [item1] [item2]: Craft or merge items.\n" +
                " - attack [enemy]: Engage in combat with an enemy.\n" +
                " - cast [spell]: Use a Witcher sign.\n" +
                " - solve_riddle [answer]: Solve a riddle or puzzle.\n" +
                " - light [item]: Ignite a torch or lantern.\n" +
                " - talk to [character]: Speak with an NPC.\n" +
                " - give [item] [character]: Give an item to an NPC.\n" +
                " - read [book/note]: Read a text-based item.\n" +
                " - unlock [door/chest]: Unlock using a key.\n" +
                " - meditate: Restore health and refill potions.\n" +
                " - help: Display this help menu.\n" +
                " - end/quit: End the main.game.";
    }
}