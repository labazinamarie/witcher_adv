package cz.vse.adv_witcher_labm02.main.game.commands;
import java.util.*;
import cz.vse.adv_witcher_labm02.main.game.*;
/**
 * Třída implementující příkaz pro darování předmětu postavě ve hře.
 * Hráč může použít tento příkaz k předání předmětu ze svého inventáře
 * nehratelné postavě (NPC) v aktuální lokaci.
 *
 * @author Mariia Labazina
 * @version ZS-2024, 2025-01-12
 */
public class CommandGive implements ICommand {
    private Game game;

    /**
     * Konstruktor třídy.
     *
     * @param game hra, ve které se bude příkaz používat
     */
    public CommandGive(Game game) {
        this.game = game;
    }

    /**
     * Metoda vrací název příkazu, tj.&nbsp;slovo <b>give</b>.
     *
     * @return název příkazu
     */
    @Override
    public String getName() {
        return "give";
    }

    /**
     * Metoda umožňuje hráči darovat předmět ze svého inventáře NPC postavě
     * v aktuální lokaci. Nejprve zkontroluje počet parametrů. Pokud není zadán
     * dostatek parametrů, vrátí chybové hlášení. Pokud jsou parametry zadány,
     * zkontroluje, zda hráč má specifikovaný předmět v inventáři a zda je v lokaci
     * postava s odpovídajícím jménem. Pokud všechny podmínky splní, předmět je
     * odebrán z inventáře hráče a předán NPC postavě.
     *
     * @param argumentsCommand parametry příkazu <i>(očekávají se dva parametry: název předmětu a jméno postavy)</i>
     * @return zpráva potvrzující akci nebo chybové hlášení
     */
    @Override
    public String run(String[] argumentsCommand) {
        if (argumentsCommand.length < 2) {
            return "You need to specify an item and a character to give it to.";
        }

        String itemName = argumentsCommand[0];
        String characterName = String.join(" ", Arrays.copyOfRange(argumentsCommand, 1, argumentsCommand.length));
        Location currentLocation = game.getGameWorld().getActualLocation();

        Item item = game.getInventory().getItemByName(itemName);
        if (item == null) {
            return "You don't have that item.";
        }

        for (NPC character : currentLocation.getCharacters()) {
            if (character.getName().equalsIgnoreCase(characterName)) {
                character.addPhrase("Thank you for the " + item.getName() + "!");
                game.getInventory().removeItem(itemName);
                return "You have given " + item.getName() + " to " + character.getName() + ".";
            }
        }

        game.notifyInventoryChanged();
        return "There is no character named '" + characterName + "' here.";
    }
}