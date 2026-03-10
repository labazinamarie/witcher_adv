package cz.vse.adv_witcher_labm02.main.game.commands;
import java.util.*;
import cz.vse.adv_witcher_labm02.main.game.*;
/**
 * Třída implementující příkaz pro vypití lektvaru.
 * Hráč může použít tento příkaz k vypití lektvaru z inventáře, což může
 * mít různé efekty na jeho zdraví, výdrž nebo jiné atributy.
 *
 * @author Mariia Labazina
 * @version ZS-2024, 2025-01-12
 */
public class CommandDrinkPotion implements ICommand {
    private Game game;

    /**
     * Konstruktor třídy.
     *
     * @param game hra, ve které se bude příkaz používat
     */
    public CommandDrinkPotion(Game game) {
        this.game = game;
    }

    /**
     * Metoda vrací název příkazu, tj.&nbsp;slovo <b>drink</b>.
     *
     * @return název příkazu
     */
    @Override
    public String getName() {
        return "drink";
    }

    /**
     * Metoda umožňuje hráči vypít specifický lektvar z inventáře.
     * Zkontroluje, zda byl zadán název lektvaru a zda se daný lektvar nachází
     * v inventáři. Pokud ano, odebere jej z inventáře a vrátí zprávu
     * o vypití a jeho efektu. Pokud lektvar není nalezen, vrátí chybové hlášení.
     *
     * @param argumentsCommand parametry příkazu <i>(očekává se název lektvaru)</i>
     * @return zpráva potvrzující vypití lektvaru nebo chybové hlášení
     */
    @Override
    public String run(String[] argumentsCommand) {
        if (argumentsCommand.length == 0) {
            return "You need to specify a potion to drink.";
        }

        String potionName = String.join(" ", argumentsCommand);
        Item potion = game.getInventory().getItemByName(potionName);

        if (potion == null) {
            return "You don't have a potion named '" + potionName + "' in your inventory.";
        }

        String effect = potion.getDescription();
        game.getInventory().removeItem(potionName);
        return "You drink the " + potion.getName() + ". " + effect;
    }
}