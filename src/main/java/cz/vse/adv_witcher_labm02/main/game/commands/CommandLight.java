package cz.vse.adv_witcher_labm02.main.game.commands;
import java.util.*;
import cz.vse.adv_witcher_labm02.main.game.*;
/**
 * Třída implementující příkaz pro zapalování předmětů.
 * Hráč může použít tento příkaz k zapálení určitých předmětů ve svém inventáři,
 * jako jsou pochodně nebo lampy.
 *
 * @author Mariia Labazina
 * @version ZS-2024, 2025-01-12
 */
public class CommandLight implements ICommand {
    private Game game;

    /**
     * Konstruktor třídy.
     *
     * @param game hra, ve které se bude příkaz používat
     */
    public CommandLight(Game game) {
        this.game = game;
    }

    /**
     * Metoda vrací název příkazu, tj.&nbsp;slovo <b>light</b>.
     *
     * @return název příkazu
     */
    @Override
    public String getName() {
        return "light";
    }

    /**
     * Metoda umožňuje hráči zapálit předmět z inventáře.
     * Nejprve zkontroluje, zda byl zadán název předmětu. Poté ověří,
     * zda se daný předmět nachází v hráčově inventáři a zda je možné jej zapálit.
     * Pokud je vše v pořádku, provede zapálení předmětu a vrátí zprávu o úspěchu.
     *
     * @param argumentsCommand parametry příkazu <i>(očekává se název předmětu)</i>
     * @return zpráva potvrzující zapálení předmětu nebo chybové hlášení
     */
    @Override
    public String run(String[] argumentsCommand) {
        if (argumentsCommand.length == 0) {
            return "You need to specify what you want to light.";
        }

        String itemName = String.join(" ", argumentsCommand);
        Item item = game.getInventory().getItemByName(itemName);
        if (item== null) {
            return "You don't have " + itemName + " in your inventory.";
        }

        if (!item.canBeLit()) {
            return "You cannot light " + itemName + ".";
        }
        if(item.isLit()){
            return "The " + itemName + " is already lit.";
        }
        //item.light();
        if (item.getQuantity() > 1){
            item.setQuantity(item.getQuantity() - 1);
        }
        else {
            game.getInventory().removeItem(itemName);
        }
        game.getInventory().addItem(new Item("light source", "light source",
                    true, "it will help you light the way through the darkness",
                    1, true, true,1));

        game.notifyInventoryChanged();
        return "You light the " + itemName + ".";
    }
}