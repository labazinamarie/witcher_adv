package cz.vse.adv_witcher_labm02.main.game.commands;
import java.util.*;
import cz.vse.adv_witcher_labm02.main.game.*;
/**
 * Třída implementující příkaz pro čtení předmětů.
 * Hráč může použít tento příkaz k přečtení čitelných předmětů
 * nacházejících se v jeho inventáři.
 *
 * @author Mariia Labazina
 * @version ZS-2024, 2025-01-12
 */
public class CommandRead implements ICommand {
    private Game game;

    /**
     * Konstruktor třídy.
     *
     * @param game hra, ve které se bude příkaz používat
     */
    public CommandRead(Game game) {
        this.game = game;
    }

    /**
     * Metoda vrací název příkazu, tj.&nbsp;slovo <b>read</b>.
     *
     * @return název příkazu
     */
    @Override
    public String getName() {
        return "read";
    }

    /**
     * Metoda umožňuje hráči přečíst specifický předmět ve svém inventáři.
     * Zkontroluje, zda byl zadán název předmětu, zda se daný předmět nachází
     * v inventáři a zda je čitelný. Pokud všechny podmínky splňuje, vrátí
     * obsah předmětu. Jinak vrátí příslušné chybové hlášení.
     *
     * @param argumentsCommand parametry příkazu <i>(očekává se název předmětu)</i>
     * @return textový obsah předmětu nebo chybové hlášení
     */
    @Override
    public String run(String[] argumentsCommand) {
        if (argumentsCommand.length == 0) {
            return "You need to specify what you want to read.";
        }

        String itemName = String.join(" ", argumentsCommand);
        Item item = game.getInventory().getItemByName(itemName);

        if (item == null) {
            return "You don't have an item named '" + itemName + "' in your inventory.";
        }

        if (!item.isReadable()) {
            return "You cannot read the " + item.getName() + ".";
        }

        game.notifyInventoryChanged();
        return item.getTextContent();
    }
}