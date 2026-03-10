package cz.vse.adv_witcher_labm02.main.game.commands;
import java.util.*;
import cz.vse.adv_witcher_labm02.main.game.*;
/**
 * Třída implementující příkaz pro rozhovor s postavou ve hře.
 * Hráč může použít tento příkaz k interakci s NPC postavami v aktuální lokaci.
 *
 * @author Mariia Labazina
 * @version ZS-2024, 2025-01-12
 */
public class CommandTalk implements ICommand {
    private Game game;

    /**
     * Konstruktor třídy.
     *
     * @param game hra, ve které se bude příkaz používat
     */
    public CommandTalk(Game game) {
        this.game = game;
    }

    /**
     * Metoda vrací název příkazu, tj.&nbsp;slova <b>talk to</b>.
     *
     * @return název příkazu
     */
    @Override
    public String getName() {
        return "talk";
    }

    /**
     * Metoda umožňuje hráči zahájit rozhovor s postavou (NPC) v aktuální lokaci.
     * Nejprve zkontroluje, zda byl zadán parametr. Pokud ne, vrátí chybové hlášení.
     * Pokud je zadán parametr, pokusí se najít postavu s odpovídajícím jménem
     * v aktuální lokaci. Pokud je postava nalezena, spustí její dialog. Jinak
     * vrátí zprávu, že postava nebyla nalezena.
     *
     * @param argumentsCommand parametry příkazu <i>(očekává se jméno postavy)</i>
     * @return zpráva s dialogem postavy nebo chybové hlášení
     */
    @Override
    public String run(String[] argumentsCommand) {
        if (argumentsCommand.length == 0) {
            return "You need to specify a character to talk to.";
        }

        String characterName = String.join(" ", argumentsCommand);
        Location currentLocation = game.getGameWorld().getActualLocation();

        for (NPC character : currentLocation.getCharacters()) {
            if (character.getName().equalsIgnoreCase(characterName)) {
                return character.speak();
            }
        }

        return "There is no character named '" + characterName + "' here.";
    }
}