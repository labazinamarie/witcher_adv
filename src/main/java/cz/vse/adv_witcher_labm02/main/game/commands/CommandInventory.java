package cz.vse.adv_witcher_labm02.main.game.commands;

import cz.vse.adv_witcher_labm02.main.game.Game;

/**
 * Třída implementující příkaz pro zobrazení inventáře hráče.
 * Hráč může použít tento příkaz k zobrazení všech předmětů, které má momentálně v inventáři.
 *
 * @author Mariia Labazina
 * @version ZS-2024, 2025-01-12
 */
public class CommandInventory implements ICommand {
    private Game game;

    /**
     * Konstruktor třídy.
     *
     * @param game hra, ve které se bude příkaz používat
     */
    public CommandInventory(Game game) {
        this.game = game;
    }

    /**
     * Metoda vrací název příkazu, tj.&nbsp;slovo <b>inventory</b>.
     *
     * @return název příkazu
     */
    @Override
    public String getName() {
        return "inventory";
    }

    /**
     * Metoda zobrazí obsah hráčova inventáře.
     * Nevyžaduje žádné parametry. Vrací textovou reprezentaci všech předmětů
     * aktuálně dostupných v inventáři hráče.
     *
     * @param argumentsCommand parametry příkazu <i>(neočekávají se žádné)</i>
     * @return obsah inventáře ve formě textu
     */
    @Override
    public String run(String[] argumentsCommand) {
        if (game.getInventory().getItems().isEmpty()) {
            return "Your inventory is empty.";
        }
        return game.getInventory().viewInventory();
    }
}