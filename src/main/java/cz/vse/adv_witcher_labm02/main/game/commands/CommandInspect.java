package cz.vse.adv_witcher_labm02.main.game.commands;
import cz.vse.adv_witcher_labm02.main.game.*;
/**
 * Třída implementující příkaz pro inspekci předmětu v aktuální lokaci.
 * Hráč může použít tento příkaz k podrobnému prohlédnutí předmětů,
 * které se nacházejí v jeho okolí.
 *
 * @author Mariia Labazina
 * @version ZS-2024, 2025-01-12
 */
public class CommandInspect implements ICommand {
    private Game game;

    /**
     * Konstruktor třídy.
     *
     * @param game hra, ve které se bude příkaz používat
     */
    public CommandInspect(Game game) {
        this.game = game;
    }

    /**
     * Metoda vrací název příkazu, tj.&nbsp;slovo <b>Inspect</b>.
     *
     * @return název příkazu
     */
    @Override
    public String getName() {
        return "Inspect";
    }

    /**
     * Metoda provádí inspekci předmětu v aktuální lokaci.
     * Nejprve zkontroluje, zda byl zadán název předmětu. Pokud ne, vrátí chybové
     * hlášení. Pokud je název předmětu zadán, pokusí se najít předmět v aktuální
     * lokaci. Pokud je předmět nalezen, vrátí jeho popis. Jinak vrátí zprávu, že
     * předmět nebyl nalezen.
     *
     * @param argumentsCommand parametry příkazu <i>(očekává se název předmětu)</i>
     * @return popis předmětu nebo chybové hlášení
     */
    @Override
    public String run(String[] argumentsCommand) {
        if (argumentsCommand.length == 0) {
            return "You need to specify what you want to look at.";
        }

        String itemName = argumentsCommand[0];
        Location currentLocation = game.getGameWorld().getActualLocation();

        Item item = currentLocation.returnItem(itemName);
        if (item == null) {
            return "There is no '" + itemName + "' here to inspect.";
        }

        return item.getDescription();
    }
}