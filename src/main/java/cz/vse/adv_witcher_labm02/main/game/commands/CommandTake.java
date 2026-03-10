package cz.vse.adv_witcher_labm02.main.game.commands;
import cz.vse.adv_witcher_labm02.main.game.*;
import cz.vse.adv_witcher_labm02.main.start.GameChange;

/**
 * Třída implementující příkaz pro sebrání předmětu z aktuální lokace.
 * Hráč může použít tento příkaz k přidání přenositelného předmětu
 * z lokace do svého inventáře.
 *
 * @author Mariia Labazina
 * @version ZS-2024, 2025-01-12
 */
public class CommandTake implements ICommand {
    private Game game;

    /**
     * Konstruktor třídy.
     *
     * @param game hra, ve které se bude příkaz používat
     */
    public CommandTake(Game game) {
        this.game = game;
    }

    /**
     * Metoda vrací název příkazu, tj.&nbsp;slovo <b>Take</b>.
     *
     * @return název příkazu
     */
    @Override
    public String getName(){
        return "Take";
    }

    /**
     * Metoda umožňuje hráči sebrat předmět z aktuální lokace a přidat jej do inventáře.
     * Nejprve zkontroluje, zda byl zadán název předmětu. Pokud ne, vrátí chybové
     * hlášení. Pokud je název předmětu zadán, pokusí se najít předmět v aktuální
     * lokaci. Pokud je předmět nalezen a je přenosný, přidá jej do inventáře a
     * odstraní jej z lokace. Pokud předmět není nalezen nebo není přenosný,
     * vrátí odpovídající chybové hlášení.
     *
     * @param argumentsCommand parametry příkazu <i>(očekává se název předmětu)</i>
     * @return zpráva potvrzující sebrání předmětu nebo chybové hlášení
     */
    @Override
    public String run(String[] argumentsCommand) {
        if (argumentsCommand.length == 0) {
            return "You need to specify what you want to take.";
        }

        String itemName = String.join(" ", argumentsCommand);

        Location currentLocation = game.getGameWorld().getActualLocation();
        Item item = currentLocation.returnItem(itemName);
        if (item == null) {
            return "There is no '" + itemName + "' here to take.";
        }


        if(!item.isMovable()){
            return "Well, you can't take this, it's heavy";
        }
        if(game.getInventory().isFull()){
            return "You can not take the " + item.getName() + ", because your inventory is full.";
        }


        game.getInventory().addItem(item);
        currentLocation.removeItem(itemName);
        game.notifyInventoryChanged();
        return "You take " + item.getName();

    }
}