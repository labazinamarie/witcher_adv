package cz.vse.adv_witcher_labm02.main.game.commands;
import java.util.*;
import cz.vse.adv_witcher_labm02.main.game.*;
/**
 * Třída implementující příkaz pro odhození předmětu.
 * Hráč může použít tento příkaz k odstranění předmětu z inventáře
 * a jeho přidání do aktuální lokace.
 *
 * @author Mariia Labazina
 * @version ZS-2024, 2025-01-12
 */
public class CommandDrop implements ICommand {
    private Game game;

    /**
     * Konstruktor třídy.
     *
     * @param game hra, ve které se bude příkaz používat
     */
    public CommandDrop(Game game) {
        this.game = game;
    }

    /**
     * Metoda vrací název příkazu, tj.&nbsp;slovo <b>drop</b>.
     *
     * @return název příkazu
     */
    @Override
    public String getName() {
        return "drop";
    }

    /**
     * Metoda umožňuje hráči odhodit předmět z inventáře do aktuální lokace.
     * Nejprve zkontroluje, zda byl zadán název předmětu. Pokud ne, vrátí chybové hlášení.
     * Pokud je zadán, zkontroluje, zda hráč má předmět v inventáři. Pokud hráč předmět
     * vlastní, je odstraněn z inventáře a přidán do aktuální lokace.
     *
     * @param argumentsCommand parametry příkazu <i>(očekává se jeden parametr: název předmětu)</i>
     * @return zpráva potvrzující akci nebo chybové hlášení
     */
    @Override
    public String run(String[] argumentsCommand) {
        if (argumentsCommand.length == 0) {
            return "You need to specify what you want to drop.";
        }
        String itemName = argumentsCommand[0];
        Item item = game.getInventory().getItemByName(itemName);
        if (item == null) {
            return "You don't have that item.";
        }

        game.getInventory().removeItem(itemName);
        game.getGameWorld().getActualLocation().addItem(item);
        game.notifyInventoryChanged();
        return "You have dropped the " + item.getName() + ".";
    }
}