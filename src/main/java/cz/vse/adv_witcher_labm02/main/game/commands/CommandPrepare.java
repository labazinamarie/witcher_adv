package cz.vse.adv_witcher_labm02.main.game.commands;
import java.util.*;
import cz.vse.adv_witcher_labm02.main.game.*;
/**
 * Třída implementující příkaz pro přípravu předmětů.
 * Hráč může použít tento příkaz k přípravě konkrétních předmětů ve svém inventáři,
 * aby je bylo možné použít v dalším průběhu hry.
 *
 * @author Mariia Labazina
 * @version ZS-2024, 2025-01-12
 */
public class CommandPrepare implements ICommand {
    private Game game;

    /**
     * Konstruktor třídy.
     *
     * @param game hra, ve které se bude příkaz používat
     */
    public CommandPrepare(Game game) {
        this.game = game;
    }

    /**
     * Metoda vrací název příkazu, tj.&nbsp;slovo <b>prepare</b>.
     *
     * @return název příkazu
     */
    @Override
    public String getName() {
        return "prepare";
    }

    /**
     * Metoda umožňuje hráči připravit specifický předmět ve svém inventáři.
     * Nejprve zkontroluje, zda byl zadán název předmětu. Poté ověří, zda se
     * předmět nachází v hráčově inventáři. Pokud ano, vrátí zprávu o úspěšné přípravě.
     *
     * @param argumentsCommand parametry příkazu <i>(očekává se název předmětu)</i>
     * @return zpráva potvrzující přípravu předmětu nebo chybové hlášení
     */
    @Override
    public String run(String[] argumentsCommand) {
        if (argumentsCommand.length == 0) {
            return "You need to specify an item to prepare.";
        }

        String itemName = String.join(" ", argumentsCommand);
        Item item = game.getInventory().getItemByName(itemName);

        if (item == null) {
            return "You don't have an item named '" + itemName + "' in your inventory.";
        }

        String preparationEffect = "You prepare the " + item.getName() + " for use.";
        return preparationEffect;
    }
}